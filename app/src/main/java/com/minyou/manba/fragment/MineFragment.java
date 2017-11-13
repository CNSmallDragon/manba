package com.minyou.manba.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.R;
import com.minyou.manba.activity.LoginActivity;
import com.minyou.manba.bean.ManBaUserInfo;
import com.minyou.manba.util.GlideCircleTransform;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment implements View.OnClickListener{

	private static final String TAG = "MineFragment";
	public static final int LOGIN_CODE = 100;
	public static final String REQUEST = "requestInfo";

	@BindView(R.id.bt_login)
	Button bt_login;
	@BindView(R.id.rl_after_login)
	RelativeLayout rl_after_login;
	@BindView(R.id.rl_before_login)
	RelativeLayout rl_before_login;
	@BindView(R.id.ll_after_login)
	LinearLayout ll_after_login;
	@BindView(R.id.iv_user_pic)
	ImageView user_pic;
	@BindView(R.id.tv_pub_time)
	TextView user_name;

	private RequestManager glideRequest;

	private ManBaUserInfo mUserInfo;

	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

		}
	};

	@Override
	public int getContentViewId() {
		return R.layout.fragment_mine;
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		if(isLogin()){
			rl_after_login.setVisibility(View.VISIBLE);
			ll_after_login.setVisibility(View.VISIBLE);
			rl_before_login.setVisibility(View.GONE);
		}else{
			rl_after_login.setVisibility(View.GONE);
			ll_after_login.setVisibility(View.GONE);
			rl_before_login.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	private boolean isLogin(){
		String phone = SharedPreferencesUtil.getInstance(getActivity()).getSP("phone");
		String password = SharedPreferencesUtil.getInstance(getActivity()).getSP("password");
		String userInfoStr = SharedPreferencesUtil.getInstance(getActivity()).getSP("user_info");
		
		if("18516145120".equals(phone) && "123456".equals(password)){
			return true;
		}else if (!TextUtils.isEmpty(userInfoStr)){
			Log.i(TAG, "isLogin: ");
			try {
				JSONObject jsonObject = new JSONObject(userInfoStr);
				mUserInfo = new ManBaUserInfo();
				mUserInfo.setNickName(jsonObject.get("nickname").toString().trim());
				mUserInfo.setSex(Integer.parseInt(jsonObject.get("sex").toString().trim()));
				mUserInfo.setPhotoUrl(jsonObject.get("headimgurl").toString().trim());
				return true;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		return false;
	}

	@OnClick(R.id.bt_login)
	public void login(){
		Intent intent = new Intent();
		intent.setClass(getActivity(),LoginActivity.class);
//		startActivityForResult(intent, LOGIN_CODE);
		startActivity(intent);
	}


	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()){
			case R.id.bt_login:
				intent = new Intent();
				intent.setClass(getActivity(),LoginActivity.class);
//				startActivityForResult(intent, LOGIN_CODE);
				startActivity(intent);
				break;

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case LOGIN_CODE:
				if (resultCode == Activity.RESULT_OK) {
					String nickname = data.getStringExtra("nickname");
					String photoUrl = data.getStringExtra("figureurl_qq_2");
					LogUtil.d(TAG,"nickname--------------" + nickname);
					LogUtil.d(TAG,"photoUrl--------------" + photoUrl);
					Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
					//SharedPreferencesUtil.getInstance(getActivity()).putSP("phone","18516145120");
					//SharedPreferencesUtil.getInstance(getActivity()).putSP("password","123456");

					rl_after_login.setVisibility(View.VISIBLE);
					ll_after_login.setVisibility(View.VISIBLE);
					rl_before_login.setVisibility(View.GONE);

					user_name.setText(nickname);

					glideRequest = Glide.with(getActivity());
					glideRequest.load(photoUrl).transform(new GlideCircleTransform(getActivity())).into(user_pic);
				}else{
					Toast.makeText(getActivity(), "登陆失败", Toast.LENGTH_SHORT).show();
				}
				break;

		}
	}
}
