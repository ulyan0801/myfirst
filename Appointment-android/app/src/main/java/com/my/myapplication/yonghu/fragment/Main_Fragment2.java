package com.my.myapplication.yonghu.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.makeramen.roundedimageview.RoundedImageView;
import com.my.myapplication.admin.ui.PhotoViewActivity;
import com.my.myapplication.bean.UserBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.ImageUtils;
import com.my.myapplication.utils.SpUtils;
import com.my.myapplication.yonghu.ui.GaiShouJiHaoActivity;
import com.my.myapplication.yonghu.ui.GaiZiLaoActivity;
import com.my.myapplication.yonghu.ui.LoginActivity;
import com.my.myapplication.yonghu.ui.RegisterActivity;
import com.my.myapplication.yonghu.ui.WoDeShouCangActivity;
import com.my.myapplication.yonghu.ui.WoDeYuYueActivity;

//主界面 我的
public class Main_Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RoundedImageView mIvPic;
    private TextView mTvZhanghao;
    private RelativeLayout mRvWodeyuyue;
    private ImageView mIv6;
    private RelativeLayout mRvShoucang;
    private ImageView mIv1;
    private RelativeLayout mRvZiliao;
    private ImageView mIv2;
    private RelativeLayout mRvMima;
    private ImageView mIv3;
    private RelativeLayout mRvKefu;
    private ImageView mIv4;
    private RelativeLayout mRvTuichu;
    private TextView mTvshoujihao;

    public Main_Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Main_Fragment2 newInstance(String param1, String param2) {
        Main_Fragment2 fragment = new Main_Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main2, container, false);
        initView(view);
        //        联系客服 点击
        mRvKefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "888";

                new AlertDialog.Builder(getActivity())//绑定当前窗口
                        .setTitle("联系我们")//设置标题
                        .setMessage("客服电话：" + phone)//设置提示细信息
                        .setIcon(R.mipmap.ic_launcher)//设置图标
                        .setPositiveButton("拨打号码", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:" + phone));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), "该设备不支持拨号功能", Toast.LENGTH_SHORT).show();
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
//改密码 点击
        mRvMima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                intent.putExtra("type", "xiugai");
                startActivity(intent);
            }
        });
//        我的预约
        mRvWodeyuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WoDeYuYueActivity.class);
                startActivity(intent);
            }
        });
        mRvZiliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GaiZiLaoActivity.class);
                startActivity(intent);
            }
        });
//收藏点击 跳转界面
        mRvShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WoDeShouCangActivity.class);
                startActivity(intent);
            }
        });
//        改手机号 点击跳转界面
        view.findViewById(R.id.rv_gaishoujihao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GaiShouJiHaoActivity.class);
                startActivity(intent);
            }
        });

//        退出点击 清空本地存储数据
        mRvTuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否记账账号 SharedPreferences存储用的key
                String key_is_remember = "key_is_remember";
                // 存储账号 SharedPreferences用的key
                String key_account = "ke_account";
                //存储密码 SharedPreferences用的key
                String key_password = "key_password";
                SpUtils.getInstance(getActivity()).putBoolean(key_is_remember, false);
                SpUtils.getInstance(getActivity()).putString(key_account, "");
                SpUtils.getInstance(getActivity()).putString(key_password, "");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return view;
    }

    //找控件
    private void initView(View view) {
        mIvPic = view.findViewById(R.id.iv_pic);
        mTvZhanghao = view.findViewById(R.id.tv_zhanghao);
        mRvWodeyuyue = view.findViewById(R.id.rv_wodeyuyue);
        mIv6 = view.findViewById(R.id.iv6);
        mRvShoucang = view.findViewById(R.id.rv_shoucang);
        mIv1 = view.findViewById(R.id.iv1);
        mRvZiliao = view.findViewById(R.id.rv_ziliao);
        mIv2 = view.findViewById(R.id.iv2);
        mRvMima = view.findViewById(R.id.rv_mima);
        mIv3 = view.findViewById(R.id.iv3);
        mRvKefu = view.findViewById(R.id.rv_kefu);
        mIv4 = view.findViewById(R.id.iv4);
        mRvTuichu = view.findViewById(R.id.rv_tuichu);
        mTvshoujihao = view.findViewById(R.id.tv_shoujihao);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 这里是调用耗时操作方法 获取用户信息 显示到控件上
                    UserBean user = MyHelper.getInstance(getActivity()).getUserByUserName(SpUtils.getInstance(getActivity()).getString("username"));
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String avatar = user.getAvatar();

                            mTvZhanghao.setText(user.getUserNickname());
                            try {
                                mTvshoujihao.setText(user.getPhone().substring(0, 3) + "****" + user.getPhone().substring(7));
                            } catch (Exception e) {
                                mTvshoujihao.setText(user.getPhone() + "");

                            }


                            if ("".equals(avatar)) {

                                mIvPic.setImageResource(R.mipmap.touxiang);

                            } else {
                                ImageUtils.displayImage(getActivity(), mIvPic, avatar);
                                //        头像点击
                                mIvPic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
                                        intent.putExtra("tupian", avatar);
                                        getActivity().startActivity(intent);
                                    }
                                });
                            }


                        }
                    });
                } catch (Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("---------------------" + e.getMessage());
                            Toast.makeText(getActivity(), "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();
    }
}