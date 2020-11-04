package com.aSem.findingPetsMobile.Presentation;


import javax.inject.Inject;

import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Fragments.Profile;

public class ProfileFragmentPresentation {
    // НУжно для обратной связи
    public Profile profile;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;
    public ProfileFragmentPresentation(Profile profile)
    {
        this.profile = profile;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setProfileFragmentPresentation(this);


    }
    /*
     * Получение токена
     */
    public void getToken()
    {
        presentationWorkDomain.getToken();
    }

    public void completeGetToken(String token)
    {
        profile.completeGetToken(token);
    }

    /*
     * Выход пользователя
     */
    public void exitUser()
    {
        presentationWorkDomain.exitUser();
    }

    public void completeExitUser(Boolean token)
    {
        profile.completeExitUser(token);
    }

    /*
     * Получение информации о пользователе
     */
    public void getUserInfo()
    {
        presentationWorkDomain.getUserInfo();
    }

    public void completeGetUserInfo(String json)
    {
        profile.competeGetUserInfo(json);
    }
}
