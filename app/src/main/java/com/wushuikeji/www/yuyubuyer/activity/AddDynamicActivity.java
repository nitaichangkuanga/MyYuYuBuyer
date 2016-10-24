package com.wushuikeji.www.yuyubuyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;
import com.wushuikeji.www.yuyubuyer.utils.BitmapUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddDynamicActivity extends AppCompatActivity {

    @InjectView(R.id.rl_add_back)
    RelativeLayout rl_add_back;
    //发送
    @InjectView(R.id.rl_addDynamic_send)
    RelativeLayout rl_addDynamic_send;

    @InjectView(R.id.et_add_input)
    EditText et_add_input;

    //视频
    @InjectView(R.id.ib_add_video)
    ImageButton ib_add_video;
    //放视频的缩放图
    @InjectView(R.id.add_img)
    ImageView add_img;

    //删除视频图标
    @InjectView(R.id.add_delete)
    ImageButton add_delete;

    //视频的缩约图
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dynamic);
        initView();
        initEvent();
    }

    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        //返回
        BackListenerUtils.backFinish(rl_add_back,this);
        //视频
        ib_add_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启视频的拍摄
                Intent intent=new Intent();
                intent.setClass(AddDynamicActivity.this, RecorderVideoActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        //发送
        rl_addDynamic_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除目录(取消发送)
                //    if(localPath != null){
                //        File file = new File(localPath);
                //        if(file.exists())
                //            file.delete();
                //    }
                if(TextUtils.isEmpty(et_add_input.getText().toString().trim())) {
                    ToastUtils.showToastInUIThread(AddDynamicActivity.this,"动态文字不能为空");
                    return;
                }else if(bitmap == null) {
                    ToastUtils.showToastInUIThread(AddDynamicActivity.this,"视频不能为空");
                    return;
                }else {
                    //真正的发送
                }
            }
        });
        //删除视频图标
        add_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_img.setVisibility(View.GONE);
                add_delete.setVisibility(View.GONE);
                if(ib_add_video !=null) {
                    ib_add_video.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode==100 ) {
                if(data != null) {
                    boolean videoStatus = data.getBooleanExtra("videoStatus", false);
                    String videoPath = data.getStringExtra("videoPath");
                    //获取视频的缩略图
                    bitmap = BitmapUtils.getVideoThumbnail(videoPath, 200, 200, MediaStore.Video.Thumbnails.MINI_KIND);
                    if(add_img != null) {
                        add_img.setVisibility(View.VISIBLE);
                        add_delete.setVisibility(View.VISIBLE);
                        add_img.setImageBitmap(bitmap);
                    }
                    //根据拍摄视频类返回的状态来判断图标的显示
                    if(videoStatus && ib_add_video !=null) {
                        ib_add_video.setVisibility(View.GONE);
                    }else {
                        ib_add_video.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
}
