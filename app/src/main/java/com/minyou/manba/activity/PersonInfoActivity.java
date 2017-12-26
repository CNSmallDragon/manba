package com.minyou.manba.activity;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityPersonInfoBinding;
import com.minyou.manba.event.EventInfo;
import com.minyou.manba.imageloader.GlideImageLoader;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.UserDetailResultModel;
import com.minyou.manba.network.resultModel.UserLoginResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luchunhao on 2017/12/25.
 */
public class PersonInfoActivity extends DataBindingBaseActivity implements View.OnClickListener {

    private static final String TAG = PersonInfoActivity.class.getSimpleName();
    private ActivityPersonInfoBinding binding;

    private RequestManager glideRequest;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> images = null;
    public static final int HEAD_PIC = 1001;

    private String userId;


    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person_info);
        glideRequest = Glide.with(this);
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        Intent intent = getIntent();
        if(intent == null){
            Toast.makeText(PersonInfoActivity.this, "传入ID为空", Toast.LENGTH_SHORT).show();
            finish();
        }
        userId = intent.getStringExtra(Appconstant.User.USER_ID);
        getUserInfo();
        initListener();
    }



    private void initListener() {
        binding.ivUserPic.setOnClickListener(this);
        binding.llUserNickName.setOnClickListener(this);
        binding.llUserBirthday.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_user_pic:
                chooseHeadPid();
                break;
            case R.id.ll_user_nick_name:

                break;
            case R.id.ll_user_birthday:

                break;
        }
    }

    /**
     * 选择头像
     */
    private void chooseHeadPid() {
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        Integer radius = 140;       // 圆形半径
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);
        // 图片保存宽高
        imagePicker.setOutPutX(800);
        imagePicker.setOutPutY(800);

        Intent intent = new Intent(PersonInfoActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
        startActivityForResult(intent, HEAD_PIC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case HEAD_PIC:
                if(resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null){
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    // 只有一个
                    if(images.size() > 0){  // 返回成功
                        String picUrl = images.get(0).path;
                        uploadHeadPhoto(picUrl);
                    }
                }
                break;
        }
    }

    /**
     * 上传用户头像
     * @param picUrl
     */
    private void uploadHeadPhoto(final String picUrl) {
        LogUtil.d(TAG,"picUrl==="+picUrl);
        List<File> zoneFile = new ArrayList<File>();
        File file = new File(picUrl);
        if(!file.exists()){
            return;
        }
        zoneFile.add(file);
        loading();
        String userID = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID);
        HashMap<String,Object> params = new HashMap<>();
        params.put(Appconstant.User.USER_ID, userID);
        params.put("file",zoneFile);
        ManBaRequestManager.getInstance().upLoadFile(OkHttpServiceApi.HTTP_USER_UPLOAD_PHOTO+"/"+userID, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                cancelLoading();
                glideRequest.load(picUrl).transform(new GlideCircleTransform(PersonInfoActivity.this)).into(binding.ivUserPic);
                // 通知minefragment更新头像
                EventInfo eventInfo = new EventInfo();
                eventInfo.setType(EventInfo.SETTING_USER_INFO);
                UserLoginResultModel.ResultBean bean = new UserLoginResultModel.ResultBean();
                bean.setPhotoUrl(picUrl);
                eventInfo.setData(bean);
                EventBus.getDefault().post(eventInfo);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                LogUtil.e(TAG, "errorMsg:" + errorMsg);
                cancelLoading();
            }
        });
    }

    public void getUserInfo() {
        HashMap<String,String> params = new HashMap<>();
        params.put("userId",userId);
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_USER_DETAIL+"/"+userId, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                UserDetailResultModel userDetailResultModel = new Gson().fromJson(result,UserDetailResultModel.class);
                LogUtil.d(TAG,result);
                LogUtil.d(TAG,new Gson().toJson(userDetailResultModel));
                if(userDetailResultModel != null){
                    LogUtil.d(TAG,new Gson().toJson(userDetailResultModel));
                    LogUtil.d(TAG,new Gson().toJson(userDetailResultModel.getResult()));
                    binding.setUserInfo(userDetailResultModel.getResult());
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    @BindingAdapter({"setUserPic"})
    public static void setUserPic(ImageView imageView, UserDetailResultModel.UserDetailBean userDetailBean){
        if(userDetailBean != null){
            LogUtil.d(TAG,userDetailBean.getPhotoUrl());
            Glide.with(imageView.getContext()).load(userDetailBean.getPhotoUrl()).into(imageView);
        }else{
            LogUtil.d(TAG,"getPhotoUrl====null");
        }
    }
}
