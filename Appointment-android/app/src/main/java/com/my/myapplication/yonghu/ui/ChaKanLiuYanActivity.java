package com.my.myapplication.yonghu.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.RecoverBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.yonghu.adapter.RecoverAdapter;

import java.util.ArrayList;
import java.util.List;

//查看留言
public class ChaKanLiuYanActivity extends AppCompatActivity {

    //recyclerview 适配器
    private RecoverAdapter mRecoverAdapter;
    private List<RecoverBean> mRecoverBeanList = new ArrayList<>();
    private Long mId;
    private RecyclerView mRecyclerview1;
    private Button mTvHuifu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cha_kan_liu_yan);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("留言信息");
        //获取数据的id 只有在查看和编辑时候才会携带id数据
        mId = getIntent().getLongExtra("beanId", 0);
        initView();
    }

    private void initView() {
        mRecyclerview1 = findViewById(R.id.recyclerview1);
        mTvHuifu = findViewById(R.id.tv_huifu);

        //绑定Recyclerview适配器
        mRecoverAdapter = new RecoverAdapter(ChaKanLiuYanActivity.this, mRecoverBeanList);
        //设置Recyclerview 布局管理器
        mRecyclerview1.setLayoutManager(new LinearLayoutManager(ChaKanLiuYanActivity.this));
        //  mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //设置Recyclerview适配器
        mRecyclerview1.setAdapter(mRecoverAdapter);
//点击回复按钮
        mTvHuifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChaKanLiuYanActivity.this, FaBiaoLiuYanActivity.class);
                intent.putExtra("beanId", mId);
                startActivity(intent);
            }
        });


    }

    //界面重新可见
    @Override
    protected void onResume() {
        super.onResume();

        //开线程 根据id查对应的数据
        new Thread(new Runnable() {
            @SuppressLint("WrongConstant")
            @Override
            public void run() {

                try {

                    //                    获取留言数据
                    mRecoverBeanList = MyHelper.getInstance(ChaKanLiuYanActivity.this).getAllRecoverData(mId);
                    //切换ui线程
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //数据显示在对应的控件上
                            mRecoverAdapter.upDate(mRecoverBeanList);

                        }
                    });


                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChaKanLiuYanActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                            System.out.println("---------------------" + e.getMessage());

                        }
                    });
                }

            }
        }).start();
    }


    //菜单栏 点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        返回箭头
        if (android.R.id.home == item.getItemId()) {
            finish();
        }

        return true;
    }

}