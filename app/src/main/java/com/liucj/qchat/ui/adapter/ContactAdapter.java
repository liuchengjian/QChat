package com.liucj.qchat.ui.adapter;

import android.content.Context;
import android.view.View;

import com.liucj.common.fragment.BaseFragment;
import com.liucj.common.recycler.BaseRecyclerAdapter;
import com.liucj.factory.card.UserCard;
import com.liucj.factory.db.User;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.holder.ContactHolder;


public class ContactAdapter extends BaseRecyclerAdapter<User> {
    private Context context;

    public ContactAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected int getItemViewType(int position, User userCard) {
        // 返回cell的布局id
        return R.layout.cell_contact_list;
    }

    @Override
    protected ViewHolder<User> onCreateViewHolder(View root, int viewType) {
        return new ContactHolder(root,context);
    }
}
