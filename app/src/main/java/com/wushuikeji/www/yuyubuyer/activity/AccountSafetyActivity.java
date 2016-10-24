package com.wushuikeji.www.yuyubuyer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.constants.Constants;
import com.wushuikeji.www.yuyubuyer.constants.MyConstants;
import com.wushuikeji.www.yuyubuyer.jsonparse.SendAuthoCodeParse;
import com.wushuikeji.www.yuyubuyer.utils.BackListenerUtils;
import com.wushuikeji.www.yuyubuyer.utils.CloseInputUtils;
import com.wushuikeji.www.yuyubuyer.utils.ISNetworkConnectUtils;
import com.wushuikeji.www.yuyubuyer.utils.RecoverClickUtils;
import com.wushuikeji.www.yuyubuyer.utils.RequestResponseUtils;
import com.wushuikeji.www.yuyubuyer.utils.SpUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToastUtils;
import com.wushuikeji.www.yuyubuyer.utils.UIUtils;
import com.wushuikeji.www.yuyubuyer.view.ClearEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

public class AccountSafetyActivity extends AppCompatActivity {
    
    @InjectView(R.id.rl_accountSafety_back)
    RelativeLayout rl_accountSafety_back;
    //实名认证
    @InjectView(R.id.rl_accountSafety_authentication)
    RelativeLayout rl_accountSafety_authentication;
    //绑定手机号
    @InjectView(R.id.rl_accountSafety_phone)
    RelativeLayout rl_accountSafety_phone;
    //密码
    @InjectView(R.id.rl_accountSafety_pass)
    RelativeLayout rl_accountSafety_pass;

    private String mUserId;

