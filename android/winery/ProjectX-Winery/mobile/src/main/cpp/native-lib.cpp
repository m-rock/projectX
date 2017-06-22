#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_projectx_projectx_1winery_MyProfile_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "C++ Winery";
    return env->NewStringUTF(hello.c_str());
}
