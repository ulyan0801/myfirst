package com.my.myapplication.admin.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.admin.adapter.AdminAddAndEditImageAdapter;
import com.my.myapplication.admin.adapter.AdminAddAndEditShiJianAdapter;
import com.my.myapplication.bean.AppointmentBean;
import com.my.myapplication.bean.ContentAndImgBean;
import com.my.myapplication.bean.ShiJianBean;
import com.my.myapplication.customview.LoadProgressDialog;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.FileUtils;
import com.my.myapplication.utils.PathUirls;
import com.my.myapplication.utils.PermissionUtils;
import com.my.myapplication.utils.ScreenUtil;
import com.my.myapplication.utils.global.Config;
import com.my.myapplication.video.MyVideoView;
import com.my.myapplication.video.VideoBusiness;
import com.my.myapplication.video.VideoController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//添加 编辑 页 共用
public class AddAndEditActivity extends AppCompatActivity {
    //各种权限申请回调
    public static final int PHOTO_REQUEST_CODE = 1;
    public static final int SD_REQUEST_CODE = 2;
    public static final int CAMERA_PHOTO_REQUEST_CODE = 3;

    public static final int ADDYUYUESHIJIAN = 4;

    public static final int XUANZESHIPIN = 6;
    public static final int PAISHIPIN = 7;


    //写入编辑按钮
    private Button mBtWrite;
    //存视频地址数据
    private String shipin = "";


    //类型 判断是添加 还是编辑修改
    private String type = "";
    //数据的id
    private Long mId;
    private AppointmentBean mBean;

    private TextView mTvAddimg;

    private ImageView mIvShanchu;
    private ImageView mIvShipin;

    //视频播放器相关
    private RelativeLayout rl_video_container;
    private MyVideoView videoview;
    private VideoController id_video_controller;
    private VideoBusiness mVideoBusiness;

    private LinearLayout mLlvideo;
    private Spinner mSpinOne1;

    private EditText mEtQita;

    private CheckBox mCb1;
    private CheckBox mCb2;
    private CheckBox mCb3;
    private CheckBox mCb4;
    private CheckBox mCb5;
    private CheckBox mCb6;
    private CheckBox mCb7;
    private RecyclerView mRecyclerview;
    private TextView mTvAddshijian;
    private RecyclerView mRecyclerview1;

    //recyclerview 适配器
    private AdminAddAndEditImageAdapter mMyAdapter;
    private AdminAddAndEditShiJianAdapter mItemAdapter;
    //recyclerview 数据
    private List<ContentAndImgBean> mBeanList = new ArrayList<>();

    private List<ShiJianBean> mItemBeanList = new ArrayList<>();
    private EditText mEtXingming;
    private Spinner mSpinOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("后台管理-新增");

