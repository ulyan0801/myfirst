package com.my.myapplication.yonghu.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.my.myapplication.ui.R;

//启动欢迎页面
public class WelcomeActivity extends AppCompatActivity {
    int time = 4;
    private TextView mTvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
            //
        }

        setContentView(R.layout.activity_welcome);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();

        mTvTime.setText(time + "");
//        点击倒计时的时间 停止倒计时 跳转界面
        mTvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimer.cancel();
                mTvTime.setEnabled(false);
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mCountDownTimer.start();
    }

    //计时类 3000单位ms总时间 1000单位ms 间隔1000ms进入一次 onTick函数 3000ms进入3次onTick 计时结束后 进入onFinish 函数
    CountDownTimer mCountDownTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            time--;
            mTvTime.setText(time + "");
        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private void initView() {
        mTvTime = findViewById(R.id.tv_time);
    }

    //    退出程序 取消计时
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountDownTimer.cancel();
    }
}