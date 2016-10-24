package com.wushuikeji.www.yuyubuyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;
import com.wushuikeji.www.yuyubuyer.view.EaseSwitchButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PersonOrderActivity extends AppCompatActivity {

    @InjectView(R.id.rl_personalOrder_back)
    RelativeLayout rl_personalOrder_back;
    //视频接单
    @InjectView(R.id.custom_switch_videoOrder)
    EaseSwitchButton custom_switch_videoOrder;
    //线下接单
    @InjectView(R.id.custom_switch_lineOrder)
    EaseSwitchButton custom_switch_lineOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_order);
        initView();
        initEvent();
    }

    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        //返回
        BackListenerUtils.backFinish(rl_personalOrder_back,this);
        //视频接单
        custom_switch_videoOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (custom_switch_videoOrder.isSwitchOpen()) {
                    custom_switch_videoOrder.closeSwitch();
                } else {
                    custom_switch_videoOrder.openSwitch();
                }
            }
        });
        //线下接单
        custom_switch_lineOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (custom_switch_lineOrder.isSwitchOpen()) {
                    custom_switch_lineOrder.closeSwitch();
                } else {
                    custom_switch_lineOrder.openSwitch();
                }
            }
        });
    }
}
