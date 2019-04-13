package an.kurosaki.movienight.settings;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

import an.kurosaki.movienight.R;

public class SettingsActivity extends AppCompatPreferenceActivity {

    /**
     * A preference value change listener that updates the preference's
     * summary to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener
            = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String valueString = value.toString();
            if (preference instanceof ListPreference) {
                // Look up the correct display value in the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(valueString);

                // Set the summary to reflect the new value.
                preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
            } else if (preference instanceof EditTextPreference && TextUtils.isEmpty(valueString)) {
                // Ignore EditTextPreference with empty input
                return false;
            } else {
                preference.setSummary(valueString);
            }
            return true;
        }
    };


    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's current value.
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(preference.getContext());

        sBindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                sharedPreferences.getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    protected boolean isValidFragment(String fragmentName) {
        // Stop fragment injection in malicious applications.
        return PreferenceFragment.class.getName().equals(fragmentName)
                || MoviesPreferenceFragment.class.getName().equals(fragmentName)
                || TvShowsPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows movies preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class MoviesPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_movies);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design guidelines.
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_movie_min_vote_avg_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_movie_min_vote_count_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_movie_min_release_date_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_movie_max_release_date_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_movie_original_language_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_movie_sort_by_key)));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows TV Shows preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class TvShowsPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_tv_shows);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_tv_show_min_vote_avg_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_tv_show_min_vote_count_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_tv_show_min_release_date_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_tv_show_max_release_date_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_tv_show_original_language_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_tv_show_sort_by_key)));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}

