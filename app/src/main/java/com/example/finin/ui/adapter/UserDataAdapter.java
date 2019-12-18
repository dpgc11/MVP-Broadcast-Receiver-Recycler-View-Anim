package com.example.finin.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finin.R;
import com.example.finin.data.network.model.Data;
import com.example.finin.fininApp;
import com.example.finin.utils.CircleTransformationBorder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogesh on 29/11/17.
 */

public class UserDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Inject
    Picasso mPicasso;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private List<Data> mDataList;
    private int mRowLayout;

    public UserDataAdapter(Context context, int rowLayout) {
        mDataList = new ArrayList<>();
        this.mRowLayout = rowLayout;
        fininApp.get(context).getComponent().inject(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View gigsView = inflater.inflate(mRowLayout, parent, false);
                holder = new UserDataViewHolder(gigsView);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                holder = new LoadingVH(viewLoading);
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Data mCurrentGig = mDataList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                showUserData((UserDataViewHolder) holder, position, mCurrentGig);
                break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;
                loadingVH.getmProgressBar().setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void showUserData(UserDataViewHolder holder, int position, Data mCurrentData) {
        final UserDataViewHolder viewHolder = holder;

        viewHolder.mAspirantNameTextView.setText(mCurrentData.getFirstName() + " " + mCurrentData.getLastName());

        viewHolder.mAspirantCollegeNameTextView.setText(mCurrentData.getEmail());

        mPicasso
                .load(mCurrentData.getAvatar())
                .placeholder(R.drawable.ic_account_circle)
                .fit().centerInside()
                .error(R.drawable.ic_account_circle)
                .transform(new CircleTransformationBorder(ContextCompat.getColor(viewHolder.mAspirantProfilePic.getContext(), R.color.colorPrimaryDark)))
                .into(viewHolder.mAspirantProfilePic);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mDataList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public Data getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void add(Data r) {
        mDataList.add(r);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void addAll(List<Data> moveResults) {
        for (Data result : moveResults) {
            add(result);
        }
    }

    public void removeAll() {
        mDataList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void remove(Data r) {
        int position = mDataList.indexOf(r);
        if (position > -1) {
            mDataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Data());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mDataList.size() - 1;
        Data result = getItem(position);

        if (result != null) {
            mDataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class UserDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_user_name)
        TextView mAspirantNameTextView;
        @BindView(R.id.iv_user_profile_pic)
        ImageView mAspirantProfilePic;
        @BindView(R.id.tv_user_college)
        TextView mAspirantCollegeNameTextView;

        private Context mContext;

        public UserDataViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.mContext = view.getContext();
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            int pos = getAdapterPosition();
        }
    }

    public class LoadingVH extends RecyclerView.ViewHolder {

        private ProgressBar mProgressBar;

        public ProgressBar getmProgressBar() {
            return mProgressBar;
        }

        public LoadingVH(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
        }
    }
}
