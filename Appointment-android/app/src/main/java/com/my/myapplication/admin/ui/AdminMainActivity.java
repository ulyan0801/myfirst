package com.my.myapplication.admin.ui;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.my.myapplication.admin.fragment.Home_item_admin;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

//后台管理主界面
public class AdminMainActivity extends AppCompatActivity {
    //定义变量
    private final ArrayList<Fragment> tabFragmentList = new ArrayList<>();

    private String[] mTitles_3;
    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private boolean run = true;
    private TextView mTvTime;
    String nowDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_yonghu);
//        设置标题
        getSupportActionBar().setTitle("后台管理");
//        初始化控件
        initView();
//开线程 显示当前的日期 时间和星期 时间数据从网络中请求获取
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean is_get = false;

                while (run) {
                    try {

                        if (is_get == false) {
                            is_get = true;
                            nowDate = DateUtils.getNowDate();
                        }
                        Calendar calendar = Calendar.getInstance();

                        // 创建一个24小时格式的SimpleDateFormat
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

                        // 获取当前时间
                        String currentTime = timeFormat.format(calendar.getTime());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvTime.setText("今天是:  " + nowDate + " " + currentTime);
                            }
                        });
                        Thread.sleep(500);
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AdminMainActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }


            }
        }).start();


    }

    //查找控件 绑定点击事件
    private void initView() {
//        找控件
        mViewpager = findViewById(R.id.viewpager);
        mTablayout = findViewById(R.id.tablayout);
        mTitles_3 = getResources().getStringArray(R.array.data1);
//        追加全部类型 页面
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(mTitles_3));
        list.add(0, "全部");
        mTitles_3 = list.toArray(new String[list.size()]);

//        获取分类数据
        if (mTitles_3.length == 0) {
            Toast.makeText(this, "资源文件分类数据不能为了空", Toast.LENGTH_SHORT).show();
        }
//        根据分类的数据 初始化页面
        for (int i = 0; i < mTitles_3.length; i++) {
            Home_item_admin home_item1 = Home_item_admin.newInstance(mTitles_3[i], "1");
            tabFragmentList.add(home_item1);
        }
//        绑定 viewpager fragment Tablelayout
        mViewpager.setOffscreenPageLimit(tabFragmentList.size());
        mViewpager.setAdapter(new MPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabFragmentList));
        mTablayout.setupWithViewPager(mViewpager);

        mTvTime = findViewById(R.id.tv_time);
    }


    //    viewpager适配器
    class MPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;

        public MPagerAdapter(FragmentManager fm, int behavior, List<Fragment> fragmentList) {
            super(fm, behavior);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        //返回tablayout的标题文字;
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles_3[position];
        }
    }

    @Override
    protected void onDestroy() {
        run = false;
        super.onDestroy();
    }
}