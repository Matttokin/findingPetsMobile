package com.aSem.findingPetsMobile.Dagger;


import android.content.Context;

import com.aSem.findingPetsMobile.ChangeImgProfileActivity;
import com.aSem.findingPetsMobile.FullInfoWall;
import com.aSem.findingPetsMobile.Presentation.AddWallPresentation;
import com.aSem.findingPetsMobile.Presentation.AuthPresentation;
import com.aSem.findingPetsMobile.Presentation.ChangeImageProfilePresentation;
import com.aSem.findingPetsMobile.Presentation.ChangePasswordPresentation;
import com.aSem.findingPetsMobile.Presentation.EditWallPresentation;
import com.aSem.findingPetsMobile.Presentation.FullInfoWallMyPresentation;
import com.aSem.findingPetsMobile.Presentation.FullInfoWallPresentation;
import com.aSem.findingPetsMobile.Presentation.MyWallsPresentation;
import com.aSem.findingPetsMobile.Presentation.ProfileFragmentPresentation;
import com.aSem.findingPetsMobile.Presentation.RegPresentation;
import com.aSem.findingPetsMobile.Presentation.WallsFragmentPresentation;
import com.aSem.findingPetsMobile.SqlIte.SqlDataManager;
import com.aSem.findingPetsMobile.MainActivity;
import com.aSem.findingPetsMobile.Presentation.MainPresentation;
import com.aSem.findingPetsMobile.Presentation.PresentationWorkDomain;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();

    void inject(SqlDataManager sqlDataManager);
    void inject(MainPresentation mainPresentation);
    void inject(PresentationWorkDomain presentationWorkDomain);
    void inject(ProfileFragmentPresentation profilePresentation);
    void inject(AuthPresentation authPresentation);
    void inject(RegPresentation regPresentation);
    void inject(ChangePasswordPresentation changePasswordPresentation);
    void inject(WallsFragmentPresentation wallsFragmentPresentation);
    void inject(FullInfoWallPresentation fullInfoWallPresentation);
    void inject(MyWallsPresentation myWallsPresentation);
    void inject(FullInfoWallMyPresentation fullInfoWallMyPresentation);
    void inject(AddWallPresentation addWallPresentation);
    void inject(EditWallPresentation editWallPresentation);
    void inject(ChangeImageProfilePresentation changeImageProfilePresentation);
}
