package com.my.myapplication.yonghu.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.ContentAndImgBean;
import com.my.myapplication.bean.RecoverBean;
import com.my.myapplication.customview.LoadProgressDialog;
import com.my.myapplication.customview.MyDialog;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.FileUtils;
import com.my.myapplication.utils.PathUirls;
import com.my.myapplication.utils.PermissionUtils;
import com.my.myapplication.utils.SpUtils;
import com.my.myapplication.yonghu.adapter.LiuYanAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//发表留言
public class FaBiaoLiuYanActivity extends AppCompatActivity {
    //各种权限申请回调
    public static final int PHOTO_REQUEST_CODE = 1;
    public static final int SD_REQUEST_CODE = 2;
    public static final int CAMERA_PHOTO_REQUEST_CODE = 3;
    //recyclerview 数据
    private final List<ContentAndImgBean> mBeanList = new ArrayList<>();
    //写入编辑按钮
    private Button mBtWrite;
    //存图片地址数据
    private String tupian = "";
    //数据的id
    private Long mId;
    private TextView mTvAddtext;
    private TextView mTvAddimg;
    private RecyclerView mRecyclerview;
    //recyclerview 适配器
    private LiuYanAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_biao_liu_yan);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("发表留言");
        //获取数据的id 只有在查看和编辑时候才会携带id数据
        mId = getIntent().getLongExtra("beanId", 0);
        initView();
    }

    private void initView() {
//        找控件
        mBtWrite = findViewById(R.id.bt_write);
        mTvAddtext = findViewById(R.id.tv_addtext);
        mTvAddimg = findViewById(R.id.tv_addimg);
        mRecyclerview = findViewById(R.id.recyclerview);
        //绑定Recyclerview适配器
        mMyAdapter = new LiuYanAdapter(FaBiaoLiuYanActivity.this, mBeanList);
        //设置Recyclerview 布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(FaBiaoLiuYanActivity.this));
        //  mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //设置Recyclerview适配器
        mRecyclerview.setAdapter(mMyAdapter);

//        添加内容文字按钮点击
        mTvAddtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDialog(FaBiaoLiuYanActivity.this, R.style.dialog, "请输文字内容", new MyDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, String content) {
//                        点击确定
                        if (confirm) {
//                            校验数据
                            if ("".equals(content)) {
                                Toast.makeText(FaBiaoLiuYanActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();

                                return;
                            }
//                            创建数据 添加到数据库
                            ContentAndImgBean contentAndImgBean = new ContentAndImgBean();
                            contentAndImgBean.setImg("");
                            contentAndImgBean.setContent(content);
                            mBeanList.add(contentAndImgBean);
                            mMyAdapter.upDate(mBeanList);
                            dialog.dismiss();

                        } else {
//点击取消按钮
                            dialog.dismiss();
                        }
                    }

                })
                        .setTitle("请输文字内容")
                        .setNegativeButton("取消")
                        .setPositiveButton("确定")

                        .show();
            }
        });
        //        添加内容图片按钮点击
        mTvAddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //弹对话框 是选择还是拍照
                final String[] items = {"相册", "拍照",};
                AlertDialog.Builder builder = new AlertDialog.Builder(FaBiaoLiuYanActivity.this);
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


//申请权限 sd存储权限 拍照权限
        requestPermissions();


//写入编辑按钮 点击事件
        mBtWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBeanList.size() == 0) {
                    Toast.makeText(FaBiaoLiuYanActivity.this, "留言内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
//创建数据类
                RecoverBean bean = new RecoverBean();
                bean.setPid(mId);
                bean.setName(SpUtils.getInstance(FaBiaoLiuYanActivity.this).getString("username"));
                //新增 开线程
                new Thread(new Runnable() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void run() {

                        try {
                            // 这里是调用耗时操作方法  调数据库方法插入新的数据 数据转换成json
                            JSONArray jsonArray = new JSONArray();
                            JSONObject tmpObj = null;
                            int count = mBeanList.size();
                            for (int i = 0; i < count; i++) {
                                tmpObj = new JSONObject();
                                tmpObj.put("content", mBeanList.get(i).getContent());
                                tmpObj.put("img", mBeanList.get(i).getImg());
                                jsonArray.put(tmpObj);
                            }
                            String personInfos = jsonArray.toString(); // 将JSONArray转换得到String
                            bean.setContent(personInfos);
                            MyHelper.getInstance(FaBiaoLiuYanActivity.this).addRecoverData(bean);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FaBiaoLiuYanActivity.this, "留言成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });

                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FaBiaoLiuYanActivity.this, "未知错误", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }

                    }
                }).start();


            }
        });


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
            //选择照片返回 内容部分选择图片
            if (requestCode == PHOTO_REQUEST_CODE) {
                //得到选择的图片的真实地址
                String filePathFromUri = PathUirls.getFilePathFromUri(FaBiaoLiuYanActivity.this, data.getData());
                //判断图片是否存在 如模拟器用as的工具删sd卡的图片的话 图片即使删除 但是模拟器相册中还是会显示
                if (!new File(filePathFromUri).exists()) {
                    new AlertDialog.Builder(FaBiaoLiuYanActivity.this)//绑定当前窗口
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
                    new AlertDialog.Builder(FaBiaoLiuYanActivity.this)//绑定当前窗口
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
                        LoadProgressDialog loadProgressDialog = new LoadProgressDialog(FaBiaoLiuYanActivity.this, "上传中...", false);
                        loadProgressDialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    // 这里是调用耗时操作方法
                                    String imgUrl = MyHelper.getInstance(FaBiaoLiuYanActivity.this).oneUploadFile(FileUtils.getPath(filePathFromUri));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //                    内容部分拍摄图片后 绑定数据 更新界面
                                            ContentAndImgBean contentAndImgBean = new ContentAndImgBean();
                                            contentAndImgBean.setImg(imgUrl);
                                            contentAndImgBean.setContent("");
                                            mBeanList.add(contentAndImgBean);
                                            mMyAdapter.upDate(mBeanList);
                                            loadProgressDialog.dismiss();
                                        }
                                    });
                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(FaBiaoLiuYanActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
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
                    new AlertDialog.Builder(FaBiaoLiuYanActivity.this)//绑定当前窗口
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
                        LoadProgressDialog loadProgressDialog = new LoadProgressDialog(FaBiaoLiuYanActivity.this, "上传中...", false);
                        loadProgressDialog.show();
                        //                    上传图片到服务器 服务器返回图片地址 显示到控件
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 这里是调用耗时操作方法
                                    String imgUrl = MyHelper.getInstance(FaBiaoLiuYanActivity.this).oneUploadFile(FileUtils.getPath(takePhotoPath));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //                    内容部分拍摄图片后 绑定数据 更新界面
                                            ContentAndImgBean contentAndImgBean = new ContentAndImgBean();
                                            contentAndImgBean.setImg(imgUrl);
                                            contentAndImgBean.setContent("");
                                            mBeanList.add(contentAndImgBean);
                                            mMyAdapter.upDate(mBeanList);

                                            loadProgressDialog.dismiss();
                                        }
                                    });
                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(FaBiaoLiuYanActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
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


        } else {

            Toast.makeText(this, "没有选择内容", Toast.LENGTH_SHORT).show();

        }

    }


}