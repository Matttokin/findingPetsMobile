package com.aSem.findingPetsMobile.CallbackInterface;


import android.view.Menu;

public interface CallbackToProfileFragment {

    void completeGetToken(String StatusToken);
    void completeExitUser(Boolean resp);
    void competeGetUserInfo(String json);

}
