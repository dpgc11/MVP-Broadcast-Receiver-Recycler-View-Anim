package com.example.finin.ui.main;


import com.example.finin.data.network.model.Data;
import com.example.finin.ui.base.MvpView;

import java.util.List;


/**
 * Created by yogesh
 */

public interface MainMvpView extends MvpView {


    void showNetworkErrorMsg();
    void hideNetworkErrorMsg();
    void onError(String msg);

    void showSomethingWentWrongMsg();


    void showPaginatedUserData(List<Data> dataList);
    void showUserData(List<Data> dataList);
    void setTotalPages(int pages);
}