        initView();
    }

    private void initView() {
//        找控件
        mBtWrite = findViewById(R.id.bt_write);
        mTvAddimg = findViewById(R.id.tv_addimg);
        mIvShanchu = findViewById(R.id.iv_shanchu);
        mIvShipin = findViewById(R.id.iv_shipin);
        mSpinOne1 = findViewById(R.id.spin_one1);
        mEtQita = findViewById(R.id.et_qita);
        mRecyclerview = findViewById(R.id.recyclerview);
        mTvAddshijian = findViewById(R.id.tv_addshijian);
        mRecyclerview1 = findViewById(R.id.recyclerview1);
        mEtXingming = findViewById(R.id.et_xingming);
        mSpinOne = findViewById(R.id.spin_one);
        mCb1 = findViewById(R.id.cb_1);
        mCb2 = findViewById(R.id.cb_2);
        mCb3 = findViewById(R.id.cb_3);
        mCb4 = findViewById(R.id.cb_4);
        mCb5 = findViewById(R.id.cb_5);
        mCb6 = findViewById(R.id.cb_6);
        mCb7 = findViewById(R.id.cb_7);

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
//        设置Recyclerview是否启用嵌套滚动
        mRecyclerview.setNestedScrollingEnabled(false);
        mRecyclerview1.setNestedScrollingEnabled(false);


        //绑定Recyclerview适配器
        mMyAdapter = new AdminAddAndEditImageAdapter(AddAndEditActivity.this, mBeanList);
        //设置Recyclerview 布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(AddAndEditActivity.this));
        //  mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //设置Recyclerview适配器
        mRecyclerview.setAdapter(mMyAdapter);


        //绑定Recyclerview适配器
        mItemAdapter = new AdminAddAndEditShiJianAdapter(AddAndEditActivity.this, mItemBeanList);
        //设置Recyclerview 布局管理器
        // mRecyclerview1.setLayoutManager(new LinearLayoutManager(AddAndEditActivity.this));
        mRecyclerview1.setLayoutManager(new GridLayoutManager(this, 2));
        //设置Recyclerview适配器
        mRecyclerview1.setAdapter(mItemAdapter);
//删除图片 按钮点击 移除list中数据 更新界面
        mItemAdapter.setOnDeleteItemClick(new AdminAddAndEditShiJianAdapter.OnDeleteItemClick() {
            @Override
            public void OnDeleteItemClick(int position) {
                mItemBeanList.remove(position);
                mItemAdapter.upDate(mItemBeanList);
            }
        });

//        添加预约时间段按钮点击 跳转添加预约时间段界面 添加预约时间段界面会将数据回传到该界面
        mTvAddshijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAndEditActivity.this, AdminAddYuYueShiJianActivity.class);

                startActivityForResult(intent, ADDYUYUESHIJIAN);
            }
        });

        //        选择视频点击事件
        mIvShipin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹对话框 选择
                final String[] items = {"选择视频", "拍视频"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddAndEditActivity.this);
                builder.setTitle("选择方式");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        switch (which) {
//选择视频 跳转到手机存储
                            case 0:
//                                跳转选择视频
                                intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
                                startActivityForResult(intent, XUANZESHIPIN);
                                break;
                            case 1:
                                //拍照跳 拍视频
                                intent = new Intent();
                                intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);//相机action
                                startActivityForResult(intent, PAISHIPIN);
                                break;

                        }
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        //        添加图片按钮点击
        mTvAddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //弹对话框 是选择还是拍照
                final String[] items = {"相册", "拍照"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddAndEditActivity.this);
                builder.setTitle("选择方式");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        switch (which) {
//选择图片 跳转到相册
                            case 0:
                                intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, PHOTO_REQUEST_CODE);
                                break;
                            case 1:
                                //拍照跳 拍照
                                intent = new Intent();
                                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//相机action
                                startActivityForResult(intent, CAMERA_PHOTO_REQUEST_CODE);
                                break;

                        }
                        dialog.dismiss();
                    }
                });
                builder.create().show();

            }
        });
//删除视频按钮点击
        mIvShanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                暂停视频播放
                if (mVideoBusiness != null) {


                    mVideoBusiness.pause();

                }
//                视频地址设置为空
                shipin = "";
//                设置一下控件可见状态
                mLlvideo.setVisibility(View.GONE);
                mIvShipin.setVisibility(View.VISIBLE);
                mIvShanchu.setVisibility(View.GONE);
            }
        });

//        //得到类型 判断是新增 添加还是编辑
        type = getIntent().getStringExtra("type");
