package com.minyou.manba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.R;
import com.minyou.manba.network.resultModel.SociationResultModel;
import com.minyou.manba.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luchunhao on 2017/12/22.
 */
public class ChooseSociationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ChooseSociationAdapter.class.getSimpleName();
    private Context context;
    private List<SociationResultModel.ResultBean.SociationResultBean> list;
    private List<Integer> checkedList;
    private LayoutInflater inflater;
    private RequestManager glideRequest;

    public ChooseSociationAdapter(Context context,List<SociationResultModel.ResultBean.SociationResultBean> list){
        this.context = context;
        this.list = list;
        this.checkedList = new ArrayList<Integer>();
        inflater = LayoutInflater.from(context);
        glideRequest = Glide.with(context);
    }

    public List<Integer> getCheckedList() {
        return checkedList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_choose_gonghui,parent,false);
        return new ChooseSociationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ChooseSociationHolder){
            final ChooseSociationHolder mHolder1 = (ChooseSociationHolder) holder;

            if(!TextUtils.isEmpty(list.get(position).getGuildPhoto())){
                glideRequest.load(list.get(position).getGuildPhoto()).into(mHolder1.iv_gonghui_pic);
            }
            mHolder1.tv_gonghui_name.setText(list.get(position).getGuildName());

            mHolder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHolder1.cb_check.setChecked(mHolder1.cb_check.isChecked() ? false : true);
                    LogUtil.d(TAG,"name-====" + list.get(position).getGuildName());
                }
            });

            mHolder1.cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        checkedList.add(list.get(position).getId());
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ChooseSociationHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_gonghui_pic)
        ImageView iv_gonghui_pic;
        @BindView(R.id.tv_gonghui_name)
        TextView tv_gonghui_name;
        @BindView(R.id.cb_check)
        CheckBox cb_check;

        public ChooseSociationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setOnclickListener(View.OnClickListener l){
            itemView.setOnClickListener(l);
        }
    }

}
