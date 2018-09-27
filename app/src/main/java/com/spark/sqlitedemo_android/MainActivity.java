package com.spark.sqlitedemo_android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.spark.sksqlitemanger.SKSQLiteConst;
import com.spark.sksqlitemanger.SKSQLiteHelper;
import com.spark.sksqlitemanger.SKStatDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText nameTv;
    private EditText phoneTv;
    private List<StatBean> datas = new ArrayList<StatBean>();
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initDatas();
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void initDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Cursor cursor = SKStatDao.getInstance(MainActivity.this).getAll(null, null);

                if (cursor.getCount() == 0) {
                    showToast("暂无数据");
                    handler.sendEmptyMessage(0x0002);
                    return;
                }
                datas.clear();
                while (cursor.moveToNext()) {
                    StatBean statBean = new StatBean();
                    statBean.setName(cursor.getString(1));
                    statBean.setPhone(cursor.getString(2));
                    datas.add(0, statBean);
                }

                handler.sendEmptyMessage(0x0001);
                Looper.loop();
            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x0001:
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    break;
                case 0x0002:
                    recyclerView.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void initView() {
        nameTv = (EditText)findViewById(R.id.nameTv);
        phoneTv = (EditText)findViewById(R.id.phoneTv);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this, datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }


    public void insert(View view) {

        ContentValues cv = new ContentValues();
        cv.put(SKSQLiteConst.statTableColumn_Name, nameTv.getText().toString());
        cv.put(SKSQLiteConst.statTableColumn_Phone, phoneTv.getText().toString());
        if (TextUtils.isEmpty(nameTv.getText().toString()) && TextUtils.isEmpty(phoneTv.getText().toString())) {
            showToast("不能全部为空");
            return;
        }

        SKStatDao.getInstance(this).insertToStatTable(cv);
        showToast("插入成功");
        initDatas();
    }

    public void queryAll(View view) {
        Cursor cur = SKStatDao.getInstance(this).getAll(null, null);
        while (cur.moveToNext()){
            String name = cur.getString(1);
            String phone = cur.getString(2);
            Log.e(SKSQLiteConst.TAG, " \nname: " + name + "\nphone:" + phone + "\nid:" + cur.getInt(0));
        }
    }

    public void deleteAll(View view) {
        SKStatDao.getInstance(this).deleteAll();
        initDatas();
    }

    private void loadJson() {
        try {
            InputStream inputStream = getResources().getAssets().open("province.json");

            StringBuilder sb = new StringBuilder();

            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {

                sb.append(new String(buffer, 0, len));
            }

            Log.e("SKSQLITE", sb.toString());

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
