package com.minyou.manba.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivitySettingBinding;
import com.minyou.manba.manager.DataCleanManager;
import com.minyou.manba.ui.dialog.PointInfoDialog;
import com.minyou.manba.util.SharedPreferencesUtil;

/**
 * Created by Administrator on 2017/12/3.
 */
public class SettingActivity extends DataBindingBaseActivity implements View.OnClickListener {

    private ActivitySettingBinding binding;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setting);
        initView();
        initListener();
    }

    private void initView() {
        // 显示缓存
        binding.tvCacheSize.setText(DataCleanManager.getTotalCacheSize(this));
    }

    private void initListener() {
        binding.llUserSetting.setOnClickListener(this);
        binding.btExitLogin.setOnClickListener(this);
        binding.llHuancunSetting.setOnClickListener(this);
        binding.llQaSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_user_setting:
                intent = new Intent(SettingActivity.this,PersonInfoActivity.class);
                intent.putExtra("userId",SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                startActivity(intent);
                break;
            case R.id.ll_huancun_setting:
                // 清除缓存
                final PointInfoDialog dialog = new PointInfoDialog(this);
                dialog.show();
                dialog.setDialogTitle(getResources().getString(R.string.setting_clear_cache_ornot));
                dialog.setCancelButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setOKButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataCleanManager.clearAllCache(SettingActivity.this);
                        dialog.dismiss();
                        binding.tvCacheSize.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
                    }
                });

//                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this,0);
//                builder.setTitle(getResources().getString(R.string.prompt));
//                builder.setMessage(getResources().getString(R.string.setting_clear_cache_ornot));
//                AlertDialog dialog = builder.create();
//                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        DataCleanManager.clearAllCache(SettingActivity.this);
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
                break;
            case R.id.ll_qa_setting:
                break;
            case R.id.ll_idea_fankui:
                break;
            case R.id.ll_update_version:
                break;
            case R.id.ll_about_us:
                break;
            case R.id.bt_exit_login:
                SharedPreferencesUtil.getInstance().removeAll();
                // 退出登录后跳转登陆界面
                intent = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
