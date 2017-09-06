package com.marklordan.airgead;

import android.support.test.espresso.IdlingResource;

import com.marklordan.airgead.ui.LoginActivity;

/**
 * Created by Mark on 06/09/2017.
 */

public class LoginActivityIdlingResource implements IdlingResource {

    private LoginActivity mActivity;
    private ResourceCallback mCallback;
    private LoginActivity.ProgressListener mProgressListener;

    public LoginActivityIdlingResource(LoginActivity activity){
        this.mActivity = activity;
        mProgressListener = new LoginActivity.ProgressListener(){

            @Override
            public void onProgressShown() {

            }

            @Override
            public void onProgressDismissed() {
                if(mCallback == null){
                    return;
                }
                mCallback.onTransitionToIdle();
            }
        };
        mActivity.setProgressListener(mProgressListener);
    }

    @Override
    public String getName() {
        return "LoginActivityIdlingResource";
    }

    @Override
    public boolean isIdleNow() {
        return !mActivity.isInProgress();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }
}