    private String accountSafeSubmitUrl = Constants.commontUrl + "auth/accreditation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safety);
        initView();
        initEvent();
    }

    private void initView() {
        ButterKnife.inject(this);
        //得到用户ID
        mUserId = SpUtils.getCacheString(MyConstants.LOGINSPNAME, UIUtils.getContext(), MyConstants.BUYERID, "");
    }

    private void initEvent() {
        //返回
        BackListenerUtils.backFinish(rl_accountSafety_back,this);
        //实名认证
        rl_accountSafety_authentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出自定义对话框
                customAlertDialog();
            }
        });
        //绑定手机号
        rl_accountSafety_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPhoneDialog();
            }
        });
        //登录密码
        rl_accountSafety_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPasswordDialog();
            }
        });

    }

    /**
     * 自定义密码的dialog
     */
    private void customPasswordDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AccountSafetyActivity.this);
        View inflate = View.inflate(AccountSafetyActivity.this, R.layout.custom_passalert_dialog, null);
        EditText pass_et = (EditText) inflate.findViewById(R.id.et_pass_userName);
        //取消
        Button canleButton = (Button) inflate.findViewById(R.id.b_pass_canle);
        //提交
        Button submitButton = (Button) inflate.findViewById(R.id.b_pass_submit);
        //弹出输入法
        displayInput(pass_et);
        mBuilder.setView(inflate);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();

        canleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 自定义电话的dialog
     */
    private void customPhoneDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AccountSafetyActivity.this);
        View inflate = View.inflate(AccountSafetyActivity.this, R.layout.custom_phonealert_dialog, null);
        EditText phone_et = (EditText) inflate.findViewById(R.id.et_dialog_num);
        //取消
        Button canleButton = (Button) inflate.findViewById(R.id.b_phone_canle);
        //发送验证码
        Button sendButton = (Button) inflate.findViewById(R.id.b_phone_send);
        //弹出输入法
        displayInput(phone_et);
        mBuilder.setView(inflate);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();

        canleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 自定义对话框
     */
    private void customAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountSafetyActivity.this);
        View inflate = View.inflate(AccountSafetyActivity.this, R.layout.custom_alert_dialog, null);
        //输入姓名
        final ClearEditText userNameET = (ClearEditText) inflate.findViewById(R.id.clearEt_dialog_userName);
        //输入身份证
        final ClearEditText cardET = (ClearEditText) inflate.findViewById(R.id.clearEt_dialog_idCard);
        //取消
        final Button canleButton = (Button) inflate.findViewById(R.id.b_alert_canle);
        //提交
        final Button submitButton = (Button) inflate.findViewById(R.id.b_alert_submit);
        //进度条
        final ProgressBar pb_alertDialog_progressBar = (ProgressBar) inflate.findViewById(R.id.pb_alertDialog_progressBar);
        //弹出输入法
        displayInput(userNameET);
        builder.setView(inflate);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //取消
        canleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        //提交
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameET.getText().toString().trim();
                String userCard = cardET.getText().toString().trim();
                if(TextUtils.isEmpty(userName)) {
                    userNameET.setShakeAnimation();
                    ToastUtils.showToastInUIThread(AccountSafetyActivity.this,"真实姓名不能为空");
                    return;
                }

                if(TextUtils.isEmpty(userCard)) {
                    cardET.setShakeAnimation();
                    ToastUtils.showToastInUIThread(AccountSafetyActivity.this,"身份证号不能为空");
                    return;
                }
                //判断网络
                boolean iSNetworkConnect = ISNetworkConnectUtils.iSNetworkConnect(UIUtils.getContext());
                if (!iSNetworkConnect) {
                    ToastUtils.showToastInUIThread(UIUtils.getContext(), "您尚未开启网络");
                    return;
                } else {
                    CloseInputUtils.closeInput(UIUtils.getContext(), AccountSafetyActivity.this);
                    //进度条
                    pb_alertDialog_progressBar.setVisibility(View.VISIBLE);
                    //进度的时候，按钮不能点击了
                    canleButton.setClickable(false);
                    submitButton.setClickable(false);
                    userNameET.setFocusable(false);
                    cardET.setFocusable(false);
                    OkHttpUtils.post().url(accountSafeSubmitUrl).addParams("user_id", mUserId)
                            .addParams("real_name", userName)
                            .addParams("ID_number", userCard).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                                //动画关闭
                                pb_alertDialog_progressBar.setVisibility(View.GONE);
                                //恢复编辑状态
                                recoverClick(userNameET,cardET,canleButton,submitButton);
                                ToastUtils.showToastInUIThread(UIUtils.getContext(),"请求超时");

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                //动画关闭
                                pb_alertDialog_progressBar.setVisibility(View.GONE);
                                //恢复编辑状态
                                recoverClick(userNameET,cardET,canleButton,submitButton);
                                // 成功了，只是返回来请求的json数据,还需要解析
                                Map<String, String> map = SendAuthoCodeParse.sendAuthoCodeJson(response);
                                String response_code = map.get("response_code");
                                //需要判断各种响应码,获取具体错误信息
                                String responseContent = RequestResponseUtils.getResponseContent(response_code);
                                if("0".equals(response_code)) {
                                    ToastUtils.showToastInUIThread(UIUtils.getContext(),"认证成功");
                                    alertDialog.dismiss();
                                }else if (responseContent != null) {
                                    ToastUtils.showToastInUIThread(UIUtils.getContext(), responseContent);
                                }
                            } catch (Exception e) {
                                ToastUtils.showToastInUIThread(UIUtils.getContext(),"请求超时");
                                //动画关闭
                                pb_alertDialog_progressBar.setVisibility(View.GONE);
                                //恢复编辑状态
                                recoverClick(userNameET,cardET,canleButton,submitButton);
                            }
                        }
                    });

                }
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
     * 恢复默认可以点击
     */
    private void recoverClick(ClearEditText userNameEditText,ClearEditText userCardEditText,Button cancel,Button submit) {
        //让EditText恢复编辑状态
        RecoverClickUtils.recoverClickUtils(userNameEditText);
        RecoverClickUtils.recoverClickUtils(userCardEditText);
        //其他控件可以点击
        cancel.setClickable(true);
        submit.setClickable(true);
    }
}
