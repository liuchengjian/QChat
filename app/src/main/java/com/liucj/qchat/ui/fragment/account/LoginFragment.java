package com.liucj.qchat.ui.fragment.account;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.liucj.common.Application;
import com.liucj.common.utils.QUtils;
import com.liucj.factory.presenter.PresenterFragment;
import com.liucj.factory.presenter.account.LoginContract;
import com.liucj.factory.presenter.account.LoginPresenter;
import com.liucj.factory.utils.AccountUtil;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.activity.MainActivity;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends PresenterFragment<LoginContract.LoginPresenter>
        implements LoginContract.LoginView {
    private AccountTrigger mAccountTrigger;

    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassword;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 拿到我们的Activity的引用
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected LoginContract.LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initData() {
        super.initData();
        mPhone.setText(AccountUtil.getUser().getPhone());
    }

    @OnClick(R.id.txt_go_register)
    void onShowRegisterClick() {
        // 让AccountActivity进行界面切换
        mAccountTrigger.triggerView();
    }

    @OnClick({R.id.btn_submit})
    void onClick() {
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        // 调用P层进行注册
        mPresenter.login(phone, password);
    }

    @Override
    public void loginSuccess() {
        QUtils.makeText(getActivity(),"登录成功");
//        startActivity(new Intent(getContext(), MainActivity.class));
        MainActivity.show(getActivity());
        getActivity().finish();
    }

    @Override
    public void showError(int str) {
        // 当需要显示错误的时候触发，一定是结束了
        // 停止Loading
        mLoading.stop();
        // 让控件可以输入
        mPhone.setEnabled(true);
        mPassword.setEnabled(true);
        // 提交按钮可以继续点击
        mSubmit.setEnabled(true);
        QUtils.makeText(getActivity(), Application.getInstance().getString(str));
    }

    @Override
    public void showLoading() {

        // 正在进行时，正在进行注册，界面不可操作
        // 开始Loading
        mLoading.start();
        // 让控件不可以输入
        mPhone.setEnabled(false);
        mPassword.setEnabled(false);
        // 提交按钮不可以继续点击
        mSubmit.setEnabled(false);
    }
}
