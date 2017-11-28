package com.minyou.manba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minyou.manba.R;
import com.minyou.manba.bean.WalletItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/28.
 */
public class WalletAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<WalletItem> list;

    public WalletAdapter(Context context,List<WalletItem> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallet,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder viewHolder = (ItemHolder) holder;
        WalletItem item = list.get(position);
        int resId = item.getType() == 0 ? R.drawable.me_icon_lingqishi : R.drawable.me_icon_lingshi;
        viewHolder.iv_wallet_type.setImageResource(resId);
        viewHolder.tv_wallet_name.setText(item.getItemName());
        viewHolder.tv_wallet_count.setText(String.valueOf(item.getCount()));
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_wallet_type)
        ImageView iv_wallet_type;
        @BindView(R.id.tv_wallet_name)
        TextView tv_wallet_name;
        @BindView(R.id.tv_wallet_count)
        TextView tv_wallet_count;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
