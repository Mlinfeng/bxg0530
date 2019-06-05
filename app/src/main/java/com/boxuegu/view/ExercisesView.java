package com.boxuegu.view;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import com.boxuegu.adapter.ExercisesAdapter;
import com.boxuegu.bean.ExercisesBean;
import com.boxuegu.R;
import com.boxuegu.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.os.Handler;
import android.os.Message;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class ExercisesView {
    private ListView lv_list;
    private ExercisesAdapter adapter;
    private List<ExercisesBean> ebl;
    private Activity mContext;
    private LayoutInflater mInflater;
    private View mCurrentView;
    public ExercisesView(Activity context) {
        mContext = context;
        // 为之后将Layout转化为view时用
        mInflater = LayoutInflater.from(mContext);
    }
    private void createView() {
        initView();
    }
    /**
     * 初始化控件
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                List<User> listUser=(ArrayList<User>)msg.obj;
                for (int i=0;i<listUser.size();i++){
                    ExercisesBean bean = new ExercisesBean();
                    bean.title = listUser.get(i).getUsername();
                    bean.content=listUser.get(i).getPassword();
                    ebl.add(bean);
                }
                adapter.notifyDataSetChanged();
            }
        }
    };
    private void initView() {
        mCurrentView = mInflater
                .inflate(R.layout.main_view_exercises, null);
        lv_list = (ListView) mCurrentView.findViewById(R.id.lv_list);
        adapter = new ExercisesAdapter(mContext);
        initData();
        adapter.setData(ebl);
        lv_list.setAdapter(adapter);
    }
    private void initData(){
        ebl = new ArrayList<ExercisesBean>();
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://172.28.22.61:8080/df0530/ListAllUserServlet";
        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TEST","onFailue");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString=response.body().string();
                Log.d("TEST", jsonString);
                Gson gson=new Gson();
                final List<User> userList=gson.fromJson(jsonString,new TypeToken<List<User>>(){}.getType());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=1;
                        message.obj=userList;
                        handler.sendMessage(message);

                    }
                }).start();
            }
        });
    }
//    private void initData() {
//        ebl = new ArrayList<ExercisesBean>();
//        for (int i = 0; i < 10; i++) {
//            ExercisesBean bean = new ExercisesBean();
//            bean.id = (i + 1);
//            switch (i) {
//                case 0:
//                    bean.title = "第1章 Android基础入门";
//                    bean.content = "共计5题";
//                    bean.background = (R.drawable.exercises_bg_1);
//                    break;
//                case 1:
//                    bean.title = "第2章 Android UI开发";
//                    bean.content = "共计5题";
//                    bean.background = (R.drawable.exercises_bg_2);
//                    break;
//                case 2:
//                    bean.title = "第3章 Activity";
//                    bean.content = "共计5题";
//                    bean.background = (R.drawable.exercises_bg_3);
//                    break;
//                case 3:
//                    bean.title = "第4章 数据存储";
//                    bean.content = "共计5题";
//                    bean.background = (R.drawable.exercises_bg_4);
//                    break;
//                case 4:
//                    bean.title = "第5章 SQLite数据库";
//                    bean.content = "共计5题";
//                    bean.background = (R.drawable.exercises_bg_1);
//                    break;
//                case 5:
//                    bean.title = "第6章 广播接收者";
//                    bean.content = "共计5题";
//                    bean.background = (R.drawable.exercises_bg_2);
//                    break;
//                case 6:
//                    bean.title = "第7章 服务";
//                    bean.content = "共计5题";
//                    bean.background = (R.drawable.exercises_bg_3);
//                    break;
//                case 7:
//                    bean.title = "第8章 内容提供者";
//                    bean.content = "共计5题";
//                    bean.background = (R.drawable.exercises_bg_4);
//                    break;
//                case 8:
//                    bean.title = "第9章 网络编程";
//                    bean.content = "共计5题";
//                    bean.background = (R.drawable.exercises_bg_1);
//                    break;
//                case 9:
//                    bean.title = "第10章 高级编程";
//                    bean.content = "共计5题";
//                    bean.background = (R.drawable.exercises_bg_2);
//                    break;
//                default:
//                    break;
//            }
//            ebl.add(bean);
//        }
//    ExercisesBean bean1=new ExercisesBean();
//    bean1.title="第11章  微信开发";
//    bean1.content="共计11题";
//    bean1.background=R.drawable.exercises_bg_1;
//    ebl.add(bean1);
//        ExercisesBean bean2=new ExercisesBean();
//        bean2.title="第12章  钉钉开发";
//        bean2.content="共计10题";
//        bean2.background=R.drawable.exercises_bg_2;
//        ebl.add(bean2);
//        getUserList();
//    }
    /**
     *
     * 获取当前在导航栏上方显示对应的View
     */
//    public  void getUserList(){
//        String url = "http://192.168.1.101:8080/CTK0516/ListAllUserServlet";
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request=new Request.Builder()
//                .url(url)
//                .get()
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//
//            public void onFailure(Call call, IOException e) {
//                Log.d("TEST", "onFailure: ");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json=response.body().string();
//                Log.d("TEST", "onResponse: " + json);
//                Gson gson=new Gson();
//                Type listType=new TypeToken<List<User>>(){}.getType();
//                List<User> userList=gson.fromJson(json,listType);
//                Log.d("TEST",userList.toString());
//            }
//    });
//    }

    public View getView() {
        if (mCurrentView == null) {
            createView();
        }
        return mCurrentView;
    }
    /**
     * 显示当前导航栏上方所对应的view界面
     */
    public void showView() {
        if (mCurrentView == null) {
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
}