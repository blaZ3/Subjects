package com.example.subjects.app.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.subjects.R;
import com.example.subjects.app.subject.models.Subject;
import com.example.subjects.app.utils.DataBindingViewHolder;
import com.example.subjects.databinding.LayoutSubjectItemBinding;

import java.util.ArrayList;

/**
 * Created by vivek on 07/03/18.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectAdapterViewHolder> {
    private static final String TAG = SubjectAdapter.class.getSimpleName();

    private ArrayList<Subject> items;
    private Context context;

    static class SubjectAdapterViewHolder extends DataBindingViewHolder{
        LayoutSubjectItemBinding dataBinding;
        public SubjectAdapterViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding);
            this.dataBinding = (LayoutSubjectItemBinding) viewDataBinding;
        }
    }

    public SubjectAdapter(Context context, ArrayList<Subject> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public void onBindViewHolder(SubjectAdapterViewHolder holder, int position) {
        Subject subject = items.get(position);
        holder.dataBinding.setSubject(subject);
        if (!TextUtils.isEmpty(subject.getImgFilePath())){
            holder.dataBinding.imgSubjectItem.setImageBitmap(
                    BitmapFactory.decodeFile(subject.getImgFilePath()));
        }else {
            holder.dataBinding.imgSubjectItem.setImageDrawable(
                    context.getResources().getDrawable(R.drawable.ic_placeholder));
        }
    }

    @Override
    public SubjectAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SubjectAdapterViewHolder vh = new SubjectAdapterViewHolder(DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.layout_subject_item,
                        parent, false));
        return vh;
    }

    @Override
    public int getItemCount() {
        if (items!=null) {
            return items.size();
        }
        return 0;
    }
}
