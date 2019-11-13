package latheesh.com.appwithoutxml.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import latheesh.com.appwithoutxml.R;
import latheesh.com.appwithoutxml.model.DataFeed;

public class WebViewFragment extends Fragment {
    private static final String ARTICLE_URL = "ARTICLE_URL";
    private static final String ARTICLE_TITLE = "ARTICLE_TITLE";

    private String articleUrl;
    private String articleTitle;


    public WebViewFragment() {
    }

     static WebViewFragment newInstance(DataFeed feed) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARTICLE_TITLE, feed.getTitle());
        args.putString(ARTICLE_URL, feed.getLink());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            articleUrl = getArguments().getString(ARTICLE_URL);
            articleTitle = getArguments().getString(ARTICLE_TITLE);
        } else {
            articleUrl = "";
            articleTitle = "";
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        //Set toolbar title
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(articleTitle);

        WebView webView = new WebView(getActivity());
        webView.setId(R.id.wv_web_view);
        webView.setPadding(
                (int) getActivity().getResources().getDimension(R.dimen.margin),
                (int) getActivity().getResources().getDimension(R.dimen.margin),
                (int) getActivity().getResources().getDimension(R.dimen.margin),
                (int) getActivity().getResources().getDimension(R.dimen.margin));
        webView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        webView.getSettings().setJavaScriptEnabled(true);

        String displayMode = getActivity().getResources().getString(R.string.web_view_display_mode);

        if (articleUrl != null) {
            String webViewUrl = articleUrl + "?" + displayMode;
            webView.loadUrl(webViewUrl);
        }

        return webView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem menuItem = menu.findItem(R.id.menu_refresh);
        menuItem.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }
}
