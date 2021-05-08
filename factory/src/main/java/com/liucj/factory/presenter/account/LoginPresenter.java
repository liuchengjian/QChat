package com.liucj.factory.presenter.account;

import android.text.TextUtils;

import com.liucj.common.factory.data.DataSource;
import com.liucj.factory.R;
import com.liucj.factory.model.api.account.LoginBean;
import com.liucj.factory.model.db.User;
import com.liucj.factory.presenter.base.BasePresenter;
import com.liucj.factory.data.helper.AccountHelper;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 登录的Presenter
 */
public class LoginPresenter extends BasePresenter<LoginContract.LoginView>
        implements LoginContract.LoginPresenter, DataSource.Callback<User> {
    public LoginPresenter(LoginContract.LoginView view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {
        start();

        final LoginContract.LoginView view = getView();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            view.showError(R.string.data_account_login_invalid_parameter);
        } else {
            // 尝试传递PushId
            LoginBean model = new LoginBean(phone, password, "");
            AccountHelper.login(model, this);
        }
    }

    @Override
    public void onDataLoaded(User user) {
        final LoginContract.LoginView view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.loginSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        // 网络请求告知注册失败
        final LoginContract.LoginView view = getView();
        if (view == null)
            return;
        // 此时是从网络回送回来的，并不保证处于主现场状态
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                // 调用主界面注册失败显示错误
                view.showError(strRes);
            }
        });
    }
}
