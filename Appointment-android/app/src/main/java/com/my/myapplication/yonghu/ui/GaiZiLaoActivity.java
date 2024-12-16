package com.my.myapplication.yonghu.ui;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.makeramen.roundedimageview.RoundedImageView;
import com.my.myapplication.admin.ui.PhotoViewActivity;
import com.my.myapplication.bean.UserBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.FileUtils;
import com.my.myapplication.utils.ImageUtils;
import com.my.myapplication.utils.PathUirls;
import com.my.myapplication.utils.PermissionUtils;
import com.my.myapplication.utils.SpUtils;

import java.io.File;

//改资料界面
public class GaiZiLaoActivity extends AppCompatActivity {

    //定义变量
    private EditText mEtNickname;
    private Button mBtXiugai;
    private String tupian = "";
    private RoundedImageView mIvPic;
    private TextView mTvGaitongxiang;
    public static final int PHOTO_REQUEST_CODE = 1;
    public static final int SD_REQUEST_CODE = 2;
    public static final int CAMERA_PHOTO_REQUEST_CODE = 3;
    private Button mBtFanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gai_zi_liao);
        requestPermissions();
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("改资料");
        initView();
        mBtFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // 这里是调用耗时操作方法 获取用户资料显示到界面
                    UserBean userBean = MyHelper.getInstance(GaiZiLaoActivity.this).getUserByUserName(SpUtils.getInstance(GaiZiLaoActivity.this).getString("username"));
                    tupian = userBean.getAvatar();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            绑定数据
                            mEtNickname.setText(userBean.getUserNickname());

                            if ("".equals(tupian)) {

                                mIvPic.setImageResource(R.mipmap.touxiang);

                            } else {
                                ImageUtils.displayImage(GaiZiLaoActivity.this, mIvPic, tupian);
                                //        头像点击
                                mIvPic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(GaiZiLaoActivity.this, PhotoViewActivity.class);
                                        intent.putExtra("tupian", tupian);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GaiZiLaoActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();
//修改头像点击
        mTvGaitongxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹对话框 是选择还是拍照
                final String[] items = {"相册", "拍照", "默认头像"};
                AlertDialog.Builder builder = new AlertDialog.Builder(GaiZiLaoActivity.this);
                builder.setTitle("修改头像");
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

                            case 2:
                                tupian = "";
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            // 这里是调用耗时操作方法
                                            UserBean user = new UserBean();
                                            user.setUsername(SpUtils.getInstance(GaiZiLaoActivity.this).getString("username"));
                                            user.setAvatar(tupian);
//                            更新数据
                                            long result = MyHelper.getInstance(GaiZiLaoActivity.this).updateUserTouXiang(user);
                                            if (result != -1) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        mIvPic.setImageResource(R.mipmap.touxiang);
                                                        Toast.makeText(GaiZiLaoActivity.this, "头像修改成功", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        //注册失败提示用户
                                                        Toast.makeText(GaiZiLaoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                            }

                                        } catch (Exception e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(GaiZiLaoActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                }).start();
                                Toast.makeText(GaiZiLaoActivity.this, "头像修改成功", Toast.LENGTH_SHORT).show();
                                break;


                        }
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
//        改资料按钮
        mBtXiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                判断数据是否为空
                String Nickname = mEtNickname.getText().toString().trim();
                if ("".equals(Nickname)) {
                    Toast.makeText(GaiZiLaoActivity.this, "昵称不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 这里是调用耗时操作方法 创建数据类
                            UserBean user = new UserBean();
                            user.setUsername(SpUtils.getInstance(GaiZiLaoActivity.this).getString("username"));
                            user.setUserNickname(Nickname);
//                            更新数据
                            long result = MyHelper.getInstance(GaiZiLaoActivity.this).updateUserNickname(user);
                            if (result != -1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GaiZiLaoActivity.this, "昵称修改成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //注册失败提示用户
                                        Toast.makeText(GaiZiLaoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GaiZiLaoActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }).start();
            }
        });
    }

    private void initView() {
//找控件
        mEtNickname = findViewById(R.id.et_nickname);
        mBtXiugai = findViewById(R.id.bt_xiugai);
        mIvPic = findViewById(R.id.iv_pic);
        mTvGaitongxiang = findViewById(R.id.tv_gaitongxiang);
        mBtFanhui = findViewById(R.id.bt_fanhui);
    }

    //返回箭头点击事件 销毁退出页面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                String filePathFromUri = PathUirls.getFilePathFromUri(GaiZiLaoActivity.this, data.getData());
                //判断图片是否存在 如模拟器用as的工具删sd卡的图片的话 图片即使删除 但是模拟器相册中还是会显示
                if (!new File(filePathFromUri).exists()) {
                    new AlertDialog.Builder(GaiZiLaoActivity.this)//绑定当前窗口
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
                    new AlertDialog.Builder(GaiZiLaoActivity.this)//绑定当前窗口
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

//                    选择图片后 绑定数据 更新界面

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 这里是调用耗时操作方法
                                    String imgUrl = MyHelper.getInstance(GaiZiLaoActivity.this).oneUploadFile(FileUtils.getPath(filePathFromUri));
                                    UserBean user = new UserBean();
                                    user.setUsername(SpUtils.getInstance(GaiZiLaoActivity.this).getString("username"));
                                    user.setAvatar(imgUrl);
//                            更新数据
                                    long result = MyHelper.getInstance(GaiZiLaoActivity.this).updateUserTouXiang(user);
                                    if (result != -1) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ImageUtils.displayImage(GaiZiLaoActivity.this, mIvPic, imgUrl);
                                                Toast.makeText(GaiZiLaoActivity.this, "头像修改成功", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //注册失败提示用户
                                                Toast.makeText(GaiZiLaoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }

                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GaiZiLaoActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
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
                    new AlertDialog.Builder(GaiZiLaoActivity.this)//绑定当前窗口
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
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 这里是调用耗时操作方法
                                    String imgUrl = MyHelper.getInstance(GaiZiLaoActivity.this).oneUploadFile(FileUtils.getPath(takePhotoPath));
                                    UserBean user = new UserBean();
                                    user.setUsername(SpUtils.getInstance(GaiZiLaoActivity.this).getString("username"));
                                    user.setAvatar(imgUrl);
//                            更新数据
                                    long result = MyHelper.getInstance(GaiZiLaoActivity.this).updateUserTouXiang(user);
                                    if (result != -1) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ImageUtils.displayImage(GaiZiLaoActivity.this, mIvPic, imgUrl);
                                                Toast.makeText(GaiZiLaoActivity.this, "头像修改成功", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //注册失败提示用户
                                                Toast.makeText(GaiZiLaoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }

                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GaiZiLaoActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
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

            // Toast.makeText(this, "没有选择内容", Toast.LENGTH_SHORT).show();

        }

    }
}
