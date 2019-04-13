package an.kurosaki.movienight.ui;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import an.kurosaki.movienight.R;
import an.kurosaki.movienight.adapters.MediaAdapter;
import an.kurosaki.movienight.api.RestApi;
import an.kurosaki.movienight.api.RestApiInterface;
import an.kurosaki.movienight.model.FilterCardDetails;
import an.kurosaki.movienight.model.CardDetails;
import an.kurosaki.movienight.model.CardDetailsSource;
import an.kurosaki.movienight.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static an.kurosaki.movienight.api.RestApi.API_KEY;

public class TvShowViewPager extends Fragment {

    private RecyclerView recyclerView;
    private MediaAdapter adapter;
    private List<CardDetails> tvShowsList;
    private boolean isFetchingTvShows = false;
    // Specify the page of results to query.
    // Note: for TMDb the default min page is 1.
    private int currentPage = 1;

    // Required empty public constructor
    public TvShowViewPager() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.fragment_results_movies,
                container,
                false);
        recyclerView = rootView.findViewById(R.id.movie_results_recycler_view);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Utils.isNetworkAvailable(getActivity())) {
            resetResultsPage();
            setupOnScrollListener();
        } else {
            Toast.makeText(getContext(),
                    getString(R.string.no_internet_message),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void resetResultsPage(){
        currentPage = 1;
        loadResultsPage(currentPage);
    }

    private void setupOnScrollListener() {
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                // When the user has 10 items left to be scrolled,
                // load next page of results.
                if ((currentPage > 0) &&
                        (!isFetchingTvShows) &&
                        (firstVisibleItem + visibleItemCount >= totalItemCount - 10)) {
                    loadResultsPage(currentPage + 1);
                }
            }
        });
    }

    public void loadResultsPage(final int page) {
        FilterCardDetails filter = getFilterFromSharedPreferences();
        try {
            RestApiInterface apiService = RestApi.getClient().create(RestApiInterface.class);
            isFetchingTvShows = true;
            Call<CardDetailsSource> call = apiService.getFilteredTvShows(
                    API_KEY,
                    page,
                    filter.getMinVoteAverage(),
                    filter.getMinVoteCount(),
                    filter.getMinReleaseDate(),
                    filter.getMaxReleaseDate(),
                    filter.getWithLanguage(),
                    filter.getWithGenres(),
                    filter.getSortBy());

            if (call != null) {
                call.enqueue(new Callback<CardDetailsSource>() {
                    @Override
                    public void onResponse(@NonNull Call<CardDetailsSource> call,
                                           @NonNull Response<CardDetailsSource> response) {
                        if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {
                            int totalPages = response.body().getTotalPages();
                            if (adapter == null) {
                                tvShowsList = response.body().getResults();
                                adapter = new MediaAdapter(getContext(), tvShowsList);
                                recyclerView.setAdapter(adapter);
                            } else {
                                // If this is a new query, need to clear the adapter first.
                                if (page == 1) adapter.clearData();

                                // Append the new results to the already existing results
                                tvShowsList.addAll(response.body().getResults());
                                adapter.notifyDataSetChanged();
                            }
                            if(currentPage+1 <= totalPages) currentPage ++;
                                // All available pages have been fetched
                            else currentPage = 0;

                            isFetchingTvShows = false;
                        } else {
                            Toast.makeText(getContext(),
                                    "Failed to retrieve response from themoviedb.org",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CardDetailsSource> call,
                                          @NonNull Throwable throwable) {
                        throwable.printStackTrace();
                        isFetchingTvShows = false;
                        Toast.makeText(
                                getContext(),
                                "Failed to retrieve response from themoviedb.org",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private FilterCardDetails getFilterFromSharedPreferences() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getContext());

        String minRatingString = sharedPrefs.getString(
                getString(R.string.pref_tv_show_min_vote_avg_key),
                getString(R.string.pref_tv_show_min_vote_avg_default_value));
        double minRating;
        try {
            minRating = Double.parseDouble(minRatingString);
        } catch (NumberFormatException nfe) {
            minRating = Double.parseDouble(
                    getString(R.string.pref_tv_show_min_vote_avg_default_value));
        }

        String minVoteCountString = sharedPrefs.getString(
                getString(R.string.pref_tv_show_min_vote_count_key),
                getString(R.string.pref_tv_show_min_vote_count_default_value));
        int minVoteCount;
        try {
            minVoteCount = Integer.parseInt(minVoteCountString);
        } catch (NumberFormatException nfe) {
            minVoteCount = Integer.parseInt(
                    getString(R.string.pref_tv_show_min_vote_count_default_value));
        }

        // TODO Validate date format
        String minFirstAirDate = sharedPrefs.getString(
                getString(R.string.pref_tv_show_min_release_date_key),
                getString(R.string.pref_tv_show_min_release_date_default_value));

        String maxFirstAirDate = sharedPrefs.getString(
                getString(R.string.pref_tv_show_max_release_date_key),
                getString(R.string.pref_tv_show_max_release_date_default_value));

        String originalLanguage = sharedPrefs.getString(
                getString(R.string.pref_tv_show_original_language_key),
                getString(R.string.pref_tv_show_original_language_default_value));

        // Get TV Show genre preferences
        final Set<String> prefGenresDefaultValues = new HashSet<>();
        prefGenresDefaultValues.addAll(Arrays.asList(
                getResources().getStringArray(R.array.pref_tv_show_genres_default_values)));
        Set<String> genresSet = sharedPrefs.getStringSet(
                getString(R.string.pref_tv_show_genres_key),prefGenresDefaultValues);
        String withGenres = String.join(", ", genresSet);

        String sortBy = sharedPrefs.getString(
                getString(R.string.pref_tv_show_sort_by_key),
                getString(R.string.pref_tv_show_sort_by_default_value));

        return new FilterCardDetails(
                minRating,
                minVoteCount,
                minFirstAirDate,
                maxFirstAirDate,
                originalLanguage,
                withGenres,
                sortBy);
    }
}

