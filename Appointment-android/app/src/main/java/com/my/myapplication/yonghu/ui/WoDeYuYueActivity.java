package com.my.myapplication.yonghu.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.WoDeYuYueBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.DateUtils;
import com.my.myapplication.utils.SpUtils;
import com.my.myapplication.yonghu.adapter.WoDeYuYueAdapter;

import java.util.ArrayList;
import java.util.List;

//查自己预约信息界面
public class WoDeYuYueActivity extends AppCompatActivity {

    //定义变量
    private RecyclerView mRecyclerview1;
    private List<WoDeYuYueBean> mItemBeanList = new ArrayList<>();

    private WoDeYuYueAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_yu_yue);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("我的预约");

        initView();


    }

    @Override
    protected void onResume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String nowDate = DateUtils.getNowDate();

                    // 这里是调用耗时操作方法 查当前注册的账号是否有预约信息
                    mItemBeanList = MyHelper.getInstance(WoDeYuYueActivity.this).getWoDeYuYue(SpUtils.getInstance(WoDeYuYueActivity.this).getString("username"));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//没有预约
                            if (mItemBeanList.size() == 0) {
                                Toast.makeText(WoDeYuYueActivity.this, "没有预约", Toast.LENGTH_SHORT).show();

                            }
//         有预约 将预约信息显示到 recyclerview列表控件
                            if (mItemAdapter == null) {
                                mItemAdapter = new WoDeYuYueAdapter(WoDeYuYueActivity.this, mItemBeanList, nowDate);
                                mRecyclerview1.setLayoutManager(new LinearLayoutManager(WoDeYuYueActivity.this));
                                mRecyclerview1.setAdapter(mItemAdapter);
                            } else {
                                mItemAdapter.upDate(mItemBeanList);
                            }


                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WoDeYuYueActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
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
