package an.kurosaki.movienight.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardDetails implements Media {

    @SerializedName("id") private Integer id;
    @SerializedName("original_title") private String originalTitle;
    @SerializedName("original_name") private String originalName;
    @SerializedName("poster_path") private String posterPath;
    @SerializedName("backdrop_path") private String backdropPath;
    @SerializedName("overview") private String overview;
    @SerializedName("release_date") private String releaseDate;
    @SerializedName("first_air_date") private String firstAirDate;
    @SerializedName("vote_average") private String voteAverage;
    @SerializedName("original_language") private String originalLang;
    @SerializedName("genres") private List<Genre> genres;

    public Integer getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }
    public String getOriginalName() {
        return originalName;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getFirstAirDate(){
        return firstAirDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getOriginalLang() {
        return originalLang;
    }

    public List<Genre> getGenres() {
        return genres;
    }

}

