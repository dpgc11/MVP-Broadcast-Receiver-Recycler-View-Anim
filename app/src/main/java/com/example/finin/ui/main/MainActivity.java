package com.example.finin.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finin.R;
import com.example.finin.data.network.model.Data;
import com.example.finin.fininApp;
import com.example.finin.receivers.ConnectivityReceiver;
import com.example.finin.ui.adapter.UserDataAdapter;
import com.example.finin.ui.base.BaseActivity;
import com.example.finin.utils.LinearLayoutPaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.finin.utils.AppConstants.PAGE_START;


public class MainActivity extends BaseActivity implements MainMvpView, ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainMvpPresenter<MainMvpView> mMainMvpPresenter;
    @BindView(R.id.rv_user_data)
    RecyclerView mUserDataRecyclerView;
    @BindView(R.id.tv_error)
    TextView mTextView;

    private boolean mIsLoading = false;
    private boolean mIsLastPage = false;
    private int mCurrentPage = PAGE_START;
    private int mTotalPages;
    private List<Data> mDataList;
    private UserDataAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ConnectivityReceiver mConnectivityReceiver;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mMainMvpPresenter.onAttach(MainActivity.this);

        mConnectivityReceiver = new ConnectivityReceiver();

        mDataList = new ArrayList<>();

        mAdapter = new UserDataAdapter(this, R.layout.list_item_user1);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mUserDataRecyclerView.setLayoutManager(mLinearLayoutManager);
        mUserDataRecyclerView.setAdapter(mAdapter);
        int resId = R.anim.layout_animation_slide_from_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        mUserDataRecyclerView.setLayoutAnimation(animation);

        mUserDataRecyclerView.addOnScrollListener(new LinearLayoutPaginationScrollListener(mLinearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                Log.d(TAG, "addOnScrollListener: currentPage: " + mCurrentPage);
                if (isNetworkConnected()) {
                    if (mCurrentPage < mTotalPages) {
                        mIsLoading = true;

                        mCurrentPage += 1;

                        mMainMvpPresenter.onPageScrolled(mCurrentPage);
                    } else {

                    }
                } else {
                    mIsLoading = false;
                }
            }

            @Override
            public boolean isLastPage() {
                return mIsLastPage;
            }

            @Override
            public boolean isLoading() {
                return mIsLoading;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver();

        // register connection status listener
        fininApp.get(this).setConnectivityListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mConnectivityReceiver);
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        registerReceiver(mConnectivityReceiver, intentFilter);
    }

    @Override
    public void setTotalPages(int pages) {
        Log.d(TAG, "setTotalPages: " + pages);
        mTotalPages = pages;
    }

    @Override
    public void showPaginatedUserData(List<Data> dataList) {
        if (dataList.isEmpty()) {
            // displayEmptyList();
        } else {
            setupPaginatedUserData(dataList);
        }
    }

    @Override
    public void showUserData(List<Data> dataList) {
        if (dataList.isEmpty()) {
            // displayEmptyList();
        } else {
            setupUserData(dataList);
        }
    }

    private void setupUserData(List<Data> dataList) {
        resetData();

        mDataList.addAll(dataList);
        Log.d(TAG, "setupUserData: dataList: " + mDataList.size() + "\tmCurrentPage: " + mCurrentPage);
        mUserDataRecyclerView.setVisibility(View.VISIBLE);
        mUserDataRecyclerView.scheduleLayoutAnimation();
        mAdapter.addAll(dataList);

        if (mCurrentPage <= mTotalPages) {
            Log.d(TAG, "setupUserData: if condition");
            mAdapter.addLoadingFooter();
            mIsLastPage = false;
        } else {
            Log.d(TAG, "setupUserData: else condition");
            mIsLastPage = true;
        }
    }

    private void resetData() {
        mDataList = new ArrayList<>();
        mAdapter.removeAll();
        mCurrentPage = PAGE_START;
        mIsLoading = false;
        mIsLastPage = false;
    }

    private void setupPaginatedUserData(List<Data> dataList) {
        mDataList.addAll(dataList);
        Log.d(TAG, "setupPaginatedUserData: dataList: " + dataList.size() + "\t mCurrentPage: " + mCurrentPage);
        Log.d(TAG, "setupPaginatedUserData: mDataList: " + mDataList.size());
        mAdapter.removeLoadingFooter();
        mIsLoading = false;
        mAdapter.addAll(dataList);

        if (mCurrentPage < mTotalPages) {
            Log.d(TAG, "setupPaginatedUserData: if condition");
            mAdapter.addLoadingFooter();
            mIsLastPage = false;
        } else {
            Log.d(TAG, "setupPaginatedUserData: else condition");
            mIsLastPage = true;
        }
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        Log.d(TAG, "onNetworkConnectionChanged: ");
        mMainMvpPresenter.onNetworkConnectionChanged(isConnected);
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkErrorMsg() {
        mTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNetworkErrorMsg() {
        mTextView.setVisibility(View.GONE);
    }

    @Override
    public void showSomethingWentWrongMsg() {
        Toast.makeText(getApplicationContext(), getString(R.string.something_went_wrong_msg), Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        mMainMvpPresenter.onDetach();
        super.onDestroy();
    }


}
