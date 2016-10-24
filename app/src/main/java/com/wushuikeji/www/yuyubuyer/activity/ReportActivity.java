package com.wushuikeji.www.yuyubuyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ReportActivity extends AppCompatActivity {

    @InjectView(R.id.rl_report_back)
    RelativeLayout rl_report_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initView();
        initEvent();
    }
    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        BackListenerUtils.backFinish(rl_report_back,this);
    }
}
