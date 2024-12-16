package com.my.myapplication.admin.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.admin.adapter.AdminChaKanShiJianAdapter;
import com.my.myapplication.admin.adapter.ImageAdapter;
import com.my.myapplication.admin.adapter.RiQiAdapter;
import com.my.myapplication.bean.AppointmentBean;
import com.my.myapplication.bean.ContentAndImgBean;
import com.my.myapplication.bean.RecoverBean;
import com.my.myapplication.bean.RiQiBean;
import com.my.myapplication.bean.ShiJianBean;
import com.my.myapplication.bean.YuYueBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.DateUtils;
import com.my.myapplication.utils.ScreenUtil;
import com.my.myapplication.utils.SpUtils;
import com.my.myapplication.utils.global.Config;
import com.my.myapplication.video.MyVideoView;
import com.my.myapplication.video.VideoBusiness;
import com.my.myapplication.video.VideoController;
import com.my.myapplication.yonghu.ui.AllYuYueActivity;
import com.my.myapplication.yonghu.ui.ChaKanLiuYanActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

//管理查看详情页
public class AdminChaKanActivity extends AppCompatActivity {
    //定义变量
    private RecyclerView mRecyclerview;
    private AppointmentBean mBean;
    private Long mId;
    //视频播放器相关
    private RelativeLayout rl_video_container;
    private MyVideoView videoview;
    private VideoController id_video_controller;
    private VideoBusiness mVideoBusiness;

    private LinearLayout mLlvideo;
    private RecyclerView mRecyclerview1;


    //recyclerview 适配器
    private ImageAdapter mMyAdapter;
    private AdminChaKanShiJianAdapter mShiJianAdapter;
    //recyclerview 数据
    private List<ContentAndImgBean> mContentAndImgBeanList = new ArrayList<>();

    private List<ShiJianBean> mShiJianBeanList = new ArrayList<>();
    private TextView mTvQita;
    private RecyclerView mRecyclerview2;

