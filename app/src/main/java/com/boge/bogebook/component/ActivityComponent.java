package com.boge.bogebook.component;

import com.boge.bogebook.module.ActivityModule;
import com.boge.bogebook.mvp.ui.activity.BookDetailActivity;
import com.boge.bogebook.mvp.ui.activity.BookListDetailActivity;
import com.boge.bogebook.mvp.ui.activity.CategortBookActivity;
import com.boge.bogebook.mvp.ui.activity.CategoryActivity;
import com.boge.bogebook.mvp.ui.activity.LocalBookActivity;
import com.boge.bogebook.mvp.ui.activity.MainActivity;
import com.boge.bogebook.mvp.ui.activity.RankDetailActivity;
import com.boge.bogebook.mvp.ui.activity.RankingActivity;
import com.boge.bogebook.mvp.ui.activity.ReaderActivity;
import com.boge.bogebook.mvp.ui.activity.SearchActivity;
import com.boge.bogebook.mvp.ui.activity.ThemeBookActivity;

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

    void inject(LocalBookActivity activity);

    void inject(ReaderActivity readerActivity);

    void inject(SearchActivity activity);

    void inject(ThemeBookActivity activity);

    void inject(BookListDetailActivity activity);
}