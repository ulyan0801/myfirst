package com.my.myapplication.yonghu.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.my.myapplication.bean.UserBean;
import com.my.myapplication.customview.LoadProgressDialog;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.SpUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//注册
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //判断是注册还是修改密码标志
    String type = "";
    //定义变量
    private EditText mEtName;
    private EditText mEtPassword;
    private EditText mEtPasswordAgain;
    private Button mBtRegister;
    private LinearLayout mLlPswOld;
    private EditText mEtPasswordOld;
    private EditText mEtPhone;
    private EditText mEtYanzhengma;
    private Button mBtYanzhengma;
    private CountDownTimer countDownTimer;
    private int time_cnt = 60;
    private LinearLayout mLlGaimima;
    private TextView mTvPasswordOld;
    private TextView mTvPasswordNew;
    private TextView mTvPasswordNew1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("注册");
//        获取是修改密码还是注册的标志 type的值由跳转页面 intent携带
        type = getIntent().getStringExtra("type");
        initView();
        mEtYanzhengma.setEnabled(false);
//        如果是修改密码 设置按钮可见和可用状态 获取修改用户名 由启动该页面的intent携带 设置提示信息hint
        if ("xiugai".equals(type)) {
            mLlPswOld.setVisibility(View.VISIBLE);
            mBtRegister.setText("修改密码");
            getSupportActionBar().setTitle("修改密码");
            mEtName.setText(SpUtils.getInstance(RegisterActivity.this).getString("username"));
            mEtName.setEnabled(false);
            mEtPasswordOld.setHint("请输入原始密码");
            mEtPassword.setHint("请输入新密码");
            mEtPasswordAgain.setHint("请再次输入新密码");
            mEtYanzhengma.setVisibility(View.GONE);
            mEtPhone.setVisibility(View.GONE);
            mBtYanzhengma.setVisibility(View.GONE);
            mLlGaimima.setVisibility(View.GONE);
            mTvPasswordOld.setText("旧密码");
            mTvPasswordNew.setText("新密码");
            mTvPasswordNew1.setText("新密码");
        }
