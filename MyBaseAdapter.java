package com.example.flylsb.myyy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyBaseAdapter extends BaseAdapter {

    //数据源与数据适配器进行关联
    private List<User> mList;//一个集合，写在userList中
    private LayoutInflater mInflater;
    private TextView rid;//item.xml里的TextView：rid
    private TextView ren;//item.xml里的TextView：en
    private TextView rcn;//item.xml里的TextView：cn
/*
* listView在开始绘制的时候，系统首先调用getCount()函数，根据他的返回值得到 listView的长度，然后根据这个长度，调用getView()逐一绘制每一行。
如果你的 getCount()返回值是0的话，列表将不显示；同样return 1，就只显示一行。
系统显示列表时，首先实例化一个适配器（这里将实例化自定义的适配器）。

当手动完成适配时，必须手动映射数据，这需要重写getView（）方法。系统在绘制列表的每一行的时候将调用此方法。
getView()有三个参数，
position表示将显示的是第几行，
covertView是从布局文件中inflate来的布局。
我们用LayoutInflater的方法将定义好的item.xml文件提取成View实例用来显示。然后将xml文件中的各个组件实例化（简单的findViewById()方法）。这样便可以将数据对应到各个组件上了。
* */



    //创建一个构造方法 初始化适配器要加载的数据 以list的形式展示
    MyBaseAdapter(Context context, List<User> list){
        mList=list;
        mInflater=LayoutInflater.from(context);  //获取这个view的布局.从一个Context中，获得一个布局填充器，这样你就可以使用这个填充器来把xml布局文件转为View对象了。

    }

    //导过包出来的四个方法。
    //得到要绑定的条目的总数 在绘制视图时调用
    @Override
    public int getCount() {
        return mList.size();//返回ListVIew所需要显示的数据量
    }
    //给定索引值，得到索引值对应的对象
    @Override
    public Object getItem(int i) {
        return mList.get(i);//集合的位置
    }
    //获取条目的id
    @Override
    public long getItemId(int i) {
        return i;//id
    }
    //取得条目界面 position 代表当前条目所要绑定的数据在集合中的索引值 在绘制视图时调用
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
   // public View getView(int i, View view, ViewGroup viewGroup) {

       // LayoutInflater inflater = HellotowActivity.getLayoutInflater();
        //LayoutInflater inflater =LayoutInflater.from(new HellotowActivity()); //加载视图
        View view;

        if (convertView==null) {
            //因为getView()返回的对象，adapter会自动赋给ListView
            view = mInflater.inflate(R.layout.item, null); //将xml布局转换为view对象

            System.out.print(view);
        }else{
            view=convertView;
            //view= (TextView) view.getTag();
            Log.i("info","有缓存，不需要重新生成"+position);
        }
        rid =(TextView) view.findViewById(R.id.rid);//找到Textview rid
        ren = (TextView) view.findViewById(R.id.en);
        rcn = (TextView) view.findViewById(R.id.cn);
       rid.setText(mList.get(position).getId());
       ren.setText(mList.get(position).getEn());
       rcn.setText(mList.get(position).getCn());
       // System.out.println(mList);
        return view;
    }



}
