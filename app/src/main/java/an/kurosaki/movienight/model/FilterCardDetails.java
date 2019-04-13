package an.kurosaki.movienight.model;

public class FilterCardDetails {

    private Double minVoteAverage;
    private Integer minVoteCount;
    private String minReleaseDate;
    private String maxReleaseDate;
    private String withLanguage;
    private String withGenres;
    private String sortBy;

    public FilterCardDetails(Double minVoteAverage, Integer minVoteCount,
                             String minReleaseDate, String maxReleaseDate,
                             String withLanguage, String withGenres, String sortBy) {

        this.minVoteAverage = minVoteAverage;
        this.minVoteCount = minVoteCount;
        this.minReleaseDate = minReleaseDate;
        this.maxReleaseDate = maxReleaseDate;
        this.withLanguage = withLanguage;
        this.withGenres = withGenres;
        this.sortBy = sortBy;
    }

    public Double getMinVoteAverage() {
        return minVoteAverage;
    }

    public Integer getMinVoteCount() {
        return minVoteCount;
    }

    public String getMinReleaseDate() {
        return minReleaseDate;
    }

    public String getMaxReleaseDate() {
        return maxReleaseDate;
    }

    public String getWithLanguage() {
        return withLanguage;
    }

    public String getWithGenres() {
        return withGenres;
    }

    public String getSortBy() {
        return sortBy;
    }
}


