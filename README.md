# Android Demo

### [Flutter Demo](https://github.com/webabcd/FlutterDemo)

#### java
1. Callback
2. 位操作

#### kotlin
1. hello world，kotlin 调用 java，java 调用 kotlin
2. 包的定义和导入，基本数据类型，位操作，变量和常量，注释可嵌套，lateinit，by lazy，字符串模板
3. 可空类型，数据类型判断（is, !is），类型转换，可空类型的相关操作符（let, ?:, ?, !!, as?），== 和 ===
4. 语句（if..else, while, do..while, for, repeat, when, continue, break, return, 遍历 iterator 对象, try/catch/finally, kotlin.runCatching）
5. 字符串的常用操作
6. 数组和集合的常用操作
7. 函数（方法）
8. 类相关 1（基础）
9. 类相关 2（类继承，接口，抽象类，by 委托）
10. 类相关 3（枚举，密封类，数据类）
11. Lambda 表达式，高阶函数
12. 泛型
13. let, also, with, run, runCatching, apply

#### kotlin 协程
1. 协程基础（CoroutineScope, 为 CoroutineScope 扩展方法, runBlocking, launch, async, await, suspend, withContext, 设置/获取 CoroutineScope 的名称）
2. Job 的等待与取消，超时处理，取消协程
3. 协程的顺序执行，并行执行，async 的立即执行与懒启动，以及 async/await 的其他说明
4. Channel（信道，用于在不同协程之间传输数据）
5. 通过 ticker 信道实现类似计时器的效果，协程的异常处理，解决协程的并发问题
6. Flow（异步流，通过 flow 发送和接收数据，flow 的超时处理，取消处理，异常处理，重试处理，指定 flow 阶段的运行协程使其不同于 collect 阶段的运行协程，让 collect 阶段运行到其他协程从而不阻塞当前协程）
7. Flow（异步流，各种操作符的使用 buffer, conflate, collectLatest, drop, take, filter, map, transform, onEach, first, last, single, reduce, zip, combine, flatMapConcat, flatMapMerge 等)

#### jetpack
1. Lifecycle 基础以及 lifecycleScope
2. ViewModel 基础以及 viewModelScope
3. LiveData 基础，以及 LiveData 和 ViewModel 结合使用
4. LiveData 指定的对象的某个属性发生了变化时通知给观察者
5. DataBinding（MVVM）

#### view（基础）
1. 位置相关
2. 边距相关，隐藏相关
3. 剪裁

#### view（文本类）
1. TextView 常用属性
2. TextView 字体相关
3. TextView 阴影和图文
4. TextView 的 html 支持
5. TextView 的 Spannable（自定义显示样式）
6. TextView 常用行为
7. EditText 常用属性
8. EditText 选中和光标
9. EditText 软键盘
10. AutoCompleteTextView 基础
11. MultiAutoCompleteTextView 基础

#### view（按钮类）
1. Button 响应单击事件的方法
2. Button 样式
3. ImageButton 图片按钮

#### view（布局类）
1. FrameLayout 叠加布局
2. LinearLayout 线性布局
3. RelativeLayout 相对布局
4. TableLayout 表格布局
5. GridLayout 网格布局
6. ConstraintLayout 约束布局（基础）
7. ConstraintLayout 约束布局控件（链）
8. ConstraintLayout 约束布局控件（屏障/分组/占位）
9. ScrollView 滚动容器
10. 通过 include 静态加载布局文件
11. 通过 inflate 动态加载布局文件
12. setContentView() 和 addContentView()

#### view（导航类）
1. ToolBar 基础
2. ToolBar 显示自定义 view，清除自定义 view 与 Toolbar 两侧的间距，自定义弹出的 OptionMenu 的样式
3. TabBar 自己实现
4. DrawerLayout 基础

#### view（媒体类）
1. ImageView 基础
2. ImageView 的 scaleType
3. 9patch（nine patch）图片
4. Picasso 基础
5. Glide 基础
6. 截图
7. MediaPlayer（在 SurfaceView 上播放）
8. MediaPlayer（在 TextureView 上播放，可截图）
9. MediaPlayer（视频中插广告）

#### view（进度类）
1. ProgressBar 基础
2. ProgressBar 样式
3. SeekBar 基础
4. SeekBar 样式
5. RatingBar 基础
6. RatingBar 样式

