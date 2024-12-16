package com.my.myapplication.admin.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.my.myapplication.bean.ShiJianBean;
import com.my.myapplication.ui.R;

import java.time.LocalTime;
import java.util.Calendar;

//添加预约时间段信息界面
public class AdminAddYuYueShiJianActivity extends AppCompatActivity {

    private EditText mEtKaishishijian;
    private TextView mTvKaishishijian;
    private EditText mEtJieshushijian;
    private TextView mTvJieshushijian;
    private EditText mEtJiage;
    private Button mBtQuedin;
    private Button mBtQuxiao;
    private EditText mEtRenshu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__admin_add_yuyueshijian);
//        设置标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("添加预约时间段和价格");

        initView();

        //        设置预约开始时间按钮点击
        mTvKaishishijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// 获取当前时间
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AdminAddYuYueShiJianActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String h = hourOfDay + "";
                        String m = minute + "";
                        if (hourOfDay < 10) {
                            h = "0" + h;
                        }
                        if (minute < 10) {
                            m = "0" + m;
                        }

                        mEtKaishishijian.setText(h + ":" + m);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

//        设置预约结束时间按钮点击
        mTvJieshushijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// 获取当前时间
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AdminAddYuYueShiJianActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        小时分钟不显示0处理
                        String h = hourOfDay + "";
                        String m = minute + "";
                        if (hourOfDay < 10) {
                            h = "0" + h;
                        }
                        if (minute < 10) {
                            m = "0" + m;
                        }
//                        绑定时间到控件
                        mEtJieshushijian.setText(h + ":" + m);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
//        确定添加按钮
        mBtQuedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                校验数据
                String jiage = mEtJiage.getText().toString().trim();
                String Renshu = mEtRenshu.getText().toString().trim();
                if (TextUtils.isEmpty(Renshu)) {
                    Toast.makeText(AdminAddYuYueShiJianActivity.this, "总人数不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(jiage)) {
                    Toast.makeText(AdminAddYuYueShiJianActivity.this, "价格不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("0".equals(Renshu.charAt(0) + "")) {
                    Toast.makeText(AdminAddYuYueShiJianActivity.this, "总人数必须大于0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(Renshu) <= 0) {
                    Toast.makeText(AdminAddYuYueShiJianActivity.this, "总人数必须大于0", Toast.LENGTH_SHORT).show();
                    return;
                }


                if ("0".equals(jiage.charAt(0) + "")) {
                    Toast.makeText(AdminAddYuYueShiJianActivity.this, "价格必须大于0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Double.parseDouble(jiage) <= 0) {
                    Toast.makeText(AdminAddYuYueShiJianActivity.this, "价格必须大于0", Toast.LENGTH_SHORT).show();
                    return;
                }
//创建数据类
                ShiJianBean bean = new ShiJianBean();
                bean.setJiage(jiage);
                bean.setRenshu(Integer.parseInt(Renshu));
                //校验数据 开始时间是否大于结束时间
                try {

                    // 将时间字符串转换为LocalTime对象
                    LocalTime time1 = LocalTime.parse(mEtKaishishijian.getText().toString().trim());
                    LocalTime time2 = LocalTime.parse(mEtJieshushijian.getText().toString().trim());

                    // 比较两个LocalTime对象
                    int compareResult = time1.compareTo(time2);

// 输出比较结果
                    if (compareResult > 0) {
                        Toast.makeText(AdminAddYuYueShiJianActivity.this, "预约开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();

                        System.out.println("----------- 大于 ");
                        return;
                    } else if (compareResult < 0) {
                        System.out.println("---------- 小于 ");
                    } else {
                        Toast.makeText(AdminAddYuYueShiJianActivity.this, "预约开始时间不能等于结束时间", Toast.LENGTH_SHORT).show();

                        System.out.println("------------------ 等于 ");
                        return;
                    }
                } catch (Exception e) {
                    Toast.makeText(AdminAddYuYueShiJianActivity.this, "预约时间错误", Toast.LENGTH_SHORT).show();
                    bean.setYuyueshijian("08:00-20:00");
                }

                bean.setYuyueshijian(mEtKaishishijian.getText().toString().trim() + "-" + mEtJieshushijian.getText().toString().trim());

//数据返回给添加数据界面
                Intent intent = new Intent();
                intent.putExtra("itemBean", bean);
                setResult(RESULT_OK, intent); // 设置结果码为 RESULT_OK
                finish(); // 结束活动，返回结果
            }
        });
        mBtQuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    //找控件
    private void initView() {
        mEtKaishishijian = findViewById(R.id.et_kaishishijian);
        mTvKaishishijian = findViewById(R.id.tv_kaishishijian);
        mEtJieshushijian = findViewById(R.id.et_jieshushijian);
        mTvJieshushijian = findViewById(R.id.tv_jieshushijian);
        mEtJiage = findViewById(R.id.et_jiage);
        mBtQuedin = findViewById(R.id.bt_quedin);
        mBtQuxiao = findViewById(R.id.bt_quxiao);
        mEtRenshu = findViewById(R.id.et_renshu);
    }
}