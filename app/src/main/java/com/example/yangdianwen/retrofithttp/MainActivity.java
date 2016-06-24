package com.example.yangdianwen.retrofithttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String string;
    private List<JavaBean.ResultsBean> data;
    private ListView listView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv);
        //获取retrofit对象
                Retrofit retrofit=new Retrofit.Builder().baseUrl(Constans.BASE_URL)
                        .build();
               //获取网络服务
                GetService service = retrofit.create(GetService.class);
               //调用接口中的方法
                Call<ResponseBody> json = service.getJson();

               //执行callback回调
                json.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //判断如果响应成功，则显示数据
                        if (response.isSuccessful()) {
                            try {
                                string = response.body().string();
                                //解析数据
                                data=new ArrayList<>();
                                Gson gson=new Gson();
                                JavaBean bean = gson.fromJson(string, JavaBean.class);
                                List<JavaBean.ResultsBean> results = bean.getResults();
                                for (int i = 0; i <results.size() ; i++) {
                                         data.add(results.get(i));
                                }
                                Log.d(TAG, "onResponse: "+ string);
                                Log.d(TAG, "threadId: "+Thread.currentThread().getId());
                                Log.d(TAG, "onCreate: "+data);
                                myAdapter = new MyAdapter(data,MainActivity.this);
                                listView.setAdapter(myAdapter);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

}
