package com.minyou.manba.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minyou.manba.R;
import com.minyou.manba.bean.SociationBean;
import com.minyou.manba.util.BitmapUtil;
import com.minyou.manba.util.LogUtil;

import java.util.List;

public class SociationListAdapter extends BaseAdapter {
	
	private static final String TAG = "SociationListAdapter";
	private Context context;
	private List<SociationBean> list;
	
	public SociationListAdapter(Context context,List<SociationBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder viewHolder;
		if(null == convertView){
			view = LinearLayout.inflate(context, R.layout.item_gonghui_list, null);
			viewHolder = new ViewHolder();
			viewHolder.gonghui_top = (ImageView) view.findViewById(R.id.iv_gonghui_top);
			viewHolder.gonghui_pic = (ImageView) view.findViewById(R.id.iv_gonghui_pic);
			viewHolder.gonghui_name = (TextView) view.findViewById(R.id.tv_gonghui_name);
			viewHolder.gonghui_member_num = (TextView) view.findViewById(R.id.tv_gonghui_member_num);
			viewHolder.gonghui_hot_num = (TextView) view.findViewById(R.id.tv_gonghui_hot_num);
			viewHolder.gonghui_join = (TextView) view.findViewById(R.id.tv_gonghui_join);
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder) convertView.getTag();
		}
		LogUtil.d(TAG, "position:===" + position);
//		viewHolder.gonghui_pic.setImageBitmap(BitmapFactory.decodeFile(list.get(position).getPicPath()));
		Bitmap bitmap = BitmapUtil.toRoundBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
		viewHolder.gonghui_pic.setImageBitmap(bitmap);
		//bitmap.recycle();
		//viewHolder.gonghui_pic.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
		viewHolder.gonghui_name.setText(list.get(position).getName());
		viewHolder.gonghui_member_num.setText("成员数：" + list.get(position).getMemberNum());
		viewHolder.gonghui_hot_num.setText("活跃度" + list.get(position).getMemberNum());
		viewHolder.gonghui_join.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "已加入", Toast.LENGTH_SHORT).show();
			}
		});
		
		// �ж��Ƿ���ʾtop123ͼ��
		if(position == 0){
			viewHolder.gonghui_top.setBackground(context.getResources().getDrawable(R.drawable.guild_label_first));
			viewHolder.gonghui_top.setVisibility(View.VISIBLE);
		}else if(position == 1){
			viewHolder.gonghui_top.setBackground(context.getResources().getDrawable(R.drawable.guild_label_second));
			viewHolder.gonghui_top.setVisibility(View.VISIBLE);
		}else if(position == 2){
			viewHolder.gonghui_top.setBackground(context.getResources().getDrawable(R.drawable.guild_label_third));
			viewHolder.gonghui_top.setVisibility(View.VISIBLE);
		}else{
			viewHolder.gonghui_top.setBackground(null);
			viewHolder.gonghui_top.setVisibility(View.GONE);
		}
		return view;
	}
	
	class ViewHolder {
		ImageView gonghui_top;
		ImageView gonghui_pic;
		TextView gonghui_name;
		TextView gonghui_member_num;
		TextView gonghui_hot_num;
		TextView gonghui_join;
	}
	

}
