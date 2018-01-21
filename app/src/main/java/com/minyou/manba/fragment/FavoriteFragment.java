package com.minyou.manba.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.BR;
import com.minyou.manba.R;
import com.minyou.manba.adapter.mvvm.MyMvvmAdapter;
import com.minyou.manba.databinding.FragmentStreetBinding;
import com.minyou.manba.manager.UserManager;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.ZoneListResultModel;
import com.minyou.manba.ui.view.DefalutRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

public class FavoriteFragment extends DataBindingBaseFragment {

	private FragmentStreetBinding binding;

	private MyMvvmAdapter<ZoneListResultModel.ResultBean.ZoneListBean> myMvvmAdapter;
	private List<ZoneListResultModel.ResultBean.ZoneListBean> zoneList;
	private View footView;

	private int pageSize = 10;
	private int pageNo = 1;

	private boolean isShowBackIcon = false;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_street, container, false);
		Bundle bundle = getArguments();
		if(null != bundle){
			isShowBackIcon = bundle.getBoolean("showBackIcon");
		}
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
	}

	private void initView() {
		if(isShowBackIcon){
			binding.streetTitle.showBackIcon();
		}
		zoneList = new ArrayList<ZoneListResultModel.ResultBean.ZoneListBean>();
		getData();

		myMvvmAdapter = new MyMvvmAdapter<>(getActivity(), zoneList, R.layout.item_home_new, BR.zoneBean);
		// 底部刷新控件
		binding.pcflRefreshFavorite.setHeaderView(new DefalutRefreshView(getActivity()));
		footView = new DefalutRefreshView(getActivity());
		binding.pcflRefreshFavorite.setFooterView(footView);
		binding.streetRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		binding.streetRecyclerView.setAdapter(myMvvmAdapter);

		initListener();
	}

	public void getData() {
		pageNo = 1;
		HashMap<String, String> params = new HashMap<>();
		params.put("pageSize", String.valueOf(pageSize));
		params.put("pageNo", String.valueOf(pageNo));
		params.put("userId", UserManager.getUserId());
		params.put("currentUserId", UserManager.getUserId());
		ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_FAVORITELIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
			@Override
			public void onReqSuccess(String result) {
				ZoneListResultModel zoneListResultModel = new Gson().fromJson(result, ZoneListResultModel.class);
				if(zoneListResultModel.isSuccess() && zoneListResultModel.getResult().getTotalCount() > 0){
					binding.pcflRefreshFavorite.setVisibility(View.VISIBLE);
					binding.tvEmptyText.setVisibility(View.GONE);
					zoneList.clear();
					zoneList.addAll(zoneListResultModel.getResult().getResultList());
					myMvvmAdapter.notifyDataSetChanged();
					footView.setVisibility(View.GONE);
				}else{
					binding.pcflRefreshFavorite.setVisibility(View.GONE);
					binding.tvEmptyText.setVisibility(View.VISIBLE);
				}
				binding.pcflRefreshFavorite.refreshComplete();
			}

			@Override
			public void onReqFailed(String errorMsg) {

			}
		});

	}

	private void loadMore() {
		pageNo++;
		HashMap<String, String> params = new HashMap<>();
		params.put("pageSize", String.valueOf(pageSize));
		params.put("pageNo", String.valueOf(pageNo));
		params.put("userId", UserManager.getUserId());
		params.put("currentUserId", UserManager.getUserId());
		ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_LIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
			@Override
			public void onReqSuccess(String result) {
				ZoneListResultModel zoneListResultModel = new Gson().fromJson(result, ZoneListResultModel.class);

				if (zoneListResultModel.isSuccess() && zoneListResultModel.getResult().getResultList().size() > 0) {
					zoneList.addAll(zoneListResultModel.getResult().getResultList());
					myMvvmAdapter.notifyDataSetChanged();
					//结束后停止刷新
					binding.pcflRefreshFavorite.refreshComplete();
					footView.setVisibility(View.GONE);
				} else {
					//Toast.makeText(getActivity(), getResources().getString(R.string.no_more_2), Toast.LENGTH_SHORT).show();
					binding.pcflRefreshFavorite.refreshComplete();
				}

			}

			@Override
			public void onReqFailed(String errorMsg) {

			}
		});
	}

	private void initListener() {

		binding.pcflRefreshFavorite.setPtrHandler(new PtrHandler2() {

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return !binding.pcflRefreshFavorite.isRefreshing() && PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				getData();
			}

			@Override
			public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
				return !binding.pcflRefreshFavorite.isRefreshing() && PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
			}

			@Override
			public void onLoadMoreBegin(PtrFrameLayout frame) {
				loadMore();
			}
		});


		binding.streetRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
					Glide.with(getActivity()).resumeRequests();
				} else {
					Glide.with(getActivity()).pauseRequests();
				}

			}
		});

	}

	public void showBackIcon(){
		binding.streetTitle.showBackIcon();
	}
}
