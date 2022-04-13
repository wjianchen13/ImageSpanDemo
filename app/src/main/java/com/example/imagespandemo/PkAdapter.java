package com.example.imagespandemo;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.imagespandemo.bean.PkBean;
import com.example.imagespandemo.imagespan.PkLevelImageSpan;
import com.example.imagespandemo.utils.SpanUtils;

import java.util.List;

/**
 *
 */
public class PkAdapter extends RecyclerView.Adapter<PkAdapter.ViewHolder>{

    private Context mContext;
    private List<PkBean> mDataset;

    public PkAdapter(Context context, List<PkBean> dataset) {
        super();
        this.mContext = context;
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = View.inflate(viewGroup.getContext(), R.layout.item_test, null);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_test, viewGroup, false);//
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PkBean pkBean = mDataset.get(i);
//        SpannableString fansImageSpan = SpanUtils.getPkImageSpan(mContext, pkBean.name, pkBean.level, pkBean.grade, ImageSpanAlign.XIU_ALIGN_CENTER, PkLevelImageSpan.MODE_NORMAL);
//        viewHolder.mTextView.setText(fansImageSpan);

        SpannableString fansImageSpan = SpanUtils.getPkImageSpan1(mContext, pkBean.name, pkBean.segment, PkLevelImageSpan.MODE_NORMAL);
        viewHolder.mTextView.setText(fansImageSpan);

        SpannableString fansImageSpan1 = SpanUtils.getPkImageSpan1(mContext, pkBean.name, pkBean.segment, PkLevelImageSpan.MODE_LIVE);
        viewHolder.mTextView1.setText(fansImageSpan1);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public TextView mTextView1;

        public ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.tv_test);
            mTextView1 = view.findViewById(R.id.tv_test1);
        }
    }
}
