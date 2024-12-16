package com.my.myapplication.yonghu.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.my.myapplication.customview.MyViewPager;
import com.my.myapplication.ui.R;
import com.my.myapplication.yonghu.fragment.Main_Fragment1;
import com.my.myapplication.yonghu.fragment.Main_Fragment2;

import java.util.ArrayList;
import java.util.List;

//用户登录成功后跳转的主界面
public class YongHuMainActivity extends AppCompatActivity {

    private RelativeLayout mRvBn1;
    private ImageView mIvBn1;
    private TextView mTvBn1;
    private RelativeLayout mRvBn2;
    private ImageView mIvBn2;
    private TextView mTvBn2;
    private MyViewPager mViewpager;
    private List<Fragment> pageLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_yonghu);
        getSupportActionBar().hide();
        initView();
//添加界面
        pageLists.add(new Main_Fragment1());
        pageLists.add(new Main_Fragment2());

//        Viewpager相关的初始化
        mViewpager.setOffscreenPageLimit(pageLists.size());
        mViewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, pageLists));
//底部导航按钮点击事件处理
        mRvBn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvBn1.setTextColor(getColor(R.color.bn_text_select));
                mIvBn1.setImageResource(R.mipmap.bn1_y);

                mTvBn2.setTextColor(getColor(R.color.bn_text_noselect));
                mIvBn2.setImageResource(R.mipmap.bn2_n);
                mViewpager.setCurrentItem(0);

            }
        });
        //底部导航按钮点击事件处理

        mRvBn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTvBn1.setTextColor(getColor(R.color.bn_text_noselect));
                mIvBn1.setImageResource(R.mipmap.bn1_n);

                mTvBn2.setTextColor(getColor(R.color.bn_text_select));
                mIvBn2.setImageResource(R.mipmap.bn2_y);
                mViewpager.setCurrentItem(1);
            }
        });

    }

    private void initView() {
        mRvBn1 = findViewById(R.id.rv_bn1);
        mIvBn1 = findViewById(R.id.iv_bn1);
        mTvBn1 = findViewById(R.id.tv_bn1);
        mRvBn2 = findViewById(R.id.rv_bn2);
        mIvBn2 = findViewById(R.id.iv_bn2);
        mTvBn2 = findViewById(R.id.tv_bn2);
        mViewpager = findViewById(R.id.viewpager);
    }

    //viewpager 绑定fragmnet适配器
    public class FragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;

        public FragmentAdapter(FragmentManager fm, int behavior, List<Fragment> fragmentList) {
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


    }
}