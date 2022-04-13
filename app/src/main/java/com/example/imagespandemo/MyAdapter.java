package com.example.imagespandemo;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.imagespandemo.imagespan.ImageSpanAlign;
import com.example.imagespandemo.utils.ScreenUtils;
import com.example.imagespandemo.utils.SpanUtils;

import java.util.List;

/**
 *
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private Context mContext;
    private List<Integer> mDataset;

    public MyAdapter(Context context, List<Integer> dataset) {
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
        SpannableString fansImageSpan = SpanUtils.getBadgeImageSpan(mContext, Integer.toString(mDataset.get(i)),  ScreenUtils.dip2px(mContext, 17), ImageSpanAlign.XIU_ALIGN_CENTER);
        viewHolder.mTextView.setText(fansImageSpan);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.tv_test);
        }
    }
}
