package com.project.urlShortener.Model.Request;

import com.project.urlShortener.Enum.AllowedCategories;

public class CreateUrlRequest {

    private String longUrl;

    private AllowedCategories tag;


    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public AllowedCategories getTag() {
        return tag;
    }

    public void setTag(AllowedCategories tag) {
        this.tag = tag;
    }
}
