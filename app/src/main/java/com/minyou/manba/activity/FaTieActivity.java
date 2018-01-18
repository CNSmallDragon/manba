package com.minyou.manba.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.adapter.ChooseSociationAdapter;
import com.minyou.manba.adapter.ImagePickerAdapter;
import com.minyou.manba.databinding.ActivityFabudongtaiBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.SociationResultModel;
import com.minyou.manba.ui.dialog.SelectDialog;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luchunhao on 2017/12/19.
 */
public class FaTieActivity extends DataBindingBaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    private ActivityFabudongtaiBinding binding;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private static final String TAG = "FaTieActivity";


    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数

    private ChooseSociationAdapter chooseSociationAdapter;
    private List<SociationResultModel.ResultBean.SociationResultBean> list = new ArrayList<SociationResultModel.ResultBean.SociationResultBean>();
    private String contentStr;
    private int checkedId;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_fabudongtai);
        initWidget();
        initData();
    }

    private void initData() {
        HashMap<String,String> params = new HashMap<>();
        params.put("pageSize","10");
        params.put("pageNo","1");
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_GUILD_LIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                SociationResultModel sociationResultModel = new Gson().fromJson(result,SociationResultModel.class);
                list.addAll(sociationResultModel.getResult().getResultList());
                chooseSociationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    private void initWidget() {
        // 初始化头部信息
        binding.actTitle.setRightToDo(getResources().getString(R.string.fabu_publish), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发布帖子
                if(null != chooseSociationAdapter){
                    checkedId = chooseSociationAdapter.getCheckedId();
                }
                LogUtil.d(TAG, "选中的公会id---" + checkedId);
                contentStr = binding.etDongtaiContent.getText().toString().trim();
                LogUtil.d(TAG, "发布内容---" + contentStr);
                http_postSendTieZi(contentStr,checkedId,selImageList);
            }
        });

        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        chooseSociationAdapter = new ChooseSociationAdapter(this,list,binding.recyclerviewChooseGonghui);
        binding.recyclerviewChooseGonghui.setLayoutManager(manager);
        binding.recyclerviewChooseGonghui.setAdapter(chooseSociationAdapter);


    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                /**
                                 * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                                 *
                                 * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                                 *
                                 * 如果实在有所需要，请直接下载源码引用。
                                 */
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(FaTieActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(FaTieActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }

                    }
                }, names);


                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    private void http_postSendTieZi(String contentStr,int guildId,ArrayList<ImageItem> imageItems) {

        List<File> zoneFile = new ArrayList<File>();

        // 构造图片路径
        for(ImageItem imageItem : imageItems){
            File file = new File(imageItem.path);
            if(file.exists()){
                zoneFile.add(file);
            }
        }

        // 显示加载框
        loading();
        String userID = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID);
        HashMap<String,Object> params = new HashMap<>();
        params.put(Appconstant.User.USER_ID, userID);
        params.put("zoneContent",contentStr);
        params.put("guildId",guildId);
        params.put("fileType",1);
        params.put("zoneFile",zoneFile);
        ManBaRequestManager.getInstance().upLoadFile(OkHttpServiceApi.HTTP_ZONE_PUBLISH, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                cancelLoading();
                LogUtil.d(TAG, "-----result---------" + result);
                finish();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                cancelLoading();
            }
        });

    }


}
