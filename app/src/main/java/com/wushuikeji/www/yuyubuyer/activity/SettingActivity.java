package com.wushuikeji.www.yuyubuyer.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.base.BaseApplication;
import com.wushuikeji.www.yuyubuyer.constants.MyConstants;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;
import com.wushuikeji.www.yuyubuyer.utils.SpUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToNextActivityUtils;
import com.wushuikeji.www.yuyubuyer.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.rl_setting_back)
    RelativeLayout rl_setting_back;

    @InjectView(R.id.tv_setting_loginStatus)
    TextView tv_setting_loginStatus;

    @InjectView(R.id.rl_setting_exit)
    RelativeLayout rl_setting_exit;

    private boolean mIsLoginStatus;

    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initEvent();
    }

    private void initView() {
        ButterKnife.inject(this);

        //得到用户是否有登录的缓存
        mIsLoginStatus = SpUtils.getCacheBoolean(MyConstants.LOGINSPNAME, UIUtils.getContext(), MyConstants.ISLOGINSTATUS, false);

        mSharedPreferences = getSharedPreferences(MyConstants.LOGINSPNAME, Context.MODE_PRIVATE);
        //一进来就判断是否登陆过
        if (!mIsLoginStatus) {
            //说明没登录过,那么点击就是登录,字改成登录
            tv_setting_loginStatus.setText("登录");
        }else {
            tv_setting_loginStatus.setText("退出登录");
        }
    }

    private void initEvent() {
        BackListenerUtils.backFinish(rl_setting_back, this);
        //登录
        rl_setting_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_setting_exit://登录或者退出登录
                if (mIsLoginStatus) {
                    //退出登录，清理登录的缓存
                    mSharedPreferences.edit().clear().commit();
                    //关闭mainactivity和自己
                    BaseApplication.getInstance().closeActivity();
                    ToNextActivityUtils.toNextActivity(SettingActivity.this,LoginActivity.class);
                }else {
                    //跳转到登录，进行登录
                    ToNextActivityUtils.toNextAndNoFinishActivity(SettingActivity.this,LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }
}
