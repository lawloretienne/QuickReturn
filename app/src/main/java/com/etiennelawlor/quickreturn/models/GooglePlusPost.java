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
    private String mCommenterOneDisplayName;
    private String mCommenterTwoDisplayName;
    private String mCommenterThreeDisplayName;
    private String mComment;
    private String mDisplayName;
    private String mMessage;
    private String mTimestamp;
    private int mCommentCount;
    private int mPlusOneCount;
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

    public String getCommenterOneDisplayName() { return mCommenterOneDisplayName; }

    public String getCommenterTwoDisplayName() { return mCommenterTwoDisplayName; }

    public String getCommenterThreeDisplayName() { return mCommenterThreeDisplayName; }

    public String getComment() { return mComment; }

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

    public int getPlusOneCount() {
        return mPlusOneCount;
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

    public void setCommenterOneDisplayName(String displayName){
        mCommenterOneDisplayName = displayName;
    }

    public void setCommenterTwoDisplayName(String displayName){
        mCommenterTwoDisplayName = displayName;
    }

    public void setCommenterThreeDisplayName(String displayName){
        mCommenterThreeDisplayName = displayName;
    }

    public void setComment(String comment) { mComment = comment; }

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

    public void setPlusOneCount(int plusOneCount) { mPlusOneCount = plusOneCount; }
    // endregion
}
