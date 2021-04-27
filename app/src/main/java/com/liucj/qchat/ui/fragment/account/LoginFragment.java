package com.liucj.qchat.ui.fragment.account;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.liucj.common.BaseFragment;
import com.liucj.qchat.R;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment {
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
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        // 调用P层进行注册
    }

    @OnClick(R.id.txt_go_register)
    void onShowRegisterClick() {
        // 让AccountActivity进行界面切换
        mAccountTrigger.triggerView();
    }

}
