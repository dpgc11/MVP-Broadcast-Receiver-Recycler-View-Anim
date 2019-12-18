
package com.example.finin.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Yogesh on 27/01/17.
 */

public abstract class BaseSubView extends ViewGroup implements SubMvpView {

    private MvpView mParentMvpView;

    public BaseSubView(Context context) {
        super(context);
    }

    public BaseSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BaseSubView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void attachParentMvpView(MvpView mvpView) {
        mParentMvpView = mvpView;
    }

    @Override
    public void hideKeyboard() {
        if (mParentMvpView != null) {
            mParentMvpView.hideKeyboard();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (mParentMvpView != null) {
            return mParentMvpView.isNetworkConnected();
        }
        return false;
    }

    protected abstract void bindViewsAndSetOnClickListeners();

    protected abstract void setUp();
}
