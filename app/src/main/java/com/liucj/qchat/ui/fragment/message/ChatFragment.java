package com.liucj.qchat.ui.fragment.message;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.liucj.common.recycler.BaseRecyclerAdapter;
import com.liucj.common.widget.view.PortraitView;
import com.liucj.common.widget.TextWatcherAdapter;
import com.liucj.factory.model.db.Message;
import com.liucj.factory.model.db.User;
import com.liucj.factory.presenter.PresenterFragment;
import com.liucj.factory.presenter.message.ChatContract;
import com.liucj.factory.utils.AccountUtil;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.activity.MessageActivity;

import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class ChatFragment<InitModel>
        extends PresenterFragment<ChatContract.Presenter>
        implements AppBarLayout.OnOffsetChangedListener,
        ChatContract.View<InitModel>{

    protected String mReceiverId;
    protected Adapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingLayout;

    @BindView(R.id.edit_content)
    EditText mContent;

    @BindView(R.id.btn_submit)
    View mSubmit;

    // 控制顶部面板与软键盘过度的Boss控件
//    private AirPanel.Boss mPanelBoss;
//    private PanelFragment mPanelFragment;

    // 语音的基础
//    private FileCache<AudioHolder> mAudioFileCache;
//    private AudioPlayHelper<AudioHolder> mAudioPlayer;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mReceiverId = bundle.getString(MessageActivity.KEY_RECEIVER_ID);
    }

    @Override
    protected final int getContentLayoutId() {
        return R.layout.fragment_chat_common;
    }


    @Override
    protected void initWidget(View root) {
        // 拿到占位布局
        // 替换顶部布局一定需要发生在super之前
        // 防止控件绑定异常
        ViewStub stub = (ViewStub) root.findViewById(R.id.view_stub_header);
        stub.setLayoutResource(getHeaderLayoutId());
        stub.inflate();

        // 在这里进行了控件绑定
        super.initWidget(root);

        // 初始化面板操作
//        mPanelBoss = (AirPanel.Boss) root.findViewById(R.id.lay_content);
//        mPanelBoss.setup(new AirPanel.PanelListener() {
//            @Override
//            public void requestHideSoftKeyboard() {
//                // 请求隐藏软键盘
//                Util.hideKeyboard(mContent);
//            }
//        });
//        mPanelBoss.setOnStateChangedListener(new AirPanel.OnStateChangedListener() {
//            @Override
//            public void onPanelStateChanged(boolean isOpen) {
//                // 面板改变
//                if (isOpen)
//                    onBottomPanelOpened();
//            }
//
//            @Override
//            public void onSoftKeyboardStateChanged(boolean isOpen) {
//                // 软键盘改变
//                if (isOpen)
//                    onBottomPanelOpened();
//            }
//        });
//        mPanelFragment = (PanelFragment) getChildFragmentManager().findFragmentById(R.id.frag_panel);
//        mPanelFragment.setup(this);

        initToolbar();
        initAppbar();
        initEditContent();

        // RecyclerView基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        // 添加适配器监听器，进行点击的实现
        mAdapter.setListener(new BaseRecyclerAdapter.AdapterListenerImpl<Message>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter.ViewHolder holder, Message message) {
//                if (message.getType() == Message.TYPE_AUDIO && holder instanceof ChatFragment.AudioHolder) {
//                    // 权限的判断，当然权限已经全局申请了
//                    mAudioFileCache.download((ChatFragment.AudioHolder) holder, message.getContent());
//                }
            }
        });

    }

    // 得到顶部布局的资源Id
    @LayoutRes
    protected abstract int getHeaderLayoutId();
    @Override
    public BaseRecyclerAdapter<Message> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        //不用实现，站位
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        if (mSubmit.isActivated()) {
            // 发送
            String content = mContent.getText().toString();
            mContent.setText("");
            mPresenter.pushText(content);
        } else {
//            onMoreClick();
        }
    }
    @Override
    protected void initData() {
        super.initData();
        //开始进行初始化操作
        mPresenter.start();
    }

    // 初始化Toolbar
    protected void initToolbar() {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
    //  给界面的Appbar设置一个监听，得到关闭与打开的时候的进度
    private void initAppbar() {
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    // 初始化输入框监听
    private void initEditContent() {
        mContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                boolean needSendMsg = !TextUtils.isEmpty(content);
                // 设置状态，改变对应的Icon
                mSubmit.setActivated(needSendMsg);
            }
        });
    }

    // 内容的适配器
    private class Adapter extends BaseRecyclerAdapter<Message> {
        @SuppressLint("NewApi")
        @Override
        protected int getItemViewType(int position, Message message) {
            // 我发送的在右边，收到的在左边
             boolean isRight = Objects.equals(message.getSender().getId(), AccountUtil.getUserId());

            switch (message.getType()) {
                // 文字内容
                case Message.TYPE_STR:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;

                // 语音内容
//                case Message.TYPE_AUDIO:
//                    return isRight ? R.layout.cell_chat_audio_right : R.layout.cell_chat_audio_left;
//
//                // 图片内容
//                case Message.TYPE_PIC:
//                    return isRight ? R.layout.cell_chat_pic_right : R.layout.cell_chat_pic_left;

                // 其他内容：文件
                default:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
            }
        }

        @Override
        protected ViewHolder<Message> onCreateViewHolder(View root, int viewType) {
            switch (viewType) {
                // 左右都是同一个
                case R.layout.cell_chat_text_right:
                case R.layout.cell_chat_text_left:
                    return new TextHolder(root);

//                case R.layout.cell_chat_audio_right:
//                case R.layout.cell_chat_audio_left:
//                    return new AudioHolder(root);
//
//                case R.layout.cell_chat_pic_right:
//                case R.layout.cell_chat_pic_left:
//                    return new PicHolder(root);

                // 默认情况下，返回的就是Text类型的Holder进行处理
                // 文件的一些实现
                default:
                    return new TextHolder(root);
            }
        }
    }

    // 文字的Holder
    class TextHolder extends BaseHolder {
        @BindView(R.id.txt_content)
        TextView mContent;

        public TextHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);

