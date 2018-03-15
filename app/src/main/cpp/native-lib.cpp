#include <jni.h>
#include <string>

int penis() {
    return 3;
}

extern "C"
JNIEXPORT jstring
JNICALL
Java_com_example_administrator_a1_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    char shit[4];
    sprintf(shit, "%d", penis());
    return env->NewStringUTF(shit);
}
