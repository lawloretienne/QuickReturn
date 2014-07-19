package com.etiennelawlor.quickreturn.models;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class Tweet {

    // region Member Variables
    private String mAvatarUrl;
    private String mDisplayName;
    private String mUsername;
    private String mMessage;
    private String mTimestamp;
    private int mRetweetCount;
    private int mStarCount;
    // endregion

    // region Constructors
    public Tweet(){
    }
    // endregion

    // region Getters
    public String getAvatarUrl(){
        return mAvatarUrl;
    }

    public String getDisplayName(){
        return mDisplayName;
    }

    public String getUsername(){
        return mUsername;
    }

    public String getMessage(){
        return mMessage;
    }

    public String getTimestamp(){
        return mTimestamp;
    }

    public int getRetweetCount(){
        return mRetweetCount;
    }

    public int getStarCount(){
        return mStarCount;
    }
    // endregion

    // region Setters
    public void setAvatarUrl(String avatarUrl){
        mAvatarUrl = avatarUrl;
    }

    public void setDisplayName(String displayName){
        mDisplayName = displayName;
    }

    public void setUsername(String username){
        mUsername = username;
    }

    public void setMessage(String message){
        mMessage = message;
    }

    public void setTimestamp(String timestamp){
        mTimestamp = timestamp;
    }

    public void setRetweetCount(int retweetCount){
        mRetweetCount = retweetCount;
    }

    public void setStarCount(int starCount){
        mStarCount = starCount;
    }
    // endregion
}