//根据类型判断是 编辑还是新增 编辑 按钮的是否可见
        if ("edit".equals(type)) {
            Toast.makeText(AddAndEditActivity.this, "修改后，所有该条数据的预约将会被重置", Toast.LENGTH_SHORT).show();

            //类型是编辑
            mBtWrite.setText("编辑");
            getSupportActionBar().setTitle("后台管理-编辑");
            //获取数据的id 只有在辑时候才会携带id数据
            mId = getIntent().getLongExtra("beanId", 0);
            //开线程 根据id查对应的数据
            new Thread(new Runnable() {
                @SuppressLint("WrongConstant")
                @Override
                public void run() {

                    try {
                        // 这里是调用耗时操作方法 调用数据方法 根据id查数据
                        mBean = MyHelper.getInstance(AddAndEditActivity.this).getDataById(mId);
                        //切换ui线程
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //不为空 则把数据显示在对应的控件上
                                if (mBean != null) {
//                                    获取类型所在的位置 绑定下拉控件 spinner控件默认选中选项
                                    mEtXingming.setText(mBean.getXingming());
                                    mSpinOne1.setSelection(findString(getResources().getStringArray(R.array.data1), mBean.getLeixing()));
                                    mSpinOne.setSelection(findString(getResources().getStringArray(R.array.data), mBean.getLeixingf()));


//                                    绑定星期复选按钮的选中状态
                                    if (mBean.getXingqi().contains(mCb1.getText().toString())) {
                                        mCb1.setChecked(true);
                                    } else {
                                        mCb1.setChecked(false);
                                    }
                                    if (mBean.getXingqi().contains(mCb2.getText().toString())) {
                                        mCb2.setChecked(true);
                                    } else {
                                        mCb2.setChecked(false);
                                    }

                                    if (mBean.getXingqi().contains(mCb3.getText().toString())) {
                                        mCb3.setChecked(true);
                                    } else {
                                        mCb3.setChecked(false);
                                    }

                                    if (mBean.getXingqi().contains(mCb4.getText().toString())) {
                                        mCb4.setChecked(true);
                                    } else {
                                        mCb4.setChecked(false);
                                    }
                                    if (mBean.getXingqi().contains(mCb5.getText().toString())) {
                                        mCb5.setChecked(true);
                                    } else {
                                        mCb5.setChecked(false);
                                    }

                                    if (mBean.getXingqi().contains(mCb6.getText().toString())) {
                                        mCb6.setChecked(true);
                                    } else {
                                        mCb6.setChecked(false);
                                    }

                                    if (mBean.getXingqi().contains(mCb7.getText().toString())) {
                                        mCb7.setChecked(true);
                                    } else {
                                        mCb7.setChecked(false);
                                    }
                                    mEtQita.setText(mBean.getQita());

//设置视频播放内容
                                    if (mBean.getShipin() != null) {
                                        shipin = mBean.getShipin();
                                        if (shipin.contains(".")) {
                                            String suffix = shipin.substring(shipin.lastIndexOf("."));
                                            if (".mp4".equals(suffix)) {
                                                Uri uri = Uri.parse(Config.HTTP_BASE_URL + shipin);
                                                mVideoBusiness.initVideo(videoview, id_video_controller, uri);
                                                mLlvideo.setVisibility(View.VISIBLE);
                                                mIvShipin.setVisibility(View.GONE);
                                                mIvShanchu.setVisibility(View.VISIBLE);
                                            } else {
                                                Toast.makeText(AddAndEditActivity.this, "仅支持.mp4格式视频", Toast.LENGTH_SHORT).show();
                                            }
                                        }


                                    }

//解析图片数据
                                    try {
                                        //                            解析json数据
                                        mBeanList.clear();
                                        JSONArray jsonArray1 = new JSONArray(mBean.getContent());
                                        for (int i = 0; i < jsonArray1.length(); i++) {
                                            JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                                            ContentAndImgBean contentAndImgBean = new ContentAndImgBean();
                                            contentAndImgBean.setContent(jsonObject2.getString("content"));
                                            contentAndImgBean.setImg(jsonObject2.getString("img"));
                                            mBeanList.add(contentAndImgBean);
                                        }

//                                更新数据显示到界面
                                        mMyAdapter.upDate(mBeanList);
                                    } catch (Exception e) {
                                        mBean.setContent("[]");
                                    }


                                } else {
                                    Toast.makeText(AddAndEditActivity.this, "数据查找失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddAndEditActivity.this, "未知错误", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }
            }).start();
//查预约时间段数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 这里是调用耗时操作方法 查预约时间段数据
                        mItemBeanList = MyHelper.getInstance(AddAndEditActivity.this).getShiJianById(mId);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                更新界面
                                mItemAdapter.upDate(mItemBeanList);
                            }
                        });
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddAndEditActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }).start();
        }

//申请权限 sd存储权限 拍照权限
        requestPermissions();


