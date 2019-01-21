package com.example.reem.newsfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Reem on 20,Jan,2019
 * {@link NewsAdapter} is an {@link ArrayAdapter} that can provide
 * the layout of the NewsItem(s) list based on data source, which is
 * a list of NewsItem objects
 */

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    private static final String PUBLICATION_DATE_SEPARATOR = "T";

    /**
     * Custom constructor
     *
     * @param context      The current context. Used to inflate the layout file.
     * @param newsItemList A List of NewsItem objects to display in a list.
     */
    public NewsAdapter(Context context, List<NewsItem> newsItemList) {
        super(context, 0, newsItemList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_feed_list_item, parent, false);
        }

        // Get the {@link NewsItem} object located at this position in the list
        NewsItem currentNewsItem = getItem(position);

        // Handle Views
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        titleTextView.setText(currentNewsItem.getTitle());

        TextView authorTextView = convertView.findViewById(R.id.authorTextView);
        authorTextView.setText(currentNewsItem.getAuthorName());

        TextView sectionTextView = convertView.findViewById(R.id.sectionTextView);
        sectionTextView.setText(currentNewsItem.getSection());

        // Date&Time formatting(Splitting into 2 strings)
        String dateAndTime = currentNewsItem.getPublicationDate();

        String publicationDate;
        String publicationTime;

        if (dateAndTime.contains(PUBLICATION_DATE_SEPARATOR)) {
            String[] parts = dateAndTime.split(PUBLICATION_DATE_SEPARATOR);
            publicationDate = parts[0];
            publicationTime = parts[1];
        } else {
            publicationDate = getContext().getString(R.string.no_date_available);
            publicationTime = "";
        }

        // Once we have the 2 separate Strings, we can display them in the 2 TextViews in the list item layout.
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        dateTextView.setText(publicationDate);

        TextView timeTextView = convertView.findViewById(R.id.timeTextView);
        timeTextView.setText(publicationTime);

        return convertView;
    }
}
