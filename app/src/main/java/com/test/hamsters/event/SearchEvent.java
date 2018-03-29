package com.test.hamsters.event;

public class SearchEvent {

    private String searchText;

    public SearchEvent(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}