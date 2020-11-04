package com.aSem.findingPetsMobile.Presentation;


import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Fragments.Walls;
import com.aSem.findingPetsMobile.MyWallsActivity;

import javax.inject.Inject;

public class MyWallsPresentation {
    // НУжно для обратной связи
    public MyWallsActivity myWallsActivity;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;
    public MyWallsPresentation(MyWallsActivity myWallsActivity)
    {
        this.myWallsActivity = myWallsActivity;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setMyWallsPresentation(this);


    }
    /*
     * Получить объявления (все категории)
     */
    public void getWallInfoMy()
    {
        presentationWorkDomain.getWallInfoMy();
    }

    public void completeGetWallInfoMy(String response)
    {
        myWallsActivity.completeGetWallInfoMy(response);
    }


}
