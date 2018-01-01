package com.minyou.manba.adapter.mvvm;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by luchunhao on 2017/12/28.
 */
public abstract class BaseMvvmAdapter<T> extends RecyclerView.Adapter {

    //item类型
    protected final int ITEM_TYPE_HEADER = 1001;//头视图
    protected final int ITEM_TYPE_FOOTER = 1002;//脚视图
    protected final int ITEM_TYPE_LOADING = 1003;//加载中视图
    protected final int ITEM_TYPE_NONE = 1004;//无数据视图
    protected final int ITEM_TYPE_ERROR = 1005;//加载失败视图

    protected final int LOADING = 101;//加载中
    protected final int EMPTY = 102;//无数据
    protected final int ERROR = 103;//加载失败

    public int NODATA_STATUS = LOADING;//内容 ,根据这个属性来决定在没有数据时需要加载哪种视图,默认状态为正在加载


    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mList;   // 数据集

    private View headerView;
    private View footerView;


    public BaseMvvmAdapter(Context ctx, List<T> list) {
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
        mList = list;
    }

    /**
     * 根据ViewType创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE_HEADER) {//头视图
            return new RecyclerHolder(headerView);
        } else if (viewType == ITEM_TYPE_FOOTER) {//脚视图
            return new RecyclerHolder(footerView);
        } else if (viewType == ITEM_TYPE_NONE) {//空视图
            View view = getEmptyView(parent);
            return new RecyclerHolder(view);
        } else if (viewType == ITEM_TYPE_ERROR) {//错误视图
            View view = getErrorView(parent);
            return new RecyclerHolder(view);
        } else if (viewType == ITEM_TYPE_LOADING) {//加载中视图
            View view = getLoadingView(parent);
            return new RecyclerHolder(view);
        }

        return createContentHolder(parent, viewType);
    }

    /**
     * 根据 position 为holder绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mList == null || mList.size() == 0) {
            return;//无数据时不绑定，以免出错
        }

        if (isFootView(position) || isHeaderView(position)) {
            return;//是头布局或者脚布局也不绑定
        }

        if (mList == null || mList.size() == 0) {
            return;
        }

        if (headerView != null) {
            position--;
        }

        try {
            onInjectView((RecyclerHolder) holder, mList.get(position), position);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 整个recyclerView的item数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return (headerView == null ? 0 : 1) + (footerView == null ? 0 : 1) + getContentItemCount();
    }


    /*************************以上是必须要重写的方法******************************/


    public abstract RecyclerHolder createContentHolder(ViewGroup parent, int viewType);


    /**
     * 正文内容item数量
     *
     * @return
     */
    public int getContentItemCount() {
        int count = mList == null ? 0 : mList.size();
        if (count == 0) {
            count = 1;
        }
        return count;
    }

    /****************************必须要重写方法的拓展*************************************/


    /**
     * 根据position判断当前item类型
     */

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return ITEM_TYPE_HEADER;
        }

        if (isFootView(position)) {
            return ITEM_TYPE_FOOTER;
        }

        /*if (mList == null || (mList.size() == 0 && isLoadingError)) {
            //加载失败时视图
            return ITEM_TYPE_ERROR;
        }*/
        if (mList == null || mList.size() == 0) {
            //加载成功但是没有数据时的视图
            switch (NODATA_STATUS) {
                case LOADING:
                    return ITEM_TYPE_LOADING;
                case EMPTY:
                    return ITEM_TYPE_NONE;
                case ERROR:
                    return ITEM_TYPE_ERROR;
            }
            return ITEM_TYPE_NONE;
        }

        if (headerView != null) {
            //如果有headerView，则数据项要顺延
            position--;
        }
        return getContentViewType(position, mList.get(position));
    }


    public abstract int getContentViewType(int position, T data);

    public abstract void onInjectView(RecyclerHolder holder, T data, int position);


    public void setHeaderView(View view) {
        headerView = view;
    }

    public void setFooterView(View view) {
        footerView = view;
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return headerView != null && position == 0;
    }

    //判断当前item是否是FooterView
    public boolean isFootView(int position) {
        return footerView != null && position == getContentItemCount() + (headerView == null ? 0 : 1);
    }

    protected View getLoadingView(ViewGroup parent) {
        if (getLoadindId() != -1) {
            View view = mInflater.inflate(getLoadindId(), parent, false);
            return view;
        }
        return new View(mContext);
    }

    protected int getLoadindId() {
        return -1;
    }


    protected View getErrorView(ViewGroup parent) {
        if (getErrorId() != -1) {
            View view = mInflater.inflate(getErrorId(), parent, false);
            return view;
        }
        return new View(mContext);
    }

    protected int getErrorId() {
        return -1;
    }

    protected View getEmptyView(ViewGroup parent) {
        if (getEmptyId() != -1) {
            return mInflater.inflate(getEmptyId(), parent, false);
        }
        return new View(mContext);
    }

    protected int getEmptyId() {
        return -1;
    }

    public void loadSuccess() {
        NODATA_STATUS = EMPTY;
        notifyDataSetChanged();
    }

    public void loadEmpty() {
        NODATA_STATUS = EMPTY;
        notifyDataSetChanged();
    }

    public void loadError() {
        NODATA_STATUS = ERROR;
        notifyDataSetChanged();
    }

    public void loadStart() {
        NODATA_STATUS = LOADING;
        notifyDataSetChanged();
    }


    public static class RecyclerHolder extends RecyclerView.ViewHolder {

        ViewDataBinding dataBinding;

        public RecyclerHolder(View itemView) {
            super(itemView);
        }

        public RecyclerHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }

        public ViewDataBinding getDataBinding() {
            return dataBinding;
        }

        public <T extends View> T get(int viewId) {
            return (T) itemView.findViewById(viewId);
        }


        /**
         * 通过ViewId设置点击监听
         *
         * @param viewId
         * @param l
         */
        public void setOnClickListener(int viewId, View.OnClickListener l) {//通过ViewId设置点击监听
            get(viewId).setOnClickListener(l);
        }

        /**
         * 整体设置点击监听
         *
         * @param l
         */
        public void setOnClickListener(View.OnClickListener l) {//通过ViewId设置点击监听
            itemView.setOnClickListener(l);
        }

        /**
         * 整体设置点击监听
         *
         * @param l
         */
        public void setOnLongClickListener(View.OnLongClickListener l) {//通过ViewId设置点击监听
            itemView.setOnLongClickListener(l);
        }

    }
}
