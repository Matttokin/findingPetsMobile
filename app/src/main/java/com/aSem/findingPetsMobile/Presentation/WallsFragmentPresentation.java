package com.aSem.findingPetsMobile.Presentation;


import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Fragments.Profile;
import com.aSem.findingPetsMobile.Fragments.Walls;

import javax.inject.Inject;

public class WallsFragmentPresentation {
    // НУжно для обратной связи
    public Walls walls;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;
    public WallsFragmentPresentation(Walls walls)
    {
        this.walls = walls;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setWallsFragmentPresentation(this);


    }
    /*
     * Получить объявления (все категории)
     */
    public void getWallInfoAll(String offset, String limit, String searchQry)
    {
        presentationWorkDomain.getWallInfoAll(offset,limit,searchQry);
    }

    public void completeGetWallInfoAll(String response)
    {
        walls.completeGetWallInfoAll(response);
    }

    /*
     * Получить объявления (c параметрами)
     */
    public void getWallInfoAllWithParam(String offset, String limit, String searchQry,String category, String town, String sortBy)
    {
        presentationWorkDomain.getWallInfoAllWithParam(offset,limit,searchQry,category, town, sortBy);
    }

    public void completeGetWallInfoAllWithParam(String response)
    {
        walls.completeGetWallInfoAllWithParam(response);
    }

    /*
     * Получить категории
     */
    public void getListCategory()
    {
        presentationWorkDomain.getListCategory();
    }

    public void completeGetListCategory(String response)
    {
        walls.completeGetListCategory(response);
    }
    /*
     * Получить города
     */
    public void getListTown()
    {
        presentationWorkDomain.getListTown();
    }

    public void completeGetListTown(String response)
    {
        walls.completeGetListTown(response);
    }


}
