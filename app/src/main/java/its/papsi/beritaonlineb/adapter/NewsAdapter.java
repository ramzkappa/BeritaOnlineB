package its.papsi.beritaonlineb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import its.papsi.beritaonlineb.R;
import its.papsi.beritaonlineb.model.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private ArrayList<News> data;

    public NewsAdapter(Context context, ArrayList<News> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<News> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_news_item, parent, false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvTitle, tvAuthor, tvCreatedAt;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.iv_image);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvCreatedAt = itemView.findViewById(R.id.tv_created_at);
        }

        public void bind(News news) {
            tvTitle.setText(news.getTitle());
            tvAuthor.setText(news.getAuthor());
            tvCreatedAt.setText(news.getCreatedAt());
        }
    }
}
