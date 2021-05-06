package com.liucj.qchat.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liucj.common.fragment.BaseFragment;
import com.liucj.common.recycler.BaseRecyclerAdapter;
import com.liucj.common.widget.PortraitView;
import com.liucj.factory.db.User;
import com.liucj.qchat.R;
import butterknife.BindView;

public class ContactHolder extends BaseRecyclerAdapter.ViewHolder<User> {
    private Context context;
    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;

    @BindView(R.id.txt_name)
    TextView mName;

    @BindView(R.id.txt_desc)
    TextView mDesc;


    public ContactHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
    }

    @Override
    protected void onBind(User userCard) {
        mPortraitView.setup(Glide.with(context), userCard.getPortrait());
        mName.setText(userCard.getName());
        mDesc.setText(userCard.getDesc());
    }

}