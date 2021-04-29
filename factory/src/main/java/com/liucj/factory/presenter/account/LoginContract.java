package com.liucj.factory.presenter.account;

import com.liucj.factory.presenter.BaseContract;

/**
 * 登录的契约
 */
public interface LoginContract {

    interface LoginView extends BaseContract.View<LoginPresenter> {
        // 登录成功
        void loginSuccess();
    }


    interface LoginPresenter extends BaseContract.Presenter {
        // 发起一个登录
        void login(String phone, String password);
    }
}
