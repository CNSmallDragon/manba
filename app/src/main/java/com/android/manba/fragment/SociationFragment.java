package com.android.manba.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.manba.Appconstant;
import com.android.manba.R;
import com.android.manba.activity.SociationDetailActivity;
import com.android.manba.adapter.SociationListAdapter;
import com.android.manba.bean.SociationBean;
import com.android.manba.ui.ActionTitleView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SociationFragment extends BaseFragment {
	public static final int DETAIL = 0;
	private ListView lv_gonghui_list;
	private SociationListAdapter adapter;
	private List<SociationBean> list;
	private ActionTitleView atv_title;
	
	public SociationFragment(Context context) {
		super(context);
	}
	
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_gonghui, null);
		lv_gonghui_list = (ListView) view.findViewById(R.id.lv_gonghui_list);
		// 处理标题
		atv_title = (ActionTitleView) view.findViewById(R.id.atv_title);
		atv_title.setTitle(context.getResources().getString(R.string.home_gonghui));
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		list = new ArrayList<SociationBean>();
		getData();
		adapter = new SociationListAdapter(getActivity(), list);
		lv_gonghui_list.setAdapter(adapter);
		
		lv_gonghui_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(),SociationDetailActivity.class);
				intent.putExtra(Appconstant.SOCIATION_NAME, list.get(position).getName());
				intent.putExtra(Appconstant.SOCIATION_MEMBER_NUM, "成员数：" + list.get(position).getMemberNum());
				intent.putExtra(Appconstant.SOCIATION_HOT_NUM, "活跃度" + list.get(position).getMemberNum());
				intent.putExtra(Appconstant.SOCIATION_PIC_PATH, list.get(position).getPicPath());
				getActivity().startActivityForResult(intent, DETAIL);
				
			}
		});
		
		// 设置添加公会按钮事件
		atv_title.setRightToDo(R.drawable.guild_nav_icon_add, null, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub  添加公会
				
			}
		});
	}

	private void getData() {
		list.clear();
		for(int i=0;i<=100;i++){
			SociationBean e = new SociationBean();
			e.setName("test公会" + i);
			e.setMemberNum((int)(Math.random()*10000));
			e.setHotNum((int)(Math.random()*10000));
			list.add(e);
		}
		
	}

}
