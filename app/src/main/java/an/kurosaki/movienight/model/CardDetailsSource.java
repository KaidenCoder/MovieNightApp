package an.kurosaki.movienight.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardDetailsSource {

    @SerializedName("results") private final List<CardDetails> results;
    @SerializedName("total_results") private Integer totalResults;
    @SerializedName("page") private Integer currentPage;
    @SerializedName("total_pages") private Integer totalPages;

    public CardDetailsSource(List<CardDetails> results, Integer currentPage, Integer totalPages) {
        this.results = results;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public List<CardDetails> getResults() {
        return results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
}
