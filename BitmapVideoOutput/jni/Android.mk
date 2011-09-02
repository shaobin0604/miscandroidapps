LOCAL_PATH := $(call my-dir)

#declare the prebuilt library
include $(CLEAR_VARS)
LOCAL_MODULE := ffmpeg-prebuilt
LOCAL_SRC_FILES := ffmpeg-0.8/android/armv5te/libffmpeg.so
LOCAL_EXPORT_C_INCLUDES := ffmpeg-0.8/android/armv5te/include
LOCAL_EXPORT_LDLIBS := ffmpeg-0.8/android/armv5te/libffmpeg.so
LOCAL_PRELINK_MODULE := true
include $(PREBUILT_SHARED_LIBRARY)


include $(CLEAR_VARS)
LOCAL_MODULE    := android_display
LOCAL_ALLOW_UNDEFINED_SYMBOLS:=false
LOCAL_SRC_FILES := JNIHelp.c android_display2.c msjava.c
LOCAL_C_INCLUDES := $(LOCAL_PATH)/ffmpeg-0.8/android/armv5te/include
LOCAL_SHARED_LIBRARY := ffmpeg-prebuilt
LOCAL_LDLIBS    := -lm -llog -ljnigraphics -lm $(LOCAL_PATH)/ffmpeg-0.8/android/armv5te/libffmpeg.so
include $(BUILD_SHARED_LIBRARY)


#include $(CLEAR_VARS)
#LOCAL_MODULE    := tutorial01
#LOCAL_ALLOW_UNDEFINED_SYMBOLS:=false
#LOCAL_SRC_FILES := tutorial01.c
#LOCAL_C_INCLUDES := $(LOCAL_PATH)/ffmpeg-0.8/android/armv6/include
#LOCAL_SHARED_LIBRARY := ffmpeg-prebuilt
#LOCAL_LDLIBS    := -lm -llog -lm $(LOCAL_PATH)/ffmpeg-0.8/android/armv6/libffmpeg.so
#include $(BUILD_EXECUTABLE)