//写入编辑按钮 点击事件
        mBtWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //获取用户填写数据 校验数据

                String Qita = mEtQita.getText().toString().trim();
                String Xingming = mEtXingming.getText().toString().trim();


                if (TextUtils.isEmpty(Xingming)) {
                    Toast.makeText(AddAndEditActivity.this, "医师姓名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Qita)) {
                    Toast.makeText(AddAndEditActivity.this, "医师简介/擅长不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mItemBeanList.size() == 0) {
                    Toast.makeText(AddAndEditActivity.this, "预约时间段不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

//创建数据类
                AppointmentBean bean = new AppointmentBean();


//以下 绑定数据
                bean.setShipin(shipin);
                bean.setXingming(Xingming);
                bean.setQita(Qita);
                bean.setLeixing(mSpinOne1.getSelectedItem().toString());
                bean.setLeixingf(mSpinOne.getSelectedItem().toString());


                String xingqi = "";
                if (mCb1.isChecked()) {
                    xingqi = xingqi + mCb1.getText().toString() + ",";
                }

                if (mCb2.isChecked()) {
                    xingqi = xingqi + mCb2.getText().toString() + ",";
                }
                if (mCb3.isChecked()) {
                    xingqi = xingqi + mCb3.getText().toString() + ",";
                }
                if (mCb4.isChecked()) {
                    xingqi = xingqi + mCb4.getText().toString() + ",";
                }
                if (mCb5.isChecked()) {
                    xingqi = xingqi + mCb5.getText().toString() + ",";
                }
                if (mCb6.isChecked()) {
                    xingqi = xingqi + mCb6.getText().toString() + ",";
                }
                if (mCb7.isChecked()) {
                    xingqi = xingqi + mCb7.getText().toString();
                }
                bean.setXingqi(xingqi);
//如果没有添加图片 则将图片数据设置为空
                if (mBeanList.size() == 0) {
                    bean.setContent("[]");
                    Toast.makeText(AddAndEditActivity.this, "医师介绍图片最少一张", Toast.LENGTH_SHORT).show();
                    return;
                } else {
//如果有添加图片 则把图片数据转换成json字符
                    try {
                        // 这里是调用耗时操作方法  数据转换成json
                        JSONArray jsonArray = new JSONArray();
                        JSONObject tmpObj = null;
                        int count = mBeanList.size();
                        for (int i = 0; i < count; i++) {
                            tmpObj = new JSONObject();
                            tmpObj.put("content", mBeanList.get(i).getContent());
                            tmpObj.put("img", mBeanList.get(i).getImg());
                            jsonArray.put(tmpObj);
                        }
                        String infos = jsonArray.toString(); // 将JSONArray转换得到String
                        bean.setContent(infos);


                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddAndEditActivity.this, "未知错误", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }

                //判断类型 是新增 还是编辑修改
                if ("add".equals(type)) {
                    //新增 开线程
                    new Thread(new Runnable() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {
                            try {
                                // 这里是调用耗时操作方法  调数据库方法插入新的数据 返回值是当前插入数据的id
                                long result = MyHelper.getInstance(AddAndEditActivity.this).addData(bean);
//根据返回上方id值 循环遍历预约时间段数据   写入到数据库
                                for (int i = 0; i < mItemBeanList.size(); i++) {
                                    ShiJianBean bean1 = new ShiJianBean();
//                                    上方返回的id
                                    bean1.setTid(result);
                                    bean1.setBianhao(i);
                                    bean1.setRenshu(mItemBeanList.get(i).getRenshu());
                                    bean1.setYuyueshijian(mItemBeanList.get(i).getYuyueshijian());
                                    bean1.setJiage(mItemBeanList.get(i).getJiage());
                                    MyHelper.getInstance(AddAndEditActivity.this).addShiJianData(bean1);
                                }

                                //判断结果 提示用户 销毁当前窗口
                                if (result != 0) {
                                    //切ui线程
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddAndEditActivity.this, "新增成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                } else {
                                    //切ui线程
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddAndEditActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }


                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddAndEditActivity.this, "未知错误", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }

                        }
                    }).start();
                }
                //编辑
                if ("edit".equals(type)) {
                    //设置id
                    bean.setId(mId);
                    new Thread(new Runnable() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {
                            try {
//                              删预约时间段数据
                                MyHelper.getInstance(AddAndEditActivity.this).delShiJianByTid(mId);
//                                删该条目下的预约数据
                                MyHelper.getInstance(AddAndEditActivity.this).delYuYueByTid(mId);

                                // 调数据库方法更新数据
                                MyHelper.getInstance(AddAndEditActivity.this).updateDataById(bean);
//                                重新循序预约时间段数据 写数据库
                                for (int i = 0; i < mItemBeanList.size(); i++) {
                                    ShiJianBean bean1 = new ShiJianBean();
                                    bean1.setTid(mId);
                                    bean1.setBianhao(i);
                                    bean1.setRenshu(mItemBeanList.get(i).getRenshu());
                                    bean1.setYuyueshijian(mItemBeanList.get(i).getYuyueshijian());
                                    bean1.setJiage(mItemBeanList.get(i).getJiage());
                                    MyHelper.getInstance(AddAndEditActivity.this).addShiJianData(bean1);
                                }

                                //切ui线程
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddAndEditActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddAndEditActivity.this, "未知错误", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }
                    }).start();
                }


            }
        });


    }

    public int findString(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
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

    //权限申请
    public void requestPermissions() {
        PermissionUtils.requestPermissions(this, PermissionUtils.PERMISSIONS, 1,
                new PermissionUtils.OnPermissionListener() {//实现接口方法

                    @Override
                    public void onPermissionGranted() {//获取权限成功
                        // 先判断有没有权限
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            // 先判断有没有权限
                            if (Environment.isExternalStorageManager()) {
                                //大于等于安卓11 并且同意了所有权限 做某事
                            } else {
                                //大于等于安卓11 没有权限 跳转到权限赋予界面
                                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivityForResult(intent, SD_REQUEST_CODE);
                            }
                        } else {
                            //小等于安卓11  做某事
                        }
                    }

                    @Override
                    public void onPermissionDenied() {//获取权限失败
                        Toast.makeText(getApplicationContext(), "拒绝权限", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //再次判断如果大于等于安卓11跳到权限界面 返回是否赋予权限
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SD_REQUEST_CODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 检查是否有权限
            if (Environment.isExternalStorageManager()) {
                // 授权成功

            } else {
                Toast.makeText(this, "用户拒绝了SD存储权限", Toast.LENGTH_SHORT).show();
                // 授权失败
            }
        }

        if (resultCode == Activity.RESULT_OK) {
//            预约时间段界面 返回的预约时间数据
            if (requestCode == ADDYUYUESHIJIAN) {
//                接收 校验 添加到list中 更新界面
                ShiJianBean itemBean = (ShiJianBean) data.getSerializableExtra("itemBean");
                if (itemBean != null) {
                    mItemBeanList.add(itemBean);
                    mItemAdapter.upDate(mItemBeanList);
                }

            }

            //选择照片返回 内容部分选择图片
            if (requestCode == PHOTO_REQUEST_CODE) {
                //得到选择的图片的真实地址
                String filePathFromUri = PathUirls.getFilePathFromUri(AddAndEditActivity.this, data.getData());
                //判断图片是否存在 如模拟器用as的工具删sd卡的图片的话 图片即使删除 但是模拟器相册中还是会显示
                if (!new File(filePathFromUri).exists()) {
                    new AlertDialog.Builder(AddAndEditActivity.this)//绑定当前窗口
                            .setCancelable(false)
                            .setTitle("错误信息")//设置标题
                            .setMessage("图片不存在,请重新选择,或者长按模拟器电源键选择restart重启模拟器,不是直接poweroff")//设置提示细信息
                            .setIcon(R.mipmap.ic_launcher)//设置图标
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })//添加确定按钮
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            })//添加取消按钮
                            .create()//创建对话框
                            .show();//显示对话框
                    return;
                }
                //判断选择图片的大小
                long fileSize = FileUtils.getFileSize(filePathFromUri);
                //如果超过限制 则弹对话框提示用户
                if (fileSize / 1024 > FileUtils.IMAGE_MAX_SIZE) {
                    new AlertDialog.Builder(AddAndEditActivity.this)//绑定当前窗口
                            .setCancelable(false)
                            .setTitle("错误信息")//设置标题
                            .setMessage("图片大小最大限制" + FileUtils.IMAGE_MAX_SIZE + "kb")//设置提示细信息
                            .setIcon(R.mipmap.ic_launcher)//设置图标
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })//添加确定按钮
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            })//添加取消按钮
                            .create()//创建对话框
                            .show();//显示对话框
                } else {
                    try {
                        //                    上传图片到服务器 服务器返回图片地址 显示到控件
                        LoadProgressDialog loadProgressDialog = new LoadProgressDialog(AddAndEditActivity.this, "上传中...", false);
                        loadProgressDialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    // 这里是调用耗时操作方法
                                    String imgUrl = MyHelper.getInstance(AddAndEditActivity.this).oneUploadFile(FileUtils.getPath(filePathFromUri));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //                    内容部分拍摄图片后 绑定数据 更新界面
                                            ContentAndImgBean contentAndImgBean = new ContentAndImgBean();
                                            contentAndImgBean.setImg(imgUrl);
                                            contentAndImgBean.setContent("");
                                            mBeanList.add(contentAndImgBean);
                                            mMyAdapter.upDate(mBeanList);
                                            clearFocus();
                                            loadProgressDialog.dismiss();
                                        }
                                    });
                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddAndEditActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                                            loadProgressDialog.dismiss();
                                        }
                                    });

                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        Toast.makeText(this, "图片处理错误，请重新选择图片", Toast.LENGTH_SHORT).show();
                    }
                }

            }


            //拍照回调 返回 逻辑同上 内容部分拍摄图片
            if (requestCode == CAMERA_PHOTO_REQUEST_CODE) {
                /**
                 * 通过这种方法取出的拍摄会默认压缩，因为如果相机的像素比较高拍摄出来的图会比较高清，
                 * 如果图太大会造成内存溢出（OOM），因此此种方法会默认给图片尽心压缩
                 */


                String takePhotoPath = PathUirls.getTakePhotoPath(this, data);
                long fileSize = FileUtils.getFileSize(takePhotoPath);
                if (fileSize / 1024 > FileUtils.IMAGE_MAX_SIZE) {
                    new AlertDialog.Builder(AddAndEditActivity.this)//绑定当前窗口
                            .setCancelable(false)
                            .setTitle("错误信息")//设置标题
                            .setMessage("图片大小最大限制" + FileUtils.IMAGE_MAX_SIZE + "kb")//设置提示细信息
                            .setIcon(R.mipmap.ic_launcher)//设置图标
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })//添加确定按钮
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            })//添加取消按钮
                            .create()//创建对话框
                            .show();//显示对话框
                } else {
                    try {
                        LoadProgressDialog loadProgressDialog = new LoadProgressDialog(AddAndEditActivity.this, "上传中...", false);
                        loadProgressDialog.show();
                        //                    上传图片到服务器 服务器返回图片地址 显示到控件
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 这里是调用耗时操作方法
                                    String imgUrl = MyHelper.getInstance(AddAndEditActivity.this).oneUploadFile(FileUtils.getPath(takePhotoPath));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //                    内容部分拍摄图片后 绑定数据 更新界面
                                            ContentAndImgBean contentAndImgBean = new ContentAndImgBean();
                                            contentAndImgBean.setImg(imgUrl);
                                            contentAndImgBean.setContent("");
                                            mBeanList.add(contentAndImgBean);
                                            mMyAdapter.upDate(mBeanList);
                                            clearFocus();
                                            loadProgressDialog.dismiss();
                                        }
                                    });
                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddAndEditActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                                            loadProgressDialog.dismiss();
                                        }
                                    });

                                }
                            }
                        }).start();

                    } catch (Exception e) {
                        Toast.makeText(this, "图片处理错误，请重新选择图片", Toast.LENGTH_SHORT).show();
                    }


                }


            }


            //拍视频或选择视频
            if (requestCode == PAISHIPIN || requestCode == XUANZESHIPIN) {
                String filePathFromUri = PathUirls.getFilePathFromUri(this, data.getData());
                if (filePathFromUri.contains(".")) {
                    String suffix = filePathFromUri.substring(filePathFromUri.lastIndexOf("."));
                    if (".mp4".equals(suffix)) {
                        //创建LoadProgressDialog
                        LoadProgressDialog loadProgressDialog = new LoadProgressDialog(AddAndEditActivity.this, "上传中...", false);
                        loadProgressDialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    // 这里是调用耗时操作方法
                                    String imgUrl = MyHelper.getInstance(AddAndEditActivity.this).oneUploadFile(filePathFromUri);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            shipin = imgUrl;
                                            Uri uri = Uri.parse(Config.HTTP_BASE_URL + imgUrl);

                                            mVideoBusiness.initVideo(videoview, id_video_controller, uri);
                                            mLlvideo.setVisibility(View.VISIBLE);
                                            mIvShipin.setVisibility(View.GONE);
                                            mIvShanchu.setVisibility(View.VISIBLE);
                                            clearFocus();
                                            loadProgressDialog.dismiss();
                                        }
                                    });
                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddAndEditActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                                            loadProgressDialog.dismiss();
                                        }
                                    });

                                }
                            }
                        }).start();


                    } else {
                        Toast.makeText(this, "仅支持.mp4后缀视频", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "仅支持.mp4后缀视频", Toast.LENGTH_SHORT).show();
                }

            }

        } else {

            // Toast.makeText(this, "没有选择内容", Toast.LENGTH_SHORT).show();

        }

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

    //界面重新可见 继续播放视频
    @Override
    protected void onResume() {
        super.onResume();
        clearFocus();
        if (mVideoBusiness != null) {

            if (mVideoBusiness.isPause()) {
                mVideoBusiness.resume();
            }


        }

    }

    //清除控件的焦点
    public void clearFocus() {
        mEtQita.clearFocus();
        mEtXingming.clearFocus();
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