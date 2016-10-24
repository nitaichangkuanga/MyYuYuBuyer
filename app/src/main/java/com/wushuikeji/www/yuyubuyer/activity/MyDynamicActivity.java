package com.wushuikeji.www.yuyubuyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToNextActivityUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyDynamicActivity extends AppCompatActivity {

    @InjectView(R.id.rl_md_back)
    RelativeLayout rl_md_back;

    @InjectView(R.id.ib_myDynamic_add)
    ImageButton ib_myDynamic_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dynamic);
        initView();
        initEvent();
    }
    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        //返回
        BackListenerUtils.backFinish(rl_md_back,this);
        //添加动态
        ib_myDynamic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivityUtils.toNextAndNoFinishActivity(MyDynamicActivity.this,AddDynamicActivity.class);
            }
        });

    }
}
