package com.minyou.manba.activity;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
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
import com.minyou.manba.network.resultModel.BaseResultModel;
import com.minyou.manba.network.resultModel.UserDetailResultModel;
import com.minyou.manba.network.resultModel.UserLoginResultModel;
import com.minyou.manba.ui.dialog.OneEditDialog;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private long selectedTime;

    private UserDetailResultModel.UserDetailBean userDetailBean;


    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person_info);
        glideRequest = Glide.with(this);
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(PersonInfoActivity.this, "传入ID为空", Toast.LENGTH_SHORT).show();
            finish();
        }
        userId = intent.getStringExtra(Appconstant.User.USER_ID);
        getUserInfo();
        initListener();
    }


    private void initListener() {
        binding.rbSexMan.setOnClickListener(this);
        binding.rbSexWomen.setOnClickListener(this);
        binding.ivUserPic.setOnClickListener(this);
        binding.llUserNickName.setOnClickListener(this);
        binding.llUserBirthday.setOnClickListener(this);
        binding.llUserSign.setOnClickListener(this);

        binding.atvTitle.setRightToDo(getString(R.string.setting_save),this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_pic:
                chooseHeadPid();

                break;
            case R.id.ll_user_nick_name:
                showEditNickNameDialog();
                break;
            case R.id.ll_user_birthday:
//                String currentDateStr = userDetailBean.getBirthday();
//                Date currentDate = null;
//                if(!TextUtils.isEmpty(currentDateStr)){
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    try {
//                        currentDate = sdf.parse(currentDateStr);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                        currentDate = null;
//                    }
//                }
                showTimePickerView();
                break;
            case R.id.ll_user_sign:
                showEditSignDialog();
                break;
            case R.id.rb_sex_man:
                userDetailBean.setSex(1);
//                binding.rbSexMan.setChecked(true);
//                binding.rbSexWomen.setChecked(false);
                break;
            case R.id.rb_sex_women:
                userDetailBean.setSex(2);
//                binding.rbSexWomen.setChecked(true);
//                binding.rbSexMan.setChecked(false);
                break;

            case R.id.tv_title_right:       // 保存
                savePersonInfo();
                break;
        }
    }


     private void savePersonInfo() {
         HashMap<String,String> params = new HashMap<>();
         params.put("userId",userId);
         params.put("nickName",userDetailBean.getNickName());
         params.put("sex",userDetailBean.getSex()+"");
         params.put("birthday",userDetailBean.getBirthday());
         params.put("signName",userDetailBean.getSignName());

         ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_USER_UPDATE, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {
             @Override
             public void onReqSuccess(String result) {
                 BaseResultModel resultModel = new Gson().fromJson(result,BaseResultModel.class);
                 if(resultModel != null && resultModel.getCode().equals("0")){
                     // 成功
                     EventInfo eventInfo = new EventInfo();
                     eventInfo.setType(EventInfo.SETTING_USER_INFO);
                     UserLoginResultModel.ResultBean bean = new UserLoginResultModel.ResultBean();
                     bean.setNickName(userDetailBean.getNickName());
                     eventInfo.setData(bean);
                     EventBus.getDefault().post(eventInfo);
                     finish();
                 }
             }

             @Override
             public void onReqFailed(String errorMsg) {

             }
         });

    }

    // 设置用户昵称
    private void showEditNickNameDialog() {
        final OneEditDialog dialog = new OneEditDialog(this);
        dialog.show();
        dialog.setHintText(getResources().getString(R.string.setting_input_hint));
        dialog.setLeftButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setRightButton(getResources().getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDetailBean.setNickName(dialog.getInputString());
                dialog.dismiss();
            }
        });
    }

    // 设置用户签名
    private void showEditSignDialog() {
        final OneEditDialog dialog = new OneEditDialog(this);
        dialog.show();
        dialog.setHintText(getResources().getString(R.string.setting_input_hint));
        dialog.setLeftButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setRightButton(getResources().getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDetailBean.setSignName(dialog.getInputString());
                dialog.dismiss();
            }
        });
    }

    // 设置用户生日
    private void showTimePickerView() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
//        if(selectDate != null){
//            selectedDate.setTime(new Date(selectedTime));
//        }
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        startDate.set(1900, 0, 1);
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                //selectedTime = date.getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                LogUtil.d(TAG, "onTimeSelect: " + format.format(date));
                if (null != userDetailBean) {
                    userDetailBean.setBirthday(format.format(date));
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
                //.setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
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
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
        startActivityForResult(intent, HEAD_PIC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case HEAD_PIC:
                if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    // 只有一个
                    if (images.size() > 0) {  // 返回成功
                        String picUrl = images.get(0).path;
                        uploadHeadPhoto(picUrl);
                    }
                }
                break;
        }
    }

    /**
     * 上传用户头像
     *
     * @param picUrl
     */
    private void uploadHeadPhoto(final String picUrl) {
        LogUtil.d(TAG, "picUrl===" + picUrl);
        List<File> zoneFile = new ArrayList<File>();
        File file = new File(picUrl);
        if (!file.exists()) {
            return;
        }
        zoneFile.add(file);
        loading();
        String userID = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID);
        HashMap<String, Object> params = new HashMap<>();
        params.put(Appconstant.User.USER_ID, userID);
        params.put("file", zoneFile);
        ManBaRequestManager.getInstance().upLoadFile(OkHttpServiceApi.HTTP_USER_UPLOAD_PHOTO + "/" + userID, params, new ReqCallBack<String>() {
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
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_USER_DETAIL + "/" + userId, ManBaRequestManager.TYPE_GET, null, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                UserDetailResultModel userDetailResultModel = new Gson().fromJson(result, UserDetailResultModel.class);
                LogUtil.d(TAG, result);
                LogUtil.d(TAG, new Gson().toJson(userDetailResultModel));
                if (userDetailResultModel != null) {
                    userDetailBean = userDetailResultModel.getResult();
                    binding.setUserInfo(userDetailBean);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    @BindingAdapter({"setUserPic"})
    public static void setUserPic(ImageView imageView, UserDetailResultModel.UserDetailBean userDetailBean) {
        if (userDetailBean != null) {
            Glide.with(imageView.getContext()).load(userDetailBean.getPhotoUrl())
                    .transform(new GlideCircleTransform(imageView.getContext())).into(imageView);
        }
    }
}
