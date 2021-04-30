package com.liucj.factory.bean;

import com.liucj.common.DataSource;
import com.liucj.common.DbDataSource;
import com.liucj.factory.presenter.BaseContract;
import com.liucj.factory.presenter.BaseRecyclerPresenter;


import java.util.List;

/**
 * 基础的仓库源的Presenter定义
 *
 */
public abstract class BaseSourcePresenter<Data, ViewModel,
        Source extends DbDataSource<Data>,
        View extends BaseContract.RecyclerView>
        extends BaseRecyclerPresenter<ViewModel, View>
        implements DataSource.SucceedCallback<List<Data>> {

    protected Source mSource;

    public BaseSourcePresenter(Source source, View view) {
        super(view);
        this.mSource = source;
    }

    @Override
    public void start() {
        super.start();
        if (mSource != null)
            mSource.load(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        mSource.dispose();
        mSource = null;
    }
}
