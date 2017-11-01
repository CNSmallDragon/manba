package com.android.manba.activity;

import com.android.manba.Appconstant;
import com.android.manba.R;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "LoginActivity";
	private CheckBox cb_display_pwd;
	private EditText login_pwd;
	private Button bt_login;
	private EditText login_number;
	private TextView tv_forget_pwd;
	private Tencent mTencent;
	private LinearLayout ll_qq;
	
	public static String mAppid;
	private String inputNumber;
	private String inputPWD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		login_number = (EditText) findViewById(R.id.login_number);
		login_pwd = (EditText) findViewById(R.id.login_pwd);
		cb_display_pwd = (CheckBox) findViewById(R.id.cb_display_pwd);
		cb_display_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					login_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				}else{
					login_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
				// 将光标移动到最后
				login_pwd.setSelection(login_pwd.getText().toString().length());
			}
		});
		
		bt_login = (Button) findViewById(R.id.bt_login);
		bt_login.setOnClickListener(this);
		tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
		tv_forget_pwd.setOnClickListener(this);
		ll_qq = (LinearLayout) findViewById(R.id.ll_qq);
		ll_qq.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login:
			if(checkLoginNum() && checkPassWord()){
				login();
			}
			break;
		case R.id.tv_forget_pwd:
			if(checkLoginNum()){
				tv_forget_pwd.setTextColor(Color.BLUE);
				Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
				intent.putExtra("number", inputNumber);
				startActivity(intent);
			}
			break;
		case R.id.ll_qq:
			loginQQ();
			break;
		case R.id.ll_weixin:
			loginWeiXin();
			break;
		case R.id.ll_xinlang:
			loginXinLang();
			break;
		default:
			break;
		}
		
	}

	private void loginXinLang() {
		// TODO Auto-generated method stub
		
	}

	private void loginWeiXin() {
		// TODO Auto-generated method stub
		
	}

	private void login() {
		// TODO Auto-generated method stub
		
	}

	private boolean checkLoginNum() {
		inputNumber = login_number.getText().toString();
		if(TextUtils.isEmpty(inputNumber)){
			Toast.makeText(this, "请输入手机号", 0).show();
			return false;
		}
		if(inputNumber.length() != 11){
			Toast.makeText(this, "请输入完整的手机号", 0).show();
			return false;
		}
		Log.e(TAG, "inputNumber===========" + inputNumber);
		return true;
	}
	
	private boolean checkPassWord(){
		inputPWD = login_pwd.getText().toString();
		if(TextUtils.isEmpty(inputPWD)){
			Toast.makeText(this, "密码不能为空", 0).show();
			return false;
		}
		if(inputPWD.length() < 6 || inputPWD.length() > 16){
			Toast.makeText(this, "您输入的密码不符合规范，请重新输入!", 0).show();
			return false;
		}
		return true;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		tv_forget_pwd.setTextColor(Color.GRAY);
	}
	
	private void loginQQ() {
		mAppid = Appconstant.APP_ID;
		mTencent = Tencent.createInstance(mAppid, getApplicationContext());
		mTencent.login(this, "all", new BaseUiListener());
	}
	
	private class BaseUiListener implements IUiListener{

		@Override
		public void onCancel() {
			Log.d(TAG, "取消-----------------");
		}

		@Override
		public void onComplete(Object response) {
			Log.d(TAG, "成功-----------------");
			Log.d(TAG, response.toString());
		}

		@Override
		public void onError(UiError arg0) {
			Log.d(TAG, "error-----------------");
		}
		
	}
}
