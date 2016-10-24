package com.wushuikeji.www.yuyubuyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SystemInfoDetailsActivity extends AppCompatActivity {

    @InjectView(R.id.rl_systemInfoDetails_back)
    RelativeLayout rl_systemInfoDetails_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_info_details);
        initView();
        initEvent();
    }

    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        //返回
        BackListenerUtils.backFinish(rl_systemInfoDetails_back,this);
    }
}
