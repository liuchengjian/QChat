package com.liucj.qchat.ui.adapter;

import android.view.View;

import com.liucj.common.recycler.BaseRecyclerAdapter;
import com.liucj.factory.db.User;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.fragment.contact.ContactFragment;
import com.liucj.qchat.ui.holder.ContactHolder;


public class ContactAdapter extends BaseRecyclerAdapter<User> {

    @Override
    protected int getItemViewType(int position, User userCard) {
        // 返回cell的布局id
        return R.layout.cell_contact_list;
    }

    @Override
    protected ViewHolder<User> onCreateViewHolder(View root, int viewType) {
        return new ContactHolder(root);
    }
}
