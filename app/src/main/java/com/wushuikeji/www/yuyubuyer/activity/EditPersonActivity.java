package com.wushuikeji.www.yuyubuyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToNextActivityUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditPersonActivity extends AppCompatActivity {

    @InjectView(R.id.tv_edit_edit)
    TextView tv_edit_edit;

    @InjectView(R.id.rl_edit_back)
    RelativeLayout rl_edit_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        initView();
        initEvent();
    }

    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        //返回
        BackListenerUtils.backFinish(rl_edit_back,this);
        //编辑事件
        tv_edit_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivityUtils.toNextAndNoFinishActivity(EditPersonActivity.this,RealEditActivity.class);
            }
        });
    }
}
