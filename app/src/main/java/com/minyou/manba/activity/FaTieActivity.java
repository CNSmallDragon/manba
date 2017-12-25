package com.minyou.manba.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.adapter.ChooseSociationAdapter;
import com.minyou.manba.adapter.ImagePickerAdapter;
import com.minyou.manba.dialog.SelectDialog;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.SociationResultModel;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luchunhao on 2017/12/19.
 */
public class FaTieActivity extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private static final String TAG = "FaTieActivity";
    @BindView(R.id.act_title)
    ActionTitleView actTitle;
    @BindView(R.id.et_dongtai_title)
    EditText etDongtaiTitle;
    @BindView(R.id.et_dongtai_content)
    EditText etDongtaiContent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.recyclerview_choose_gonghui)
    RecyclerView recyclerviewChooseGonghui;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数

    private ChooseSociationAdapter chooseSociationAdapter;
    private List<SociationResultModel.ResultBean.SociationResultBean> list = new ArrayList<SociationResultModel.ResultBean.SociationResultBean>();
    private String contentStr;
    private List<Integer> checkedList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabudongtai);
        ButterKnife.bind(this);

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

//    private void initData2(){
//        ShopCarRequestModel requestModel = new ShopCarRequestModel();
//        requestModel.setCultureID(CultureID);
//        requestModel.setCurrencyName(CurrencyName);
//        subscription = RxHttpUtils.createHttpRequest(RetrofitServiceGenerator.createService(TbDressDataApiService.class, new AESGsonResponseBodyConverter.JsonBridge() {
//            @Override
//            public void onJson(String json) {
//                LogUtil.d("json", json);
//            }
//        })
//                .http_getCarBaglist(RequestBodyUtils.getRequestBody(requestModel)))
//                .subscribe(new CallBackSubscriber<BaseResultModel>(listener) {
//                               @Override
//                               public void _onStart() {
//
//                               }
//
//                               @Override
//                               public void _onNext(BaseResultModel baseResultModel) {
//                               }
//
//                               @Override
//                               public void _onError(String msg) {
//
//                               }
//
//                               @Override
//                               public void _onCompleted() {
//
//                               }
//                           }
//                );
//    }


    private void initWidget() {
        // 初始化头部信息
        actTitle.setTitle(getResources().getString(R.string.fabu_dongtai));
        actTitle.setRightToDo(R.drawable.details_icon_guanzhu, null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发布帖子
                for (ImageItem item : selImageList) {
                    LogUtil.d(TAG, "添加图片返回---" + new Gson().toJson(item));
                    LogUtil.d(TAG, "添加图片返回URL---" + item.path);
                }
                if(null != chooseSociationAdapter){
                    checkedList = chooseSociationAdapter.getCheckedList();
                }
                for(int i : checkedList){
                    LogUtil.d(TAG, "选中的公会id---" + i);
                }
                contentStr = etDongtaiContent.getText().toString().trim();
                LogUtil.d(TAG, "发布内容---" + contentStr);
                http_postSendTieZi(contentStr,checkedList,selImageList);
            }
        });

        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        chooseSociationAdapter = new ChooseSociationAdapter(this,list);
        recyclerviewChooseGonghui.setLayoutManager(manager);
        recyclerviewChooseGonghui.setAdapter(chooseSociationAdapter);


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

    private void http_postSendTieZi(String actionUrl,List<Integer> guildIds,ArrayList<ImageItem> imageItems) {

        long guildId;
        List<File> zoneFile = new ArrayList<File>();

        // 构造guild
        if(guildIds == null || guildIds.size() <= 0){
            guildId = -1;
        }else{
            guildId = guildIds.get(0);
        }

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
        params.put("guildId",guildIds.get(0));
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


    protected void loading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(getString(R.string.loading_up));
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void cancelLoading() {
        if (progressDialog != null)
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
    }
}
