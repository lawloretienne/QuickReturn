package com.etiennelawlor.quickreturn.models;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class FacebookPost {

    // region Member Variables
    private String mAvatarUrl;
    private String mPostImageUrl;
    private String mDisplayName;
    private String mMessage;
    private String mTimestamp;
    private int mCommentCount;
    private int mLikeCount;
    // endregion

    // region Constructors
    public FacebookPost(){
    }
    // endregion

    // region Getters
    public String getAvatarUrl(){
        return mAvatarUrl;
    }

    public String getPostImageUrl() { return mPostImageUrl; }

    public String getDisplayName(){
        return mDisplayName;
    }

    public String getMessage(){
        return mMessage;
    }

    public String getTimestamp(){
        return mTimestamp;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public int getLikeCount() {
        return mLikeCount;
    }
    // endregion

    // region Setters
    public void setAvatarUrl(String avatarUrl){
        mAvatarUrl = avatarUrl;
    }

    public void setPostImageUrl(String postImageUrl){
        mPostImageUrl = postImageUrl;
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

    public void setCommentCount(int commentCount) { mCommentCount = commentCount; }

    public void setLikeCount(int likeCount) { mLikeCount = likeCount; }
    // endregion
}
