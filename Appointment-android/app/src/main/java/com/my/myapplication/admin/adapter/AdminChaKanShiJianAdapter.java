package com.my.myapplication.admin.adapter;

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

import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.ShiJianBean;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.SpUtils;

import java.util.List;

//查看预约时间段recyclerview列表控件适配器
public class AdminChaKanShiJianAdapter extends RecyclerView.Adapter<AdminChaKanShiJianAdapter.VH> {
    private final Context context;
    private List<ShiJianBean> mBeanList;

    //构造方法 初始化数据
    public AdminChaKanShiJianAdapter(Context context, List<ShiJianBean> mBeanList) {
        this.context = context;
        this.mBeanList = mBeanList;

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
        if (mBeanList.get(position).getRenshu() == mBeanList.get(position).getShengyurenshu()) {
            holder.tv_zhuangtai.setText("无预约");
            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ffffff"));
            holder.yuyueshijian.setTextColor(Color.parseColor("#ffffff"));
            holder.tv_jiage.setTextColor(Color.parseColor("#ffffff"));
            holder.tv_renshu.setTextColor(Color.parseColor("#ffffff"));
            Resources res = context.getResources();
            Drawable shapeDrawable = res.getDrawable(R.drawable.llshape4);
            holder.ll_main.setBackground(shapeDrawable);
        } else {
            holder.tv_zhuangtai.setText("有预约");
            for (String s : mBeanList.get(position).getUsername()) {
                if (SpUtils.getInstance(context).getString("username").equals(s)) {
                    holder.tv_zhuangtai.setText("有虚拟预约");
                    break;
                }
            }

            holder.tv_zhuangtai.setTextColor(Color.parseColor("#FEABAB"));
            holder.yuyueshijian.setTextColor(Color.parseColor("#FEABAB"));
            holder.tv_jiage.setTextColor(Color.parseColor("#FEABAB"));
            holder.tv_renshu.setTextColor(Color.parseColor("#FEABAB"));
            Resources res1 = context.getResources();
            Drawable shapeDrawable1 = res1.getDrawable(R.drawable.llshap3);
            holder.ll_main.setBackground(shapeDrawable1);
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

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    //更新数据
    public void upDate(List<ShiJianBean> mBeanList) {
        this.mBeanList = mBeanList;
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

    private OnClickShiJian mOnClickShiJian;

    public void setOnClickRiQi(OnClickShiJian mOnClickShiJian) {
        this.mOnClickShiJian = mOnClickShiJian;
    }

    public interface OnClickShiJian {
        void OnClickShiJian(int position);
    }
}
