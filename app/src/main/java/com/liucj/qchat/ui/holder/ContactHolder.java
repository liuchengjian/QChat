package com.liucj.qchat.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liucj.common.recycler.BaseRecyclerAdapter;
import com.liucj.common.widget.PortraitView;
import com.liucj.factory.db.User;
import com.liucj.qchat.R;
import butterknife.BindView;

public class ContactHolder extends BaseRecyclerAdapter.ViewHolder<User> {
    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;

    @BindView(R.id.txt_name)
    TextView mName;

    @BindView(R.id.txt_desc)
    TextView mDesc;


    public ContactHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(User user) {
//        mPortraitView.setup(Glide.with(ContactFragment.this), user);
//        mName.setText(user.getName());
//        mDesc.setText(user.getDesc());
    }

}