package com.wushuikeji.www.yuyubuyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RechargeActivity extends AppCompatActivity {

    @InjectView(R.id.b_recharge_recharge)
    Button b_recharge_recharge;

    @InjectView(R.id.rl_recharge_back)
    RelativeLayout rl_recharge_back;

    @InjectView(R.id.et_recharge_money)
    EditText et_recharge_money;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
        initEvent();
    }
    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        //返回
        BackListenerUtils.backFinish(rl_recharge_back,this);
        //确认充值
        b_recharge_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
