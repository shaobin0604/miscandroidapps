/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <jni.h>
#include <time.h>
#include <android/bitmap.h>
#include <dlfcn.h>
#include <assert.h>
#include <pthread.h>

#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libswscale/swscale.h>

#include "JNIHelp.h"
#include "Log.h"
#include "msjava.h"

/* Set to 1 to enable debug log traces. */
#define DEBUG 0

typedef unsigned char bool_t;
#undef TRUE
#undef FALSE
#define TRUE 1
#define FALSE 0

typedef struct MSPicture {
    int w;
    int h;
    uint8_t* planes[4];
    int strides[4];
} MSPicture;

typedef struct MSVideoSize {
    int width,height;
} MSVideoSize;

typedef struct MSRect {
    int x,y,w,h;
} MSRect;

typedef struct PacketQueue {
    AVPacketList *first_node, *last_node;
    int nb_packets;
    int size;
    pthread_mutex_t mutex;
    pthread_cond_t cond;
} PacketQueue;

typedef struct AndroidDisplay {
    jobject android_video_window;
    jobject jbitmap;
    jmethodID get_bitmap_id;
    jmethodID update_id;
    jmethodID request_orientation_id;
    AndroidBitmapInfo bmpinfo;
    bool_t orientation_change_pending;
    struct SwsContext *sws;
    
    PacketQueue queue;
    int quit;
} AndroidDisplay;

static AndroidDisplay display;
static const char* const kClassPathName = "cn/yo2/aquarium/example/bitmapvideooutput/AndroidVideoWindowImpl";

static int (*sym_AndroidBitmap_getInfo)(JNIEnv *env,jobject bitmap, AndroidBitmapInfo *bmpinfo)=NULL;
static int (*sym_AndroidBitmap_lockPixels)(JNIEnv *env, jobject bitmap, void **pixels)=NULL;
static int (*sym_AndroidBitmap_unlockPixels)(JNIEnv *env, jobject bitmap)=NULL;



static void packet_queue_init(PacketQueue *q) {
    memset(q, 0, sizeof(PacketQueue));
    pthread_mutex_init(&q->mutex, NULL);
    pthread_cond_init(&q->cond, NULL);
}

static int packet_queue_put(PacketQueue *q, AVPacketList *node) {
    if (!node) {
        LOGE("node cannot be NULL");
        return -1;
    }
    
    pthread_mutex_lock(&q->mutex);
    {
        if (!q->last_node)
            q->first_node = node;
        else
            q->last_node->next = node;
        q->last_node = node;
        q->nb_packets++;
        q->size += node->pkt.size;
    }
    pthread_mutex_unlock(&q->mutex);
    
    pthread_cond_signal(&q->cond);
    
    return 0;
} 
/*
static int packet_queue_put(PacketQueue *q, AVPacket *pkt) {

    AVPacketList *pkt1;
    if(av_dup_packet(pkt) < 0) {
        return -1;
    }
    pkt1 = av_malloc(sizeof(AVPacketList));
    if (!pkt1)
        return -1;
    pkt1->pkt = *pkt;
    pkt1->next = NULL;


    pthread_mutex_lock(&q->mutex);
    {
        if (!q->last_pkt)
            q->first_pkt = pkt1;
        else
            q->last_pkt->next = pkt1;
        q->last_pkt = pkt1;
        q->nb_packets++;
        q->size += pkt1->pkt.size;
    }
    pthread_mutex_unlock(&q->mutex);
    
    pthread_cond_signal(&q->cond);
    return 0;
}
*/
static int packet_queue_get(PacketQueue *q, AVPacketList **node, int block)
{
    AVPacketList *first_node;
    int ret;

    pthread_mutex_lock(&q->mutex);

    for(;;) {

        if(display.quit) {
            ret = -1;
            break;
        }

        first_node = q->first_node;
        if (first_node) {
            q->first_node = first_node->next;
            if (!q->first_node)
                q->last_node = NULL;
            q->nb_packets--;
            q->size -= first_node->pkt.size;
            *node = first_node;
            ret = 1;
            break;
        } else if (!block) {
            ret = 0;
            break;
        } else {
            pthread_cond_wait(&q->cond, &q->mutex);
        }
    }
    pthread_mutex_unlock(&q->mutex);
    return ret;
}
/*
static int packet_queue_get(PacketQueue *q, AVPacket *pkt, int block)
{
    AVPacketList *pkt1;
    int ret;

    pthread_mutex_lock(&q->mutex);

    for(;;) {

        if(display.quit) {
            ret = -1;
            break;
        }

        pkt1 = q->first_pkt;
        if (pkt1) {
            q->first_pkt = pkt1->next;
            if (!q->first_pkt)
                q->last_pkt = NULL;
            q->nb_packets--;
            q->size -= pkt1->pkt.size;
            *pkt = pkt1->pkt;
            av_free(pkt1);
            ret = 1;
            break;
        } else if (!block) {
            ret = 0;
            break;
        } else {
            pthread_cond_wait(&q->cond, &q->mutex);
        }
    }
    pthread_mutex_unlock(&q->mutex);
    return ret;
}
*/

