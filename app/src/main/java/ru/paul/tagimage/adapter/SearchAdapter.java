package ru.paul.tagimage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.paul.tagimage.Constants;
import ru.paul.tagimage.R;
import ru.paul.tagimage.model.Post;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Post> posts;

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nickname.setText(posts.get(position).getAuthor().getNickname());
        holder.nickname_caption.setText(posts.get(position).getAuthor().getNickname());
//        holder.age.setText(String.valueOf(posts.get(position).getAuthor().getAge()));
        holder.caption.setText(posts.get(position).getCaption());
        holder.data.setText(posts.get(position).getDate().toString());

        Picasso.get()
                .load(Constants.BASE_URL + Constants.IMAGE_URL + posts.get(position).getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        else
            return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
