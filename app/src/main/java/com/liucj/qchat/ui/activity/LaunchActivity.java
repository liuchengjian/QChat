package com.liucj.qchat.ui.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.util.Property;
import android.view.View;

import com.liucj.common.activity.BaseActivity;
import com.liucj.qchat.R;

import net.qiujuer.genius.ui.compat.UiCompat;

/**
 * QChat 的入口 Activity
 */
public class LaunchActivity extends BaseActivity {

    private ColorDrawable mBgDrawable;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void initWidget() {
        super.initWidget();
        // 拿到跟布局
        View root = findViewById(R.id.activity_launch);
        // 获取颜色
        int color = UiCompat.getColor(getResources(), R.color.white);
        // 创建一个Drawable
        ColorDrawable drawable = new ColorDrawable(color);
        // 设置给背景 getResources().getDrawable(R.drawable.bg_src_tianjin)
        root.setBackground(drawable);
        mBgDrawable = drawable;
    }

    @Override
    protected void initData() {
        super.initData();
        // 动画进入到50%等待PushId获取到
        startAnim(0.5f, new Runnable() {
            @Override
            public void run() {
                // 检查等待状态
//                waitPushReceiverId();
                Skip();
                finish();
            }
        });
    }

    /**
     * 真实的跳转
     */
    private void Skip() {
        // 检查跳转到主页还是登录
        startAct(LoginActivity.class);
//        if (!Account.isLogin()) {
//            startAct(MainActivity.class);
//        } else {
//            startAct(AccountActivity.class);
//        }
    }



    /**
     * 给背景设置一个动画
     *
     * @param endProgress 动画的结束进度
     * @param endCallback 动画结束时触发
     */
    private void startAnim(float endProgress, final Runnable endCallback) {
        // 获取一个最终的颜色
        int finalColor = UiCompat.getColor(getResources(), R.color.colorAccent); // UiCompat.getColor(getResources(), R.color.white);
        // 运算当前进度的颜色
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int endColor = (int) evaluator.evaluate(endProgress, mBgDrawable.getColor(), finalColor);
        // 构建一个属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofObject(this, property, evaluator, endColor);
        valueAnimator.setDuration(1500); // 时间
        valueAnimator.setIntValues(mBgDrawable.getColor(), endColor); // 开始结束值
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 结束时触发
                endCallback.run();
            }
        });
        valueAnimator.start();
    }

    private final Property<LaunchActivity, Object> property = new Property<LaunchActivity, Object>(Object.class, "color") {
        @Override
        public void set(LaunchActivity object, Object value) {
            object.mBgDrawable.setColor((Integer) value);
        }

        @Override
        public Object get(LaunchActivity object) {
            return object.mBgDrawable.getColor();
        }
    };
}