    private List<RiQiBean> mRiQiBeanList = new ArrayList<>();
    private RiQiAdapter mRiQiAdapter;
    private String mriqi = "";
    //recyclerview 适配器
    private LinearLayout mLlDianzan;
    private ImageView mIvDianzan;
    private TextView mTvDianzan;
    private LinearLayout mLlPinlun;
    private TextView mTvLiuyan;
    private LinearLayout mLlShoucang;
    private ImageView mIvShoucang;
    private LinearLayout mLlLianxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cha_kan_admin_yonghu);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("后台管理-详情");
        //获取数据的id 只有在查看和编辑时候才会携带id数据
        mId = getIntent().getLongExtra("beanId", 0);
        initView();
    }

    private void initView() {
//        找控件
        mRecyclerview = findViewById(R.id.recyclerview);

        mLlDianzan = findViewById(R.id.ll_dianzan);
        mIvDianzan = findViewById(R.id.iv_dianzan);
        mTvDianzan = findViewById(R.id.tv_dianzan);
        mLlPinlun = findViewById(R.id.ll_pinlun);
        mTvLiuyan = findViewById(R.id.tv_liuyan);
        mLlShoucang = findViewById(R.id.ll_shoucang);
        mIvShoucang = findViewById(R.id.iv_shoucang);
        mLlLianxi = findViewById(R.id.ll_lianxi);
        TextView tv_msg = findViewById(R.id.tv_msg);
        tv_msg.setText("点击下方预约时间段查看预约的用户信息");
        //视频播放器相关
        mLlvideo = findViewById(R.id.llvideo);


        rl_video_container = findViewById(R.id.rl_video_container);
        videoview = findViewById(R.id.id_videoview);
        id_video_controller = findViewById(R.id.id_video_controller);


        id_video_controller.getTv_title().setText("这是标题");
        id_video_controller.getIv_fan().setVisibility(View.INVISIBLE);
        id_video_controller.getId_fl_video_expand().setVisibility(View.INVISIBLE);
        id_video_controller.getTv_title().setVisibility(View.GONE);
        //        VideoView 只支持播放.mp4和.3GP格式

        mVideoBusiness = new VideoBusiness(this);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int screenWidth = ScreenUtil.getScreenWidth(this);
        setVideoContainerParam(screenWidth, screenWidth * 9 / 16);


        mRecyclerview1 = findViewById(R.id.recyclerview1);


        //绑定Recyclerview适配器
        mMyAdapter = new ImageAdapter(AdminChaKanActivity.this, mContentAndImgBeanList);
        //设置Recyclerview 布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(AdminChaKanActivity.this));
        //  mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //设置Recyclerview适配器
        mRecyclerview.setAdapter(mMyAdapter);


        mTvQita = findViewById(R.id.tv_qita);

        mRecyclerview2 = findViewById(R.id.recyclerview2);
//        设置无需滑动recyclerview列表控件控件 将所有的条目都全部显示 需在布局文件中recyclerview列表控件包裹父布局 如相对布局

        mRecyclerview.setNestedScrollingEnabled(false);
        mRecyclerview1.setNestedScrollingEnabled(false);
        mRecyclerview2.setNestedScrollingEnabled(false);
//        预约日期处理
        mRiQiAdapter = new RiQiAdapter(this, mRiQiBeanList);
        mRecyclerview1.setLayoutManager(new GridLayoutManager(AdminChaKanActivity.this, 3));
        mRecyclerview1.setAdapter(mRiQiAdapter);
        //        预约日期条目点击事件回调
        mRiQiAdapter.setOnClickRiQi(new RiQiAdapter.OnClickRiQi() {
            @Override
            public void OnClickRiQi(int position) {
                mriqi = mRiQiBeanList.get(position).getRiqi();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            // 查当前条目下的所有预约信息
                            List<YuYueBean> yuYueDataByTid = MyHelper.getInstance(AdminChaKanActivity.this).getYuYueDataByTid(mId);
                            mShiJianBeanList = MyHelper.getInstance(AdminChaKanActivity.this).getShiJianById(mId);
//循环遍历该条目下的预约时间段数据 和 查询到的该条目下的所有用户预约数据 2个数据进行比较 筛选出当前点击的日期是否有预约
//   用户预约每条数据里存某预约日期和某个预约时间段的id，  先用预约数据里的某个预约时间段里的id和该条目下所有预约时间段里的id进行比较 ，
//                            如果比较成功 ，说明该预约信息里有这个预约时间段的预约 ，再比较预约信息里的日期和当前点击的日期 ，如果2条都相等则可以确定这个日期里的某个预约时间段已经被预约
//将预约时间段里的某个条目设置为选中状态 更新适配器


                            for (int i = 0; i < mShiJianBeanList.size(); i++) {
                                mShiJianBeanList.get(i).setSelect(false);
                                mShiJianBeanList.get(i).setShengyurenshu(mShiJianBeanList.get(i).getRenshu());

                            }

                            for (int i = 0; i < mShiJianBeanList.size(); i++) {
                                for (int j = 0; j < yuYueDataByTid.size(); j++) {
                                    if (mShiJianBeanList.get(i).getId().equals(yuYueDataByTid.get(j).getShijianid())) {
                                        if (mriqi.equals(yuYueDataByTid.get(j).getRiqi())) {
                                            mShiJianBeanList.get(i).setSelect(true);
                                            mShiJianBeanList.get(i).getUsername().add(yuYueDataByTid.get(j).getUsername());
                                            mShiJianBeanList.get(i).setShengyurenshu(mShiJianBeanList.get(i).getShengyurenshu() - 1);
                                        }
                                    }
                                }
                            }


//更新界面
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mShiJianAdapter.upDate(mShiJianBeanList);
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AdminChaKanActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }).start();
            }
        });
