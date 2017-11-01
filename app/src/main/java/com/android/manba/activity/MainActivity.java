package com.android.manba.activity;

import com.android.manba.R;
import com.android.manba.R.id;
import com.android.manba.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button bt_login;
	private Button bt_regist;
	private Button bt_home;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(layout.activity_main);

		// StatService.trackCustomEvent(this, "onCreate", "");
		bt_login = (Button) findViewById(id.bt_login);
		bt_login.setOnClickListener(this);
		bt_regist = (Button) findViewById(id.bt_regist);
		bt_regist.setOnClickListener(this);
		bt_home = (Button) findViewById(id.bt_home);
		bt_home.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case id.bt_login:
			intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent );
			break;
		case id.bt_regist:
			intent = new Intent(MainActivity.this, RegistActivity.class);
			startActivity(intent );
			break;
		case id.bt_home:
			intent = new Intent(MainActivity.this, HomeActivity.class);
			startActivity(intent );
			break;
		default:
			break;
		}

	}

}
