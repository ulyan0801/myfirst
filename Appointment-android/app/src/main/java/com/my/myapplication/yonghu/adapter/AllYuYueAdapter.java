package com.my.myapplication.yonghu.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.admin.ui.PhotoViewActivity;
import com.my.myapplication.bean.WoDeYuYueBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.ImageUtils;
import com.my.myapplication.yonghu.ui.AllYuYueActivity;

import java.util.List;

//购买人信息列表recyclerview列表控件适配器
public class AllYuYueAdapter extends RecyclerView.Adapter<AllYuYueAdapter.VH> {
    private final Context context;
    private List<WoDeYuYueBean> mBeanList;
    private Long tid = 0L;
    private Long shijianid = 0L;

    private String riqi = "";

    //构造方法 初始化数据
    public AllYuYueAdapter(Context context, List<WoDeYuYueBean> beanList, Long tid, Long shijianid, String riqi) {
        this.context = context;
        this.mBeanList = beanList;
        this.tid = tid;
        this.shijianid = shijianid;
        this.riqi = riqi;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法 加载布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_yuyue, parent, false);
        return new VH(v);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, @SuppressLint("RecyclerView") int position) {
        //绑定数据

        holder.tv_username.setText("昵称: " + mBeanList.get(position).getUserNickname());
        holder.tv_phone.setText("电话: " + mBeanList.get(position).getPhone());

        if ("".equals(mBeanList.get(position).getAvatar())) {

            holder.iv_heard.setImageResource(R.mipmap.touxiang);

        } else {

            ImageUtils.displayImage(context, holder.iv_heard, mBeanList.get(position).getAvatar());
            holder.iv_heard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PhotoViewActivity.class);
                    intent.putExtra("tupian", mBeanList.get(position).getAvatar());
                    context.startActivity(intent);
                }
            });

        }
        AllYuYueActivity activity = (AllYuYueActivity) context;
//取消预约
        holder.tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)//绑定当前窗口
                        .setTitle("提示")//设置标题
                        .setMessage("确定取消预约?取消后费用将退回到该用户的的账户中。")//设置提示细信息
                        .setIcon(R.mipmap.ic_launcher)//设置图标
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            // 这里是调用耗时操作方法
                                            String jiage = mBeanList.get(position).getJiage();
                                            MyHelper.getInstance(context).delYuYueByid(mBeanList.get(position).getYuyueid());
                                            mBeanList = MyHelper.getInstance(context).getAllYuYue(tid, shijianid, riqi);

                                            activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    notifyDataSetChanged();
                                                    new AlertDialog.Builder(context)//绑定当前窗口
                                                            .setTitle("提示")//设置标题
                                                            .setMessage("取消成功，预约费用 " + jiage + " 元,已原路返回到用户的的账户中")//设置提示细信息
                                                            .setIcon(R.mipmap.ic_launcher)//设置图标
                                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
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
                                        } catch (Exception e) {
                                            activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
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
        });

//联系预约人
        holder.tv_lianxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + mBeanList.get(position).getPhone()));
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "该设备不支持拨号功能", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    //更新数据
    public void upDate(List<WoDeYuYueBean> beanList) {
        this.mBeanList = beanList;
        notifyDataSetChanged();
    }

    //查找控件
    public class VH extends RecyclerView.ViewHolder {
        ImageView iv_heard;
        TextView tv_username, tv_phone, tv_quxiao, tv_lianxi;

        public VH(View v) {
            super(v);
            iv_heard = v.findViewById(R.id.iv_heard);
            tv_username = v.findViewById(R.id.tv_username);
            tv_phone = v.findViewById(R.id.tv_phone);
            tv_quxiao = v.findViewById(R.id.tv_quxiao);
            tv_lianxi = v.findViewById(R.id.tv_lianxi);

        }
    }
}
