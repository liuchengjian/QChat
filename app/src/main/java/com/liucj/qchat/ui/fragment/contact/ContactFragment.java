package com.liucj.qchat.ui.fragment.contact;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.liucj.common.fragment.BaseFragment;
import com.liucj.common.recycler.BaseRecyclerAdapter;
import com.liucj.common.widget.PortraitView;
import com.liucj.common.widget.convention.EmptyView;
import com.liucj.factory.card.UserCard;
import com.liucj.factory.db.User;
import com.liucj.factory.presenter.PresenterFragment;
import com.liucj.factory.presenter.contact.ContactContract;
import com.liucj.factory.presenter.contact.ContactPresenter;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.activity.MessageActivity;
import com.liucj.qchat.ui.activity.PersonalActivity;
import com.liucj.qchat.ui.adapter.ContactAdapter;

import butterknife.BindView;
import butterknife.OnClick;

public class ContactFragment extends PresenterFragment<ContactContract.Presenter>
        implements ContactContract.View{

    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    // 适配器，User，可以直接从数据库查询数据
    private ContactAdapter mAdapter;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        // 初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter= new ContactAdapter(getContext());
        mRecycler.setAdapter(mAdapter);
        // 点击事件监听
        mAdapter.setListener(new BaseRecyclerAdapter.AdapterListenerImpl<User>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter.ViewHolder holder, User user) {
                // 跳转到聊天界面
                MessageActivity.show(getContext(), user);
//                PersonalActivity.show(getContext(), user.getId());
            }
        });

        // 初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolderView(mEmptyView);

    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        // 进行一次数据加载
        mPresenter.start();
    }

    @Override
    protected ContactContract.Presenter initPresenter() {
        // 初始化Presenter
        return new ContactPresenter(this);
    }

    @Override
    public BaseRecyclerAdapter<User> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        // 进行界面操作
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }


    class ViewHolder extends BaseRecyclerAdapter.ViewHolder<User> {
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.txt_desc)
        TextView mDesc;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(User user) {
            Glide.with(getContext()).load(user.getPortrait()).into(mPortraitView);
            mName.setText(user.getName());
            mDesc.setText(user.getDesc());
            mPortraitView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PersonalActivity.show(getContext(), mData.getId());
                }
            });
        }
    }
}
