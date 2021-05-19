package com.liucj.qchat.ui.activity;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.liucj.common.activity.BaseActivity;
import com.liucj.common.fragment.BaseFragment;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.fragment.account.AccountTrigger;
import com.liucj.qchat.ui.fragment.account.LoginFragment;
import com.liucj.qchat.ui.fragment.account.RegisterFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements AccountTrigger {
    private BaseFragment mCurFragment;
    private BaseFragment mLoginFragment;
    private BaseFragment mRegisterFragment;

    @BindView(R.id.im_bg)
    ImageView mBg;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        // 初始化Fragment
        mCurFragment = mLoginFragment = new LoginFragment();
        setBgImage();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();
    }
    private void setBgImage(){
        // 初始化背景
        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop() //居中剪切
                .into(new DrawableImageViewTarget(mBg) {
                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        if (resource == null) {
                            super.setResource(resource);
                            return;
                        }
                        // 使用适配类进行包装
                        Drawable drawable = DrawableCompat.wrap(resource);
                        drawable.setColorFilter(UiCompat.getColor(getResources(), R.color.colorAccent),
                                PorterDuff.Mode.SCREEN); // 设置着色的效果和颜色，蒙板模式
                        // 设置给ImageView
                        super.setResource(drawable);
                    }
                });
    }

    @Override
    public void triggerView() {
        BaseFragment fragment;
        if (mCurFragment == mLoginFragment) {
            if (mRegisterFragment == null) {
                //默认情况下为null，
                //第一次之后就不为null了
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        }else{
            // 因为默认请求下mLoginFragment已经赋值，无须判断null
            fragment = mLoginFragment;
        }
        // 重新赋值当前正在显示的Fragment
        mCurFragment = fragment;
        setBgImage();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container,fragment)
                .commit();
    }

}