package com.wushuikeji.www.yuyubuyer.fragment;


import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.activity.AccountSafetyActivity;
import com.wushuikeji.www.yuyubuyer.activity.BindBankCardActivity;
import com.wushuikeji.www.yuyubuyer.activity.EditPersonActivity;
import com.wushuikeji.www.yuyubuyer.activity.LoginActivity;
import com.wushuikeji.www.yuyubuyer.activity.MoneyRecordActivity;
import com.wushuikeji.www.yuyubuyer.activity.MyAccountActivity;
import com.wushuikeji.www.yuyubuyer.activity.MyCollectActivity;
import com.wushuikeji.www.yuyubuyer.activity.MyDynamicActivity;
import com.wushuikeji.www.yuyubuyer.activity.MyEvaluateActivity;
import com.wushuikeji.www.yuyubuyer.activity.PersonOrderActivity;
import com.wushuikeji.www.yuyubuyer.activity.RealEditActivity;
import com.wushuikeji.www.yuyubuyer.activity.ReportActivity;
import com.wushuikeji.www.yuyubuyer.activity.ReturnApplyActivity;
import com.wushuikeji.www.yuyubuyer.activity.SettingActivity;
import com.wushuikeji.www.yuyubuyer.activity.SubmitRecordActivity;
import com.wushuikeji.www.yuyubuyer.base.BaseApplication;
import com.wushuikeji.www.yuyubuyer.base.BaseFragment;
import com.wushuikeji.www.yuyubuyer.constants.MyConstants;
import com.wushuikeji.www.yuyubuyer.utils.PtrFrameRefreshUtils;
import com.wushuikeji.www.yuyubuyer.utils.SpUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToNextActivityUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToastUtils;
import com.wushuikeji.www.yuyubuyer.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class PersonalFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.tv_personal_login)
    TextView tv_personal_login;

    @InjectView(R.id.tv_personal_id)
    TextView tv_personal_id;

    //刷新
    @InjectView(R.id.in_mainpersonal_pf)
    PtrClassicFrameLayout mPersonalPtrFrame;

    //ScrollView
    @InjectView(R.id.personal_scrollView)
    ScrollView personal_scrollView;

    //编辑
    @InjectView(R.id.iv_mainPersonal_edit)
    ImageView iv_mainPersonal_edit;

    //个人接单
    @InjectView(R.id.rl_mainPersonal_personalOrder)
    RelativeLayout rl_mainPersonal_personalOrder;
    //我的动态
    @InjectView(R.id.rl_mainPersonal_my)
    RelativeLayout rl_mainPersonal_my;

    @InjectView(R.id.rl_personal_bom)
    RelativeLayout rl_personal_bom;

    @InjectView(R.id.rl_personal_a)
    RelativeLayout rl_personal_a;
    //我的账户
    @InjectView(R.id.rl_personal_rl)
    RelativeLayout rl_personal_rl;

    @InjectView(R.id.rl_personal_b)
    RelativeLayout rl_personal_b;

    @InjectView(R.id.rl_personal_rltwo)
    RelativeLayout rl_personal_rltwo;

    @InjectView(R.id.rl_personal_c)
    RelativeLayout rl_personal_c;

    @InjectView(R.id.rl_personal_rlthree)
    RelativeLayout rl_personal_rlthree;

    @InjectView(R.id.rl_personal_d)
    RelativeLayout rl_personal_d;

    @InjectView(R.id.rl_personal_jubao)
    RelativeLayout rl_personal_jubao;

    @InjectView(R.id.rl_personal_setting)
    RelativeLayout rl_personal_setting;
    //点击最上面的图片
    @InjectView(R.id.iv_main_personal_bg)
    ImageView iv_main_personal_bg;

    private View mPersonalFragmentView;
    private static final int SUCCESS = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    //对数据的刷新,设置数据
                    mPersonalPtrFrame.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };
    private boolean mIsLoginBoolean;

    @Override
    public View initView() {
        //需要展示的PersonalView
        mPersonalFragmentView = View.inflate(UIUtils.getContext(), R.layout.main_personal, null);

        //注解
        ButterKnife.inject(this, mPersonalFragmentView);

        //刷新的操作
        mPersonalPtrFrame.setLastUpdateTimeRelateObject(this);
        PtrFrameRefreshUtils.setRefreshParams(mPersonalPtrFrame);
        personal_scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //查看用户是否登陆过
        mIsLoginBoolean = SpUtils.getCacheBoolean(MyConstants.LOGINSPNAME, UIUtils.getContext(), MyConstants.ISLOGINSTATUS, false);

        return mPersonalFragmentView;
    }

    @Override
    public void initData() {
        super.initData();
        //开启线程加载数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载数据
                //模拟初始化数据
                //moniData();
                //放送handler
                mHandler.obtainMessage(SUCCESS).sendToTarget();
            }
        }).start();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        //点击登录
        tv_personal_login.setOnClickListener(this);
        //编辑
        iv_mainPersonal_edit.setOnClickListener(this);
        //点击最上面的图片
        iv_main_personal_bg.setOnClickListener(this);
        //个人接单
        rl_mainPersonal_personalOrder.setOnClickListener(this);
        rl_mainPersonal_my.setOnClickListener(this);
        rl_personal_bom.setOnClickListener(this);
        rl_personal_a.setOnClickListener(this);
        rl_personal_rl.setOnClickListener(this);
        rl_personal_b.setOnClickListener(this);
        rl_personal_rltwo.setOnClickListener(this);
        rl_personal_c.setOnClickListener(this);
        rl_personal_rlthree.setOnClickListener(this);
        rl_personal_d.setOnClickListener(this);
        rl_personal_jubao.setOnClickListener(this);
        rl_personal_setting.setOnClickListener(this);
        //实现下拉刷新
        mPersonalPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                // here check $mListView instead of $content
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, personal_scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //真正实现下拉刷新,这是在主线程里
                updateData();
            }
        });
        //一进来从缓存中读取用户的用户名,必须放在设置监听事件之后才起作用
        String userName = SpUtils.getCacheString(MyConstants.LOGINSPNAME,UIUtils.getContext(), MyConstants.USERNAME, "");
        String buyerId = SpUtils.getCacheString(MyConstants.LOGINSPNAME,UIUtils.getContext(), MyConstants.BUYERID, "");
        if(!TextUtils.isEmpty(userName)) {
            //登录了，显示用户名
            tv_personal_login.setText(userName);
            tv_personal_login.setClickable(false);
        }else {
            tv_personal_login.setText("点击登录");
            tv_personal_login.setClickable(true);
        }
        //buyerId
        if(!TextUtils.isEmpty(buyerId)) {
            //登录了，显示id
            tv_personal_id.setText(buyerId);
        }else {
            tv_personal_id.setText("无");
        }

    }

    /**
     * 真正刷新数据的方法
     */
    private void updateData() {
        //刷新完成
        mPersonalPtrFrame.refreshComplete();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_personal_login://登录
                ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), LoginActivity.class);
                break;
            case R.id.iv_mainPersonal_edit://编辑
                ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), RealEditActivity.class);
                break;
            case R.id.iv_main_personal_bg://图片
                //需要先判断是否登录过,登录了就跳转用户信息界面,否则就进入登录界面
                if("点击登录".equals(tv_personal_login.getText().toString())) {
                    //进入登录界面
                    ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), LoginActivity.class);
                }else {
                    //进入到预编辑界面
                    ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), EditPersonActivity.class);
                }
                break;
            case R.id.rl_mainPersonal_personalOrder://个人接单
                ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), PersonOrderActivity.class);
                break;
            case R.id.rl_mainPersonal_my://我的动态
                if(mIsLoginBoolean) {
                    ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), MyDynamicActivity.class);
                }else {
                    ToastUtils.showToastInUIThread(UIUtils.getContext(),MyConstants.LOGIN);
                    ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.rl_personal_bom://我的评价
                if(mIsLoginBoolean) {
                    ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), MyEvaluateActivity.class);
                }else {
                    ToastUtils.showToastInUIThread(UIUtils.getContext(),MyConstants.LOGIN);
                    ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.rl_personal_a://我的收藏
                if(mIsLoginBoolean) {
                    ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), MyCollectActivity.class);
                }else {
                    ToastUtils.showToastInUIThread(UIUtils.getContext(),MyConstants.LOGIN);
                    ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.rl_personal_rl://我的账户
                ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), MyAccountActivity.class);
                break;
            case R.id.rl_personal_b://账户安全
                if(mIsLoginBoolean) {
                    ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), AccountSafetyActivity.class);
                }else {
                    ToastUtils.showToastInUIThread(UIUtils.getContext(),MyConstants.LOGIN);
                    ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.rl_personal_rltwo://银行卡绑定
                ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), BindBankCardActivity.class);
                break;
            case R.id.rl_personal_c://退款申请
                ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), ReturnApplyActivity.class);
                break;
            case R.id.rl_personal_rlthree://充值记录
                ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), MoneyRecordActivity.class);
                break;
            case R.id.rl_personal_d://提现记录
                ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), SubmitRecordActivity.class);
                break;
            case R.id.rl_personal_jubao://举报
                ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), ReportActivity.class);
                break;
            case R.id.rl_personal_setting://设置
                //点了设置就添加统一管理
                BaseApplication.getInstance().addActivity(getActivity());
                ToNextActivityUtils.toNextAndNoFinishActivity(getActivity(), SettingActivity.class);
                break;
            default:
                break;
        }
    }
}
