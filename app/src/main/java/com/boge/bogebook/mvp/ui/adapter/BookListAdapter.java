package com.boge.bogebook.mvp.ui.adapter;

import android.content.Context;

import com.boge.bogebook.R;
import com.boge.bogebook.common.Constant;
import com.boge.bogebook.entity.BookLists;
import com.boge.bogebook.mvp.ui.adapter.base.BaseRecyclerViewAdapter;
import com.boge.bogebook.mvp.ui.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 书单列表适配器
 * @author boge
 * @version 1.0
 * @date 2016/11/9
 */

public class BookListAdapter extends BaseRecyclerViewAdapter<BookLists.BookListsBean> {

    public BookListAdapter(Context mContext, List<BookLists.BookListsBean> mList) {
        super(mContext, mList, R.layout.item_book_list);
    }

    @Override
    protected void onBindData(BaseViewHolder holder, int position, BookLists.BookListsBean item) {
        holder.setText(R.id.tv_book_title, item.getTitle());
        holder.setText(R.id.tv_author_cat, item.getAuthor());
        holder.setText(R.id.tv_shortIntro, item.getDesc());
        holder.setText(R.id.tv_follower_ratio, "共"+item.getBookCount()+
                "本书  |  "+item.getCollectorCount()+"人收藏");
        holder.setImageUrl(R.id.iv_book_icon, Constant.IMG_BASE_URL+item.getCover(), R.mipmap.cover_default);
    }
}