//            Spannable spannable = new SpannableString(message.getContent());

            // 解析表情
//            Face.decode(mContent, spannable, (int) Ui.dipToPx(getResources(), 20));

            // 把内容设置到布局上
            mContent.setText(message.getContent());
        }
    }

    // Holder的基类
    class BaseHolder extends BaseRecyclerAdapter.ViewHolder<Message> {
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;

        // 允许为空，左边没有，右边有
        @Nullable
        @BindView(R.id.loading)
        Loading mLoading;


        public BaseHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            User sender = message.getSender();
            // 进行数据加载
            sender.load();
            // 头像加载
            mPortrait.setup(Glide.with(getActivity()), sender.getPortrait());

            if (mLoading != null) {
                // 当前布局应该是在右边
                int status = message.getStatus();
                if (status == Message.STATUS_DONE) {
                    // 正常状态, 隐藏Loading
                    mLoading.stop();
                    mLoading.setVisibility(View.GONE);
                } else if (status == Message.STATUS_CREATED) {
                    // 正在发送中的状态
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.setProgress(0);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(), R.color.colorAccent));
                    mLoading.start();
                } else if (status == Message.STATUS_FAILED) {
                    // 发送失败状态, 允许重新发送
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.stop();
                    mLoading.setProgress(1);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(), R.color.alertImportant));
                }

                // 当状态是错误状态时才允许点击
                mPortrait.setEnabled(status == Message.STATUS_FAILED);
            }
        }

        @OnClick(R.id.im_portrait)
        void onRePushClick() {
            // 重新发送

            if (mLoading != null && mPresenter.rePush(mData)) {
                // 必须是右边的才有可能需要重新发送
                // 状态改变需要重新刷新界面当前的信息
                updateData(mData);
            }

        }
    }

}
