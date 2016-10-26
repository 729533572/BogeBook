package com.boge.bogebook.component;

import com.boge.bogebook.module.ActivityModule;
import com.boge.bogebook.mvp.ui.activity.BookDetailActivity;
import com.boge.bogebook.mvp.ui.activity.CategortBookActivity;
import com.boge.bogebook.mvp.ui.activity.CategoryActivity;
import com.boge.bogebook.mvp.ui.activity.MainActivity;
import com.boge.bogebook.mvp.ui.activity.RankDetailActivity;
import com.boge.bogebook.mvp.ui.activity.RankingActivity;

import dagger.Component;

/**
 * @author boge
 * @version 1.0
 * @date 2016/10/13
 */

@Component(
        dependencies = ApplicationComponent.class,
        modules = ActivityModule.class
)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(RankingActivity activity);

    void inject(RankDetailActivity activity);

    void inject(BookDetailActivity activity);

    void inject(CategoryActivity activity);

    void inject(CategortBookActivity activity);
}