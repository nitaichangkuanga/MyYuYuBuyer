package com.wushuikeji.www.yuyubuyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToNextActivityUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyAccountActivity extends AppCompatActivity {

    @InjectView(R.id.b_account_recharge)
    Button b_account_recharge;

    @InjectView(R.id.b_account_withdraw)
    Button b_account_withdraw;

    @InjectView(R.id.rl_account_back)
    RelativeLayout rl_account_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        initView();
        initEvent();
    }

    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        //返回
        BackListenerUtils.backFinish(rl_account_back,this);
        //充值
        b_account_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivityUtils.toNextAndNoFinishActivity(MyAccountActivity.this,RechargeActivity.class);
            }
        });
        //提现
        b_account_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivityUtils.toNextAndNoFinishActivity(MyAccountActivity.this,WithdrawActivity.class);
            }
        });
    }
}
