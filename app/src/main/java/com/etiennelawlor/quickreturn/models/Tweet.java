package com.etiennelawlor.quickreturn.models;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class Tweet {

    // region Member Variables
    String mWord;
    // endregion

    // region Constructors
    public Tweet(String word){
        mWord = word;
    }
    // endregion

    // region Getters
    public String getWord(){
        return mWord;
    }
    // endregion
}
