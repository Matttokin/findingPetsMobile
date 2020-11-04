package com.aSem.findingPetsMobile.Presentation;

import com.aSem.findingPetsMobile.AuthActivity;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.RegActivity;

import javax.inject.Inject;

public class RegPresentation {


    // НУжно для обратной связи
    public RegActivity regActivity;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;

    public RegPresentation(RegActivity regActivity)
    {
        this.regActivity=regActivity;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setRegPresentation(this);

    }
    /*
     * Регистрация пользователя
     */
    public void regUser(String login,String fio, String phone, String password)
    {
        presentationWorkDomain.regUser(login,fio,phone,password);
    }

    public void completeRegUser(String response)
    {
        regActivity.completeRegUser(response);
    }


}
