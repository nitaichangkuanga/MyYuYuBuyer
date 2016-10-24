package com.wushuikeji.www.yuyubuyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MoneyRecordActivity extends AppCompatActivity {

    @InjectView(R.id.rl_moneyRecord_back)
    RelativeLayout rl_moneyRecord_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_record);
        initView();
        initEvent();
    }
    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        BackListenerUtils.backFinish(rl_moneyRecord_back,this);
    }
}