//        预约时间段处理
        mShiJianAdapter = new AdminChaKanShiJianAdapter(AdminChaKanActivity.this, mShiJianBeanList);
        mRecyclerview2.setLayoutManager(new GridLayoutManager(AdminChaKanActivity.this, 2));
        mRecyclerview2.setAdapter(mShiJianAdapter);
        //预约时间段条目点击事件处理

        mShiJianAdapter.setOnClickRiQi(new AdminChaKanShiJianAdapter.OnClickShiJian() {
            @Override
            public void OnClickShiJian(int position) {
                //                如果没有选择日期 提示
                if ("".equals(mriqi)) {
                    Toast.makeText(AdminChaKanActivity.this, "请先选择日期!", Toast.LENGTH_SHORT).show();
                } else {

                    //弹对话框
                    final String[] items = {"查看预约人信息", "添加一条虚拟预约"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminChaKanActivity.this);
                    builder.setTitle("选择方式");
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
//                                查看预约人信息
                                case 0:

                                    Intent intent = new Intent(AdminChaKanActivity.this, AllYuYueActivity.class);
                                    intent.putExtra("tid", mId);
                                    intent.putExtra("shijianid", mShiJianBeanList.get(position).getId());
                                    intent.putExtra("riqi", mriqi);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    //                如果没有选择日期 提示
                                    if ("".equals(mriqi)) {
                                        Toast.makeText(AdminChaKanActivity.this, "请先选择日期!", Toast.LENGTH_SHORT).show();
                                    } else {
//                    如果当前点击的条目是选中状态 说明已经被预约
                                        if (mShiJianBeanList.get(position).getShengyurenshu() <= 0) {
                                            Toast.makeText(AdminChaKanActivity.this, "预约失败，预约人数已满", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

//弹对话框 提示预约信息
                                        new AlertDialog.Builder(AdminChaKanActivity.this)//绑定当前窗口
                                                .setTitle("提示")//设置标题
                                                .setMessage("确定预约吗,将支付 " + mShiJianBeanList.get(position).getJiage() + " 预约费用")//设置提示细信息
                                                .setIcon(R.mipmap.ic_launcher)//设置图标
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //点确定按钮 开线程 删数据
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                try {
//创建一个预约数据类

                                                                    // 查当前条目下的所有预约信息
                                                                    List<YuYueBean> yuYueDataByTid = MyHelper.getInstance(AdminChaKanActivity.this).getYuYueDataByTid(mId);
                                                                    mShiJianBeanList = MyHelper.getInstance(AdminChaKanActivity.this).getShiJianById(mId);

//                                                将预约时间的状态 全部设置为未选中状态
                                                                    for (int i = 0; i < mShiJianBeanList.size(); i++) {
                                                                        mShiJianBeanList.get(i).setSelect(false);
                                                                        mShiJianBeanList.get(i).setShengyurenshu(mShiJianBeanList.get(i).getRenshu());

                                                                    }
//循环遍历该条目下的预约时间段数据 和 查询到的该条目下的所有用户预约数据 2个数据进行比较 筛选出当前点击的日期是否有预约
//   用户预约每条数据里存某预约日期和某个预约时间段的id，  先用预约数据里的某个预约时间段里的id和该条目下所有预约时间段里的id进行比较 ，
//                            如果比较成功 ，说明该预约信息里有这个预约时间段的预约 ，再比较预约信息里的日期和当前点击的日期 ，如果2条都相等则可以确定这个日期里的某个预约时间段已经被预约
//将预约时间段里的某个条目设置为选中状态 更新适配器
                                                                    for (int i = 0; i < mShiJianBeanList.size(); i++) {
                                                                        for (int j = 0; j < yuYueDataByTid.size(); j++) {
                                                                            if (mShiJianBeanList.get(i).getId().equals(yuYueDataByTid.get(j).getShijianid())) {
                                                                                if (mriqi.equals(yuYueDataByTid.get(j).getRiqi())) {
                                                                                    mShiJianBeanList.get(i).setSelect(true);
                                                                                    mShiJianBeanList.get(i).getUsername().add(yuYueDataByTid.get(j).getUsername());
                                                                                    mShiJianBeanList.get(i).setShengyurenshu(mShiJianBeanList.get(i).getShengyurenshu() - 1);

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    if (mShiJianBeanList.get(position).getRenshu() == 0) {
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                new AlertDialog.Builder(AdminChaKanActivity.this)//绑定当前窗口
                                                                                        .setTitle("提示")//设置标题
                                                                                        .setMessage("预约人数已满,请选择其它的日期进行预约")//设置提示细信息
                                                                                        .setIcon(R.mipmap.ic_launcher)//设置图标
                                                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                                            @Override
                                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                                //点确定按钮 开线程 删数据
                                                                                                dialog.dismiss();
                                                                                            }
                                                                                        })//取消按钮什么都不做
                                                                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                                                            @Override
                                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                                dialog.dismiss();

                                                                                            }
                                                                                        })//添加取消按钮
                                                                                        .create()//创建对话框
                                                                                        .show();//显示对话框

                                                                            }
                                                                        });
                                                                    } else {
                                                                        YuYueBean bean = new YuYueBean();
                                                                        bean.setTid(mId);
                                                                        bean.setShijianid(mShiJianBeanList.get(position).getId());
                                                                        bean.setRiqi(mriqi);
                                                                        bean.setUsername(SpUtils.getInstance(AdminChaKanActivity.this).getString("username"));
                                                                        bean.setHaoma(0);
//                                                预约数据写入数据库
                                                                        MyHelper.getInstance(AdminChaKanActivity.this).addYuYueData(bean);


                                                                        // 查当前条目下的所有预约信息
                                                                        yuYueDataByTid = MyHelper.getInstance(AdminChaKanActivity.this).getYuYueDataByTid(mId);
                                                                        mShiJianBeanList = MyHelper.getInstance(AdminChaKanActivity.this).getShiJianById(mId);
                                                                        List<YuYueBean> yuYueDataByTidAndShijianidAndRiqi = MyHelper.getInstance(AdminChaKanActivity.this).getYuYueDataByTidAndShijianidAndRiqi(mId, mShiJianBeanList.get(position).getId(), mriqi);

                                                                        List<Integer> list = new ArrayList<>();
                                                                        for (YuYueBean yuYueBean : yuYueDataByTidAndShijianidAndRiqi) {
                                                                            list.add(yuYueBean.getHaoma());

                                                                        }
                                                                        int haoma = 0;
                                                                        if (list.size() != 0) {
                                                                            haoma = Collections.max(list);
                                                                        }
                                                                        //                                                将预约时间的状态 全部设置为未选中状态
                                                                        for (int i = 0; i < mShiJianBeanList.size(); i++) {
                                                                            mShiJianBeanList.get(i).setSelect(false);
                                                                            mShiJianBeanList.get(i).setShengyurenshu(mShiJianBeanList.get(i).getRenshu());

                                                                        }
//循环遍历该条目下的预约时间段数据 和 查询到的该条目下的所有用户预约数据 2个数据进行比较 筛选出当前点击的日期是否有预约
//   用户预约每条数据里存某预约日期和某个预约时间段的id，  先用预约数据里的某个预约时间段里的id和该条目下所有预约时间段里的id进行比较 ，
//                            如果比较成功 ，说明该预约信息里有这个预约时间段的预约 ，再比较预约信息里的日期和当前点击的日期 ，如果2条都相等则可以确定这个日期里的某个预约时间段已经被预约
//将预约时间段里的某个条目设置为选中状态 更新适配器
                                                                        for (int i = 0; i < mShiJianBeanList.size(); i++) {
                                                                            for (int j = 0; j < yuYueDataByTid.size(); j++) {
                                                                                if (mShiJianBeanList.get(i).getId().equals(yuYueDataByTid.get(j).getShijianid())) {
                                                                                    if (mriqi.equals(yuYueDataByTid.get(j).getRiqi())) {
                                                                                        mShiJianBeanList.get(i).setSelect(true);
                                                                                        mShiJianBeanList.get(i).getUsername().add(yuYueDataByTid.get(j).getUsername());
                                                                                        mShiJianBeanList.get(i).setShengyurenshu(mShiJianBeanList.get(i).getShengyurenshu() - 1);

                                                                                    }
                                                                                }
                                                                            }
                                                                        }


                                                                        bean.setHaoma(haoma + 1);

                                                                        MyHelper.getInstance(AdminChaKanActivity.this).updateYuYue(bean);
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
//                                                        更新适配器 吐丝提示

                                                                                mShiJianAdapter.upDate(mShiJianBeanList);
                                                                                Toast.makeText(AdminChaKanActivity.this, "预约成功", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }

                                                                } catch (Exception e) {
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Toast.makeText(AdminChaKanActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });

                                                                }
                                                            }
                                                        }).start();
                                                    }
                                                })//取消按钮什么都不做
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();

                                                    }
                                                })//添加取消按钮
                                                .create()//创建对话框
                                                .show();//显示对话框
                                    }

                                    break;


                            }
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        });

        //        点赞按钮点击事件
        mLlDianzan.setVisibility(View.GONE);
        mLlShoucang.setVisibility(View.GONE);

        mLlLianxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AdminChaKanActivity.this)//绑定当前窗口
                        .setTitle("联系我们")//设置标题
                        .setMessage("客服电话：888")//设置提示细信息
                        .setIcon(R.mipmap.ic_launcher)//设置图标
                        .setPositiveButton("拨打号码", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:888"));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(AdminChaKanActivity.this, "该设备不支持拨号功能", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })//添加确定按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        })//添加取消按钮
                        .create()//创建对话框
                        .show();//显示对话框
            }
        });
        mLlPinlun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminChaKanActivity.this, ChaKanLiuYanActivity.class);
                intent.putExtra("beanId", mId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoBusiness != null) {

            if (mVideoBusiness.isPause()) {
                mVideoBusiness.resume();
            }


        }
        //开线程
        new Thread(new Runnable() {
            @SuppressLint("WrongConstant")
            @Override
            public void run() {

                try {
                    // 这里是调用耗时操作方法 调用数据方法 根据id查数据
                    mBean = MyHelper.getInstance(AdminChaKanActivity.this).getDataById(mId);
                    // 抓取北京时间
                    URL url = new URL("https://time.tianqi.com");
                    // 连接到我们需要抓取时间的网址
                    URLConnection uc = url.openConnection();
                    uc.connect();
                    // 抓取北京时间，获取到的时间为long类型
                    Long time = uc.getDate();
//处理当前的日期 向后加7天 比较每个日期是否在预约星期内 如果在预约星期内 则将数据添加到list中 生成预约日期数据
// 创建一个适用于中国时区的Calendar对象
                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
                    calendar.setTimeInMillis(time);

// 转换为日期对象
                    Date date = calendar.getTime();

// 格式化日期对象为字符串
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = sdf.format(date);

                    //                    生成7天日期
                    String s = formattedDate; // 将时间戳转换为日期字符串
                    String s1 = DateUtils.addDate(s, 1);
                    String s2 = DateUtils.addDate(s1, 1);
                    String s3 = DateUtils.addDate(s2, 1);
                    String s4 = DateUtils.addDate(s3, 1);
                    String s5 = DateUtils.addDate(s4, 1);
                    String s6 = DateUtils.addDate(s5, 1);
//获取生成日期的星期数
                    String week = DateUtils.getWeek(s);
                    String week1 = DateUtils.getWeek(s1);
                    String week2 = DateUtils.getWeek(s2);
                    String week3 = DateUtils.getWeek(s3);
                    String week4 = DateUtils.getWeek(s4);
                    String week5 = DateUtils.getWeek(s5);
                    String week6 = DateUtils.getWeek(s6);


                    //切换ui线程
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //不为空 则把数据显示在对应的控件上
                            if (mBean != null) {

                                String xingqi = mBean.getXingqi();
                                String qita = mBean.getQita();
                                if ("".equals(qita)) {
                                    mTvQita.setVisibility(View.GONE);
                                }
                                mTvQita.setText("简介/擅长: " + qita);


                                //                                比较星期数据和生成的日期 是否在预约信息内 如果在则将该日期添加到list中 显示到预约日期上
                                mRiQiBeanList.clear();
                                if (xingqi.contains(week)) {
                                    mRiQiBeanList.add(new RiQiBean(s));
                                }
                                if (xingqi.contains(week1)) {
                                    mRiQiBeanList.add(new RiQiBean(s1));
                                }
                                if (xingqi.contains(week2)) {
                                    mRiQiBeanList.add(new RiQiBean(s2));
                                }
                                if (xingqi.contains(week3)) {
                                    mRiQiBeanList.add(new RiQiBean(s3));
                                }
                                if (xingqi.contains(week4)) {
                                    mRiQiBeanList.add(new RiQiBean(s4));
                                }
                                if (xingqi.contains(week5)) {
                                    mRiQiBeanList.add(new RiQiBean(s5));
                                }
                                if (xingqi.contains(week6)) {
                                    mRiQiBeanList.add(new RiQiBean(s6));
                                }

//更新日期列表控件 显示预约日期数据

                                mRiQiAdapter.upDate(mRiQiBeanList);
//展示图片数据
                                try {
                                    //                            解析json数据
                                    mContentAndImgBeanList.clear();
                                    JSONArray jsonArray1 = new JSONArray(mBean.getContent());
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                                        ContentAndImgBean contentAndImgBean = new ContentAndImgBean();
                                        contentAndImgBean.setContent(jsonObject2.getString("content"));
                                        contentAndImgBean.setImg(jsonObject2.getString("img"));
                                        mContentAndImgBeanList.add(contentAndImgBean);
                                    }

//                                更新数据显示到界面
                                    mMyAdapter.upDate(mContentAndImgBeanList);
                                } catch (Exception e) {
                                    Toast.makeText(AdminChaKanActivity.this, "图片解析错误", Toast.LENGTH_SHORT).show();
                                }
//设置视频播放内容
                                if (mBean.getShipin() != null) {
                                    String shipin = mBean.getShipin();
                                    if (shipin.contains(".")) {
                                        String suffix = shipin.substring(shipin.lastIndexOf("."));
                                        if (".mp4".equals(suffix)) {
                                            Uri uri = Uri.parse(Config.HTTP_BASE_URL + shipin);
                                            mVideoBusiness.initVideo(videoview, id_video_controller, uri);
                                            mLlvideo.setVisibility(View.VISIBLE);

                                        } else {
                                            Toast.makeText(AdminChaKanActivity.this, "仅支持.mp4格式视频", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }


                            }

                        }
                    });


                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AdminChaKanActivity.this, "未知错误", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 查预约时间段数据
                    mShiJianBeanList = MyHelper.getInstance(AdminChaKanActivity.this).getShiJianById(mId);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//更新界面
                            mShiJianAdapter.upDate(mShiJianBeanList);

                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AdminChaKanActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 这里是调用耗时操作方法 判断是否收藏 更新收藏部分的图片和文字
                    boolean is_shoucang = MyHelper.getInstance(AdminChaKanActivity.this).isShouCang(SpUtils.getInstance(AdminChaKanActivity.this).getString("username"), mId);
                    boolean is_dianzan = MyHelper.getInstance(AdminChaKanActivity.this).isDianZan(SpUtils.getInstance(AdminChaKanActivity.this).getString("username"), mId);
                    Integer userDianZan = MyHelper.getInstance(AdminChaKanActivity.this).getItemDianZan(mId);
                    List<RecoverBean> allRecoverData = MyHelper.getInstance(AdminChaKanActivity.this).getAllRecoverData(mId);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (is_shoucang) {
                                mIvShoucang.setImageResource(R.mipmap.sc_y);
                            } else {
                                mIvShoucang.setImageResource(R.mipmap.sc_n);
                            }

                            if (is_dianzan) {
                                mIvDianzan.setImageResource(R.mipmap.dianzan_y);
                            } else {
                                mIvDianzan.setImageResource(R.mipmap.dianzan_n);
                            }
                            mTvDianzan.setText(userDianZan + "");
                            mTvLiuyan.setText(allRecoverData.size() + "");
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AdminChaKanActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();
    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    //菜单栏 点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        返回箭头
        if (android.R.id.home == item.getItemId()) {
            finish();
        }

//        修改按钮 跳转界面
        if (item.getItemId() == R.id.id1) {
            Intent intent = new Intent(AdminChaKanActivity.this, AddAndEditActivity.class);
            intent.putExtra("type", "edit");
            intent.putExtra("beanId", mId);
            startActivity(intent);
            finish();
        }
//删除按钮
        if (item.getItemId() == R.id.id2) {
//            弹对话框
            new AlertDialog.Builder(AdminChaKanActivity.this)//绑定当前窗口
                    .setTitle("提示")//设置标题
                    .setMessage("确定删除吗")//设置提示细信息
                    .setIcon(R.mipmap.ic_launcher)//设置图标
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //点确定按钮 开线程 删数据
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        // 这里是调用耗时操作方法 删主信息数据
                                        int result = MyHelper.getInstance(AdminChaKanActivity.this).delDataById(mId);
//                                        删除该条目的预约时间段数据
                                        MyHelper.getInstance(AdminChaKanActivity.this).delShiJianByTid(mId);
//                                        删除该条目下的预约数据
                                        MyHelper.getInstance(AdminChaKanActivity.this).delYuYueByTid(mId);
//                                        删收藏
                                        MyHelper.getInstance(AdminChaKanActivity.this).delShouCangTid(mId);
//                                        删点赞
                                        MyHelper.getInstance(AdminChaKanActivity.this).delDianZanTid(mId);
//                                       删留言
                                        MyHelper.getInstance(AdminChaKanActivity.this).delLiuYanTid(mId);
                                        //切ui线程  判断删除结果 更新界面
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (result != -1) {
                                                    Toast.makeText(AdminChaKanActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(AdminChaKanActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    } catch (Exception e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(AdminChaKanActivity.this, "未知错误", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                }
                            }).start();
                        }
                    })//取消按钮什么都不做
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    })//添加取消按钮
                    .create()//创建对话框
                    .show();//显示对话框
        }

        return true;
    }

    //视频比例
    private void setVideoContainerParam(int w, int h) {
        ViewGroup.LayoutParams params = rl_video_container.getLayoutParams();
        params.width = w;
        params.height = h;
        rl_video_container.setLayoutParams(params);
    }

    //退出 释放视频资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoBusiness != null) {
            mVideoBusiness.release();
        }
    }

    //界面不可见 暂停播放视频
    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoBusiness != null) {


            mVideoBusiness.pause();

        }
    }
}