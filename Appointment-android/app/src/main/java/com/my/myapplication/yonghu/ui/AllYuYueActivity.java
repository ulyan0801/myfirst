package com.my.myapplication.yonghu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.WoDeYuYueBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.yonghu.adapter.AllYuYueAdapter;

import java.util.ArrayList;
import java.util.List;

//查所有预约人信息界面
public class AllYuYueActivity extends AppCompatActivity {

    //定义变量
    private RecyclerView mRecyclerview1;
    private AllYuYueAdapter mAdapter;
    private Long tid = 0L;
    private Long shijianid = 0L;

    private String riqi = "";
    private List<WoDeYuYueBean> mBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_yu_yue);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("预约人信息");
        Intent intent = getIntent();
        tid = intent.getLongExtra("tid", 0);
        shijianid = intent.getLongExtra("shijianid", 0);

        riqi = intent.getStringExtra("riqi");


        initView();


    }

    @Override
    protected void onResume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    // 这里是调用耗时操作方法
                    mBeanList = MyHelper.getInstance(AllYuYueActivity.this).getAllYuYue(tid, shijianid, riqi);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//没有预约
                            if (mBeanList.size() == 0) {
                                Toast.makeText(AllYuYueActivity.this, "没有预约", Toast.LENGTH_SHORT).show();

                            }
//         有预约 将预约信息显示到 recyclerview列表控件
                            if (mAdapter == null) {
                                mAdapter = new AllYuYueAdapter(AllYuYueActivity.this, mBeanList, tid, shijianid, riqi);
                                mRecyclerview1.setLayoutManager(new LinearLayoutManager(AllYuYueActivity.this));
                                mRecyclerview1.setAdapter(mAdapter);
                            } else {
                                mAdapter.upDate(mBeanList);
                            }


                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AllYuYueActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();
        super.onResume();
    }

    //返回箭头点击事件 销毁退出页面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return true;
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void initView() {
        mRecyclerview1 = findViewById(R.id.recyclerview1);
    }
}
