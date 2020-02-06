
package com.example.moviebuff.ModelClass;

import android.widget.ArrayAdapter;

import java.util.List;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TMDB {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer total_results;
    @SerializedName("total_pages")
    @Expose
    private Integer total_pages;
    @SerializedName("results")
    @Expose
    private List<MovieClass> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return total_results;
    }

    public void setTotalResults(Integer totalResults) {
        this.total_results = totalResults;
    }

    public Integer getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(Integer totalPages) {
        this.total_pages = totalPages;
    }

    public List<MovieClass> getResults() {
        return results;
    }

    public void setResults(List<MovieClass> results) {
        this.results = results;
    }

}
