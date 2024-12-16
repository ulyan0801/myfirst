package com.my.myapplication.admin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.admin.ui.PhotoViewActivity;
import com.my.myapplication.bean.ContentAndImgBean;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.ImageUtils;

import java.util.List;

//显示图片recyclerview列表控件适配器
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.VH> {
    private final Context context;
    private List<ContentAndImgBean> mBeanList;

    //构造方法 初始化数据
    public ImageAdapter(Context context, List<ContentAndImgBean> beanList) {
        this.context = context;
        this.mBeanList = beanList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法 加载布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_chankan_img, parent, false);
        return new VH(v);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, @SuppressLint("RecyclerView") int position) {
        //绑定数据 判断内容和图片是否为空 对应的显示隐藏控件 绑定数据


        if (!"".equals(mBeanList.get(position).getImg())) {
            holder.iv_img.setVisibility(View.VISIBLE);
            ImageUtils.displayImage(context, holder.iv_img, mBeanList.get(position).getImg());
        } else {
            holder.iv_img.setVisibility(View.GONE);
        }
        //        图片点击按钮 跳看大图
        holder.iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoViewActivity.class);
                intent.putExtra("tupian", mBeanList.get(position).getImg());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    //更新数据
    public void upDate(List<ContentAndImgBean> beanList) {
        this.mBeanList = beanList;
        notifyDataSetChanged();
    }

    //查找控件
    public class VH extends RecyclerView.ViewHolder {
        ImageView iv_img;

        public VH(View v) {
            super(v);
            iv_img = v.findViewById(R.id.iv_img);

        }
    }
}
