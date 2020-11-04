package com.aSem.findingPetsMobile.Presentation;


import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.FullInfoWall;
import com.aSem.findingPetsMobile.FullInfoWallMy;

import javax.inject.Inject;

public class FullInfoWallMyPresentation {
    // НУжно для обратной связи
    public FullInfoWallMy fullInfoWallMy;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;
    public FullInfoWallMyPresentation(FullInfoWallMy fullInfoWallMy)
    {
        this.fullInfoWallMy = fullInfoWallMy;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setFullInfoWallMyPresentation(this);


    }
    /*
     * Получить подробную информацию об объявлении
     */
    public void getFullInfoWallMy(String idWalls)
    {
        presentationWorkDomain.getFullInfoWallMy(idWalls);
    }

    public void completeGetFullInfoWallMy(String response)
    {
        fullInfoWallMy.completeGetFullInfoWall(response);
    }
    /*
     * Удалить запись
     */
    public void delWall(String idWalls)
    {
        presentationWorkDomain.delWall(idWalls);
    }

    public void completeDelWall(String response)
    {
        fullInfoWallMy.completeDelWall(response);
    }


}
