package com.aSem.findingPetsMobile.Presentation;

import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.MainActivity;
import com.aSem.findingPetsMobile.AuthActivity;

import javax.inject.Inject;

public class AuthPresentation {


    // НУжно для обратной связи
    public AuthActivity authActivity;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;

    public AuthPresentation(AuthActivity authActivity)
    {
        this.authActivity=authActivity;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setAuthPresentation(this);

    }
    /*
     * Получение токена
     */
    public void authUser(String login,String password)
    {
        presentationWorkDomain.authUser(login, password);
    }

    public void completeAuthUser(String response)
    {
        authActivity.completeAuthUser(response);
    }


}
