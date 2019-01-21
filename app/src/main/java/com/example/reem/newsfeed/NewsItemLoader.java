package com.example.reem.newsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

/**
 * Created by Reem on 20,Jan,2019
 */

public class NewsItemLoader extends AsyncTaskLoader<List<NewsItem>> {

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link NewsItemLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public NewsItemLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<NewsItem> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        List<NewsItem> news = QueryUtils.fetchNewsFeed(mUrl);
        return news;
    }
}
