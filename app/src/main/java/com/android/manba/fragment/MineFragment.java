package com.android.manba.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.manba.R;
import com.android.manba.activity.LoginActivity;
import com.android.manba.util.LogUtil;
import com.android.manba.util.SharedPreferencesUtil;

public class MineFragment extends BaseFragment implements View.OnClickListener{

	private static final String TAG = "MineFragment";
	public static final int LOGIN_CODE = 100;
	public static final String REQUEST = "requestInfo";
	private Button bt_login;
	private RelativeLayout rl_after_login;
	private RelativeLayout rl_before_login;
	private LinearLayout ll_after_login;

	@Override
	public View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_mine, null);
		rl_after_login = (RelativeLayout)view.findViewById(R.id.rl_after_login);
		ll_after_login = (LinearLayout) view.findViewById(R.id.ll_after_login);
		rl_before_login = (RelativeLayout)view.findViewById(R.id.rl_before_login);
		// 判断用户当前登录状态,先本地保存用户信息
		if(isLogin()){
			rl_after_login.setVisibility(View.VISIBLE);
			ll_after_login.setVisibility(View.VISIBLE);
			rl_before_login.setVisibility(View.GONE);
		}else{
			rl_after_login.setVisibility(View.GONE);
			ll_after_login.setVisibility(View.GONE);
			rl_before_login.setVisibility(View.VISIBLE);
		}
		bt_login = (Button)view.findViewById(R.id.bt_login);
		bt_login.setOnClickListener(this);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	private boolean isLogin(){
		String phone = SharedPreferencesUtil.getInstance(getActivity()).getSP("phone");
		String password = SharedPreferencesUtil.getInstance(getActivity()).getSP("password");
		if("18516145120".equals(phone) && "123456".equals(password)){
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()){
			case R.id.bt_login:
				intent = new Intent();
				intent.setClass(getActivity(),LoginActivity.class);
				startActivityForResult(intent, LOGIN_CODE);
				break;

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case LOGIN_CODE:
				if (resultCode == Activity.RESULT_OK) {
					String requestStr = data.getStringExtra(REQUEST);
					LogUtil.d(TAG,requestStr+"--------------");
					Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
					SharedPreferencesUtil.getInstance(getActivity()).putSP("phone","18516145120");
					SharedPreferencesUtil.getInstance(getActivity()).putSP("password","123456");

					rl_after_login.setVisibility(View.VISIBLE);
					ll_after_login.setVisibility(View.VISIBLE);
					rl_before_login.setVisibility(View.GONE);
				}else{
					Toast.makeText(getActivity(), "登陆失败", Toast.LENGTH_SHORT).show();
				}
				break;

		}
	}
}
