package com.liucj.factory.presenter.account;

import android.text.TextUtils;

import com.liucj.common.factory.data.DataSource;
import com.liucj.factory.R;
import com.liucj.factory.model.api.account.RegisterBean;
import com.liucj.factory.model.db.User;
import com.liucj.factory.data.helper.AccountHelper;
import com.liucj.factory.net.api.CommonApi;
import com.liucj.factory.presenter.base.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

/**
 * 注册的Presenter
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.RegisterView>
        implements RegisterContract.RegisterPresenter, DataSource.Callback<User> {

    public RegisterPresenter(RegisterContract.RegisterView view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password) {
        // 调用开始方法，在start中默认启动了Loading
        start();

        // 得到View接口
        RegisterContract.RegisterView view = getView();

        // 校验
        if (!checkMobile(phone)) {
            // 提示
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        } else if (name.length() < 2) {
            // 姓名需要大于2位
            view.showError(R.string.data_account_register_invalid_parameter_name);
        } else if (password.length() < 6) {
            // 密码需要大于6位
            view.showError(R.string.data_account_register_invalid_parameter_password);
        } else {
            // 进行网络请求
            // 构造Model，进行请求调用
            RegisterBean model = new RegisterBean(phone, password, name, "");
            // 进行网络请求，并设置回送接口为自己
            AccountHelper.register(model, this);
        }
    }


    @Override
    public void onDataLoaded(User user) {
        // 当网络请求成功，注册好了，回送一个用户信息回来
        // 告知界面，注册成功
        final RegisterContract.RegisterView view = getView();
        if (view == null)
            return;
        // 此时是从网络回送回来的，并不保证处于主现场状态
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                // 调用主界面注册成功
                view.registerSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(int strRes) {

    }


    /**
     * 检查手机号是否合法
     *
     * @param phone 手机号码
     * @return 合法为True
     */
    @Override
    public boolean checkMobile(String phone) {
        // 手机号不为空，并且满足格式
        return !TextUtils.isEmpty(phone)
                && Pattern.matches(CommonApi.Constance.REGEX_MOBILE, phone);
    }

}