/* Return current time in milliseconds */
static uint64_t now_ms(void)
{
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return tv.tv_sec*1000 + tv.tv_usec/1000;
}

static int android_display_init(JNIEnv *jenv) {
    jclass wc;

    wc=(*jenv)->FindClass(jenv, kClassPathName);
    if (wc==0) {
        LOGE("Could not find class %s!", kClassPathName);
        return -1;
    }
    display.get_bitmap_id=(*jenv)->GetMethodID(jenv,wc,"getBitmap", "()Landroid/graphics/Bitmap;");
    display.update_id=(*jenv)->GetMethodID(jenv,wc,"update","()V");
    display.request_orientation_id=(*jenv)->GetMethodID(jenv,wc,"requestOrientation","(I)V");

    return 0;
}

static int android_display_uninit(JNIEnv *jenv) {

}

static void android_display_process(JNIEnv* jenv, AVFrame *pFrame) {
    void *pixels=NULL;

    MSPicture dest = {0};

    if (display.jbitmap == NULL) {
        LOGE("No Bitmap to render!");
        return;
    }

    dest.w = display.bmpinfo.width;
    dest.h = display.bmpinfo.height;

    AVCodecContext *pCodecCtx = pFrame->owner;

    if (display.sws == NULL) {
        display.sws = sws_getContext(pCodecCtx->width, pCodecCtx->height, pCodecCtx->pix_fmt,
                                     pCodecCtx->width, pCodecCtx->height, PIX_FMT_RGB565,
                                     SWS_FAST_BILINEAR, NULL, NULL, NULL);// other codes

        if (display.sws == NULL) {
            LOGE("Cannot create img_convert_ctx");
            return;
        }
    }

    if (sym_AndroidBitmap_lockPixels(jenv,display.jbitmap,&pixels)==0) {
        if (pixels!=NULL) {
            dest.planes[0]=(uint8_t*)pixels;
            dest.strides[0]=display.bmpinfo.stride;
            
            sws_scale(display.sws, (const uint8_t* const)pFrame->data, pFrame->linesize, 0, pCodecCtx->height, dest.planes, dest.strides);
            
            sym_AndroidBitmap_unlockPixels(jenv,display.jbitmap);
        
            (*jenv)->CallVoidMethod(jenv,display.android_video_window,display.update_id);
        } else {
            LOGW("Pixels==NULL in android bitmap !");
        }
    } else {
        LOGE("AndroidBitmap_lockPixels() failed !");
    }
}