#### view（选择类）
1. RadioButton 基础
2. RadioButton 样式
3. CheckBox 基础
4. CheckBox 样式
5. ToggleButton 基础
6. ToggleButton 样式
7. Switch 基础
8. Switch 样式
9. NumberPicker 基础
10. NumberPicker 样式
11. Spinner 基础
12. Spinner 样式
13. Spinner 通过 ArrayAdapter 显示数据
14. Spinner 通过 SimpleAdapter 显示数据
15. Spinner 通过自定义 BaseAdapter 显示数据

#### view（弹出类）
1. AlertDialog 基础
2. AlertDialog 自定义
3. AlertDialog 大小、位置和动画
4. AlertDialog 样式
5. ProgressDialog 基础
6. DatePickerDialog 基础
7. TimePickerDialog 基础
8. PopupWindow 基础
9. PopupMenu 基础
10. PopupMenu 样式
11. ContextMenu 基础
12. ContextMenu 样式

#### view（集合类）
1. ViewFlipper 基础
2. ViewPager 基础 1
3. ViewPager 基础 2
4. ViewPager 和 FragmentPagerAdapter
5. ViewPager 和 FragmentStatePagerAdapter
6. GridView 基础 1
7. GridView 基础 2
8. ExpandableListView 基础

#### view（ListView）
1. ListView 通过 ArrayAdapter 显示数据
2. ListView 通过 SimpleAdapter 显示数据
3. ListView 通过自定义 BaseAdapter 显示数据（同时演示如何通过 convertView 复用的方式提高效率，以及 getView() 的调用时机）
4. ListView 的 item 的点击事件和长按事件
5. ListView 的单选和多选
6. ListView 的表头，表尾，分隔线，滚动条的显示与隐藏，数据更新与 ListView 刷新，滚动到指定位置，监听 ListView 的滚动状态
7. ListView 的多布局（不同的 item 使用不同的项模板）
8. ListView 滚动到底部加载更多数据
9. ListView 长按弹出上下文菜单
10. ListView 多选删除

#### view（RecyclerView）
1. RecyclerView 基础，各种布局方式（垂直布局，水平布局，标准网格布局，错列网格布局），响应单击事件和长按事件，不同的 item 使用不同的项模板，表头和表尾
2. RecyclerView 分隔线
3. RecyclerView 下拉刷新（结合 SwipeRefreshLayout 控件实现）
4. RecyclerView 上拉加载更多数据

#### view（WebView）
1. WebView 基础，WebView 调试
2. WebView 和 javascript 交互
3. WebView 拦截 url 跳转，拦截 alert, confirm, prompt 弹出框，拦截文件选择框
4. WebView 拦截 url 请求并返回自定义数据
5. WebView 通过 post 加载 url，自定义请求 header，获取或设置 cookie

#### view（自定义）
1. 通过一个自定义 View 来演示 measure, layout, draw
2. 通过一个自定义 ViewGroup 来演示 measure, layout, draw
3. 自定义组合控件
4. 自定义控件的自定义属性
5. 自定义圆形带进度提示的 loading 控件

#### UI
1. 屏幕密度（物理分辨率, 逻辑分辨率, density, dpi, drawable 文件夹, mipmap 文件夹, dp, sp, px）
2. 样式简介，自定义样式，动态更换样式
3. 主题简介，继承主题并重写其中的一些样式，指定主题
4. 自定义主题，动态更换主题
5. 通过主题修改控件的默认样式
6. 颜色和不透明度
7. shape 渐变色（线性渐变，放射性渐变，扫描式渐变）
8. shape 之填充，描边，圆角，尺寸，内部间距
9. shape 形状（矩形，圆形，环形，直线）
10. 图标
11. 状态栏（statusBar）
12. 导航栏（navigationBar）
13. 沉浸式（immersive）
14. 沉浸式（关于 statusBar 和 navigationBar 的常用效果）
15. 监听配置变化（比如横竖屏切换等）
16. 闪屏页（启动页）

