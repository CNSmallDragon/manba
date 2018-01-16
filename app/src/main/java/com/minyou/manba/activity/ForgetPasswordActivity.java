package com.minyou.manba.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityForgetpwdBinding;
import com.minyou.manba.manager.UserManager;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.util.Assistant;
import com.minyou.manba.util.LogUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetPasswordActivity extends DataBindingBaseActivity implements View.OnClickListener {

	private static final String TAG = "ForgetPasswordActivity";

	private ActivityForgetpwdBinding binding;

	// 总倒计时时间
	private static final long MILLIS_IN_FUTURE = 60 * 1000;
	// 每次减去1秒
	private static final long COUNT_DOWN_INTERVAL = 1000;

	String inputNumber;
	String etMimaStr;

	@Override
	public void setContentViewAndBindData() {
		binding = DataBindingUtil.setContentView(this,R.layout.activity_forgetpwd);
		Intent intent = getIntent();
		if(null != intent){
			binding.loginNumber.setText(intent.getStringExtra("number"));
		}

		initListener();
	}

	private void initListener() {
		binding.tvSendSms.setOnClickListener(this);
		binding.btResetPwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tv_send_sms:
				if(checkLoginNum()){
					startCountDown();
					getSmsCode();
				}
				break;
			case R.id.bt_reset_pwd:
				if(checkRegist()){
					postReset();
				}
				break;
		}
	}

	private void postReset() {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("newPassword", Assistant.BoxingWithMD5(etMimaStr));
		params.put("userId", UserManager.getUserId());
		params.put("smsCode",binding.etSms.getText().toString().trim());
		ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_RESET_PWD, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {
			@Override
			public void onReqSuccess(String result) {

			}

			@Override
			public void onReqFailed(String errorMsg) {

			}
		});
	}

	private boolean checkLoginNum() {
		inputNumber = binding.loginNumber.getText().toString();
		if (TextUtils.isEmpty(inputNumber)) {
			Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (inputNumber.length() != 11) {
			Toast.makeText(this, "请输入完整的手机号", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	// 启动倒计时
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void startCountDown() {
		// 设置按钮为不可点击，并修改显示背景
		binding.tvSendSms.setEnabled(false);
		binding.tvSendSms.setBackground(getDrawable(R.drawable.shape_borderline_grey_8));
		// 开始倒计时
		new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {
			@SuppressLint("StringFormatMatches")
			@Override
			public void onTick(long millisUntilFinished) {
				// 刷新文字
				binding.tvSendSms.setText(getResources().getString(R.string.reget_sms_code_countdown, millisUntilFinished / COUNT_DOWN_INTERVAL));
			}

			@Override
			public void onFinish() {
				// 重置文字，并恢复按钮为可点击
				binding.tvSendSms.setText(R.string.regist_getyanzhengma);
				binding.tvSendSms.setEnabled(true);
				binding.tvSendSms.setBackground(getDrawable(R.drawable.shape_borderline_bluefull));
			}
		}.start();
	}

	private void getSmsCode() {
		OkHttpClient client = new OkHttpClient();
		RequestBody body = new FormBody.Builder()
				.add("phoneNumber", inputNumber)
				.build();
		Request request = new Request.Builder()
				.url(ManBaApi.HTTP_GET_SEND_SMS)
				.post(body)
				.build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String requestStr = response.body().string();
				LogUtil.d(TAG, "-----response---------" + requestStr);
			}
		});
	}

	private boolean checkRegist() {
		checkLoginNum();
		if(TextUtils.isEmpty(binding.etSms.getText().toString().trim())){
			Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
			return false;
		}
		etMimaStr = binding.loginPwd.getText().toString();
		if (TextUtils.isEmpty(etMimaStr)) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (etMimaStr.length() < 6 || etMimaStr.length() > 16) {
			Toast.makeText(this, "您输入的密码不符合规范，请重新输入!", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
