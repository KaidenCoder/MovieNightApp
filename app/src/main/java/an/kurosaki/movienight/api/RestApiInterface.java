package an.kurosaki.movienight.api;

import an.kurosaki.movienight.model.CardDetailsSource;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiInterface {

    // Define the Endpoints using special retrofit annotations to encode details
    // about the parameters and request method. Note that the return value is
    // always a parameterized Call<T> object.
    @GET("discover/movie")
    Call<CardDetailsSource> getFilteredMovies(@Query("api_key") String apiKey,
                                              @Query("page") Integer page,
                                              @Query("vote_average.gte") Double minVoteAverage,
                                              @Query("vote_count.gte") Integer minVoteCount,
                                              @Query("primary_release_date.gte") String minReleaseDate,
                                              @Query("primary_release_date.lte") String maxReleaseDate,
                                              @Query("with_original_language") String language,
                                              @Query("with_genres") String genresIds,
                                              @Query("sort_by") String sortMethod);

    @GET("discover/tv")
    Call<CardDetailsSource> getFilteredTvShows(@Query("api_key") String apiKey,
                                               @Query("page") Integer page,
                                               @Query("vote_average.gte") Double voteAverage,
                                               @Query("vote_count.gte") Integer voteCount,
                                               @Query("first_air_date.gte") String minFirstAirDate,
                                               @Query("first_air_date.lte") String maxFirstAirDate,
                                               @Query("with_original_language") String language,
                                               @Query("with_genres") String genresIds,
                                               @Query("sort_by") String sortMethod);
}

