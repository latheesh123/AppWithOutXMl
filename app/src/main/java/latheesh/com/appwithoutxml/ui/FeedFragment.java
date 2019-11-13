package latheesh.com.appwithoutxml.ui;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import latheesh.com.appwithoutxml.R;
import latheesh.com.appwithoutxml.adapter.CustomRecyclerViewAdapter;
import latheesh.com.appwithoutxml.data.RssFeedParser;
import latheesh.com.appwithoutxml.model.DataFeed;

import static latheesh.com.appwithoutxml.utils.Utils.fromHtml;

public class FeedFragment extends Fragment {

 SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        swipeRefreshLayout = new SwipeRefreshLayout(Objects.requireNonNull(getActivity()));
        swipeRefreshLayout.setId(R.id.swipe_refresh);
        swipeRefreshLayout.setLayoutParams(new SwipeRefreshLayout.LayoutParams(SwipeRefreshLayout.LayoutParams.MATCH_PARENT,
                SwipeRefreshLayout.LayoutParams.MATCH_PARENT));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FeedAsyncTask().execute();
            }
        });

        // add the recycler view within the swipe refresh view
        recyclerView = new RecyclerView(getActivity());
        recyclerView.setId(R.id.recycler_view);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.setHorizontalScrollBarEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT));

        //Handle orientation changes
        recyclerView.setLayoutManager(getGridLayoutManager());

        recyclerView.setAdapter(new CustomRecyclerViewAdapter());
        swipeRefreshLayout.addView(recyclerView);

        return swipeRefreshLayout;    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menuItem = menu.findItem(R.id.menu_refresh);
        menuItem.setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        new FeedAsyncTask().execute();
    }

//Populate layout based on devices
    private GridLayoutManager getGridLayoutManager() {
        GridLayoutManager gridLayoutManager;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        float yInches = displayMetrics.heightPixels / displayMetrics.ydpi;
        float xInches = displayMetrics.widthPixels / displayMetrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        //tablets
        if (diagonalInches >= 7.0) {
            gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return 3;
                    }
                    return 1;
                }
            });
        } else {
            //handset
            gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return 2;
                    }
                    return 1;
                }
            });
        }

        return gridLayoutManager;
    }


    @SuppressLint("StaticFieldLeak")
    public class FeedAsyncTask extends AsyncTask<Void,Boolean, List<DataFeed>>
    {
        private RssFeedParser rssFeedParser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(!swipeRefreshLayout.isRefreshing())
            {
                swipeRefreshLayout.setRefreshing(true);
            }
            rssFeedParser=new RssFeedParser();
        }

        @Override
        protected List<DataFeed> doInBackground(Void... voids) {
            String feedUrl= Objects.requireNonNull(getActivity()).getResources().getString(R.string.rss_feed_url);

            try {
                InputStream inputStream=new URL(feedUrl).openStream();
                return rssFeedParser.parse(inputStream);

            } catch (IOException | ParseException | XmlPullParserException e) {
                e.printStackTrace();

            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<DataFeed> dataFeeds) {
            super.onPostExecute(dataFeeds);

            updateRecyclerView(dataFeeds);

            // Toolbar title
            Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
            toolbar.setTitle(fromHtml(rssFeedParser.getFeedTitle()));

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }


    }

    public void refreshContent() {
        new FeedAsyncTask().execute();
    }
    private void updateRecyclerView(final List<DataFeed> feedList) {
        recyclerView.setLayoutManager(getGridLayoutManager());

        recyclerView.setAdapter(new CustomRecyclerViewAdapter(
                getActivity(),
                feedList,
                new CustomRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_main, WebViewFragment.newInstance(feedList.get(position)))
                                .addToBackStack("tag")
                                .commit();
                    }
                }));
    }
}



