/**
 * Activity 堆栈
 * 系统通过堆栈来管理多个 activity，第一个进栈的叫做栈底，最后一个进栈的叫做栈顶，后进先出，这个堆栈叫做 task，通过 activity 对象的 getTaskId() 方法可以获取当前 activity 所属的 task 的标识
 * 在 AndroidManifest.xml 中设置 activity 的 taskAffinity 属性（格式是 xxx.xxx.xxx 的形式），用于将同 taskAffinity 的 activity 分组到一个 task 中
 * 在系统的最近程序列表中显示的不是 app 记录，而是 task 记录，若要 taskAffinity 生效，还需要设置 Intent.FLAG_ACTIVITY_NEW_TASK 或者 allowTaskReparenting，说明如下：
 * 1、通过 startActivity() 启动 activity 的时候指定 Intent.FLAG_ACTIVITY_NEW_TASK  可以使 taskAffinity 生效
 *    需要启动的 activity 的 taskAffinity 和当前的 activity 所属的 taskAffinity 不一样，则会新创建一个 task 来启动这个 activity
 *    需要启动的 activity 的 taskAffinity 和当前的 activity 所属的 taskAffinity 一样，则不会启动这个 activity
 *    注：如果没有 Intent.FLAG_ACTIVITY_NEW_TASK，则 taskAffinity 属性无效，新启动的 activity 会压入当前的 activity 所属的 task
 * 2、通过在 AndroidManifest.xml 中设置 activity 节点的 allowTaskReparenting 为 true 可以使 taskAffinity 生效
 *    允许重定义 activity 的父级，跨 app 场景使用
 *    当 activity 被一个外部应用启动时，此 activity 就会被放入启动它的 task1 中
 *    如果之后此 activity 的原来本身应用的同 taskAffinity 的 task2 启动了它，则此 activity 会从 task1 中移出，并压入到 task2 的栈顶
 * 通过 startActivity() 启动 activity 的时候，如果是复用堆栈中已有 activity 的话，则会调用该 activity 的 onNewIntent() 方法
 *
 *
 * 通过 startActivity() 启动 activity 的时候可以指定 intent 的 flags，挑几个常用的说明如下
 * 1、Intent.FLAG_ACTIVITY_NO_HISTORY - 不在堆栈记录中保存，当 back 到此 activity 时会自动调用其 onDestroy() 方法
 * 2、Intent.FLAG_ACTIVITY_CLEAR_TOP - 堆栈里如果有此 activity，则会将离栈顶最近的此 activity 和其上的全部 activity 移出堆栈，然后新建此 activity 并压入堆栈
 * 3、Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP - 堆栈里如果有此 activity，则会将离栈顶最近的此 activity 之上的全部 activity 移出堆栈，然后调用此 activity 的 onNewIntent() 方法
 * 4、Intent.FLAG_ACTIVITY_NEW_TASK - 结合 taskAffinity 使用，参见上面的说明
 *
 *
 * 有一个使用场景，就是 task 进入后台（比如按了 home 键），然后再回到前台（比如点击最近列表里的项，或点击 app 图标）
 * 对于此种场景可以通过在 AndroidManifest.xml 中设置 activity 节点的一些属性来控制 task 堆栈的一些行为，说明如下
 * 1、alwaysRetainTaskState - 如果在后台时间过长，再回来时，系统可能会将除了栈底之外的所有 activity 全部清除。而如果将栈底的 activity 的此属性标记为 true 的话，则可以避免这个情况的发生
 * 2、clearTaskOnLaunch - 如果栈底 activity 被标记为 true，则返回前台后，堆栈中除了栈底外的其他 activity 都将会被销毁（会调用这些 activity 的 onDestroy()）
 *    注：我这里测试，此属性只有通过点击 app 图标返回前台时有效，而通过点击最近列表里的项返回前台时无效
 * 3、finishOnTaskLaunch - 返回前台后，标记为 true 的 activity 将会被销毁（会调用这些 activity 的 onDestroy()）
 *    注：我这里测试，此属性只有通过点击 app 图标返回前台时有效，而通过点击最近列表里的项返回前台时无效
 *
 *
 * 在 AndroidManifest.xml 中设置 activity 节点的 launchMode 属性（standard, singleTop, singleTask, singleInstance），分别说明如下：
 * 1、standard - 默认值
 *    比如当前堆栈为 A1 -> B1，然后通过 startActivity() 打开 B，则堆栈结果为 A1 -> B1 -> B2
 * 2、singleTop - 如果通过 startActivity() 打开的 activity 在堆栈的栈顶的话，则复用此栈顶实例，会调用其 onNewIntent() 方法
 *    比如当前堆栈为 A1 -> B1，然后通过 startActivity() 打开 B，则堆栈结果为 A1 -> B1（会调用 B1 的 onNewIntent() 方法）
 * 3、singleTask - 如果通过 startActivity() 打开的 activity 在堆栈中存在的话，则将此 activity 上方的 activity 全部出栈，然后复用此 activity 实例，会调用其 onNewIntent() 方法
 *    比如当前堆栈为 A1 -> B1 -> C1，然后通过 startActivity() 打开 B，则堆栈结果为 A1 -> B1（会调用 B1 的 onNewIntent() 方法）
 *    注：其效果与 Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP 的效果是一样的
 * 4、singleInstance - 如果通过 startActivity() 打开的 activity 在堆栈中不存在的话，则新建一个堆栈保存此 activity，并且此堆栈永远只有这一个 activity
 *    比如 task1 启动这个 activity 的话，则创建这个 activity 并将其归属于一个新建的 task2，然后再启动其他 activity 的话，会将其他 activity 压入 task1 堆栈，然后再启动这个 activity 的话就会调用 task2 堆栈中的这个 activity 对象的 onNewIntent() 方法
 *
 *
 * 注：如果需要比较灵活的控制堆栈中的 activity 的话，建议自己再额外维护一个 activity 集合，通过调用 activity 的 finish() 来控制它的出栈
 */

