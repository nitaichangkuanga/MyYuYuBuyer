package com.wushuikeji.www.yuyubuyer.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToastUtils;
import com.wushuikeji.www.yuyubuyer.utils.UIUtils;
import com.wushuikeji.www.yuyubuyer.view.ClearEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RealEditActivity extends AppCompatActivity {

    @InjectView(R.id.rl_realEdit_back)
    RelativeLayout rl_realEdit_back;

    @InjectView(R.id.rb_realEdit_manSex)
    RadioButton rb_realEdit_manSex;

    @InjectView(R.id.rl_realEdit_touxiang)
    RelativeLayout rl_realEdit_touxiang;

    @InjectView(R.id.iv_realEdit_userImg)
    ImageView iv_realEdit_userImg;

    @InjectView(R.id.rl_realEdit_niCheng)
    RelativeLayout rl_realEdit_niCheng;

    @InjectView(R.id.rl_realEdit_age)
    RelativeLayout rl_realEdit_age;

    @InjectView(R.id.rl_realEdit_moko)
    RelativeLayout rl_realEdit_moko;

    @InjectView(R.id.tv_realEdit_name)
    TextView tv_realEdit_name;

    @InjectView(R.id.tv_realEdit_ageTwo)
    TextView tv_realEdit_ageTwo;

    @InjectView(R.id.tv_realEdit_signatureTwo)
    TextView tv_realEdit_signatureTwo;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_edit);
        initView();
        initEvent();
    }

    private void initView() {
        ButterKnife.inject(this);
        //默认男生
        rb_realEdit_manSex.setChecked(true);
    }

    private void initEvent() {
        //返回
        BackListenerUtils.backFinish(rl_realEdit_back, this);
        //头像
        rl_realEdit_touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出pop
                setPop();
            }
        });
        //昵称
        rl_realEdit_niCheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customNiChengDialog();
            }
        });
        //年龄
        rl_realEdit_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAgeDialog();
            }
        });
        //个性签名
        rl_realEdit_moko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customMokoDialog();
            }
        });
    }

    /**
     * 弹出签名
     */
    private void customMokoDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RealEditActivity.this);
        View inflate = View.inflate(RealEditActivity.this, R.layout.custom_mokoalert_dialog, null);
        final ClearEditText mokoCedit = (ClearEditText) inflate.findViewById(R.id.ct_moko_name);
        //取消
        Button canleButton = (Button) inflate.findViewById(R.id.b_moko_canle);
        //确定
        Button decidedButton = (Button) inflate.findViewById(R.id.b_moko_decided);
        //弹出输入法
        displayInput(mokoCedit);
        mBuilder.setView(inflate);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();

        canleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        decidedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                tv_realEdit_signatureTwo.setText(mokoCedit.getText().toString().trim());
            }
        });
    }

    /**
     * 弹出年龄
     */
    private void customAgeDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RealEditActivity.this);
        View inflate = View.inflate(RealEditActivity.this, R.layout.custom_agealert_dialog, null);
        final ClearEditText ageCedit = (ClearEditText) inflate.findViewById(R.id.ct_age_name);
        //取消
        Button canleButton = (Button) inflate.findViewById(R.id.b_age_canle);
        //确定
        Button decidedButton = (Button) inflate.findViewById(R.id.b_age_decided);
        //弹出输入法
        displayInput(ageCedit);
        mBuilder.setView(inflate);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();

        canleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        decidedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                tv_realEdit_ageTwo.setText(ageCedit.getText().toString().trim());
            }
        });
    }

    /**
     * 弹出对话框昵称
     */
    private void customNiChengDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RealEditActivity.this);
        View inflate = View.inflate(RealEditActivity.this, R.layout.custom_nichengalert_dialog, null);
        final ClearEditText nameCedit = (ClearEditText) inflate.findViewById(R.id.ct_niCheng_name);
        //取消
        Button canleButton = (Button) inflate.findViewById(R.id.b_niCheng_canle);
        //确定
        Button decidedButton = (Button) inflate.findViewById(R.id.b_niCheng_decided);
        //弹出输入法
        displayInput(nameCedit);
        mBuilder.setView(inflate);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();

        canleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        decidedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                tv_realEdit_name.setText(nameCedit.getText().toString().trim());
            }
        });
    }
    //Dialog中弹出输入法
    private void displayInput(final EditText et) {
        UIUtils.getMainThreadHandler().postDelayed(new Runnable() {
            public void run() {
                InputMethodManager inManager = (InputMethodManager)et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        },200);
    }

    /**
     * 设置头像pop
     */
    private void setPop() {
        View popupwindowView = getLayoutInflater().inflate(R.layout.edit_pic_pop, null);
        //图片库
        TextView mPicKu = (TextView) popupwindowView.findViewById(R.id.tv_popupwindow_picKu);
        //拍照
        TextView mPic = (TextView) popupwindowView.findViewById(R.id.tv_popupwindow_pic);
        //取消
        TextView mTv_cancel = (TextView) popupwindowView.findViewById(R.id.tv_popupwindow_cancel);

        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(popupwindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
            //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupwindow));
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 设置点击弹框外部，弹框消失
            mPopupWindow.setOutsideTouchable(true);
            // 设置焦点
            mPopupWindow.setFocusable(true);
            // 设置所在布局
            // x和y的单位是像素
            mPopupWindow.showAtLocation(popupwindowView, Gravity.BOTTOM, 0, 15);
        }
        //取消
        mTv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
            }
        });
        //拍照
        mPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 23);
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
            }
        });
        //图库
        mPicKu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                } else {
                    intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }
                startActivityForResult(intent, 33);
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //系统照相
        if (resultCode == Activity.RESULT_OK && requestCode == 23) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                ToastUtils.showToastInUIThread(UIUtils.getContext(), "SD卡不可用");
                return;
            }
            String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            if (data != null) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                FileOutputStream b = null;
                //为什么不能直接保存在系统相册位置呢?
                File file = new File(Environment.getExternalStorageDirectory(), "myImage");
                if (!file.exists()) {
                    file.mkdirs();// 创建文件夹
                }
                String fileName = file.getAbsolutePath() + File.separator + name;

                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (b != null) {
                            b.flush();
                            b.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                iv_realEdit_userImg.setImageBitmap(bitmap);// 将图片显示在ImageView里
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == 33) {
            //图库
            try {
                Bitmap bm = null;
                // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                ContentResolver resolver = getContentResolver();

                if (data != null) {
                    Uri originalUri = data.getData(); // 获得图片的uri
                    bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                    iv_realEdit_userImg.setImageBitmap(ThumbnailUtils.extractThumbnail(bm, 100, 100));
                }
            } catch (Exception e) {
            }
        }
    }
}
