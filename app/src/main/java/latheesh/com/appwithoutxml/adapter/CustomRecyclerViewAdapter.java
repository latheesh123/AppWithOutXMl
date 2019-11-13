package latheesh.com.appwithoutxml.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import latheesh.com.appwithoutxml.R;
import latheesh.com.appwithoutxml.model.DataFeed;
import latheesh.com.appwithoutxml.ui.ImageLoaderAsyncTask;
import latheesh.com.appwithoutxml.utils.RowItems;

import static latheesh.com.appwithoutxml.utils.Utils.formatDateTime;
import static latheesh.com.appwithoutxml.utils.Utils.fromHtml;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_FIRST = 1;
    private static final int VIEW_TYPE_REMAINING = 2;

    private Context context;
    private List<DataFeed> dataFeedList;

    private OnItemClickListener onClickListener;

    //Constructor with out params
    public CustomRecyclerViewAdapter() {
        this.context = null;
        this.dataFeedList = new ArrayList<>();
        this.onClickListener = null;
    }

    //constructor with params
    public CustomRecyclerViewAdapter(Context context, List<DataFeed> feedList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataFeedList = feedList;
        this.onClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_FIRST) {
            return new FirstViewHolder(RowItems.getFirstView(context));
        }
        return new DefaultViewHolder(RowItems.getRemainingView(context));

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        DataFeed dataFeed = dataFeedList.get(position);

        FirstViewHolder firstViewHolder;
        DefaultViewHolder defaultViewHolder;

        if (holder.getItemViewType() == VIEW_TYPE_FIRST) {
            firstViewHolder = (FirstViewHolder) holder;

            firstViewHolder.row_title.setText(fromHtml(dataFeed.getTitle()));
            String fullDescription = formatDateTime(dataFeed.getPubDate())
                    + fromHtml("&mdash;")
                    + fromHtml(dataFeed.getDescription());
            firstViewHolder.row_description.setText(fullDescription.trim());
            new ImageLoaderAsyncTask(context, firstViewHolder.row_media_content, dataFeed.getImageUrl()).execute();

            firstViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClick(v, holder.getAdapterPosition());
                }
            });
        } else {
            defaultViewHolder = (DefaultViewHolder) holder;

            defaultViewHolder.row_title.setText(fromHtml(dataFeed.getTitle()));
            new ImageLoaderAsyncTask(context, defaultViewHolder.row_media_content, dataFeed.getImageUrl()).execute();

            defaultViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClick(v, holder.getAdapterPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataFeedList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_FIRST;
        } else {
            return VIEW_TYPE_REMAINING;
        }
    }

    public class FirstViewHolder extends RecyclerView.ViewHolder {
        TextView row_title;
        ImageView row_media_content;
        TextView row_description;
        View view;

        FirstViewHolder(View v) {
            super(v);
            row_title = v.findViewById(R.id.row_title);
            row_media_content = v.findViewById(R.id.row_media_content);
            row_description = v.findViewById(R.id.row_description);
            view = v;
        }
    }

    public class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView row_title;
        ImageView row_media_content;
        View view;

        DefaultViewHolder(View v) {
            super(v);
            row_title = v.findViewById(R.id.row_title);
            row_media_content = v.findViewById(R.id.row_media_content);
            view = v;
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
