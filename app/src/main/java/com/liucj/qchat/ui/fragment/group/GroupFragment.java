package com.liucj.qchat.ui.fragment.group;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.liucj.common.fragment.BaseFragment;
import com.liucj.common.recycler.BaseRecyclerAdapter;
import com.liucj.common.widget.view.EmptyView;
import com.liucj.common.widget.view.PortraitView;
import com.liucj.factory.model.db.Group;
import com.liucj.factory.presenter.PresenterFragment;
import com.liucj.factory.presenter.group.GroupsContract;
import com.liucj.factory.presenter.group.GroupsPresenter;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.activity.MessageActivity;

import butterknife.BindView;

public class GroupFragment extends PresenterFragment<GroupsContract.Presenter>
        implements GroupsContract.View {
    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    // 适配器，User，可以直接从数据库查询数据
    private BaseRecyclerAdapter<Group> mAdapter;

    public GroupFragment() {
        // Required empty public constructor
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    protected GroupsContract.Presenter initPresenter() {
        return new GroupsPresenter(this);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        // 初始化Recycler
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecycler.setAdapter(mAdapter = new BaseRecyclerAdapter<Group>() {
            @Override
            protected int getItemViewType(int position, Group group) {
                // 返回cell的布局id
                return R.layout.cell_group_list;
            }

            @Override
            protected ViewHolder<Group> onCreateViewHolder(View root, int viewType) {
                return new GroupFragment.ViewHolder(root);
            }
        });

        // 点击事件监听
        mAdapter.setListener(new BaseRecyclerAdapter.AdapterListenerImpl<Group>() {
            @Override
            public void onItemClick(BaseRecyclerAdapter.ViewHolder holder, Group group) {
                // 跳转到聊天界面
                MessageActivity.show(getContext(), group);
            }
        });

        // 初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolderView(mEmptyView);

    }

    @Override
    public BaseRecyclerAdapter<Group> getRecyclerAdapter() {
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

    class ViewHolder extends BaseRecyclerAdapter.ViewHolder<Group> {
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.txt_desc)
        TextView mDesc;

        @BindView(R.id.txt_member)
        TextView mMember;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Group group) {
            mPortraitView.setup(Glide.with(getActivity()), group.getPicture());
            mName.setText(group.getName());
            mDesc.setText(group.getDesc());

            if (group.holder != null && group.holder instanceof String) {
                mMember.setText((String) group.holder);
            } else {
                mMember.setText("");
            }
        }

    }
}
