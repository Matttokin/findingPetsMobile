package com.aSem.findingPetsMobile.Presentation;

import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.EditWallActivity;
import com.aSem.findingPetsMobile.RegActivity;

import javax.inject.Inject;

public class EditWallPresentation {


    // НУжно для обратной связи
    public EditWallActivity editWallActivity;
    // Инцилизация
    @Inject
    PresentationWorkDomain presentationWorkDomain;

    public EditWallPresentation(EditWallActivity editWallActivity)
    {
        this.editWallActivity=editWallActivity;

        // Инджектим PresentationWorkDomain
        AndroidApplication.applicationComponent.inject(this);

        // Для обратной связи
        presentationWorkDomain.setEditWallPresentation(this);

    }
    /*
     * Регистрация пользователя
     */
    public void editWall( String idWall, String title, String desc)
    {
        presentationWorkDomain.editWall(idWall, title, desc);
    }

    public void completeEditWall(String response)
    {
        editWallActivity.completeEditWall(response);
    }


}
