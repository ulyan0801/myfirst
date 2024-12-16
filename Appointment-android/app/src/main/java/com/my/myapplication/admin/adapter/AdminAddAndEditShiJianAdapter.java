package com.my.myapplication.admin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.ShiJianBean;
import com.my.myapplication.ui.R;

import java.util.List;

//添加编辑页面 预约时间段 recyclerview列表控件适配器
public class AdminAddAndEditShiJianAdapter extends RecyclerView.Adapter<AdminAddAndEditShiJianAdapter.VH> {
    private final Context context;
    private List<ShiJianBean> mBeanList;

    //构造方法 初始化数据
    public AdminAddAndEditShiJianAdapter(Context context, List<ShiJianBean> mBeanList) {
        this.context = context;
        this.mBeanList = mBeanList;

    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法 加载布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_add_yuyueshijian, parent, false);
        return new VH(v);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, @SuppressLint("RecyclerView") int position) {
        //绑定数据
        holder.yuyueshijian.setText(mBeanList.get(position).getYuyueshijian());
        holder.tv_jiage.setText("¥ " + mBeanList.get(position).getJiage());
        holder.tv_renshu.setText("剩余预约人数: " + mBeanList.get(position).getRenshu());
        holder.tv_zhuangtai.setVisibility(View.GONE);
//删除预约时间段 按钮点击 点击事件传递到添加编辑页面
        holder.iv_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteItemClick.OnDeleteItemClick(position);
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
        ImageView iv_shanchu;

        public VH(View v) {
            super(v);
            tv_zhuangtai = v.findViewById(R.id.tv_zhuangtai);
            ll_main = v.findViewById(R.id.ll_main);
            yuyueshijian = v.findViewById(R.id.yuyueshijian);
            tv_jiage = v.findViewById(R.id.tv_jiage);
            iv_shanchu = v.findViewById(R.id.iv_shanchu);
            tv_renshu = v.findViewById(R.id.tv_renshu);


        }
    }

    //自定义接口 回调点击事件
    private OnDeleteItemClick mOnDeleteItemClick;

    public void setOnDeleteItemClick(OnDeleteItemClick mOnDeleteItemClick) {
        this.mOnDeleteItemClick = mOnDeleteItemClick;
    }

    public interface OnDeleteItemClick {
        void OnDeleteItemClick(int position);
    }
}
