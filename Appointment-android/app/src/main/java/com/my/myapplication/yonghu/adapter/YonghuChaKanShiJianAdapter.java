package com.my.myapplication.yonghu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.ShiJianBean;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.SpUtils;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

//用户查看预约时间段recyclerview列表控件适配器
public class YonghuChaKanShiJianAdapter extends RecyclerView.Adapter<YonghuChaKanShiJianAdapter.VH> {


    private final Context context;
    private List<ShiJianBean> mBeanList;
    private String riqi = "";
    String nowDate = "1";

    //构造方法 初始化数据
    public YonghuChaKanShiJianAdapter(Context context, List<ShiJianBean> mBeanList) {
        this.context = context;
        this.mBeanList = mBeanList;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 这里是调用耗时操作方法

                    // 这里是调用耗时操作方法
                    // 抓取北京时间
                    URL url = new URL("https://time.tianqi.com");
                    // 连接到我们需要抓取时间的网址
                    URLConnection uc = url.openConnection();
                    uc.connect();
                    // 抓取北京时间，获取到的时间为long类型
                    long time = uc.getDate();
// 创建一个适用于中国时区的Calendar对象
                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
                    calendar.setTimeInMillis(time);

// 转换为日期对象
                    Date date = calendar.getTime();

// 格式化日期对象为字符串
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = sdf.format(date);


//                    生成7天日期
                    nowDate = formattedDate; // 将时间戳转换为日期字符串
                } catch (Exception e) {


                }
            }
        }).start();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法 加载布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_chakan_yuyueshijian, parent, false);
        return new VH(v);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, @SuppressLint("RecyclerView") int position) {
        //绑定数据
        String yuyueshijian = mBeanList.get(position).getYuyueshijian();
        holder.yuyueshijian.setText(yuyueshijian);
        holder.tv_jiage.setText("¥ " + mBeanList.get(position).getJiage());
        holder.tv_renshu.setText("总人数/剩余: " + mBeanList.get(position).getRenshu() + "/" + mBeanList.get(position).getShengyurenshu());
//判断点击的是否是当前今天的日期
        if (riqi.equals(nowDate)) {
            String[] split = yuyueshijian.split("-");
            //校验数据 开始时间是否大于结束时间
            try {
//比较当前的时间 是否在预约时间内
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String currentTime = sdf.format(new Date());
                // 将时间字符串转换为LocalTime对象
                LocalTime time1 = LocalTime.parse(currentTime);
                LocalTime time2 = LocalTime.parse(split[1]);

                // 比较两个LocalTime对象
                int compareResult = time1.compareTo(time2);
//                时间超出范围
                if (compareResult >= 0) {

                    //                不在预约的时间范围内 设置背景 文字 点击时间等
                    holder.tv_zhuangtai.setText("不可预约");
                    holder.tv_renshu.setText("总人数/剩余: " + mBeanList.get(position).getRenshu() + "/" + mBeanList.get(position).getShengyurenshu());
                    Resources res = context.getResources();
                    Drawable shapeDrawable = res.getDrawable(R.drawable.llshap2);
                    holder.ll_main.setBackground(shapeDrawable);
                    holder.tv_zhuangtai.setTextColor(Color.parseColor("#737373"));
                    holder.yuyueshijian.setTextColor(Color.parseColor("#737373"));
                    holder.tv_jiage.setTextColor(Color.parseColor("#737373"));
                    holder.tv_renshu.setTextColor(Color.parseColor("#737373"));
                    holder.ll_main.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "不在预约的时间范围内,不可预约", Toast.LENGTH_SHORT).show();
                        }
                    });

                    for (String s : mBeanList.get(position).getUsername()) {
                        if (SpUtils.getInstance(context).getString("username").equals(s)) {
                            holder.tv_zhuangtai.setText("我的预约");
                            holder.tv_renshu.setText("总人数/剩余: " + mBeanList.get(position).getRenshu() + "/" + mBeanList.get(position).getShengyurenshu());
                            holder.tv_zhuangtai.setTextColor(Color.parseColor("#FEABAB"));
                            holder.yuyueshijian.setTextColor(Color.parseColor("#FEABAB"));
                            holder.tv_jiage.setTextColor(Color.parseColor("#FEABAB"));
                            holder.tv_renshu.setTextColor(Color.parseColor("#FEABAB"));
                            Resources res1 = context.getResources();
                            Drawable shapeDrawable1 = res1.getDrawable(R.drawable.llshap3);
                            holder.ll_main.setBackground(shapeDrawable1);
                            break;
                        }
                    }

                } else if (compareResult < 0) {
//                    在预约的时间范围

                    if (mBeanList.get(position).getShengyurenshu() == 0) {
                        //                不在预约的时间范围内 设置背景 文字 点击时间等
                        holder.tv_zhuangtai.setText("不可预约");
                        holder.tv_renshu.setText("已满");
                        Resources res = context.getResources();
                        Drawable shapeDrawable = res.getDrawable(R.drawable.llshap2);
                        holder.ll_main.setBackground(shapeDrawable);
                        holder.tv_zhuangtai.setTextColor(Color.parseColor("#737373"));
                        holder.yuyueshijian.setTextColor(Color.parseColor("#737373"));
                        holder.tv_jiage.setTextColor(Color.parseColor("#737373"));
                        holder.tv_renshu.setTextColor(Color.parseColor("#737373"));
                        holder.ll_main.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context, "不可预约,预约人数已满", Toast.LENGTH_SHORT).show();
                            }
                        });
                        for (String s : mBeanList.get(position).getUsername()) {
                            if (SpUtils.getInstance(context).getString("username").equals(s)) {
                                holder.tv_zhuangtai.setText("我的预约");
                                break;
                            }
                        }


                    } else {

                        //                    文字背景设置
                        holder.tv_zhuangtai.setText("可预约");
                        holder.tv_renshu.setText("总人数/剩余: " + mBeanList.get(position).getRenshu() + "/" + mBeanList.get(position).getShengyurenshu());
                        holder.tv_zhuangtai.setTextColor(Color.parseColor("#ffffff"));
                        holder.yuyueshijian.setTextColor(Color.parseColor("#ffffff"));
                        holder.tv_jiage.setTextColor(Color.parseColor("#ffffff"));
                        holder.tv_renshu.setTextColor(Color.parseColor("#ffffff"));
                        Resources res = context.getResources();
                        Drawable shapeDrawable = res.getDrawable(R.drawable.llshape4);
                        holder.ll_main.setBackground(shapeDrawable);

                        for (String s : mBeanList.get(position).getUsername()) {
                            if (SpUtils.getInstance(context).getString("username").equals(s)) {
                                holder.tv_zhuangtai.setText("我的预约");
                                holder.tv_renshu.setText("总人数/剩余: " + mBeanList.get(position).getRenshu() + "/" + mBeanList.get(position).getShengyurenshu());
                                holder.tv_zhuangtai.setTextColor(Color.parseColor("#FEABAB"));
                                holder.yuyueshijian.setTextColor(Color.parseColor("#FEABAB"));
                                holder.tv_jiage.setTextColor(Color.parseColor("#FEABAB"));
                                holder.tv_renshu.setTextColor(Color.parseColor("#FEABAB"));
                                Resources res1 = context.getResources();
                                Drawable shapeDrawable1 = res1.getDrawable(R.drawable.llshap3);
                                holder.ll_main.setBackground(shapeDrawable1);
                                break;
                            }
                        }

                    }

                    //                点击预约时间段的条目 事件传递到 查看详情页 进行处理
                    holder.ll_main.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnClickShiJian != null) {
                                mOnClickShiJian.OnClickShiJian(position);
                            }
                        }
                    });
                }

            } catch (Exception e) {

            }


        } else {
//            点击的不是今天的日期
            if (mBeanList.get(position).getShengyurenshu() == 0) {
                //                不在预约的时间范围内 设置背景 文字 点击时间等
                holder.tv_zhuangtai.setText("不可预约");
                holder.tv_renshu.setText("已满");
                Resources res = context.getResources();
                Drawable shapeDrawable = res.getDrawable(R.drawable.llshap2);
                holder.ll_main.setBackground(shapeDrawable);
                holder.tv_zhuangtai.setTextColor(Color.parseColor("#737373"));
                holder.yuyueshijian.setTextColor(Color.parseColor("#737373"));
                holder.tv_jiage.setTextColor(Color.parseColor("#737373"));
                holder.tv_renshu.setTextColor(Color.parseColor("#737373"));
                holder.ll_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "不可预约,预约人数已满", Toast.LENGTH_SHORT).show();
                    }
                });
                for (String s : mBeanList.get(position).getUsername()) {
                    if (SpUtils.getInstance(context).getString("username").equals(s)) {
                        holder.tv_zhuangtai.setText("我的预约");
                        break;
                    }
                }

            } else {

                //                    文字背景设置
                holder.tv_zhuangtai.setText("可预约");
                holder.tv_renshu.setText("总人数/剩余: " + mBeanList.get(position).getRenshu() + "/" + mBeanList.get(position).getShengyurenshu());
                holder.tv_zhuangtai.setTextColor(Color.parseColor("#ffffff"));
                holder.yuyueshijian.setTextColor(Color.parseColor("#ffffff"));
                holder.tv_jiage.setTextColor(Color.parseColor("#ffffff"));
                holder.tv_renshu.setTextColor(Color.parseColor("#ffffff"));
                Resources res = context.getResources();
                Drawable shapeDrawable = res.getDrawable(R.drawable.llshape4);
                holder.ll_main.setBackground(shapeDrawable);

                for (String s : mBeanList.get(position).getUsername()) {
                    if (SpUtils.getInstance(context).getString("username").equals(s)) {
                        holder.tv_zhuangtai.setText("我的预约");
                        holder.tv_renshu.setText("总人数/剩余: " + mBeanList.get(position).getRenshu() + "/" + mBeanList.get(position).getShengyurenshu());
                        holder.tv_zhuangtai.setTextColor(Color.parseColor("#FEABAB"));
                        holder.yuyueshijian.setTextColor(Color.parseColor("#FEABAB"));
                        holder.tv_jiage.setTextColor(Color.parseColor("#FEABAB"));
                        holder.tv_renshu.setTextColor(Color.parseColor("#FEABAB"));
                        Resources res1 = context.getResources();
                        Drawable shapeDrawable1 = res1.getDrawable(R.drawable.llshap3);
                        holder.ll_main.setBackground(shapeDrawable1);
                        break;
                    }
                }

            }

            //                点击预约时间段的条目 事件传递到 查看详情页 进行处理
            holder.ll_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickShiJian != null) {
                        mOnClickShiJian.OnClickShiJian(position);
                    }
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    //更新数据
    public void upDate(List<ShiJianBean> mBeanList, String riqi) {
        this.mBeanList = mBeanList;
        this.riqi = riqi;
        System.out.println("---------------------" + riqi);
        notifyDataSetChanged();
    }

    //查找控件
    public class VH extends RecyclerView.ViewHolder {

        LinearLayout ll_main;
        TextView tv_zhuangtai, yuyueshijian, tv_jiage, tv_renshu;

        public VH(View v) {
            super(v);
            tv_zhuangtai = v.findViewById(R.id.tv_zhuangtai);
            ll_main = v.findViewById(R.id.ll_main);
            yuyueshijian = v.findViewById(R.id.yuyueshijian);
            tv_jiage = v.findViewById(R.id.tv_jiage);
            tv_renshu = v.findViewById(R.id.tv_renshu);
        }
    }

    //自定义接口回调
    private OnClickShiJian mOnClickShiJian;

    public void setOnClickRiQi(OnClickShiJian mOnClickShiJian) {
        this.mOnClickShiJian = mOnClickShiJian;
    }

    public interface OnClickShiJian {
        void OnClickShiJian(int position);
    }
}