//验证码按钮点击
        mBtYanzhengma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone = mEtPhone.getText().toString().trim();

                if (TextUtils.isEmpty(Phone)) {
                    Toast.makeText(RegisterActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RegisterActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
//设置获取验证码按钮为不可点击
                mBtYanzhengma.setEnabled(false);
//                启动定时器
                countDownTimer.start();
//                验证码输入框为可输入
                mEtYanzhengma.setEnabled(true);
//                模拟获取验证码
                mEtYanzhengma.setHint("输入123456");
                Toast.makeText(RegisterActivity.this, "验证码:123456", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        //查找控件id 注册点击事件
        mEtName = findViewById(R.id.et_name);
        mEtPassword = findViewById(R.id.et_password);
        mEtPasswordAgain = findViewById(R.id.et_password_again);
        mBtRegister = findViewById(R.id.bt_register);
        mBtRegister.setOnClickListener(this);

        mLlPswOld = findViewById(R.id.ll_psw_old);
        mEtPasswordOld = findViewById(R.id.et_password_old);
        mEtPhone = findViewById(R.id.et_phone);
        mEtYanzhengma = findViewById(R.id.et_yanzhengma);
        mBtYanzhengma = findViewById(R.id.bt_yanzhengma);

        //                                初始化定时器  总时长 60 * 1000 毫秒=60秒 每1000毫秒=1秒 进入一次onTick函数 60秒后进入onFinish函数
        countDownTimer = new CountDownTimer(60 * 1000, 1000L) {

            @Override
            public void onTick(long millisUntilFinished) {
//                变量自减 绑定到控件
                time_cnt--;
                mBtYanzhengma.setText(time_cnt + " 秒后重试");

            }

            @Override
            public void onFinish() {
//                初始化变量值 设置输入框为可输入状态 设置按钮文字
                time_cnt = 60;
                mBtYanzhengma.setEnabled(true);
                mBtYanzhengma.setText("获取验证码");
            }
        };
        mLlGaimima = findViewById(R.id.ll_gaimima);
        mTvPasswordOld = findViewById(R.id.tv_password_old);
        mTvPasswordNew = findViewById(R.id.tv_password_new);
        mTvPasswordNew1 = findViewById(R.id.tv_password_new1);
    }

    //返回箭头点击事件 销毁退出页面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return true;
    }

    //注册或者修改密码按钮点击事件 注册按钮和修改按钮共用一个按钮
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                register();
                break;
        }
    }

    //注册逻辑
    private void register() {
//登录注册账号密码
        String userName = mEtName.getText().toString().trim();
        String userOldPassword = mEtPasswordOld.getText().toString().trim();
        String userPassword = mEtPassword.getText().toString().trim();
        String userPasswordAgain = mEtPasswordAgain.getText().toString().trim();
        String Phone = mEtPhone.getText().toString().trim();
        String Yanzhengma = mEtYanzhengma.getText().toString().trim();
        // 判断是为空 如果有一个时空 则退出这个函数 不再执行以下代码
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        判断用户输入密码是否为空
        if (TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(userPasswordAgain)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        判断密码是否一致
        if (!userPassword.equals(userPasswordAgain)) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
//        如果是修改 判断原始密码是我空
        if ("xiugai".equals(type)) {
            if (TextUtils.isEmpty(userOldPassword)) {
                Toast.makeText(this, "原始密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            //开个线程进行注册
            LoadProgressDialog loadProgressDialog = new LoadProgressDialog(RegisterActivity.this, "请稍等...", false);
            loadProgressDialog.show();
            new Thread(new Runnable() {
                @SuppressLint("WrongConstant")
                @Override
                public void run() {

                    try {
                        // 修改密码部分 调用登录方法 判断用户原始密码是否正确
                        UserBean userBean = MyHelper.getInstance(RegisterActivity.this).matchAccount(userName, userOldPassword);
                        //登录失败 则证明原始密码错误
                        if (userBean == null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //提示
                                    loadProgressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "修改失败，原始密码错误", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            //如果登录成功 则证明原始密码正确 修改密码部分
                            UserBean user = new UserBean();
                            user.setUsername(userName);
                            user.setPassword(userPassword);
                            //调用数据库方法 将新密码更新
                            long result = MyHelper.getInstance(RegisterActivity.this).updateUserPsw(user);
                            if (result != -1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadProgressDialog.dismiss();
                                        //修改成功后 提示重新登录程序
                                        new AlertDialog.Builder(RegisterActivity.this)//绑定当前窗口
                                                .setCancelable(false)
                                                .setTitle("提示")//设置标题
                                                .setMessage("密码修改成功,请重新登录！")//设置提示细信息
                                                .setIcon(R.mipmap.ic_launcher)//设置图标
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        清除之前登录的数据 如记住账号密码数据
                                                        //是否记账账号 SharedPreferences存储用的key
                                                        String key_is_remember = "key_is_remember";
                                                        // 存储账号 SharedPreferences用的key
                                                        String key_account = "ke_account";
                                                        //存储密码 SharedPreferences用的key
                                                        String key_password = "key_password";
                                                        SpUtils.getInstance(RegisterActivity.this).putBoolean(key_is_remember, false);
                                                        SpUtils.getInstance(RegisterActivity.this).putString(key_account, "");
                                                        SpUtils.getInstance(RegisterActivity.this).putString(key_password, "");
                                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                    }
                                                })//添加确定按钮
                                                .create()//创建对话框
                                                .show();//显示对话框
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //注册失败提示用户
                                        loadProgressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }


                        }

                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadProgressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }


                }
            }).start();
        } else {

            if (TextUtils.isEmpty(Phone)) {
                Toast.makeText(RegisterActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(RegisterActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(Yanzhengma)) {
                Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!"123456".equals(Yanzhengma)) {
                Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                return;
            }

            LoadProgressDialog loadProgressDialog = new LoadProgressDialog(RegisterActivity.this, "正在注册...", false);
            loadProgressDialog.show();
            //开个线程进行注册 注册逻辑部分
            new Thread(new Runnable() {
                @SuppressLint("WrongConstant")
                @Override
                public void run() {

                    try {
                        // 这里是调用耗时操作方法 根据用户名查找用户是否被注册
                        if (MyHelper.getInstance(RegisterActivity.this).isRegister(userName)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //如果被注册 则注册失败 并提示用户
                                    loadProgressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "注册失败，账号已被注册", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {


                            //如果没有被注册 则创建个用户类
                            UserBean user = new UserBean();
                            user.setUsername(userName);
                            user.setPassword(userPassword);
                            String lastFourDigits = Phone.substring(Phone.length() - 4);
                            try {
                                user.setUserNickname("用户:" + lastFourDigits);

                            } catch (Exception e) {
                                user.setUserNickname("用户:" + userName.charAt(0));

                            }

                            user.setPhone(Phone);
                            //调用数据库方法 将注册数据插入数据库 并判断插入返回值
                            long result = MyHelper.getInstance(RegisterActivity.this).addUser(user);
                            if (result != -1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //注册成功提示用户 销毁界面 退出界面
                                        loadProgressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //注册失败提示用户
                                        loadProgressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }


                        }

                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                                loadProgressDialog.dismiss();
                            }
                        });

                    }


                }
            }).start();
        }


    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }
}
