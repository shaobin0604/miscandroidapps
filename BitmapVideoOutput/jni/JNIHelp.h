#ifndef _NATIVEHELPER_JNIHELP_H
#define _NATIVEHELPER_JNIHELP_H

#include <jni.h>


#ifndef NELEM
#define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#endif

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Register one or more native methods with a particular class.
 */
int jniRegisterNativeMethods(C_JNIEnv* env, const char* className,
    const JNINativeMethod* gMethods, int numMethods);


#ifdef __cplusplus
}
#endif

#endif /*_NATIVEHELPER_JNIHELP_H*/

