package com.android.manba.activity;

import com.android.manba.R;
import com.android.manba.fragment.HomeFragment;
import com.android.manba.fragment.MineFragment;
import com.android.manba.fragment.SociationFragment;
import com.android.manba.fragment.StreetFragment;
import com.android.manba.util.LogUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class HomeActivity_bak extends FragmentActivity implements OnClickListener {

	protected static final String TAG = "HomeActivity";
	private static final String HOME = "home";
	private static final String STREET = "street";
	private static final String SOCIATION = "sociation";
	private static final String MINE = "mine";
	private FrameLayout fl_home;
	private RadioGroup rg_main;
	private ImageView rb_fayan;

	private FragmentTransaction mFragmentTransaction;
	private HomeFragment homeFragment;
	private MineFragment mineFragment;
	private SociationFragment sociationFragment;
	private StreetFragment streetFragment;
	
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		
		context = this;

		fl_home = (FrameLayout) findViewById(R.id.fl_home);
		rg_main = (RadioGroup) findViewById(R.id.rg_main);
		rb_fayan = (ImageView) findViewById(R.id.rb_fayan);
		rb_fayan.setOnClickListener(this);

		rg_main.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_home:
					LogUtil.d(TAG, "---home");
					if (null == homeFragment) {
						LogUtil.d(TAG, "--new-home");
						homeFragment = new HomeFragment(getApplicationContext());
					}
					mFragmentTransaction.replace(R.id.fl_home, homeFragment, HOME).commit();
					break;
				case R.id.rb_jiequ:
					LogUtil.d(TAG, "---jiequ");
					if (null == streetFragment) {
						LogUtil.d(TAG, "--new-jiequ");
						streetFragment = new StreetFragment(context);
					}
					mFragmentTransaction.replace(R.id.fl_home, streetFragment, STREET).commit();
					break;
				case R.id.rb_fayan:
					// delete
					LogUtil.d(TAG, "---fayan");
					break;
				case R.id.rb_gonghui:
					LogUtil.d(TAG, "---gonghui");
					if (null == sociationFragment) {
						LogUtil.d(TAG, "--new-公会");
						sociationFragment = new SociationFragment(context);
					}
					mFragmentTransaction.replace(R.id.fl_home, sociationFragment, SOCIATION).commit();
					break;
				case R.id.rb_jia:
					LogUtil.d(TAG, "---jia");
					if (null == mineFragment) {
						LogUtil.d(TAG, "--new-家");
						mineFragment = new MineFragment(context);
					}
					mFragmentTransaction.replace(R.id.fl_home, mineFragment, MINE).commit();
					break;
				}
			}
		});

		mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		homeFragment = new HomeFragment(getApplicationContext());
		mFragmentTransaction.add(R.id.fl_home, homeFragment, HOME).commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rb_fayan:
			// TODO
			Toast.makeText(this, "发言", 0).show();
			break;

		default:
			break;
		}

	}

}
