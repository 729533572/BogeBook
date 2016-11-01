package com.boge.bogebook.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.boge.bogebook.BookApplication;
import com.boge.bogebook.R;
import com.boge.bogebook.common.Constant;
import com.boge.bogebook.dbmanager.LARBManager;
import com.boge.bogebook.listener.OnRecyclerViewItemClick;
import com.boge.bogebook.listener.OnRecyclerViewLongItemClick;
import com.boge.bogebook.util.Tools;
import com.boge.entity.LocalAndRecomendBook;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author boge
 * @version 1.0
 * @date 2016/10/14
 */

public class RecommendAdapter extends RecyclerView.Adapter {
    private final static int NET_TYPE = 0;
    private final static int LOCAL_TYPE = 1;

    private List<LocalAndRecomendBook> recommendBooks;

    /***是否是批量操作*/
    private boolean isBatch = false;

    public void setRecommendBooks(List<LocalAndRecomendBook> recommendBooks) {
        this.recommendBooks = recommendBooks;
        notifyDataSetChanged();
    }

    public List<LocalAndRecomendBook> getRecommendBooks() {
        if(recommendBooks == null)return new ArrayList<LocalAndRecomendBook>();
        return recommendBooks;
    }

    public void setBatch(boolean batch) {
        isBatch = batch;
        notifyDataSetChanged();
    }

    public void setchoiceAll(boolean choice){
        for (LocalAndRecomendBook book : recommendBooks){
            book.setSelect(choice);
        }
        notifyDataSetChanged();
    }

    public List<LocalAndRecomendBook> getChoiceBook(){
        List<LocalAndRecomendBook> books = new ArrayList<LocalAndRecomendBook>();
        for (LocalAndRecomendBook book : recommendBooks){
            if(book.isSelect()){
                books.add(book);
            }
        }
        return books;
    }

    public List<Integer> getPosition(){
        List<Integer> positions = new ArrayList<Integer>();
        for (int i = 0 ; i < recommendBooks.size() ; i++){
            if(recommendBooks.get(i).isSelect()){
                positions.add(i);
            }
        }
        return positions;
    }
    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(recommendBooks.get(position).getBookId())) {
            return LOCAL_TYPE;
        } else {
            return NET_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent, false);
        final RecommendViewHolder recommendViewHolder = new RecommendViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onRecyclerViewItemClick != null){
                    if (isBatch){
                        recommendViewHolder.checkBox.setChecked(!recommendViewHolder.checkBox.isChecked());
                        recommendBooks.get(recommendViewHolder.getLayoutPosition()).setSelect(recommendViewHolder.checkBox.isChecked());
                        Log.i("test" , recommendViewHolder.checkBox.isChecked()+"--");
                    }else{
                        if(!recommendBooks.get(recommendViewHolder.getLayoutPosition()).getIsLocal()){
                            recommendViewHolder.iv_not_read.setVisibility(View.GONE);
                            recommendBooks.get(recommendViewHolder.getLayoutPosition()).setHasUp(false);
                            LARBManager.updateBook(recommendBooks.get(recommendViewHolder.getLayoutPosition()));
                        }
                    }
                    onRecyclerViewItemClick.onItemClick(view , recommendViewHolder.getLayoutPosition());
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(onRecyclerViewLongItemClick != null){
                    onRecyclerViewLongItemClick.onLongItemClcik(view , recommendViewHolder.getLayoutPosition());
                }
                return false;
            }
        });
        return recommendViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof RecommendViewHolder){
            RecommendViewHolder viewHolder = (RecommendViewHolder) holder;
            if(recommendBooks.get(position).getIsLocal()){
                viewHolder.tvBookTitle.setText(recommendBooks.get(position).getTitle());
                viewHolder.tvLastChapter.setText(Tools.longToSize(recommendBooks.get(position).getSize()));
                viewHolder.ivTxtIcon.setImageResource(R.mipmap.home_shelf_txt_icon);
                viewHolder.iv_not_read.setVisibility(View.GONE);
            }else{
                viewHolder.tvBookTitle.setText(recommendBooks.get(position).getTitle());
                viewHolder.tvLastChapter.setText(recommendBooks.get(position).getLastChapter());
                Glide.with(BookApplication.getmContext())
                        .load(Constant.IMG_BASE_URL+recommendBooks.get(position).getCover())
                        .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .format(DecodeFormat.PREFER_RGB_565)
                        .into(viewHolder.ivTxtIcon);
                if(!recommendBooks.get(position).getHasUp()){
                    viewHolder.iv_not_read.setVisibility(View.GONE);
                } else {
                    viewHolder.iv_not_read.setVisibility(View.VISIBLE);
                }
            }
            if(isBatch){
                viewHolder.checkBox.setVisibility(View.VISIBLE);
                viewHolder.checkBox.setChecked(recommendBooks.get(position).isSelect());
                viewHolder.iv_not_read.setVisibility(View.GONE);
            }else{
                viewHolder.checkBox.setVisibility(View.GONE);
            }
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    recommendBooks.get(position).setSelect(b);
                    if(onRecyclerViewItemClick != null){
                        if(isBatch)
                        onRecyclerViewItemClick.onItemClick(compoundButton , position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return recommendBooks == null ? 0 : recommendBooks.size();
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_txt_icon)
        ImageView ivTxtIcon;
        @Bind(R.id.tv_bookTitle)
        TextView tvBookTitle;
        @Bind(R.id.tv_lastChapter)
        TextView tvLastChapter;
        @Bind(R.id.iv_not_read)
        ImageView iv_not_read;
        @Bind(R.id.ck_boxSelect)
        CheckBox checkBox;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);

        }
    }

    private OnRecyclerViewItemClick onRecyclerViewItemClick;

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    private OnRecyclerViewLongItemClick onRecyclerViewLongItemClick;

    public void setOnRecyclerViewLongItemClick(OnRecyclerViewLongItemClick onRecyclerViewLongItemClick) {
        this.onRecyclerViewLongItemClick = onRecyclerViewLongItemClick;
    }
}
