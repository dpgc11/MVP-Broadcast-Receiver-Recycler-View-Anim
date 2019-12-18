package com.example.finin.ui.main;

import android.util.Log;

import com.example.finin.data.DataManager;
import com.example.finin.data.network.model.Data;
import com.example.finin.rest.ApiClient;
import com.example.finin.rest.ApiService;
import com.example.finin.ui.base.BasePresenter;
import com.example.finin.utils.RxUtil;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.example.finin.utils.AppConstants.PAGE_DELAY;
import static com.example.finin.utils.AppConstants.PAGE_START;

/**
 * Created by yogesh
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    private ApiService mApiService;
    private static final String TAG = MainPresenter.class.getSimpleName();

    private CompositeDisposable mCompositeDisposable;

    @Inject
    public MainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
        mApiService = ApiClient.getClient().create(ApiService.class);
        mCompositeDisposable = getCompositeDisposable();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        RxUtil.clear(mCompositeDisposable);
    }

    @Override
    public void onPageScrolled(int page) {
        if (isViewAttached()) {
            getUsersData(page, PAGE_DELAY, false);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        Log.d(TAG, "onNetworkConnectionChanged: " + isConnected);
        if (isViewAttached()) {
            if (isConnected) {
                getMvpView().hideNetworkErrorMsg();
                getUsersData(PAGE_START, PAGE_DELAY, true);
            } else {
                getMvpView().showNetworkErrorMsg();
            }
        }
    }

    private void getUsersData(int page, int delay, boolean isFirstPage) {
        Log.d(TAG, "getUsersData: " + page + "\t" + delay + "\t" + isFirstPage);
        mCompositeDisposable.add(mApiService.getUsersData(page, delay)
                .doAfterSuccess(usersResponse -> {
                    // no need to set it repeatedly
                    if (isFirstPage) {
                        getMvpView().setTotalPages(usersResponse.getTotalPages());
                    }
                })
                .map(usersResponse -> usersResponse.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (isFirstPage) {
                        getMvpView().showLoading();
                    }
                })
                .doAfterTerminate(() -> {
                    if (isFirstPage) {
                        getMvpView().hideLoading();
                    }
                })
                .subscribeWith(new DisposableSingleObserver<List<Data>>() {
                    @Override
                    public void onSuccess(List<Data> dataList) {
                        Log.d(TAG, "getUsersData: onSuccess(): " + dataList.size());
                        try {
                            if (isFirstPage) {
                                getMvpView().showUserData(dataList);
                            } else {
                                getMvpView().showPaginatedUserData(dataList);
                            }
                        } catch (NullPointerException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getUsersData: onError()");
                        try {
                            parseError(e);
                        } catch (NullPointerException e1) {
                            Log.e(TAG, e1.getMessage());
                        }
                    }
                }));
    }

    private void parseError(Throwable throwable) {
        try {
            // We had non-200 http error
            if (throwable instanceof com.jakewharton.retrofit2.adapter.rxjava2.HttpException) {
                com.jakewharton.retrofit2.adapter.rxjava2.HttpException httpException = (com.jakewharton.retrofit2.adapter.rxjava2.HttpException) throwable;
                Response response = httpException.response();
                Log.e(TAG, "HttpException " + throwable.getMessage() + " / " + throwable.getClass());
                // Use the following to use a retrofit converter to convert into POJO
                getMvpView().onError(throwable.getMessage());
            } else if (throwable instanceof IOException) {
                // A network error happened
                Log.e(TAG, "IOException " + throwable.getMessage() + " / " + throwable.getClass());
                try {
                    getMvpView().onError(throwable.getMessage());
                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                }
            } else {
                try {
                    getMvpView().showSomethingWentWrongMsg();
                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                }
                Log.e(TAG, "UnknownException " + throwable.getMessage() + " / " + throwable.getClass());
            }

        } catch (
                Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
            try {
                getMvpView().showSomethingWentWrongMsg();
            } catch (NullPointerException e1) {
                Log.e(TAG, e1.getMessage());
            }
        }

    }
}
