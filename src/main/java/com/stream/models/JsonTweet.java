package com.stream.models;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;


@JsonSerialize
public class JsonTweet {
    /*
    * message = {"tweetId" : tweetId, "created_at": created_at, "username":username, "userId":userId,
    * "retweetUsername":retweetUsername, "retweetUserId":retweetUserId, "hashtags":hashtags}
    * */
    private String username;
    private Long tweetId;
    private Long created_at;
    private Long userId;
    private String retweetUsername;
    private Long retweetUserId;
    private List<String> hashtags;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRetweetUsername() {
        return retweetUsername;
    }

    public void setRetweetUsername(String retweetUsername) {
        this.retweetUsername = retweetUsername;
    }

    public Long getRetweetUserId() {
        return retweetUserId;
    }

    public void setRetweetUserId(Long retweetUserId) {
        this.retweetUserId = retweetUserId;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }
}
