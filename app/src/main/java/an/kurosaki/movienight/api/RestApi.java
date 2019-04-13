package an.kurosaki.movienight.api;

import an.kurosaki.movienight.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = BuildConfig.MOVIE_DATABASE_API_KEY;

    // Creating the Retrofit instance. To send network requests to an API, we need
    // to use the Retrofit.Builder class and specify the base URL for the service.

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
