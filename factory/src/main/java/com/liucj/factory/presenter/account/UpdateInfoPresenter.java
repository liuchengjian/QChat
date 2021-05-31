package com.liucj.factory.presenter.account;

import android.text.TextUtils;

import com.liucj.common.factory.data.DataSource;
import com.liucj.factory.Factory;
import com.liucj.factory.R;
import com.liucj.factory.data.helper.UserHelper;
import com.liucj.factory.model.api.account.UserUpdateModel;
import com.liucj.factory.model.card.UserCard;
import com.liucj.factory.model.db.User;
import com.liucj.factory.net.UploadHelper;
import com.liucj.factory.presenter.base.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View>
        implements UpdateInfoContract.Presenter, DataSource.Callback<UserCard>{
    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }
    @Override
    public void update(final String photoFilePath, final String desc, final boolean isMan) {
        start();

        final UpdateInfoContract.View view = getView();

        if (TextUtils.isEmpty(photoFilePath) || TextUtils.isEmpty(desc)) {
            view.showError(R.string.data_account_update_invalid_parameter);
        } else {
            // 上传头像
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    String url = UploadHelper.uploadPortrait(photoFilePath);
                    if (TextUtils.isEmpty(url)) {
                        Run.onUiAsync(new Action() {
                            @Override
                            public void call() {
                                // 上传失败
                                view.showError(R.string.data_upload_error);
                            }
                        });
                    } else {
                        // 构建Model
                        UserUpdateModel model = new UserUpdateModel("", url, desc,
                                isMan ? User.SEX_MAN : User.SEX_WOMAN);
                        // 进行网络请求，上传
                        UserHelper.update(model, UpdateInfoPresenter.this);
                    }
                }
            });
        }
    }

    @Override
    public void onDataLoaded(UserCard userCard) {
        final UpdateInfoContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.updateSucceed();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final UpdateInfoContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }


}
