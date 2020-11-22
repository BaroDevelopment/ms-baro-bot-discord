package com.baro.bot.discord.model.api.google;

public class GoogleSearchApiModel {

    private String kind;
    private GoogleSearchUrlModel url;
    private GoogleSearchQueriesModel queries;
    private GoogleSearchContextEntryModel context;
    private GoogleSearchSearchInformationModel searchInformation;
    private GoogleSearchItemsModel[] items;

    public GoogleSearchApiModel() {
    }

    public GoogleSearchApiModel(String kind, GoogleSearchUrlModel url, GoogleSearchQueriesModel queries,
                                GoogleSearchContextEntryModel context,
                                GoogleSearchSearchInformationModel searchInformation, GoogleSearchItemsModel[] items) {
        this.kind = kind;
        this.url = url;
        this.queries = queries;
        this.context = context;
        this.searchInformation = searchInformation;
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public GoogleSearchUrlModel getUrl() {
        return url;
    }

    public void setUrl(GoogleSearchUrlModel url) {
        this.url = url;
    }

    public GoogleSearchQueriesModel getQueries() {
        return queries;
    }

    public void setQueries(GoogleSearchQueriesModel queries) {
        this.queries = queries;
    }

    public GoogleSearchContextEntryModel getContext() {
        return context;
    }

    public void setContext(GoogleSearchContextEntryModel context) {
        this.context = context;
    }

    public GoogleSearchSearchInformationModel getSearchInformation() {
        return searchInformation;
    }

    public void setSearchInformation(GoogleSearchSearchInformationModel searchInformation) {
        this.searchInformation = searchInformation;
    }

    public GoogleSearchItemsModel[] getItems() {
        return items;
    }

    public void setItems(GoogleSearchItemsModel[] items) {
        this.items = items;
    }
}
