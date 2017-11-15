package com.xmjj.rxretrofit_master.util.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangzhiwei  on 2016/6/2.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecycleHolder> {

    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;
    private LayoutInflater mInflater;
    private boolean isScrolling;
    private OnItemClickListener onItemClickListener;

    private RecyclerView recyclerView;


    public BaseRecyclerAdapter(Context mContext, List<T> mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;
        mInflater = LayoutInflater.from(mContext);

    }


    public List<T> getmDatas() {
        return mDatas;

    }

    public void addDatas(List<T> data) {
        for (T t : data) {
            mDatas.add(t);
        }

    }

    //adapter数据的刷新
    public void insertData(List<T> data) {

        if (this.mDatas.addAll(data)) {
            notifyDataSetChanged();


        }
    }

    public void refreshData(List<T> data) {
        if (!data.isEmpty()) {
            mDatas.clear();
            mDatas.addAll(data);

            notifyDataSetChanged();
        }

    }

    //设置数据
    public void setItems(List<T> data) {
        if (!isEmpty(data)) {
            this.mDatas = data;
            notifyDataSetChanged();
        }
    }


    public void removeDatas(List<T> data) {
        for (T t : data) {
            mDatas.remove(t);
        }
    }

    public void notifyItem(int position) {
        notifyItemChanged(position, "notify");
    }

    @Override
    public BaseRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecycleHolder(mContext, mInflater.inflate(mLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseRecycleHolder holder, int position) {
        convert(holder, mDatas.get(position), position);

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
                    }
                }
            });


        }

    }


    public abstract void convert(BaseRecycleHolder holder, T data, int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void OnItemClickListener(View view, int position);
    }

    public boolean isEmpty(List list) {
        if (list != null) {
            if (!list.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
