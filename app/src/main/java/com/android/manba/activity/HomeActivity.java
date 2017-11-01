package com.android.manba.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.android.manba.R;
import com.android.manba.fragment.HomeFragment;
import com.android.manba.fragment.MineFragment;
import com.android.manba.fragment.SociationFragment;
import com.android.manba.fragment.StreetFragment;
import com.android.manba.util.LogUtil;

public class HomeActivity extends FragmentActivity implements OnClickListener {

	protected static final String TAG = "HomeActivity";
	private Context context;
	private FrameLayout fl_home;
	private RadioGroup rg_main;
	private ImageView rb_fayan;

	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		this.context = getApplicationContext();

		fl_home = (FrameLayout) findViewById(R.id.fl_home);
		rg_main = (RadioGroup) findViewById(R.id.rg_main);
		rb_fayan = (ImageView) findViewById(R.id.rb_fayan);
		rb_fayan.setOnClickListener(this);

		rg_main.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					index = 0;
					LogUtil.d(TAG, "---home");
					break;
				case R.id.rb_jiequ:
					index = 1;
					LogUtil.d(TAG, "---jiequ");
					break;
				case R.id.rb_fayan:
					// delete
					LogUtil.d(TAG, "---fayan");
					break;
				case R.id.rb_gonghui:
					index = 2;
					LogUtil.d(TAG, "---gonghui");
					break;
				case R.id.rb_jia:
					index = 3;
					LogUtil.d(TAG, "---jia");
					break;
				}
				Fragment fragment = (Fragment) adapter.instantiateItem(fl_home, index);
				adapter.setPrimaryItem(fl_home, 0, fragment);
				adapter.finishUpdate(fl_home);
			}
		});
		rg_main.check(R.id.rb_home);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rb_fayan:
			// TODO
			Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}

	FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = null;
			switch (arg0) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new StreetFragment();
				break;
			case 2:
				fragment = new SociationFragment();
				break;
			case 3:
				fragment = new MineFragment();
				break;
			}
			return fragment;
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case SociationFragment.DETAIL:
			if (resultCode == RESULT_OK) {
				Toast.makeText(context, "�Ѽ���", Toast.LENGTH_SHORT).show();
			}
			break;
		}

	};

}