static int android_display_set_window(JNIEnv *jenv, jobject thiz, jobject weak_thiz) {
    LOGD("android_display_set_window -->");
    int err;
    
    
    if (weak_thiz != NULL) {
        display.android_video_window = (*jenv)->NewGlobalRef(jenv, weak_thiz);
        
        jobject bitmap = (*jenv)->CallObjectMethod(jenv, thiz, display.get_bitmap_id);
        display.jbitmap = (*jenv)->NewGlobalRef(jenv, bitmap);
    } else {
        (*jenv)->DeleteGlobalRef(jenv, display.android_video_window);
        display.android_video_window = NULL;
        
        (*jenv)->DeleteGlobalRef(jenv, display.jbitmap);
        display.jbitmap=NULL;
    }

    if (display.jbitmap!=NULL) {
        err=sym_AndroidBitmap_getInfo(jenv,display.jbitmap,&display.bmpinfo);
        if (err!=0) {
            LOGE("AndroidBitmap_getInfo() failed.");
            display.jbitmap=0;

            return -1;
        }
    }

    if (display.sws) {
        sws_freeContext(display.sws);
        display.sws=NULL;
    }

    display.orientation_change_pending=FALSE;

    if (display.jbitmap!=NULL)
        LOGD("New java bitmap given with w=%i,h=%i,stride=%i,format=%i",
             display.bmpinfo.width,display.bmpinfo.height,display.bmpinfo.stride,display.bmpinfo.format);
    return 0;

}

bool_t libmsandroiddisplay_init(void) {
    /*See if we can use AndroidBitmap_* symbols (only since android 2.2 normally)*/
    void *handle=NULL;
    handle=dlopen("libjnigraphics.so",RTLD_LAZY);
    if (handle!=NULL) {
        sym_AndroidBitmap_getInfo=dlsym(handle,"AndroidBitmap_getInfo");
        sym_AndroidBitmap_lockPixels=dlsym(handle,"AndroidBitmap_lockPixels");
        sym_AndroidBitmap_unlockPixels=dlsym(handle,"AndroidBitmap_unlockPixels");

        if (sym_AndroidBitmap_getInfo==NULL || sym_AndroidBitmap_lockPixels==NULL
                || sym_AndroidBitmap_unlockPixels==NULL) {
            LOGE("AndroidBitmap not available.");
        } else {
            LOGI("MSAndroidDisplay registered.");
            return TRUE;
        }
    } else {
        LOGE("libjnigraphics.so cannot be loaded.");
    }
    return FALSE;
}

static void* play_thread(void* arg) {
    LOGI("play_thread -----> enter");
    
    int             i;
    AVCodecContext  *pCodecCtx;
    AVCodec         *pCodec;
    AVFrame         *pFrame;
    int             frameFinished;
    
    display.quit = 0;
    packet_queue_init(&display.queue);

    uint64_t start_time = 0;
    uint64_t end_time = 0;
    
    avcodec_init();

    // Register all formats and codecs
    avcodec_register_all();

    // Find the decoder for the video stream
    pCodec = avcodec_find_decoder(CODEC_ID_H263);
    
    if(pCodec == NULL) {
        LOGE("Unsupported codec!");
        goto close_codec; // Codec not found
    }
    
    // Open codec
    if(avcodec_open(pCodecCtx, pCodec) < 0) {
        LOGE("Could not open video codec");
        goto close_codec; // Could not open codec
    }

    // Allocate video frame
    pFrame = avcodec_alloc_frame();

    // Read frames and save first five frames to disk
    i = 0;

    LOGD("setup done, ready to read frame");

    JavaVM *jvm = ms_get_jvm();
    
    if (jvm == NULL) {
        LOGE("Cannot find JavaVM, abort");
        goto close_avframe;
    }
    
    JNIEnv *jenv = NULL;
    
    if ((*jvm)->AttachCurrentThread(jvm, &jenv,NULL) != 0){
        LOGE("AttachCurrentThread() failed !");
        return;
    } else {
        LOGI("AttachCurrentThread() success !");
    }
    
    AVPacketList **node;
    
    while(packet_queue_get(&display.queue, node, 1)>=0) {
        LOGD("packet_queue_get");
            
        start_time = now_ms();
        avcodec_decode_video2(pCodecCtx, pFrame, &frameFinished, &(*node)->pkt);
        end_time = now_ms();
            
        LOGI("decode cost time = %llu", end_time - start_time);
        
        // Did we get a video frame?
        if(frameFinished) {
            LOGI("decode frame success!");
                
            start_time = now_ms();
            android_display_process(jenv, pFrame);
            end_time = now_ms();
            
            LOGI("render cost time = %llu", end_time - start_time);
        } else {
            LOGI("decode need next frame...");
                
        }
        
        // Free the packet that was allocated by av_read_frame
        LOGD("av_free_packet");
        av_free_packet(&(*node)->pkt);
        
        LOGD("av_free node");
        av_free(*node);
    }

detach_jvm:
    // Detach current thread from JVM
    LOGI("detaching jvm from current thread");
    (*jvm)->DetachCurrentThread(jvm);

close_sws:
    // Free the Sw context
    sws_freeContext(display.sws);

close_avframe:
    // Free the YUV frame
    av_free(pFrame);

close_codec:
    // Close the codec
    avcodec_close(pCodecCtx);
}

