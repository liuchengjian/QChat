package com.liucj.factory.presenter.group;

import com.liucj.factory.Factory;
import com.liucj.factory.data.helper.UserHelper;
import com.liucj.factory.model.db.view.UserSampleModel;
import com.liucj.factory.presenter.base.BaseRecyclerPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 群创建界面的Presenter
 */
public class GroupCreatePresenter extends BaseRecyclerPresenter<GroupCreateContract.ViewModel, GroupCreateContract.View>
        implements GroupCreateContract.Presenter {
    public GroupCreatePresenter(GroupCreateContract.View view) {
        super(view);
    }
    @Override
    public void start() {
        super.start();
        // 加载
        Factory.runOnAsync(loader);
    }
    @Override
    public void create(String name, String desc, String picture) {
        GroupCreateContract.View view = getView();
//        view.showLoading();
    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {

    }

    private Runnable loader = new Runnable() {
        @Override
        public void run() {
            List<UserSampleModel> sampleModels = UserHelper.getSampleContact();
            List<GroupCreateContract.ViewModel> models = new ArrayList<>();
            for (UserSampleModel sampleModel : sampleModels) {
                GroupCreateContract.ViewModel viewModel = new GroupCreateContract.ViewModel();
                viewModel.author = sampleModel;
                models.add(viewModel);
            }

            refreshData(models);
        }
    };

}
