package com.giffedup.utils;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by vinayr on 12/3/15.
 */
public class RecentProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.recentprovider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public RecentProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
