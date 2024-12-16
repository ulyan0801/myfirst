package com.my.myapplication.yonghu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.AppointmentBean;
import com.my.myapplication.bean.ContentAndImgBean;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.DateUtils;
import com.my.myapplication.utils.ImageUtils;
import com.my.myapplication.yonghu.ui.YongHuChaKanActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//用户主界面的recyclerview列表控件适配器
public class YongHuMainAdapter extends RecyclerView.Adapter<YongHuMainAdapter.VH> {
    private final Context context;
    private List<AppointmentBean> mBeanList;

    //构造方法 初始化数据
    public YongHuMainAdapter(Context context, List<AppointmentBean> beanList) {
        this.context = context;
        this.mBeanList = beanList;

    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法 加载布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new VH(v);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, @SuppressLint("RecyclerView") int position) {
        //绑定数据
        String xingqi = mBeanList.get(position).getXingqi();

        if ("".equals(xingqi)) {
            holder.tv_zhouji.setText("预约日期: 未开放预约");

        } else {
            holder.tv_zhouji.setText("预约日期: " + xingqi.replace(",", " "));

        }
        holder.tv_name.setText("姓名: " + mBeanList.get(position).getXingming());
        holder.tv_jianjie.setText("简介/擅长: " + mBeanList.get(position).getQita());
        holder.tv_zhiwu.setText("职务: " + mBeanList.get(position).getLeixingf());
        holder.tv_leixing.setText("科室: " + mBeanList.get(position).getLeixing());
//解析图片数据 如果有图片 主界面条目则显示第一张图片
        JSONArray jsonArray1 = null;
        List<ContentAndImgBean> contentAndImgBeanList = new ArrayList<>();

        try {
            jsonArray1 = new JSONArray(mBeanList.get(position).getContent());
            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                ContentAndImgBean contentAndImgBean = new ContentAndImgBean();
                contentAndImgBean.setContent(jsonObject2.getString("content"));
                contentAndImgBean.setImg(jsonObject2.getString("img"));
                contentAndImgBeanList.add(contentAndImgBean);
            }
        } catch (JSONException e) {
            Toast.makeText(context, "图片解析错误", Toast.LENGTH_SHORT).show();
        }
        if (contentAndImgBeanList.size() == 0) {
            holder.rv.setVisibility(View.GONE);
        } else {
            holder.rv.setVisibility(View.VISIBLE);

            holder.tv_zhang.setText(contentAndImgBeanList.size() + "张");
            ImageUtils.displayImage(context, holder.iv_fengmian, contentAndImgBeanList.get(0).getImg());

        }

        //条目点击事件  id
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                当前星期和预约信息里的星期对比 如果存在 则可以预约 反之
                String nowWeek = DateUtils.getNowWeek();

                if (!xingqi.contains(nowWeek)) {
                    Toast.makeText(context, "不在预约星期范围内,不可预约", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, YongHuChaKanActivity.class);
                    intent.putExtra("beanId", mBeanList.get(position).getId());
                    context.startActivity(intent);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    //更新数据
    public void upDate(List<AppointmentBean> beanList) {
        this.mBeanList = beanList;
        notifyDataSetChanged();
    }

    //查找控件
    public class VH extends RecyclerView.ViewHolder {


        LinearLayout ll_main;
        TextView tv_zhouji, tv_name, tv_zhiwu, tv_jianjie, tv_zhang, tv_leixing;
        ImageView iv_fengmian;
        RelativeLayout rv;

        public VH(View v) {
            super(v);
            tv_name = v.findViewById(R.id.tv_name);
            tv_zhiwu = v.findViewById(R.id.tv_zhiwu);
            tv_zhouji = v.findViewById(R.id.tv_zhouji);
            tv_jianjie = v.findViewById(R.id.tv_jianjie);
            iv_fengmian = v.findViewById(R.id.iv_fengmian);
            tv_zhang = v.findViewById(R.id.tv_zhang);
            ll_main = v.findViewById(R.id.ll_main);
            rv = v.findViewById(R.id.rv);
            tv_leixing = v.findViewById(R.id.tv_leixing);
        }
    }
}
