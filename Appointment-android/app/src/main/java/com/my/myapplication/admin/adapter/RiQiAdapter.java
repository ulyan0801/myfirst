package com.my.myapplication.admin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.RiQiBean;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.DateUtils;

import java.util.List;

//预约日期recyclerview列表控件适配器
public class RiQiAdapter extends RecyclerView.Adapter<RiQiAdapter.VH> {
    private final Context context;
    private List<RiQiBean> mBeanList;

    //构造方法 初始化数据
    public RiQiAdapter(Context context, List<RiQiBean> mBeanList) {
        this.context = context;
        this.mBeanList = mBeanList;

    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法 加载布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_chakan_yuyueriqi, parent, false);
        return new VH(v);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, @SuppressLint("RecyclerView") int position) {
        //绑定数据

        holder.tv_riqi.setText(mBeanList.get(position).getRiqi());
        holder.tv_xingqi.setText(DateUtils.getWeek(mBeanList.get(position).getRiqi()));

//        处理点击后的 背景选择框
        if (mBeanList.get(position).isSelect()) {
            Resources res = context.getResources();
            Drawable shapeDrawable = res.getDrawable(R.drawable.llshap3);
            holder.ll_main.setBackground(shapeDrawable);
        } else {
            Resources res = context.getResources();
            Drawable shapeDrawable = res.getDrawable(R.drawable.llshap2);
            holder.ll_main.setBackground(shapeDrawable);
        }

//        预约日期条目点击事件
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                设置点击的条目为点击状态
                mBeanList.get(position).setSelect(true);
//                将点击事件传递到 查看页面
                if (mOnClickRiQi != null) {
                    mOnClickRiQi.OnClickRiQi(position);
                }
//                清除其他条目为未点击状态
                for (int i = 0; i < mBeanList.size(); i++) {
                    if (i != position) {
                        mBeanList.get(i).setSelect(false);
                    }
                }
//                更新界面
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    //更新数据
    public void upDate(List<RiQiBean> mBeanList) {
        this.mBeanList = mBeanList;
        notifyDataSetChanged();
    }

    //查找控件
    public class VH extends RecyclerView.ViewHolder {

        LinearLayout ll_main;
        TextView tv_xingqi, tv_riqi;

        public VH(View v) {
            super(v);
            tv_xingqi = v.findViewById(R.id.tv_xingqi);
            tv_riqi = v.findViewById(R.id.tv_riqi);
            ll_main = v.findViewById(R.id.ll_main);

        }
    }

    private OnClickRiQi mOnClickRiQi;

    public void setOnClickRiQi(OnClickRiQi mOnClickRiQi) {
        this.mOnClickRiQi = mOnClickRiQi;
    }

    public interface OnClickRiQi {
        void OnClickRiQi(int position);
    }
}
