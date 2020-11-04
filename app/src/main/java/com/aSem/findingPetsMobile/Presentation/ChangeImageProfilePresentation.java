package com.aSem.findingPetsMobile.Presentation;

import com.aSem.findingPetsMobile.ChangeImgProfileActivity;
import com.aSem.findingPetsMobile.ChangePasswordActivity;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;

import javax.inject.Inject;

public class ChangeImageProfilePresentation {


    // НУжно для обратной связи
    public ChangeImgProfileActivity changeImgProfileActivity;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;

    public ChangeImageProfilePresentation(ChangeImgProfileActivity changeImgProfileActivity)
    {
        this.changeImgProfileActivity=changeImgProfileActivity;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setChangeImageProfilePresentation(this);

    }

    public void sendImageProfile(String uriImg)
    {
        presentationWorkDomain.sendImageProfile(uriImg);
    }

    public void completeSendImageProfile(String response)
    {
        changeImgProfileActivity.completeSendImageProfile(response);
    }


}
