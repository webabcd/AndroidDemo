# Android Demo


#### kotlin
1. hello world

#### view（基础）
1. 位置相关
2. 边距相关，隐藏相关

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

#### view（媒体类）
1. ImageView 基础
2. ImageView 的 scaleType
3. 9patch（nine patch）图片

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

#### view（ListView）
1. ListView 通过 ArrayAdapter 显示数据
2. ListView 通过 SimpleAdapter 显示数据
3. ListView 通过自定义 BaseAdapter 显示数据
4. ListView 的 item 的点击事件和长按事件
5. ListView 的单选和多选，以及 getView() 的调用时机
6. ListView 的表头，表尾，分隔线，滚动条的显示与隐藏，数据更新与 ListView 刷新，滚动到指定位置，监听 ListView 的滚动状态
7. ListView 的多布局（不同的 item 使用不同的项模板）
8. ListView 滚动到底部加载更多数据

#### view（WebView）
1. WebView 基础
2. WebView 和 javascript 交互
3. WebView 拦截 url 跳转，拦截 alert, confirm, prompt 弹出框，拦截文件选择框
4. WebView 拦截 url 请求并返回自定义数据
5. WebView 通过 post 加载 url，自定义请求 header，获取或设置 cookie

#### view（自定义）
1. 通过一个自定义 View 来演示 measure, layout, draw
2. 通过一个自定义 ViewGroup 来演示 measure, layout, draw
3. 自定义组合控件
4. 自定义控件的自定义属性

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
1. 国际化（多语言）

#### Activity
1. Activity 的生命周期
2. Activity 的横屏和竖屏，以及横竖屏切换与状态保存
3. Activity 之间的跳转和数据传递
4. Activity 之间的跳转动画（单独指定或全局指定）
5. Activity 堆栈
6. Activity 样式（隐藏状态栏；对话框样式）

#### Fragment
1. Fragment 的生命周期
2. Fragment 的动态加载和生命周期，以及 Fragment 的返回堆栈
3. Fragment 与 Activity 的交互
4. Fragment 动画

#### 异步，多线程
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

#### 锁，并发处理
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

#### 服务
1. android 基础示例

#### 设计模式
1. Singleton（单例模式）

#### 工具
1. 生成 README.md