#include "cHello.h"
#include <stdlib.h>

JNIEXPORT jstring JNICALL Java_com_webabcd_androiddemo_ndk_NdkDemo1_helloJniC(JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env, "hello: ndk");
}
