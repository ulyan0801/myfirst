package com.my.myapplication.yonghu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//主界面 首页
public class Main_Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //定义变量
    private final ArrayList<Fragment> tabFragmentList = new ArrayList<>();

    private String[] mTitles_3;
    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private boolean run = true;
    private TextView mTvTime;
    String nowDate = "";

    public Main_Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Main_Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Main_Fragment1 newInstance(String param1, String param2) {
        Main_Fragment1 fragment = new Main_Fragment1();
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
        View view = inflater.inflate(R.layout.fragment_main_1, container, false);
        //        找控件
        mViewpager = view.findViewById(R.id.viewpager);
        mTablayout = view.findViewById(R.id.tablayout);
        mTitles_3 = getResources().getStringArray(R.array.data1);
//        追加全部类型 页面
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(mTitles_3));
        list.add(0, "全部");
        mTitles_3 = list.toArray(new String[list.size()]);

//        获取分类数据
        if (mTitles_3.length == 0) {
            Toast.makeText(getActivity(), "资源文件分类数据不能为了空", Toast.LENGTH_SHORT).show();
        }
//        根据分类的数据 初始化页面
        for (int i = 0; i < mTitles_3.length; i++) {
            Home_item_yonghu home_item1 = Home_item_yonghu.newInstance(mTitles_3[i], "1");
            tabFragmentList.add(home_item1);
        }
//        绑定 viewpager fragment Tablelayout
        mViewpager.setOffscreenPageLimit(tabFragmentList.size());
        mViewpager.setAdapter(new MPagerAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabFragmentList));
        mTablayout.setupWithViewPager(mViewpager);

        mTvTime = view.findViewById(R.id.tv_time);


        //开线程 获取网络日期 显示日期 时间和星期
        new Thread(new Runnable() {
            @Override
            public void run() {
                nowDate = DateUtils.getNowDate();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        String s = "                                                      一、预约挂号须知：\n" +
                                "1、实名制预约：需要您提供姓名、有效身份证件号码、有效手机号码等信息。\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "2、预约无效：若因就诊人提供的信息不正确，将视为无效预约。\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "3、预约取消：预约成功后，若不能按时就诊，请至少提前一天取消预约,当天预约不可取消。\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "4、预约成功后，请您合理安排时间、按时到达候诊区取号处出示预约信息扫码获取纸质号码，取完纸质号码后到候诊区等待叫号就诊。\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "5、温馨提示：就诊挂号当日有效，隔日自动作废。" +
                                "二、过号处理\n" +
                                "\n" +
                                "1.如果您错过了叫号，不要着急，在听到叫号后的一段时间内，请到分诊台向工作人员说明情况，我们将为您安排尽快就诊。\n" +
                                "\n" +
                                "2.若多次错过叫号，可能会影响您的就诊顺序，请按时就诊。" +
                                "三、特殊情况\n" +
                                "\n" +
                                "1.如有紧急情况或病情较重的患者，医护人员会根据情况优先安排就诊。\n" +
                                "\n" +
                                "2.如果您对叫号系统或就诊流程有任何疑问，请随时向现场工作人员咨询。\n" +
                                "\n" +
                                "门诊部电话：xxx\n" +
                                "\n" +
                                "感谢您的理解与配合，祝您早日康复！地址：xxx 电话：xxx";
                        mTvTime.setText(s + " 当前日期:  " + nowDate);
                    }
                });


            }
        }).start();

        return view;
    }

    //    viewpager适配器
    class MPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;

        public MPagerAdapter(FragmentManager fm, int behavior, List<Fragment> fragmentList) {
            super(fm, behavior);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        //返回tablayout的标题文字;
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles_3[position];
        }
    }

    @Override
    public void onDestroy() {
        run = false;
        super.onDestroy();
    }
}