package com.my.myapplication.yonghu.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.WoDeYuYueBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.DateUtils;
import com.my.myapplication.utils.SpUtils;
import com.my.myapplication.yonghu.ui.WoDeYuYueActivity;
import com.my.myapplication.yonghu.ui.YongHuChaKanActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//我的预约recyclerview列表控件适配器
public class WoDeYuYueAdapter extends RecyclerView.Adapter<WoDeYuYueAdapter.VH> {
    private final Context context;
    private List<WoDeYuYueBean> mBeanList;
    private String nowDate;

    //构造方法 初始化数据
    public WoDeYuYueAdapter(Context context, List<WoDeYuYueBean> mBeanList, String nowDate) {
        this.context = context;
        this.mBeanList = mBeanList;
        this.nowDate = nowDate;

    }

    @Override
    public WoDeYuYueAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法 加载布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yonghu_wode_yuyue, parent, false);
        return new WoDeYuYueAdapter.VH(v);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final WoDeYuYueAdapter.VH holder, @SuppressLint("RecyclerView") int position) {

        //绑定数据
        holder.tv_leixing.setText("科室: " + mBeanList.get(position).getLeixing());
        holder.tv_yishi.setText("医师: " + mBeanList.get(position).getYishi());
        holder.tv_haoma.setText("预约号: " + mBeanList.get(position).getHaoma());
        holder.tv_jiage.setText("费用: " + mBeanList.get(position).getJiage() + " 元");
        holder.tv_zhouji.setText("预约日期: " + mBeanList.get(position).getRiqi() + " " + DateUtils.getWeek(mBeanList.get(position).getRiqi()) + " " + mBeanList.get(position).getYuyueshijian());
//        当前日期和预约数据里的日期进行对比 判断是否过期
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date date1 = sdf.parse(nowDate);

            Date date2 = sdf.parse(mBeanList.get(position).getRiqi());

            System.out.println("-----date1 : " + sdf.format(date1));

            System.out.println("----------date2 : " + sdf.format(date2));
            WoDeYuYueActivity activity = (WoDeYuYueActivity) context;
            if (date1.compareTo(date2) > 0) {
                System.out.println("-----Date1 时间在 Date2 之后");

                holder.tv_zhuangtai.setText("已过期");
                holder.tv_zhuangtai.setTextColor(Color.RED);
                holder.tv_erweima.setVisibility(View.GONE);
                holder.tv_quxiao.setText("删除");
                holder.tv_quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(context)//绑定当前窗口
                                .setTitle("提示")//设置标题
                                .setMessage("确定删除?")//设置提示细信息
                                .setIcon(R.mipmap.ic_launcher)//设置图标
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    // 这里是调用耗时操作方法
                                                    MyHelper.getInstance(context).delYuYueByid(mBeanList.get(position).getYuyueid());
                                                    mBeanList = MyHelper.getInstance(context).getWoDeYuYue(SpUtils.getInstance(context).getString("username"));

                                                    activity.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            notifyDataSetChanged();
                                                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
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
            } else if (date1.compareTo(date2) <= 0) {
                System.out.println("----Date1 时间在 Date2 之前");
                holder.tv_zhuangtai.setText("进行中...");
                holder.tv_zhuangtai.setTextColor(Color.BLUE);
                holder.tv_erweima.setVisibility(View.VISIBLE);
                holder.tv_quxiao.setText("取消预约");
                holder.tv_erweima.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new Dialog(context, R.style.Dialog_Style);
                        dialog.setContentView(R.layout.qr_code);
                        dialog.show();
                    }
                });

                holder.tv_quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("---------------------" + nowDate + "---------" + mBeanList.get(position).getRiqi());
                        if (date1.compareTo(date2) == 0) {
                            Toast.makeText(activity, "当天预约不可取消！", Toast.LENGTH_SHORT).show();
                        } else {
                            new AlertDialog.Builder(context)//绑定当前窗口
                                    .setTitle("提示")//设置标题
                                    .setMessage("确定取消预约?取消后费用将退回到你的账户。")//设置提示细信息
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
                                                        System.out.println("---------------------" + mBeanList.get(position).getYuyueid());
                                                        MyHelper.getInstance(context).delYuYueByid(mBeanList.get(position).getYuyueid());
                                                        mBeanList = MyHelper.getInstance(context).getWoDeYuYue(SpUtils.getInstance(context).getString("username"));

                                                        activity.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                notifyDataSetChanged();
                                                                new AlertDialog.Builder(context)//绑定当前窗口
                                                                        .setTitle("提示")//设置标题
                                                                        .setMessage("取消成功，预约费用 " + jiage + " 元,已原路返回到你的账户，感谢使用本功能，祝您生活愉快!")//设置提示细信息
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
                                                                System.out.println("---------------------" + e.getMessage());
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


                    }
                });
            }
        } catch (Exception e) {
            holder.tv_zhuangtai.setText("ccc");
        }


        //条目点击事件  跳转到 详情页
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YongHuChaKanActivity.class);
                intent.putExtra("beanId", mBeanList.get(position).getId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    //更新数据
    public void upDate(List<WoDeYuYueBean> mBeanList) {
        this.mBeanList = mBeanList;
        notifyDataSetChanged();
    }

    //查找控件
    public class VH extends RecyclerView.ViewHolder {

        LinearLayout ll_main;
        TextView tv_zhouji, tv_zhuangtai, tv_leixing, tv_yishi, tv_haoma, tv_jiage, tv_quxiao, tv_erweima;

        public VH(View v) {
            super(v);
            tv_zhouji = v.findViewById(R.id.tv_zhouji);
            ll_main = v.findViewById(R.id.ll_main);
            tv_zhuangtai = v.findViewById(R.id.tv_zhuangtai);
            tv_leixing = v.findViewById(R.id.tv_leixing);
            tv_haoma = v.findViewById(R.id.tv_haoma);
            tv_yishi = v.findViewById(R.id.tv_yishi);
            tv_jiage = v.findViewById(R.id.tv_jiage);
            tv_erweima = v.findViewById(R.id.tv_erweima);
            tv_quxiao = v.findViewById(R.id.tv_quxiao);

        }
    }


}