static void start_play_thread(JNIEnv *jenv, jobject thiz, jstring path) {
    LOGI("start_play_thread ----->");
    const char *pathStr = (*jenv)->GetStringUTFChars(jenv, path, NULL);

    LOGD("pathStr = %s", pathStr);

    pthread_t mThread;
    pthread_create(&mThread, NULL, play_thread, (void *)pathStr);
    (*jenv)->ReleaseStringUTFChars(jenv, path, pathStr);
}

static void stop_play_thread(JNIEnv *jenv, jobject thiz) {
    LOGI("stop_play_thread ----->");
    
    if (display.quit) {
        return;
    }
    
    display.quit = 1;
    
    LOGD("signal play thread");
    pthread_cond_signal(&display.queue.cond);
}

/**
 * Write h263 raw frame to buffer. This method is called in RTP video receiver thread.
 * 
 */
static int write_h263_frame(JNIEnv *jenv, jobject thiz, jbyteArray packet, jint len) {
    if ((*jenv)->GetArrayLength(jenv, packet) < len) {
        LOGE("IllegalArgument len too large");
        return -1;
    }
    
    
    AVPacketList *pkt_node = av_malloc(sizeof(AVPacketList));
    av_new_packet(&pkt_node->pkt, len);
    pkt_node->next = NULL;
    
    (*jenv)->GetByteArrayRegion(jenv, packet, 0, len, pkt_node->pkt.data);
    
    // Producer: put the h263 frame to queue, the memory is allocated on heap, the frame consumer should free the memory 
    return packet_queue_put(&display.queue, pkt_node);
}


static JNINativeMethod gMethods[] = {
    {"writeH263Frame",      "(LBI)I",                       (void *)write_h263_frame},
    {"startPlayThread",     "(Ljava/lang/String;)V",        (void *)start_play_thread},
    {"stopPlayThread",      "()V",                          (void *)stop_play_thread},
    {"setViewWindowId",     "(Ljava/lang/Object;)V",        (void *)android_display_set_window},
};

int register_android_media_FFMpegPlayerAndroid(JNIEnv *jenv) {
    return jniRegisterNativeMethods(jenv, kClassPathName, gMethods, sizeof(gMethods) / sizeof(gMethods[0]));
}

jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv* env = NULL;
    jint result = -1;

    LOGI("Enter JNI_OnLoad.....");

    if ((*vm)->GetEnv(vm, (void**) &env, JNI_VERSION_1_4) != JNI_OK)
    {
        LOGE("ERROR: GetEnv failed");
        return -1;
    }

    assert(env != NULL);

    ms_set_jvm(vm);
    
    LOGD("Init AndroidDisplay");
    memset(&display, 0, sizeof(AndroidDisplay));

    if (libmsandroiddisplay_init() == FALSE) {
        LOGE("Error: libmsandroiddisplay_init fail!");
        return -1;
    }

    if (android_display_init(env) < 0) {
        LOGE("Error: android_display_init fail!");
        return -1;
    }

    if (register_android_media_FFMpegPlayerAndroid(env) < 0) {
        LOGE("Error: register_android_media_FFMpegPlayerAndroid fail!");
        return -1;
    }

    return JNI_VERSION_1_4;
}

