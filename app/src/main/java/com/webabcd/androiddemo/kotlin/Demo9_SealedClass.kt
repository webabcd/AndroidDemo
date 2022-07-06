/**
 * 密封类的示例
 */

package com.webabcd.androiddemo.kotlin

// 用 sealed 修饰的类就是密封类
// 密封类不能实例化
sealed class Demo9_SealedClass {
    class OK : Demo9_SealedClass()
    class ERROR : Demo9_SealedClass()
}