package com.liucj.qchat.ui.fragment.contact;


import android.view.View;

import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liucj.common.fragment.BaseFragment;
import com.liucj.common.recycler.BaseRecyclerAdapter;
import com.liucj.common.widget.convention.EmptyView;
import com.liucj.factory.db.User;
import com.liucj.factory.presenter.PresenterFragment;
import com.liucj.factory.presenter.contact.ContactContract;
import com.liucj.factory.presenter.contact.ContactPresenter;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.adapter.ContactAdapter;

import butterknife.BindView;

public class ContactFragment extends PresenterFragment<ContactContract.Presenter>
        implements ContactContract.View{


    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    // 适配器，User，可以直接从数据库查询数据
    private ContactAdapter mAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        // 初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContactAdapter();
        mRecycler.setAdapter(mAdapter);
        // 点击事件监听
        mAdapter.setListener(new BaseRecyclerAdapter.AdapterListenerImpl<User>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter.ViewHolder holder, User user) {
                // 跳转到聊天界面
//                MessageActivity.show(getContext(), user);
            }
        });

        // 初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolderView(mEmptyView);
    }

    @Override
    public BaseRecyclerAdapter<User> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        // 进行一次数据加载
        mPresenter.start();
    }

    @Override
    public void onAdapterDataChanged() {
        // 进行界面操作
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    @Override
    protected ContactContract.Presenter initPresenter() {
        // 初始化Presenter
        return new ContactPresenter(this);
    }
}
