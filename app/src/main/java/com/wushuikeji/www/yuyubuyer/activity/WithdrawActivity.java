package com.wushuikeji.www.yuyubuyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WithdrawActivity extends AppCompatActivity {

    @InjectView(R.id.b_withdraw_register)
    Button b_withdraw_register;

    @InjectView(R.id.rl_withdraw_back)
    RelativeLayout rl_withdraw_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        initView();
        initEvent();

    }

    private void initView() {
        ButterKnife.inject(this);
    }
    private void initEvent() {
        //返回
        BackListenerUtils.backFinish(rl_withdraw_back,this);
        //确认提现
        b_withdraw_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
