package com.qr.library.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class QuickAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_TYPE = 0x00000111;
    private static final int CONTENT_TYPE = 0x00000222;
    private static final int FOOTER_TYPE = 0x00000333;

    private List<T> mData;
    private int mContentResId;
    private int mHeaderResId;
    private int mFooterResId;

    public QuickAdapter(@LayoutRes int mContentResId) {
        this(mContentResId, -1, -1, null);
    }

    public QuickAdapter(@LayoutRes int mContentResId, @Nullable List<T> mData) {
        this(mContentResId, -1, -1, mData);
    }

    public QuickAdapter(@LayoutRes int mContentResId, @LayoutRes int mHeaderResId, @LayoutRes int mFooterResId, @Nullable List<T> mData) {
        this.mData = mData == null ? new ArrayList<T>() : mData;
        this.mContentResId = mContentResId;
        this.mHeaderResId = mHeaderResId;
        this.mFooterResId = mFooterResId;
    }

    public void setNewData(@Nullable List<T> data) {
        this.mData = data == null ? new ArrayList<>() : data;
        notifyDataSetChanged();
    }

    public void addData(@NonNull T data) {
        this.mData.add(data);
        notifyItemInserted(this.mData.size() + getHeaderCount());
    }

    public void addData(@IntRange(from = 0) int position, @NonNull T data) {
        this.mData.add(position, data);
        notifyItemInserted(position + getHeaderCount());
    }

    public void remove(@IntRange(from = 0) int position) {
        if (position >= 0 && position < mData.size()) {
            mData.remove(position);
            int internalPosition = position + getHeaderCount();
            notifyItemRemoved(internalPosition);
            notifyItemRangeChanged(internalPosition, mData.size() - internalPosition);
        }
    }

    public void setData(@IntRange(from = 0) int position, @NonNull T data) {
        if (position >= 0 && position < mData.size()) {
            mData.set(position, data);
            notifyItemChanged(position + getHeaderCount());
        }
    }

    public void addData(@NonNull Collection<? extends T> collection) {
        mData.addAll(collection);
        notifyItemRangeInserted(mData.size() - collection.size() + getHeaderCount(), collection.size());
    }

    public void addData(@IntRange(from = 0) int position, @NonNull Collection<? extends T> collection) {
        if (position >= 0 && position < mData.size()) {
            mData.addAll(position, collection);
            notifyItemRangeInserted(position + getHeaderCount(), collection.size());
        }
    }

    @NonNull
    public List<T> getData() {
        return mData;
    }

    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if (position >= 0 && position < mData.size()) {
            return mData.get(position);
        } else {
            return null;
        }
    }

    public void setHeaderView(@LayoutRes int resourceId) {
        if(mHeaderResId == resourceId){
            return;
        }

        int oldHeader = mHeaderResId;
        mHeaderResId = resourceId;
        if (oldHeader == -1) {
            // 添加
            notifyItemInserted(0);
        } else {
            notifyItemChanged(0);
        }
    }

    public void removeHeaderView() {
        if (mHeaderResId != -1) {
            mHeaderResId = -1;
            notifyItemRemoved(0);
            notifyItemRangeChanged(0, mData.size());
        }
    }

    public void setFooterView(@LayoutRes int resourceId) {
        if(mFooterResId == resourceId){
            return;
        }

        int oldFooter = mFooterResId;
        mFooterResId = resourceId;
        if (oldFooter == -1) {
            // 添加
            notifyItemInserted(getHeaderCount() + mData.size());
        } else {
            notifyItemChanged(getHeaderCount() + mData.size());
        }
    }

    public void removeFooterView() {
        if (mFooterResId != -1) {
            mFooterResId = -1;
            notifyItemRemoved(getHeaderCount() + mData.size());
        }
    }


    @Override
    public int getItemCount() {
        return mData.size() + getHeaderCount() + getFooterCount();
    }

    @Override
    public int getItemViewType(int position) {
        int headerCount = getHeaderCount();
        int size = mData.size();
        if (position < headerCount) {
            return HEADER_TYPE;
        } else if (position < headerCount + size) {
            return CONTENT_TYPE;
        } else {
            return FOOTER_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(mHeaderResId, parent, false);
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);
            if (onHeaderClickListener != null) {
                headerViewHolder.itemView.setOnClickListener(v -> {
                    int adapterPosition = headerViewHolder.getAdapterPosition();
                    if (adapterPosition == RecyclerView.NO_POSITION) {
                        return;
                    }
                    onHeaderClickListener.onHeaderClick(this, headerViewHolder.itemView, 0);
                });
            }
            if (onHeaderLongClickListener != null) {
                headerViewHolder.itemView.setOnLongClickListener(v -> {
                    int adapterPosition = headerViewHolder.getAdapterPosition();
                    if (adapterPosition == RecyclerView.NO_POSITION) {
                        return false;
                    }
                    return onHeaderLongClickListener.onHeaderLongClick(this, headerViewHolder.itemView, 0);
                });
            }
            return headerViewHolder;
        } else if (viewType == FOOTER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(mFooterResId, parent, false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(view);
            if (onFooterClickListener != null) {
                footerViewHolder.itemView.setOnClickListener(v -> {
                    int adapterPosition = footerViewHolder.getAdapterPosition();
                    if (adapterPosition == RecyclerView.NO_POSITION) {
                        return;
                    }
                    onFooterClickListener.onFooterClick(this, footerViewHolder.itemView, 0);
                });
            }
            if (onFooterLongClickListener != null) {
                footerViewHolder.itemView.setOnLongClickListener(v -> {
                    int adapterPosition = footerViewHolder.getAdapterPosition();
                    if (adapterPosition == RecyclerView.NO_POSITION) {
                        return false;
                    }
                    return onFooterLongClickListener.onFooterLongClick(this, footerViewHolder.itemView, 0);
                });
            }
            return footerViewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(mContentResId, parent, false);
            ContentViewHolder contentViewHolder = new ContentViewHolder(view);
            if (onItemClickListener != null) {
                contentViewHolder.itemView.setOnClickListener(v -> {
                    int adapterPosition = contentViewHolder.getAdapterPosition();
                    if (adapterPosition == RecyclerView.NO_POSITION) {
                        return;
                    }
                    onItemClickListener.onItemClick(this, contentViewHolder.itemView, adapterPosition - getHeaderCount());
                });
            }

            if (onItemLongClickListener != null) {
                contentViewHolder.itemView.setOnLongClickListener(v -> {
                    int adapterPosition = contentViewHolder.getAdapterPosition();
                    if (adapterPosition == RecyclerView.NO_POSITION) {
                        return false;
                    }
                    return onItemLongClickListener.onItemLongClick(this, contentViewHolder.itemView, adapterPosition - getHeaderCount());
                });
            }
            return contentViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == HEADER_TYPE && onHeaderClickListener != null) {
            onHeaderConvertListener.onHeaderConvert((HeaderViewHolder) holder);
        } else if (holder.getItemViewType() == CONTENT_TYPE && onContentConvertListener != null) {
            onContentConvertListener.onContentConvert((ContentViewHolder) holder, mData.get(position - getHeaderCount()));
        } else if (holder.getItemViewType() == FOOTER_TYPE && onFooterConvertListener != null) {
            onFooterConvertListener.onFooterConvert((FooterViewHolder) holder);
        }
    }

    private int getHeaderCount() {
        return mHeaderResId == -1 ? 0 : 1;
    }

    private int getFooterCount() {
        return mFooterResId == -1 ? 0 : 1;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(QuickAdapter quickAdapter, View view, int position);
    }

    private OnHeaderClickListener onHeaderClickListener;

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        this.onHeaderClickListener = onHeaderClickListener;
    }

    public OnHeaderClickListener getOnHeaderClickListener() {
        return onHeaderClickListener;
    }

    public interface OnHeaderClickListener {
        void onHeaderClick(QuickAdapter quickAdapter, View view, int position);
    }

    private OnFooterClickListener onFooterClickListener;

    public void setOnFooterClickListener(OnFooterClickListener onFooterClickListener) {
        this.onFooterClickListener = onFooterClickListener;
    }

    public OnFooterClickListener getOnFooterClickListener() {
        return onFooterClickListener;
    }

    public interface OnFooterClickListener {
        void onFooterClick(QuickAdapter quickAdapter, View view, int position);
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(QuickAdapter quickAdapter, View view, int position);
    }

    private OnHeaderLongClickListener onHeaderLongClickListener;

    public void setOnHeaderLongClickListener(OnHeaderLongClickListener onHeaderLongClickListener) {
        this.onHeaderLongClickListener = onHeaderLongClickListener;
    }

    public OnHeaderLongClickListener getOnHeaderLongClickListener() {
        return onHeaderLongClickListener;
    }

    public interface OnHeaderLongClickListener {
        boolean onHeaderLongClick(QuickAdapter quickAdapter, View view, int position);
    }

    private OnFooterLongClickListener onFooterLongClickListener;

    public void setOnFooterLongClickListener(OnFooterLongClickListener onFooterLongClickListener) {
        this.onFooterLongClickListener = onFooterLongClickListener;
    }

    public OnFooterLongClickListener getOnFooterLongClickListener() {
        return onFooterLongClickListener;
    }

    public interface OnFooterLongClickListener {
        boolean onFooterLongClick(QuickAdapter quickAdapter, View view, int position);
    }

    private OnHeaderConvertListener onHeaderConvertListener;

    public OnHeaderConvertListener getOnHeaderConvertListener() {
        return onHeaderConvertListener;
    }

    public void setOnHeaderConvertListener(OnHeaderConvertListener onHeaderConvertListener) {
        this.onHeaderConvertListener = onHeaderConvertListener;
    }

    public interface OnHeaderConvertListener {
        void onHeaderConvert(HeaderViewHolder headerViewHolder);
    }

    private OnFooterConvertListener onFooterConvertListener;

    public void setOnFooterConvertListener(OnFooterConvertListener onFooterConvertListener) {
        this.onFooterConvertListener = onFooterConvertListener;
    }

    public OnFooterConvertListener getOnFooterConvertListener() {
        return onFooterConvertListener;
    }

    public interface OnFooterConvertListener {
        void onFooterConvert(FooterViewHolder footerViewHolder);
    }

    private OnContentConvertListener<T> onContentConvertListener;

    public void setOnContentConvertListener(OnContentConvertListener<T> onContentConvertListener) {
        this.onContentConvertListener = onContentConvertListener;
    }

    public OnContentConvertListener<T> getOnContentConvertListener() {
        return onContentConvertListener;
    }

    public interface OnContentConvertListener<T> {
        void onContentConvert(ContentViewHolder contentViewHolder, T item);
    }

    public static class HeaderViewHolder extends ViewHolder {

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class FooterViewHolder extends ViewHolder {

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ContentViewHolder extends ViewHolder {
        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> views;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            views = new SparseArray<>();
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(@IdRes int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }
    }
}
