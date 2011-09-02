#include "msjava.h"
#include "Log.h"
#include <pthread.h>

static JavaVM *ms2_vm = NULL;

static pthread_key_t jnienv_key;

void _android_key_cleanup(void *data){
	LOGI("Thread end, detaching jvm from current thread");
	JNIEnv* env=(JNIEnv*)pthread_getspecific(jnienv_key);
	if (env != NULL) {
		(*ms2_vm)->DetachCurrentThread(ms2_vm);
		pthread_setspecific(jnienv_key,NULL);
	}
}

void ms_set_jvm(JavaVM *vm){
	LOGI("ms_set_jvm");
	
	ms2_vm=vm;

	//pthread_key_create(&jnienv_key,_android_key_cleanup);
}

JavaVM *ms_get_jvm(void){
	LOGI("ms_get_jvm");
	return ms2_vm;
}

JNIEnv *ms_get_jni_env(void){
	LOGI("ms_get_jni_env");
	
	JNIEnv *env=NULL;
	if (ms2_vm==NULL){
		LOGE("Calling ms_get_jni_env() while no jvm has been set using ms_set_jvm().");
	}else{
		env=(JNIEnv*)pthread_getspecific(jnienv_key);
		if (env==NULL){
			if ((*ms2_vm)->AttachCurrentThread(ms2_vm, &env,NULL)!=0){
				LOGE("AttachCurrentThread() failed !");
				return NULL;
			} else {
				LOGI("AttachCurrentThread() success !");
			}
			pthread_setspecific(jnienv_key,env);
		}
	}
	return env;
}
