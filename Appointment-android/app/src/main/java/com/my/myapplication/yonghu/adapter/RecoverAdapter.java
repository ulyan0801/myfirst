package com.my.myapplication.yonghu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.admin.ui.PhotoViewActivity;
import com.my.myapplication.bean.ContentAndImgBean;
import com.my.myapplication.bean.RecoverBean;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.ImageUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//留言列表recyclerview列表控件适配器
public class RecoverAdapter extends RecyclerView.Adapter<RecoverAdapter.VH> {
    private final Context context;
    private List<RecoverBean> mBeanList;

    //构造方法 初始化数据
    public RecoverAdapter(Context context, List<RecoverBean> beanList) {
        this.context = context;
        this.mBeanList = beanList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法 加载布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recover, parent, false);
        return new VH(v);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, @SuppressLint("RecyclerView") int position) {
        //绑定数据

        holder.tv_time.setText(mBeanList.get(position).getTime());
        holder.tv_username.setText(mBeanList.get(position).getUserNickname());
        try {
            //解析json数据
            List<ContentAndImgBean> list = new ArrayList<>();
            JSONArray jsonArray1 = new JSONArray(mBeanList.get(position).getContent());
            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                ContentAndImgBean contentAndImgBean = new ContentAndImgBean();
                contentAndImgBean.setContent(jsonObject2.getString("content"));
                contentAndImgBean.setImg(jsonObject2.getString("img"));
                list.add(contentAndImgBean);

            }
            //绑定Recyclerview适配器
            LiuYanAdapter mMyAdapter = new LiuYanAdapter(context, list);
            //设置Recyclerview 布局管理器
            holder.recyclerview.setLayoutManager(new LinearLayoutManager(context));
            //  mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
            //设置Recyclerview适配器
            holder.recyclerview.setAdapter(mMyAdapter);
        } catch (Exception e) {

        }

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

    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    //更新数据
    public void upDate(List<RecoverBean> beanList) {
        this.mBeanList = beanList;
        notifyDataSetChanged();
    }

    //查找控件
    public class VH extends RecyclerView.ViewHolder {
        ImageView iv_heard;
        TextView tv_time, tv_username;
        RecyclerView recyclerview;

        public VH(View v) {
            super(v);
            iv_heard = v.findViewById(R.id.iv_heard);
            tv_time = v.findViewById(R.id.tv_time);
            tv_username = v.findViewById(R.id.tv_username);
            recyclerview = v.findViewById(R.id.recyclerview);

        }
    }
}
