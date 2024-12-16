package com.my.myapplication.yonghu.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.my.myapplication.bean.UserBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.SpUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//改手机号
public class GaiShouJiHaoActivity extends AppCompatActivity {
    //定义变量
    private EditText mEtPhone;
    private EditText mEtYanzhengma;
    private Button mBtYanzhengma;
    private CountDownTimer countDownTimer;
    private int time_cnt = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gai_shou_ji_hao);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("修改手机号码");
        initView();

//        设置验证码输入框为禁止输入状态
        mEtYanzhengma.setEnabled(false);

//获取验证码点击
        mBtYanzhengma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                获取手机号
                String Phone = mEtPhone.getText().toString().trim();
//校验
                if (TextUtils.isEmpty(Phone)) {
                    Toast.makeText(GaiShouJiHaoActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 创建电话号码正则表达式
                String regex = "^1[0-9]{10}$";
                // 编译正则表达式
                Pattern pattern = Pattern.compile(regex);
// 创建 Matcher 对象
                Matcher matcher = pattern.matcher(Phone);

// 进行匹配 正则校验是否是手机号
                if (!matcher.matches()) {
                    Toast.makeText(GaiShouJiHaoActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
//设置获取验证码按钮为不可点击
                mBtYanzhengma.setEnabled(false);
//                启动定时器 倒计时
                countDownTimer.start();
//验证码输入框为可输入状态
                mEtYanzhengma.setEnabled(true);
                mEtYanzhengma.setHint("输入123456");
//                模拟验证码
                Toast.makeText(GaiShouJiHaoActivity.this, "验证码:123456", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        //查找控件id
        mEtPhone = findViewById(R.id.et_phone);
        mEtYanzhengma = findViewById(R.id.et_yanzhengma);
        mBtYanzhengma = findViewById(R.id.bt_yanzhengma);

        //                                 初始化定时器  总时长 60 * 1000 毫秒=60秒 每1000毫秒=1秒 进入一次onTick函数 60秒后进入onFinish函数
        countDownTimer = new CountDownTimer(60 * 1000, 1000L) {

            @Override
            public void onTick(long millisUntilFinished) {
                time_cnt--;
                mBtYanzhengma.setText(time_cnt + " 秒后重试");

            }

            @Override
            public void onFinish() {
                time_cnt = 60;
                mBtYanzhengma.setEnabled(true);
                mBtYanzhengma.setText("获取验证码");
            }
        };

//        修改按钮点击事件
        findViewById(R.id.bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                获取手机号和用户输入的验证码
                String Phone = mEtPhone.getText().toString().trim();
                String Yanzhengma = mEtYanzhengma.getText().toString().trim();
//校验数据

                if (TextUtils.isEmpty(Phone)) {
                    Toast.makeText(GaiShouJiHaoActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 创建电话号码正则表达式
                String regex = "^1[0-9]{10}$";
                // 编译正则表达式
                Pattern pattern = Pattern.compile(regex);
// 创建 Matcher 对象
                Matcher matcher = pattern.matcher(Phone);

// 进行匹配
                if (!matcher.matches()) {
                    Toast.makeText(GaiShouJiHaoActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Yanzhengma)) {
                    Toast.makeText(GaiShouJiHaoActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!"123456".equals(Yanzhengma)) {
                    Toast.makeText(GaiShouJiHaoActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    return;
                }


                //开个线程
                new Thread(new Runnable() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void run() {

                        try {

                            //则创建个用户类
                            UserBean user = new UserBean();
                            user.setPhone(Phone);
                            user.setUsername(SpUtils.getInstance(GaiShouJiHaoActivity.this).getString("username"));
                            //调用数据库方法 更新手机号
                            long result = MyHelper.getInstance(GaiShouJiHaoActivity.this).updateUserPhone(user);
                            if (result != -1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //注册成功提示用户 销毁界面 退出界面
                                        Toast.makeText(GaiShouJiHaoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //注册失败提示用户
                                        Toast.makeText(GaiShouJiHaoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }


                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GaiShouJiHaoActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                                    System.out.println("---------------------" + e.getMessage());
                                }
                            });

                        }


                    }
                }).start();

            }
        });
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
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }
}
