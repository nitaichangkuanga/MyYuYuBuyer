package com.wushuikeji.www.yuyubuyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToNextActivityUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BindBankCardActivity extends AppCompatActivity {

    @InjectView(R.id.rl_bank_addBank)
    RelativeLayout rl_bank_addBank;

    @InjectView(R.id.rl_bank_back)
    RelativeLayout rl_bank_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_bank_card);

        initView();

        initEvent();
    }

    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        BackListenerUtils.backFinish(rl_bank_back,this);
        rl_bank_addBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivityUtils.toNextAndNoFinishActivity(BindBankCardActivity.this,AddBankActivity.class);
            }
        });
    }


}
