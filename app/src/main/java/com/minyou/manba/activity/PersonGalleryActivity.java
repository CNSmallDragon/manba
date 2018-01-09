package com.minyou.manba.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.adapter.GalleryAdapter;
import com.minyou.manba.databinding.ActivityGalleryLayoutBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.GalleryResultModel;
import com.minyou.manba.ui.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class PersonGalleryActivity extends DataBindingBaseActivity {

    private ActivityGalleryLayoutBinding binding;
    private int pageSize = 30;
    private int pageNo = 1;
    private GalleryAdapter mAdapter;
    private List<String> list = new ArrayList<String>();

    private String personId;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery_layout);

        if(null != getIntent()){
            personId = getIntent().getStringExtra(Appconstant.User.USER_ID);
        }


        mAdapter = new GalleryAdapter(this,list);
        binding.galleryRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        binding.galleryRecyclerView.addItemDecoration(new SpacesItemDecoration(16));
        binding.galleryRecyclerView.setAdapter(mAdapter);
        getData();

    }

    public void getData() {
        pageNo = 1;
        list.clear();
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("userId",personId);
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_USER_GALLERY, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                GalleryResultModel resultModel = new Gson().fromJson(result,GalleryResultModel.class);
                if(resultModel.isSuccess() && resultModel.getResult().getResultList().size() > 0){
                    list.addAll(resultModel.getResult().getResultList());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    public void loadMore() {
        pageNo ++;
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("userId",personId);
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_USER_GALLERY, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                GalleryResultModel resultModel = new Gson().fromJson(result,GalleryResultModel.class);
                if(resultModel.isSuccess() && resultModel.getResult().getResultList().size() > 0){
                    list.addAll(resultModel.getResult().getResultList());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }
}
