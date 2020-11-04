package com.aSem.findingPetsMobile.Presentation;


import com.aSem.findingPetsMobile.AddWallActivity;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.FullInfoWall;

import javax.inject.Inject;

public class AddWallPresentation {
    // НУжно для обратной связи
    public AddWallActivity addWallActivity;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;
    public AddWallPresentation(AddWallActivity addWallActivity)
    {
        this.addWallActivity = addWallActivity;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setAddWallPresentation(this);


    }
    /*
     *
     */
    public void addWall(String idCategory, String titleWalls, String descriptionWalls,
                        String latitude, String longitude, String radius, String nearestTown, String urlImageAvatar)
    {
        presentationWorkDomain.addWall(idCategory, titleWalls, descriptionWalls,
                latitude, longitude, radius, nearestTown, urlImageAvatar);
    }

    public void completeAddWall(String response)
    {
        addWallActivity.completeAddWall(response);
    }
    /*
     * Получить категории
     */
    public void getListCategory()
    {
        presentationWorkDomain.getListCategoryAdd();
    }

    public void completeGetListCategory(String response)
    {
        addWallActivity.completeGetListCategory(response);
    }
    /*
     * Получить города
     */
    public void getListTown()
    {
        presentationWorkDomain.getListTownAdd();
    }

    public void completeGetListTown(String response)
    {
        addWallActivity.completeGetListTown(response);
    }
}
