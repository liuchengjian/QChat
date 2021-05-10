package com.liucj.qchat.ui.fragment.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.liucj.common.recycler.BaseRecyclerAdapter;
import com.liucj.common.widget.view.EmptyView;
import com.liucj.common.widget.view.PortraitView;
import com.liucj.factory.model.card.GroupCard;
import com.liucj.factory.presenter.PresenterFragment;
import com.liucj.factory.presenter.search.SearchContract;
import com.liucj.factory.presenter.search.SearchGroupPresenter;
import com.liucj.factory.presenter.search.SearchUserPresenter;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.activity.PersonalActivity;
import com.liucj.qchat.ui.activity.SearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchGroupFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchActivity.SearchFragment, SearchContract.GroupView {


    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private BaseRecyclerAdapter<GroupCard> mAdapter;

    public SearchGroupFragment() {
        // Required empty public constructor
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void search(String content) {
        mPresenter.search(content);
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchGroupPresenter(this);
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        // 初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new BaseRecyclerAdapter<GroupCard>() {
            @Override
            protected int getItemViewType(int position, GroupCard userCard) {
                // 返回cell的布局id
                return R.layout.cell_search_group_list;
            }

            @Override
            protected ViewHolder<GroupCard> onCreateViewHolder(View root, int viewType) {
                return new SearchGroupFragment.ViewHolder(root);
            }
        });

        // 初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolderView(mEmptyView);
    }

    @Override
    protected void initData() {
        super.initData();
        // 发起首次搜索
        search("");
    }

    @Override
    public void onSearchDone(List<GroupCard> groupCards) {
        // 数据成功的情况下返回数据
        mAdapter.replace(groupCards);
        // 如果有数据，则是OK，没有数据就显示空布局
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);

    }


    /**
     * 每一个Cell的布局操作
     */
    class ViewHolder extends BaseRecyclerAdapter.ViewHolder<GroupCard> {
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.im_join)
        ImageView mJoin;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(GroupCard groupCard) {
            mPortraitView.setup(Glide.with(getActivity()), groupCard.getPicture());
            mName.setText(groupCard.getName());
            // 加入时间判断是否加入群
            mJoin.setEnabled(groupCard.getJoinAt() == null);
        }

        @OnClick(R.id.im_join)
        void onJoinClick() {
            // 进入创建者的个人界面
            PersonalActivity.show(getContext(), mData.getOwnerId());
        }
    }
}
