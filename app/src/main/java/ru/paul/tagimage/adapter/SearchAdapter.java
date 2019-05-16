package ru.paul.tagimage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.paul.tagimage.Constants;
import ru.paul.tagimage.OnLoadMoreListener;
import ru.paul.tagimage.R;
import ru.paul.tagimage.model.Post;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<Post> posts;
    private RecyclerView recyclerView;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public SearchAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.OnLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return posts.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);

            vh = new SearchViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SearchViewHolder) {

            ((SearchViewHolder)holder).nickname.setText(posts.get(position).getAuthor().getNickname());
            ((SearchViewHolder)holder).nickname_caption.setText(posts.get(position).getAuthor().getNickname());
//        holder.age.setText(String.valueOf(posts.get(position).getAuthor().getAge()));
            ((SearchViewHolder)holder).caption.setText(posts.get(position).getCaption());
            ((SearchViewHolder)holder).data.setText(posts.get(position).getDate().toString());

            Picasso.get()
                    .load(Constants.BASE_URL + Constants.IMAGE_URL + posts.get(position).getImage())
                    .into(((SearchViewHolder)holder).image);
        } else {
            ((SearchAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        else
            return posts.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.search_item_nickname)
        TextView nickname;
//        @BindView(R.id.search_item_age)
//        TextView age;
        @BindView(R.id.search_item_nickname_caption)
        TextView nickname_caption;
        @BindView(R.id.search_item_caption)
        TextView caption;
        @BindView(R.id.search_item_data)
        TextView data;
        @BindView(R.id.search_item_image)
        AppCompatImageView image;

        SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progressBar1)
        ProgressBar progressBar;

        ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

}
