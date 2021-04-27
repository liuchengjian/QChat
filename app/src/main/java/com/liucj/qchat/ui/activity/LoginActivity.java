package com.liucj.qchat.ui.activity;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
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
        Glide.with(this)
                .load(mCurFragment == mLoginFragment?R.drawable.bg_src_morning:R.drawable.bg_src_tianjin)
                .centerCrop()//居中剪切
                .into(new ViewTarget<ImageView, GlideDrawable>(mBg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        // 拿到glide的Drawable
                        Drawable drawable = resource.getCurrent();
                        drawable.setColorFilter(UiCompat.getColor(getResources(), R.color.colorAccent),
                                PorterDuff.Mode.SCREEN);
                        // 设置给ImageView
                        this.view.setImageDrawable(drawable);
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