package com.wushuikeji.www.yuyubuyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.constants.Constants;
import com.wushuikeji.www.yuyubuyer.constants.MyConstants;
import com.wushuikeji.www.yuyubuyer.jsonparse.LoginParse;
import com.wushuikeji.www.yuyubuyer.jsonparse.SendAuthoCodeParse;
import com.wushuikeji.www.yuyubuyer.utils.CloseInputUtils;
import com.wushuikeji.www.yuyubuyer.utils.ISNetworkConnectUtils;
import com.wushuikeji.www.yuyubuyer.utils.LoadingRoateAnimationUtils;
import com.wushuikeji.www.yuyubuyer.utils.RecoverClickUtils;
import com.wushuikeji.www.yuyubuyer.utils.RequestResponseUtils;
import com.wushuikeji.www.yuyubuyer.utils.SpUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToNextActivityUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToastUtils;
import com.wushuikeji.www.yuyubuyer.utils.UIUtils;
import com.wushuikeji.www.yuyubuyer.view.ClearEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.tv_login_register)
    TextView tv_login_register;

    @InjectView(R.id.tv_login_forget)
    TextView tv_login_forget;

    @InjectView(R.id.b_login_login)
    Button b_login_login;

    @InjectView(R.id.et_login_userName)
    ClearEditText et_login_userName;

    @InjectView(R.id.et_login_password)
    ClearEditText et_login_password;

    //进度动画
    @InjectView(R.id.iv_login_loading)
    ImageView iv_login_loading;

    private String loginUrl = Constants.commontUrl + "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initView() {
        ButterKnife.inject(this);
    }

    private void initEvent() {
        tv_login_register.setOnClickListener(this);
        tv_login_forget.setOnClickListener(this);
        b_login_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_register://新用户注册
                ToNextActivityUtils.toNextAndNoFinishActivity(this, RegisterActivity.class);
                break;
            case R.id.tv_login_forget://忘记密码
                ToNextActivityUtils.toNextAndNoFinishActivity(this, FindPasswordActivity.class);
                break;
            case R.id.b_login_login://登录
                loginOperation();
                break;
            default:
                break;
        }
    }

    /**
     * 登录的操作
     */
    private void loginOperation() {
        //判断用户名 密码是否为空
        String userName = et_login_userName.getText().toString().trim();
        String password = et_login_password.getText().toString().trim();
        //判断用户名
        if (TextUtils.isEmpty(userName)) {
            // 设置晃动
            et_login_userName.setShakeAnimation();
            ToastUtils.showToastInUIThread(UIUtils.getContext(), "账号/手机号不能空");
            return;
        }
        //判断密码
        if (TextUtils.isEmpty(password)) {
            et_login_password.setShakeAnimation();
            ToastUtils.showToastInUIThread(UIUtils.getContext(), "密码不能空");
            return;
        }
        //判断网络
        boolean iSNetworkConnect = ISNetworkConnectUtils.iSNetworkConnect(UIUtils.getContext());
        if (!iSNetworkConnect) {
            ToastUtils.showToastInUIThread(UIUtils.getContext(), "您尚未开启网络");
            return;
        } else {
            CloseInputUtils.closeInput(UIUtils.getContext(), LoginActivity.this);
            //加载动画
            iv_login_loading.setVisibility(View.VISIBLE);
            LoadingRoateAnimationUtils.loadingGifPicture(iv_login_loading, UIUtils.getContext());
            // 动画的时候，登录按钮和忘记密码、注册不能点击了
            cancelClick();
            //post请求网络 验证是否正确
            OkHttpUtils.post().url(loginUrl).addParams("mobile", userName).addParams("password", password).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    try {
                        //动画关闭
                        iv_login_loading.setVisibility(View.GONE);
                        //恢复编辑状态
                        recoverClick();
                        ToastUtils.showToastInUIThread(UIUtils.getContext(),"请求超时");
                    } catch (Exception exception) {
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        //动画关闭
                        iv_login_loading.setVisibility(View.GONE);
                        //恢复编辑状态
                        recoverClick();
                        // 成功了，只是返回来请求的json数据,还需要解析
                        Map<String, String> map = SendAuthoCodeParse.sendAuthoCodeJson(response);
                        String response_code = map.get("response_code");
                        //需要判断各种响应码,获取具体错误信息
                        String responseContent = RequestResponseUtils.getResponseContent(response_code);

                        if("0".equals(response_code)) {
                            //登录成功
                            //解析
                            Map<String, String> userMap = LoginParse.loginParse(response);
                            //暂时先保存用户的用户名和鱼鱼Id
                            SpUtils.putCacheString(MyConstants.LOGINSPNAME,UIUtils.getContext(), MyConstants.USERNAME,userMap.get("username"));
                            SpUtils.putCacheString(MyConstants.LOGINSPNAME,UIUtils.getContext(), MyConstants.BUYERID,userMap.get("id"));
                            //保存登录的状态
                            SpUtils.putCacheBoolean(MyConstants.LOGINSPNAME,UIUtils.getContext(), MyConstants.ISLOGINSTATUS,true);
                            //进入主界面
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this,MainActivity.class);
                            intent.putExtra("fragmentIndex",0);
                            //设置目的，防止按返回键再次进入MainActivity
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            LoginActivity.this.startActivity(intent);
                            LoginActivity.this.finish();
                        }else if (responseContent != null) {
                            ToastUtils.showToastInUIThread(UIUtils.getContext(), responseContent);
                        }
                    } catch (Exception e) {
                        ToastUtils.showToastInUIThread(UIUtils.getContext(),"请求超时");
                        //动画关闭
                        iv_login_loading.setVisibility(View.GONE);
                        //恢复编辑状态
                        recoverClick();
                    }
                }
            });
        }
    }

    private void cancelClick() {
        b_login_login.setClickable(false);
        tv_login_register.setClickable(false);
        tv_login_forget.setClickable(false);
        // 动画的时候，让EditText失去焦点，不能输入
        et_login_userName.setFocusable(false);
        et_login_password.setFocusable(false);
    }

    /**
     * 恢复默认可以点击
     */
    private void recoverClick() {
        //让EditText恢复编辑状态
        RecoverClickUtils.recoverClickUtils(et_login_userName);
        RecoverClickUtils.recoverClickUtils(et_login_password);
        //其他控件可以点击
        b_login_login.setClickable(true);
        tv_login_register.setClickable(true);
        tv_login_forget.setClickable(true);
    }
}