#### Animation
1. Matrix 变换（用于做位移，旋转，缩放，扭曲等变换）
2. Matrix 变换（通过自定义控件实现）
3. 视图动画（View Animation）基础
4. 视图动画（View Animation）插值器（Interpolator）
5. 视图动画（View Animation）自定义 Interpolator
6. 帧动画（Drawable Animation）
7. 属性动画（Property Animation）中的 ValueAnimator
8. 属性动画（Property Animation）中的 ObjectAnimator
9. 属性动画（Property Animation）中的 ViewPropertyAnimator

#### Resource
1. 布局 xml 基础
2. 国际化（多语言）
3. 读取 meta-data 数据

#### 存储
1. 通过 context 操作 files 目录中的文件
2. 文件和文件夹操作（通过 File 对象）
3. 内部存储，外部存储，权限请求，存储大小，获取 assets 中的数据，获取 res/raw 中的数据
4. SharedPreferences 用户偏好数据的管理
5. Sqlite 基础
6. Android 11 使用外部存储
7. Android 11 通过 MediaStore 管理文件
8. Android 11 通过 Storage Access Framework 管理文件

#### 输入
1. 按键事件
2. Touch 基础（点击，双击，长按；触摸按下，触摸移动，触摸抬起）
3. Touch 基础（触摸位置，事件冒泡）
4. Touch 多点触摸（单点拖拽，两点缩放）
5. Touch 在自定义控件中处理触摸事件；处理 Activity 的触摸事件
6. Touch 简单的涂鸦板
7. Gesture 手势检测基础
8. Gesture 添加手势（向手势库中添加自定义手势）
9. Gesture 识别手势（遍历手势库中的手势，通过逐一比对来识别当前手势）

#### Activity
1. Activity 的生命周期，监听返回键，监听当前 activity 的离开事件
2. Activity 的横屏和竖屏，以及横竖屏切换与状态保存
3. Activity 之间的跳转和数据传递
4. Activity 之间的跳转动画（单独指定或全局指定）
5. Activity 堆栈
6. Activity 样式（隐藏状态栏；改变状态栏颜色；对话框样式的 activity）

#### Fragment
1. Fragment 的生命周期
2. Fragment 的动态加载和生命周期，以及 Fragment 的返回堆栈
3. Fragment 与 Activity 的交互
4. Fragment 动画

#### 异步和多线程
1. Thread 基础
2. Thread 演示 Object 的 wait() notify() notifyAll() 的使用
3. Thread 演示 join() 的用法
4. Thread 演示 interrupt() 的用法
5. Thread 的异常处理
6. Thread 导致的内存泄漏
7. Handler 的使用
8. Handler 和 Looper 的使用
9. Handler 导致的 Activity 内存泄漏
10. ThreadPool 的基础
11. ThreadPool 的关闭
12. Callable, Future, FutureTask 的使用
13. Future, FutureTask 的关闭和异常处理
14. AsyncTask 的使用
15. Timer 和 TimerTask 的使用
16. ThreadLocal 的使用

#### 锁和并发处理
1. Lock 基础
2. Lock 可 interrupt 的 Lock
3. Lock 演示 Condition 的 await() signal() signalAll() 的使用
4. Lock 演示 ReentrantLock 的使用
5. synchronized 锁方法
6. synchronized 锁代码块
7. ReadWriteLock 基础
8. ReadWriteLock 演示 ReentrantReadWriteLock 的使用
9. volatile 与原子性, 可见性, 有序性
10. CountDownLatch 信号数量
11. CyclicBarrier 屏障
12. Semaphore 许可证中心
13. atomic 原子操作

#### Notification
1. Toast
2. Notification
3. 自定义 ui 的 Notification

#### 后台服务
1. Service
2. WorkerManager
3. DownloadManager

#### ipc（跨进程通信）
1. ContentProvider
2. URLScheme(deep link)
3. 通过指定 package, activity 打开指定的 apk
4. 发送广播（静态注册广播接收器）
5. 发送广播（动态注册广播接收器）
6. 发送广播（有序广播）
7. Clipboard
8. 分享

#### 优化
1. 强引用, 软引用, 弱引用
2. Context
3. Application
4. 捕获未处理异常

#### 常用工具
1. 在 KeyStore 中保存秘钥
2. 获取唯一标识
3. 监听 logcat 日志

#### 设计模式
1. Singleton（单例模式）

#### Native Development Kit
1. NDK 简单示例

#### 项目工具
1. 生成 README.md
2. 生成 html index

