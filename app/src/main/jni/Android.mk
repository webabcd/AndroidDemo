LOCAL_PATH := $(call my-dir)

#模块1
include $(CLEAR_VARS) #清除 LOCAL_MODULE, LOCAL_SRC_FILES 之类的变量
LOCAL_CPP_EXTENSION := .cpp # C++ 文件的扩展名
LOCAL_MODULE := jniHello # 模块名。如果模块名为“abc”，则此模块将会生成“libabc.so”文件。
LOCAL_SRC_FILES := cHello.c # 需要编译的源文件
include $(BUILD_SHARED_LIBRARY) # 编译当前模块

#模块2