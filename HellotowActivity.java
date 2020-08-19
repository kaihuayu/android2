package com.example.flylsb.myyy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import com.example.flylsb.myyy.DBOpenHelper;
import android.os.Build;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.os.Environment;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.graphics.Color;
import android.widget.EditText;

public class HellotowActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final int REQUESTCODE = 101;
    private Button btn_speak;
    private TextView textView;
    private ListView listView;
    private ArrayAdapter<String> arr_aAdapter;
    private AlertDialog dialog;


    SQLiteDatabase db;
    //private List<User> userList = new ArrayList<User>();//实体类
    private List<User> userList = new ArrayList<User>();//实体类
    public static final String		DATABASE_FILENAME	= "english.db";				// 这个是DB文件名字
    public static final String		PACKAGE_NAME		= "com.example.flylsb.myyy";	// 这个是自己项目包路径

    public static final String		DATABASE_PATH		= android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/"
            +"abcdefg";	// 获取存储位置地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hellotow);
        listView = findViewById(R.id.db_listview);
        listView.setOnItemLongClickListener(new OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {  //长按事件
                String text = listView.getItemAtPosition(position) + "";// 指定位置的内容
                setAlertDialog(view,text,position); //设置对话框

                System.out.println("我是长按的结果");
                dialog.show(); //弹出窗口
                return false;
            }


        }) ;


        // 监听器
        listView.setOnItemClickListener(this);// 单击单个条目


    }


    private void setAlertDialog(final View view,final String str,final int position) {  //局部内部类只能访问被final修饰的局部变量
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());//取到LayoutInflater的实例
// 引入一个外部布局
        View contview = factory.inflate(R.layout.alerrt_dialog, null);
        contview.setBackgroundColor(Color.WHITE);// 设置该外部布局的背景
        final EditText editText = (EditText) contview
                .findViewById(R.id.edit_text);// 找到该外部布局对应的EditText控件
        Button btOK = (Button) contview.findViewById(R.id.button2);
        btOK.setOnClickListener(new OnClickListener() {// 设置按钮的点击事件  //闭包回调

            @Override
            public void onClick(View v) {
                ((TextView) view).setText(editText.getText().toString()); //改变textview 的文字等于editText
                int id =position;
              //  String  sql = "Update eng set english="+editText.getText().toString()+ " where rowid="+id;

                String updateSql = "update eng set english=? where rowid=?";
                db.execSQL(updateSql, new String[]{editText.getText().toString(), String.valueOf(id)});  //更新语句


                System.out.println(str+"更新ID等于="+position);
                dialog.dismiss();
            }




        });

        Button btDel =(Button) contview.findViewById(R.id.button3);//删除数据
        btDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int id =position+1;
                String deleteSql = "delete from eng where rowid=?";
                db.execSQL(deleteSql,new String[]{ String.valueOf(id)});
                System.out.println(str+"删除ID等于="+position);
                dialog.dismiss();
            }
        });

        Button btAdd =(Button) contview.findViewById(R.id.button4);
        btAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int id =position+1;
                String insertSql = "insert into eng (english,cn) values(?,?)";
                String text= editText.getText().toString();
                String[] txtArray=text.split(",");
                System.out.println("添加"+txtArray[0]+"-"+txtArray[1]);
                db.execSQL(insertSql, new  String[]{txtArray[0],txtArray[1]});
                dialog.dismiss();

            }
        });


        dialog = new AlertDialog.Builder(HellotowActivity.this).setView(contview)  //创建对话框
                .create();
    }

    private SQLiteDatabase openDatabase()
    {
        try {

            String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
            File dir = new File(DATABASE_PATH);
            if (!dir.exists())
                dir.mkdir();
            if (!(new File(databaseFilename)).exists()) {
                InputStream is = getResources().openRawResource(R.raw.english);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);

           // SQLiteDatabase dbd = new  DBOpenHelper(this,databaseFilename,null,2);
            return db;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    //读取数据
    public void Click_sqlite(View view) {
       // create("abcddr");
        String sql = "select * from eng where english=man";
        SQLiteDatabase db = openDatabase();
       Cursor c = db.rawQuery("select rowid,* from eng " ,null);//select * from tablename 并不能获取rowid，必须显式的指定
       // Cursor c = db.rawQuery("select * from eng where english=?" ,new String[]{"man"});
       c.moveToFirst();
       User us =new User();
       ArrayList<String> arrayList = new ArrayList<String>();




       while (!c.isAfterLast())
        {
            //String name = c.getString(c.getColumnIndex("cn"));
          //  Log.i("tran", "success"+name);
           // Toast.makeText(HellotowActivity.this,name,Toast.LENGTH_LONG).show();
            int id =c.getInt(c.getColumnIndex("rowid"));
            String en = c.getString(c.getColumnIndex("english"));
            String name_str = c.getString(c.getColumnIndex("cn"));
            //String phone = c.getString(2);
            arrayList.add(id+en+name_str);
            User ue = new User();//给实体类赋值
            ue.setAge(en);
            ue.setName(name_str);
            ue.setEn(en);
            ue.setCn(name_str);
            ue.setId(String.valueOf(id));
            userList.add(ue);
           // System.out.println("_id:"+id+";name:"+name_str+";phone:");



            c.moveToNext();
        }


          //  System.out.println(userList);
        //arr_aAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        // 3. 视图(ListView)加载适配器
     //  listView.setAdapter( arr_aAdapter);
       listView.setAdapter(new MyBaseAdapter(this,userList));//将listView与适配器进行绑

    }




   //创建文件夹方法
    public void create(String fileName){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            int checkSelfPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(checkSelfPermission == PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUESTCODE);
            }
        }
        //Environment.getExternalStorageDirectory().getAbsolutePath():SD卡根目录
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+fileName);
        if (!file.exists()){
            boolean isSuccess = file.mkdirs();
            Toast.makeText(HellotowActivity.this,"文件夹创建成功",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(HellotowActivity.this,"文件夹已存在",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {  //点击条目的事件

            // TODO Auto-generated method stub
            String text = listView.getItemAtPosition(i) + "";// 指定位置的内容
            Toast.makeText(this, "positon=" + i + "text" + text,
                    Toast.LENGTH_LONG).show();
            BdTextToSpeech.getInstance(HellotowActivity.this).speak(text);



    }


    //跳转页面按钮
    public void  Click_page(View view ){

      //  String str="22221";
      //  textView.setText(str);
        // startActivity(new Intent(this, HellotowActivity.class));//红色部分为要打开的心窗口的类名

        try
        {
            startActivity(new Intent(this, Mainhello.class));//红色部分为要打开的心窗口的类名
        }
        catch (Exception ex)
        {
            // 显示异常信息
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

}
