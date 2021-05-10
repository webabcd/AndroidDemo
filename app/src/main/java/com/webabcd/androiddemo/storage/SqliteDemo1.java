/**
 * Sqlite 基础
 *
 *
 * 注：
 * 1、请先进入 /storage/StorageDemo3.java 动态申请权限（我懒得在这里写了）
 */

package com.webabcd.androiddemo.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.webabcd.androiddemo.R;

import java.io.File;
import java.util.Locale;

public class SqliteDemo1 extends AppCompatActivity {

    private SQLiteDatabase _db;

    private TextView _textView1;
    private Button _button1;
    private Button _button2;
    private Button _button3;
    private Button _button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_sqlitedemo1);

        _textView1 = findViewById(R.id.textView1);
        _button1 = findViewById(R.id.button1);
        _button2 = findViewById(R.id.button2);
        _button3 = findViewById(R.id.button3);
        _button4 = findViewById(R.id.button4);

        sample();
    }

    private void sample() {

        // 首先创建你的数据库的保存目录，如果目录不存在的话会报错 android.database.sqlite.SQLiteCantOpenDatabaseException: unknown error (code 14): Could not open database
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/androiddemo/";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 通过 MySqliteHelper 打开数据库（如果数据库文件不存在的话，他会新建）
        // 这里可以指定你的数据库版本号，新建数据库会走到 MySqliteHelper 的 onCreate(), 更新数据库会走到 MySqliteHelper 的 onUpgrade()
        _db = new MySqliteHelper(this, dirPath + "myDB.db", null, 1).getWritableDatabase();

        // 添加数据的示例
        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < 3; i++) {
                        // 直接通过 sql 添加数据
                        // String sql = "insert into book" + " (name, pages) values ('name" + i + "', " + i + ")";
                        // _db.execSQL(sql);

                        // 通过 SQLiteDatabase 的 insert 添加数据
                        ContentValues values = new ContentValues();
                        values.put("name", "name" + i);
                        values.put("pages", i);
                        _db.insert("book", null, values);
                    }
                } catch (Exception ex) {
                    _textView1.setText(ex.toString());
                }
            }
        });

        // 查询数据的示例
        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 直接通过 sql 获取 cursor（支持 ? 当做占位符）
                    // Cursor cursor = _db.rawQuery("select * from book where id > 0", null);

                    // 通过 SQLiteDatabase 的 query 获取 cursor（支持 ? 当做占位符）
                    String[] column = { "id", "name", "pages" };
                    Cursor cursor = _db.query("book", column, "id > ?", new  String[] { "0" }, null,null, null);

                    // 获取当前 cursor 的记录总数
                    int num = cursor.getCount();
                    _textView1.setText("共 " + Integer.toString(num) + " 条记录\n");

                    // 根据 cursor 获取数据
                    if (cursor.moveToFirst()) {
                        do {
                            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            Integer pages = cursor.getInt(cursor.getColumnIndex("pages"));

                            _textView1.append(String.format(Locale.US, "cursor position:%d, id:%d, name:%s, pages:%d\n",
                                    cursor.getPosition(), id, name, pages));

                        } while (cursor.moveToNext());
                    }
                    // 关闭 cursor
                    cursor.close();

                } catch (Exception ex) {
                    _textView1.setText(ex.toString());
                }
            }
        });

        // 修改数据的示例
        _button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 直接通过 sql 修改数据（支持 ? 当做占位符）
                    // String sql = "update book set name = ? where id > ?";
                    // _db.execSQL(sql, new String[] { "xxx", "3"});

                    // 通过 SQLiteDatabase 的 update 修改数据（支持 ? 当做占位符）
                    ContentValues values = new ContentValues();
                    values.put("name", "xxx");
                    _db.update("book", values, "id > ?", new String[] { "3" });
                } catch (Exception ex) {
                    _textView1.setText(ex.toString());
                }
            }
        });

        // 删除数据的示例
        _button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 直接通过 sql 删除数据（支持 ? 当做占位符）
                    // String sql = "delete from book where id < ?";
                    // _db.execSQL(sql, new String[] { "999999"});

                    // 通过 SQLiteDatabase 的 delete 修改数据（支持 ? 当做占位符）
                    _db.delete("book", " id < 999999", null);
                } catch (Exception ex) {
                    _textView1.setText(ex.toString());
                }
            }
        });
    }

    // 自定义的 SQLiteOpenHelper
    class MySqliteHelper extends SQLiteOpenHelper {

        // 创建表的脚本
        private final String create_book = "create table book (id integer primary key autoincrement, name text, pages integer)";

        public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 新建数据库时调用（比如你的库从无到有时），可以在这里创建你的第一版的表
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(create_book);
        }

        // 数据库版本更新时调用（比如之前已经创建好数据库了，后来需要添加字段或者添加表之类的），可以在这里创建你的新表或修改表结构之类的
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            switch (oldVersion) {
                case 1:
                    // db.execSQL("alter table book add column author text");
                default:
                    break;
            }
        }

        // 每次成功打开数据库后会执行到这里
        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }
    }
}