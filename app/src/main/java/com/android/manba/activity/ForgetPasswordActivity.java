package com.android.manba.activity;

import com.android.manba.R;
import com.android.manba.ui.ActionTitleView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ForgetPasswordActivity extends Activity {

	private ActionTitleView atv_forget_pwd;
	private TextView tv_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forgetpwd);
		
		Intent intent = getIntent();
		atv_forget_pwd = (ActionTitleView) findViewById(R.id.atv_forget_pwd);
		tv_title = (TextView) atv_forget_pwd.findViewById(R.id.tv_title);
		if(null != intent){
			tv_title.setText(intent.getStringExtra("number"));
			
		}
	}
}
