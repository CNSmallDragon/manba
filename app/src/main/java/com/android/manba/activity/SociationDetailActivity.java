package com.android.manba.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.manba.Appconstant;
import com.android.manba.R;
import com.android.manba.ui.ActionTitleView;
import com.android.manba.util.GlideCircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class SociationDetailActivity extends Activity {
	
	private ActionTitleView atv_title;
	private ImageView iv_gonghui_pic;
	private TextView tv_gonghui_name;
	private TextView tv_gonghui_member_num;
	private TextView tv_gonghui_hot_num;
	private TextView tv_join;
	
	private String pic_path;
	private Bitmap bitmap;
	private RequestManager glideRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sociation_detail);
		
		atv_title = (ActionTitleView) findViewById(R.id.atv_title);
		iv_gonghui_pic = (ImageView) findViewById(R.id.iv_gonghui_pic);
		tv_gonghui_name = (TextView) findViewById(R.id.tv_gonghui_name);
		tv_gonghui_member_num = (TextView) findViewById(R.id.tv_gonghui_member_num);
		tv_gonghui_hot_num = (TextView) findViewById(R.id.tv_gonghui_hot_num);
		tv_join = (TextView) findViewById(R.id.tv_join);
		
		Intent intent = getIntent();
		if(null != intent){
			pic_path = intent.getStringExtra(Appconstant.SOCIATION_PIC_PATH);
			//bitmap = BitmapUtil.createCircleImage(BitmapFactory.decodeResource(getResources(), R.drawable.test));
			glideRequest = Glide.with(this);
			glideRequest.load(R.drawable.test).transform(new GlideCircleTransform(this)).into(iv_gonghui_pic);
			//iv_gonghui_pic.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.test));

			tv_gonghui_name.setText(intent.getStringExtra(Appconstant.SOCIATION_NAME));
			tv_gonghui_member_num.setText(intent.getStringExtra(Appconstant.SOCIATION_MEMBER_NUM));
			tv_gonghui_hot_num.setText(intent.getStringExtra(Appconstant.SOCIATION_HOT_NUM));
		}
		
		tv_join.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				setResult(RESULT_OK);
				finish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		if(bitmap != null){
			bitmap.recycle();
		}
		super.onDestroy();
	}
}
