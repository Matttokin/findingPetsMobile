package com.aSem.findingPetsMobile.Presentation;


import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Fragments.Walls;
import com.aSem.findingPetsMobile.FullInfoWall;

import javax.inject.Inject;

public class FullInfoWallPresentation {
    // НУжно для обратной связи
    public FullInfoWall fullInfoWall;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;
    public FullInfoWallPresentation(FullInfoWall fullInfoWall)
    {
        this.fullInfoWall = fullInfoWall;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setFullInfoWallPresentation(this);


    }
    /*
     * Получить подробную информацию об объявлении
     */
    public void getFullInfoWall(String idWalls)
    {
        presentationWorkDomain.getFullInfoWall(idWalls);
    }

    public void completeGetFullInfoWall(String response)
    {
        fullInfoWall.completeGetFullInfoWall(response);
    }
    /*
     * Получение токена
     */
    public void getToken()
    {
        presentationWorkDomain.getToken4FullInfoWall();
    }

    public void completeGetToken(String token)
    {
        fullInfoWall.completeGetToken(token);
    }


}
