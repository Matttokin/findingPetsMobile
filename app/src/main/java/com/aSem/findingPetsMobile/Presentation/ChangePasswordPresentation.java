package com.aSem.findingPetsMobile.Presentation;

import com.aSem.findingPetsMobile.AuthActivity;
import com.aSem.findingPetsMobile.ChangePasswordActivity;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;

import javax.inject.Inject;

public class ChangePasswordPresentation {


    // НУжно для обратной связи
    public ChangePasswordActivity changePasswordActivity;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;

    public ChangePasswordPresentation(ChangePasswordActivity changePasswordActivity)
    {
        this.changePasswordActivity=changePasswordActivity;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setChangePasswordPresentation(this);

    }
    /*
     * Смена пароля
     */
    public void changePassword(String login,String oldPassword, String newPassword)
    {
        presentationWorkDomain.changePassword(login, oldPassword,newPassword);
    }

    public void completeChangePassword(String response)
    {
        changePasswordActivity.completeChangePassword(response);
    }


}
