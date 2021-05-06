package com.liucj.qchat.ui.fragment.search;

import com.liucj.common.fragment.BaseFragment;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.activity.SearchActivity;

public class SearchGroupFragment extends BaseFragment implements SearchActivity.SearchFragment{
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void search(String content) {
    }
}
