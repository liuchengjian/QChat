package com.liucj.qchat.ui.fragment.message;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.liucj.qchat.ui.activity.MainActivity;
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

    // ???????????????????????????????????????Boss??????
//    private AirPanel.Boss mPanelBoss;
//    private PanelFragment mPanelFragment;

    // ???????????????
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
        // ??????????????????
        // ???????????????????????????????????????super??????
        // ????????????????????????
        ViewStub stub = (ViewStub) root.findViewById(R.id.view_stub_header);
        stub.setLayoutResource(getHeaderLayoutId());
        stub.inflate();

        // ??????????????????????????????
        super.initWidget(root);

        // ?????????????????????
//        mPanelBoss = (AirPanel.Boss) root.findViewById(R.id.lay_content);
//        mPanelBoss.setup(new AirPanel.PanelListener() {
//            @Override
//            public void requestHideSoftKeyboard() {
//                // ?????????????????????
//                Util.hideKeyboard(mContent);
//            }
//        });
//        mPanelBoss.setOnStateChangedListener(new AirPanel.OnStateChangedListener() {
//            @Override
//            public void onPanelStateChanged(boolean isOpen) {
//                // ????????????
//                if (isOpen)
//                    onBottomPanelOpened();
//            }
//
//            @Override
//            public void onSoftKeyboardStateChanged(boolean isOpen) {
//                // ???????????????
//                if (isOpen)
//                    onBottomPanelOpened();
//            }
//        });
//        mPanelFragment = (PanelFragment) getChildFragmentManager().findFragmentById(R.id.frag_panel);
//        mPanelFragment.setup(this);

        initToolbar();
        initAppbar();
        initEditContent();

        // RecyclerView????????????
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        manager.setStackFromEnd(true);
//        mRecyclerView.setLayoutManager(manager);
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        // ????????????????????????????????????????????????
        mAdapter.setListener(new BaseRecyclerAdapter.AdapterListenerImpl<Message>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter.ViewHolder holder, Message message) {
//                if (message.getType() == Message.TYPE_AUDIO && holder instanceof ChatFragment.AudioHolder) {
//                    // ???????????????????????????????????????????????????
//                    mAudioFileCache.download((ChatFragment.AudioHolder) holder, message.getContent());
//                }
            }
        });

    }

    // ???????????????????????????Id
    @LayoutRes
    protected abstract int getHeaderLayoutId();
    @Override
    public BaseRecyclerAdapter<Message> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        //?????????????????????
        //???RecyclerView?????????????????????
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
//        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        if (mSubmit.isActivated()) {
            // ??????
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
        //???????????????????????????
        mPresenter.start();
    }

    // ?????????Toolbar
    protected void initToolbar() {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("performIdentifierAction",2);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
//                getActivity().finish();
            }
        });
    }
    //  ????????????Appbar????????????????????????????????????????????????????????????
    private void initAppbar() {
        mAppBarLayout.setExpanded(false);
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    // ????????????????????????
    private void initEditContent() {
        mContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                boolean needSendMsg = !TextUtils.isEmpty(content);
                // ??????????????????????????????Icon
                mSubmit.setActivated(needSendMsg);
            }
        });
    }

    // ??????????????????
    private class Adapter extends BaseRecyclerAdapter<Message> {
        @SuppressLint("NewApi")
        @Override
        protected int getItemViewType(int position, Message message) {
            // ??????????????????????????????????????????
             boolean isRight = Objects.equals(message.getSender().getId(), AccountUtil.getUserId());

            switch (message.getType()) {
                // ????????????
                case Message.TYPE_STR:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;

                // ????????????
//                case Message.TYPE_AUDIO:
//                    return isRight ? R.layout.cell_chat_audio_right : R.layout.cell_chat_audio_left;
//
//                // ????????????
//                case Message.TYPE_PIC:
//                    return isRight ? R.layout.cell_chat_pic_right : R.layout.cell_chat_pic_left;

                // ?????????????????????
                default:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
            }
        }

        @Override
        protected ViewHolder<Message> onCreateViewHolder(View root, int viewType) {
            switch (viewType) {
                // ?????????????????????
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

                // ?????????????????????????????????Text?????????Holder????????????
                // ?????????????????????
                default:
                    return new TextHolder(root);
            }
        }
    }

    // ?????????Holder
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

            // ????????????
//            Face.decode(mContent, spannable, (int) Ui.dipToPx(getResources(), 20));

            // ???????????????????????????
            mContent.setText(message.getContent());
        }
    }

    // Holder?????????
    class BaseHolder extends BaseRecyclerAdapter.ViewHolder<Message> {
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;

        // ???????????????????????????????????????
        @Nullable
        @BindView(R.id.loading)
        Loading mLoading;


        public BaseHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            User sender = message.getSender();
            // ??????????????????
            sender.load();
            // ????????????
            mPortrait.setup(Glide.with(getActivity()), sender.getPortrait());

            if (mLoading != null) {
                // ??????????????????????????????
                int status = message.getStatus();
                if (status == Message.STATUS_DONE) {
                    // ????????????, ??????Loading
                    mLoading.stop();
                    mLoading.setVisibility(View.GONE);
                } else if (status == Message.STATUS_CREATED) {
                    // ????????????????????????
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.setProgress(0);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(), R.color.colorAccent));
                    mLoading.start();
                } else if (status == Message.STATUS_FAILED) {
                    // ??????????????????, ??????????????????
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.stop();
                    mLoading.setProgress(1);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(), R.color.alertImportant));
                }

                // ??????????????????????????????????????????
                mPortrait.setEnabled(status == Message.STATUS_FAILED);
            }
        }

        @OnClick(R.id.im_portrait)
        void onRePushClick() {
            // ????????????

            if (mLoading != null && mPresenter.rePush(mData)) {
                // ????????????????????????????????????????????????
                // ???????????????????????????????????????????????????
                updateData(mData);
            }

        }
    }

}
