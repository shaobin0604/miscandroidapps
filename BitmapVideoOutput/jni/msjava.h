#ifndef msjava_h
#define msjava_h

/* Helper routines for filters that use a jvm with upcalls to perform some processing */

#include <jni.h>

#ifdef __cplusplus
extern "C"{
#endif

void ms_set_jvm(JavaVM *vm);

JavaVM *ms_get_jvm(void);

JNIEnv *ms_get_jni_env(void);

#ifdef __cplusplus
}
#endif


#endif