package com.webabcd.androiddemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

import java.util.Date;

public class ActivityDemo5 extends AppCompatActivity {

    private TextView mTextView1;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_activitydemo5);

        mTextView1 = findViewById(R.id.textView1);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);
        mButton6 = findViewById(R.id.button6);
        mButton7 = findViewById(R.id.button7);

        sample();
    }

    // 当本 activity 被通过 startActivity() 打开时，而且是复用的堆栈里已有的对象，则会执行此方法（此种场景是不会执行 onCreate() 的）
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        mTextView1.append("\n");
        mTextView1.append("onNewIntent");
    }

    private void sample() {
        mTextView1.setText(Helper.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        mTextView1.append("\n");
        mTextView1.append("taskId: " + this.getTaskId()); // 获取当前 activity 对象所在的 task（堆栈）的 id

        // 将另一个 activity 压入堆栈
        // 假设当前堆栈为 A -> B，然后将 C 压入堆栈，然后将 A 压入堆栈
        // 则堆栈结果为 A1 -> B1 -> C1 -> A2
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDemo5.this, ActivityDemo5_2.class);
                startActivity(intent);
            }
        });

        // 将当前 activity 移出堆栈
        // 假设当前堆栈为 A -> B -> C，然后将 C 移出堆栈
        // 则堆栈结果为 A -> B
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 将另一个 activity 压入堆栈，并将当前 activity 移出堆栈
        // 假设当前堆栈为 A -> B，然后将 C 压入堆栈，再将 B 移出堆栈
        // 则堆栈结果为 A -> C
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityDemo5.this, ActivityDemo5_2.class));
                finish();
            }
        });

        // 将另一个 activity 压入堆栈（Intent.FLAG_ACTIVITY_NO_HISTORY 方式）
        // 假设当前堆栈为 A，然后将 B（Intent.FLAG_ACTIVITY_NO_HISTORY 方式）压入堆栈，然后将 C 默认方式压入堆栈，然后关闭 C
        // 则关闭 C 后会自动调用 B 的 onDestroy() 方法，堆栈结果为 A
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDemo5.this, ActivityDemo5_2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        // 将另一个 activity 压入堆栈（Intent.FLAG_ACTIVITY_CLEAR_TOP 方式）
        // 假设当前堆栈为 A1 -> B1 -> C1 -> A2 -> B2 -> C2 -> A3，然后将 B（Intent.FLAG_ACTIVITY_CLEAR_TOP 方式）压入堆栈
        // 则堆栈结果为 A1 -> B1 -> C1 -> A2 -> B3（也就是说堆栈里如果有 B，则会将离栈顶最近的 B 和其上的全部 activity 移出堆栈，然后将新 B 压入堆栈）
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDemo5.this, ActivityDemo5_2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 将另一个 activity 压入堆栈（Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP 方式）
        // 假设当前堆栈为 A1 -> B1 -> C1 -> A2 -> B2 -> C2 -> A3，然后将 B（Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP 方式）压入堆栈
        // 则堆栈结果为 A1 -> B1 -> C1 -> A2 -> B2（也就是说堆栈里如果有 B，则会将离栈顶最近的 B 之上的全部 activity 移出堆栈，然后将这个 B 作为栈顶显示，同时调用这个 B 的 onNewIntent() 方法）
        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDemo5.this, ActivityDemo5_2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        // 将另一个 activity 压入堆栈（Intent.FLAG_ACTIVITY_NEW_TASK 方式）
        // 假设在 AndroidManifest.xml 中设置了 activity A 的 taskAffinity 为 com.webabcd.androiddemo.task1，activity B 的 taskAffinity 为 com.webabcd.androiddemo.task2，activity C 未指定 taskAffinity
        // 假设当前 task 为 A1 -> B1 -> C1 -> A2，然后打开 B（Intent.FLAG_ACTIVITY_NEW_TASK 方式），然后再默认方式打开 C，再默认方式打开 A
        // 则会出现两个 task（即两个堆栈）：task1 为 A1 -> B1 -> C1 -> A2；task2 为 B2 -> C2 -> A3（注：此时如果想通过 Intent.FLAG_ACTIVITY_NEW_TASK 方式打开 B 的话，是不会有反应的）
        // 此时，当前 app 在系统的最近程序列表中会出现 2 个项，分别是 task1 和 task2，可以自行切换。如果进入后台后，再通过点击 app 图标返回前台，则打开的是 task1
        //
        // 注：两个 activity 切换时，如果发生了 task 的改变，那么中间会闪一下白屏或黑屏，要改善这个问题的话，可以在 application 级别指定如下主题
        // <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        //     <item name="android:windowDisablePreview">true</item>
        // </style>
        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDemo5.this, ActivityDemo5_2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}