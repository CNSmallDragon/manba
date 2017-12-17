package com.minyou.manba.wxapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.network.requestModel.RegistRequestModel;
import com.minyou.manba.network.responseModel.WeiXinResponseModel;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/13.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";
    private IWXAPI mAPi;
    private OkHttpClient client;

    private Activity mActivity;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();

        mAPi = WXAPIFactory.createWXAPI(this, Appconstant.WEIXIN_APP_ID, true);
        mAPi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d(TAG, "onNewIntent-----");
        setIntent(intent);
        mAPi.handleIntent(intent, this);
    }

    //微信发送的请求将回调到onReq方法
    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.d(TAG, "onReq-----" + baseReq.toString());
    }

    //发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.d(TAG, "onResp-----" + baseResp.toString());
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                LogUtil.d(TAG, "ERR_OK-----");
                // 发送成功
                SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
                if (null != sendResp) {
                    String code = sendResp.code;
                    getAccess_token(code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                LogUtil.d(TAG, "ERR_USER_CANCEL-----");

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                LogUtil.d(TAG, "ERR_AUTH_DENIED-----");

                break;
            default:
                LogUtil.d(TAG, "default-----");
                break;
        }
    }

    private void getAccess_token(String code) {
        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + Appconstant.WEIXIN_APP_ID
                + "&secret="
                + Appconstant.WEIXIN_APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";
        client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                LogUtil.d(TAG, "getAccess_token--resultStr---" + resultStr);
                try {
                    JSONObject jsonObject = new JSONObject(resultStr);
                    String openid = jsonObject.getString("openid").toString().trim();
                    String access_token = jsonObject.getString("access_token").toString().trim();
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_WEIXIN_ID, openid);
                    getUserMesg(access_token, openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getUserMesg(String access_token, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                LogUtil.d(TAG, "getUserMesg--resultStr---" + resultStr);
                WeiXinResponseModel weixinModel = new Gson().fromJson(resultStr,WeiXinResponseModel.class);
                //EventBus.getDefault().post(new MessageEvent(resultStr));
                SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_USER_INFO_WEIXIN, resultStr);
                SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_LAST_TYPE, Appconstant.LOGIN_WEIXIN);
                SharedPreferencesUtil.getInstance().putBoolean(Appconstant.LOGIN_OR_NOT, true);

                RegistRequestModel requestModel = new RegistRequestModel();
                requestModel.setNickName(weixinModel.getNickname());
                requestModel.setSex(weixinModel.getSex());
                requestModel.setWeixin(weixinModel.getOpenid());
                requestModel.setPicUrl(weixinModel.getHeadimgurl());
                // TODO 根据第三方账号查询用户是否已经注册
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("weiXin", weixinModel.getOpenid())
                        .build();
                Request request = new Request.Builder()
                        .url(ManBaApi.LOGINT_URL)
                        .post(body)
                        .build();
                Call weixinCall = client.newCall(request);
                weixinCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String requestStr = response.body().string();
                        LogUtil.d("TAG", "11111-----response---------" + requestStr);

                    }
                });


//                Intent intnet = new Intent(WXEntryActivity.this,BindingPhoneActivity.class);
//                intnet.putExtra(Appconstant.LOGIN_USER_INFO, requestModel);
//                startActivity(intnet);
//                finish();


            }
        });
    }
}
