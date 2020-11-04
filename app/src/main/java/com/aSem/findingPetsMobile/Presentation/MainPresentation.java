package com.aSem.findingPetsMobile.Presentation;

import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.MainActivity;

import javax.inject.Inject;

public class MainPresentation {


    // НУжно для обратной связи
    public MainActivity mainActivity;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;

    public MainPresentation(MainActivity mainActivity)
    {
        this.mainActivity=mainActivity;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setMainPresentation(this);

    }


}
