package com.my.myapplication.yonghu.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.AppointmentBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.SpUtils;
import com.my.myapplication.yonghu.adapter.YongHuMainAdapter;

import java.util.ArrayList;
import java.util.List;

//我的收藏
public class WoDeShouCangActivity extends AppCompatActivity {

    //定义变量
    private RecyclerView mRecyclerview1;
    private List<AppointmentBean> mItemBeanList = new ArrayList<>();
    //recyclerview 适配器
    private YongHuMainAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_shou_cang);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("我的收藏");

        initView();

//        初始化 recyclerview列表控件
        mMyAdapter = new YongHuMainAdapter(WoDeShouCangActivity.this, mItemBeanList);
        mRecyclerview1.setLayoutManager(new LinearLayoutManager(WoDeShouCangActivity.this));
        mRecyclerview1.setAdapter(mMyAdapter);

    }

    @Override
    protected void onResume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // 这里是调用耗时操作方法 查收藏数据
                    mItemBeanList = MyHelper.getInstance(WoDeShouCangActivity.this).getUserShouCang(SpUtils.getInstance(WoDeShouCangActivity.this).getString("username"));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//没有收藏
                            if (mItemBeanList.size() == 0) {
                                Toast.makeText(WoDeShouCangActivity.this, "没有收藏", Toast.LENGTH_SHORT).show();
                            }

                            mMyAdapter.upDate(mItemBeanList);


                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("---------------------" + e.getMessage());
                            Toast.makeText(WoDeShouCangActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
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
