package com.thousandsunny.kemis.model;

import com.google.gson.annotations.Expose;

/**
 * Created by hallidafykzir on 10/25/2015.
 */
public class Post {

    @Expose
    private String response;

    /**
     *
     * @return
     * The success
     */
    public String getResponse() {
        return response;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setResponse(String success) {
        this.response = response;
    }

}
