package com.example.reem.newsfeed;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reem on 20,Jan,2019
 * Helper methods related to requesting and receiving earthquake data from  the Guardian API.
 */

public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    /**
     * Query the Guardian data set and return a list of {@link NewsItem} objects.
     */
    public static List<NewsItem> fetchNewsFeed(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link NewsItem}s
        List<NewsItem> newsItems = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link NewsItem}s
        return newsItems;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link NewsItem} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<NewsItem> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding NewsItem(s) to
        List<NewsItem> newsFeedList = new ArrayList<>();

        // Try to parse the JSON response string & Handle exceptions.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject rootJsonObject = new JSONObject(newsJSON);

            // Extract "response" JSONObject
            JSONObject response = rootJsonObject.getJSONObject("response");

            // Extract "results" JSONArray which has the news items
            JSONArray newsItemsJsonArray = response.getJSONArray("results");

            // If there are results in the results array
            // For each newsItem in the Array, create an {@link NewsItem} object
            if (newsItemsJsonArray.length() > 0) {
                // Loop through each result in the array
                for (int i = 0; i < newsItemsJsonArray.length(); i++) {
                    // Get news item JSONObject at position i
                    JSONObject currentNewsItem = newsItemsJsonArray.getJSONObject(i);

                    // Extract "sectionName" String
                    String sectionName = currentNewsItem.getString("sectionName");

                    // Extract "webPublicationDate" which is the data and time
                    String webPublicationDate = currentNewsItem.getString("webPublicationDate");

                    // Extract "webTitle" which is the NewsItem Title
                    String title = currentNewsItem.getString("webTitle");

                    // Extract "webUrl" which is the NewsItem URL
                    String newsItemUrl = currentNewsItem.getString("webUrl");

                    // Extract Author name from the JSONArray "tags"
                    JSONArray tags = currentNewsItem.getJSONArray("tags");

                    String author;
                    if (tags.length() > 0 && !tags.getJSONObject(0).equals(null)) {
                        author = tags.getJSONObject(0).getString("webTitle");
                    } else {
                        author = "Not Available!";
                    }

                    // Create a new {@link NewsItem} object.
                    NewsItem newsItem = new NewsItem(title, sectionName, author, webPublicationDate, newsItemUrl);

                    // Add NewsItem to list of Feed
                    newsFeedList.add((newsItem));
                }
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return NewsFeed list.
        return newsFeedList;
    }
}
