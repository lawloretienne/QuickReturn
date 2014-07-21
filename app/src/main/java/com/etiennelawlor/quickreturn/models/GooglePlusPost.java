package com.etiennelawlor.quickreturn.models;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class GooglePlusPost {

    // region Member Variables
    private String mAvatarUrl;
    private String mPostImageUrl;
    private String mCommenterOneAvatarUrl;
    private String mCommenterTwoAvatarUrl;
    private String mCommenterThreeAvatarUrl;
    private String mDisplayName;
    private String mMessage;
    private String mTimestamp;
//    private int mRetweetCount;
    private int mStarCount;
    // endregion

    // region Constructors
    public GooglePlusPost(){
    }
    // endregion

    // region Getters
    public String getAvatarUrl(){
        return mAvatarUrl;
    }

    public String getPostImageUrl() { return mPostImageUrl; }

    public String getCommenterOneAvatarUrl() { return mCommenterOneAvatarUrl; }

    public String getCommenterTwoAvatarUrl() { return mCommenterTwoAvatarUrl; }

    public String getCommenterThreeAvatarUrl() { return mCommenterThreeAvatarUrl; }

    public String getDisplayName(){
        return mDisplayName;
    }

    public String getMessage(){
        return mMessage;
    }

    public String getTimestamp(){
        return mTimestamp;
    }

//    public int getRetweetCount(){
//        return mRetweetCount;
//    }

    public int getStarCount(){
        return mStarCount;
    }
    // endregion

    // region Setters
    public void setAvatarUrl(String avatarUrl){
        mAvatarUrl = avatarUrl;
    }

    public void setPostImageUrl(String postImageUrl){
        mPostImageUrl = postImageUrl;
    }

    public void setCommenterOneAvatarUrl(String avatarUrl){
        mCommenterOneAvatarUrl = avatarUrl;
    }

    public void setCommenterTwoAvatarUrl(String avatarUrl){
        mCommenterTwoAvatarUrl = avatarUrl;
    }

    public void setCommenterThreeAvatarUrl(String avatarUrl){
        mCommenterThreeAvatarUrl = avatarUrl;
    }

    public void setDisplayName(String displayName){
        mDisplayName = displayName;
    }

    public void setMessage(String message){
        mMessage = message;
    }

    public void setTimestamp(String timestamp){
        mTimestamp = timestamp;
    }

//    public void setRetweetCount(int retweetCount){
//        mRetweetCount = retweetCount;
//    }

    public void setStarCount(int starCount){
        mStarCount = starCount;
    }
    // endregion
}
