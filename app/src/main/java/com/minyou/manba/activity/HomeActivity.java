package com.minyou.manba.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.minyou.manba.R;
import com.minyou.manba.bean.ManBaUserInfo;
import com.minyou.manba.fragment.BaseFragment;
import com.minyou.manba.fragment.HomeFragment;
import com.minyou.manba.fragment.MineFragment;
import com.minyou.manba.fragment.SociationFragment;
import com.minyou.manba.fragment.StreetFragment;
import com.minyou.manba.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends FragmentActivity {

    protected static final String TAG = "HomeActivity";

    private boolean[] fragmentsUpdateFlag = {false,false,false,true};
    private List<BaseFragment> fragments = new ArrayList<BaseFragment>();

    //private Unbinder unbinder;
    private Context context;
    //@BindView(R.id.fl_home)
    FrameLayout fl_home;

    //@BindView(R.id.rg_main)
    RadioGroup rg_main;
    //@BindView(R.id.rb_fayan)
    ImageView rb_fayan;

    private int index;
    private long firstTime = 0;

    private ManBaUserInfo mManBaUserInfo;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        this.context = getApplicationContext();

        fl_home = (FrameLayout)findViewById(R.id.fl_home);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        rb_fayan = (ImageView) findViewById(R.id.rb_fayan);
        fm = getSupportFragmentManager();
        fragments.clear();
        fragments.add(new HomeFragment());
        fragments.add(new SociationFragment());
        fragments.add(new StreetFragment());
        fragments.add(new MineFragment());
        rg_main.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        index = 0;
                        LogUtil.d(TAG, "---home");
                        break;
                    case R.id.rb_gonghui:
                        index = 1;
                        LogUtil.d(TAG, "---gonghui");
                        break;
                    case R.id.rb_fayan:
                        // delete
                        LogUtil.d(TAG, "---fayan");
                        break;
                    case R.id.rb_jiequ:
                        index = 2;
                        LogUtil.d(TAG, "---jiequ");
                        break;
                    case R.id.rb_jia:
                        index = 3;
                        LogUtil.d(TAG, "---jia");
                        break;
                }
                Fragment fragment = (Fragment) adapter.instantiateItem(fl_home, index);
                adapter.setPrimaryItem(fl_home, 0, fragment);
                adapter.finishUpdate(fl_home);
            }
        });
        rg_main.check(R.id.rb_home);
    }


    //    @OnClick(R.id.rb_fayan)
//    public void fayan(){
//        Toast.makeText(this, "发言", Toast.LENGTH_SHORT).show();
//    }

    FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            Fragment fragment = (Fragment) super.instantiateItem(container, position);
//            String tag = fragment.getTag();
//            LogUtil.d(TAG, "---position====" + position);
//            LogUtil.d(TAG, "---tag====" + tag);
//            LogUtil.d(TAG, "---fragmentsUpdateFlag[position]====" + fragmentsUpdateFlag[position]);
//            if(fragmentsUpdateFlag[position]){
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.remove(fragment);
//                LogUtil.d(TAG, "---container.getId====" + container.getId());
//                fragment = fragments.get(position);
//                ft.add(container.getId(),fragment,tag);
//                ft.attach(fragment);
//                ft.commitAllowingStateLoss();
//            }
//
//            return fragment;
//
//        }
    };


    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if((currentTime - firstTime) > 2000){
            firstTime = currentTime;
            Toast.makeText(HomeActivity.this, getResources().getString(R.string.back_launcher), Toast.LENGTH_SHORT).show();
        }else{
            finish();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unbinder.unbind();
    }

    public ManBaUserInfo getManBaUserInfo() {
        return mManBaUserInfo;
    }

    public void setManBaUserInfo(ManBaUserInfo manBaUserInfo) {
        this.mManBaUserInfo = manBaUserInfo;
    }

    public RadioGroup getRg_main() {
        return rg_main;
    }
}
