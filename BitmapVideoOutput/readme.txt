1. ffmpeg 针对不同cpu环境（ armv5te armv6 ...）有汇编级的优化，如果编译的版本和运行的平台不一至，很可能导致崩溃
http://stackoverflow.com/questions/5821695/enable-armv6-ffmpeg-crashed-under-android-ndk

2. ndk crash 分析
http://hi.baidu.com/nalch/blog/item/f6a1ed6aec69d2cd80cb4a59.html
http://stackoverflow.com/questions/5314036/how-to-use-addr2line-in-android


3. JNI WARNING: 0x659 is not a valid JNI reference
http://stackoverflow.com/questions/3628412/error-in-android-application

4. native pthread 线程中使用 JNIEnv

