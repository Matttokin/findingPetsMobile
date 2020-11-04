package com.aSem.findingPetsMobile.Dagger;

import android.app.Application;
import android.content.Context;

import com.aSem.findingPetsMobile.SqlIte.SqlAdapter;
import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.JobExecutor;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;
import com.aSem.findingPetsMobile.ThreadInterface.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule
            (Application application) {
        this.application = application;
    }
    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }
    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }
    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }





//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    //SqlDatamanadger

    @Provides
    SqlAdapter provideSqlAdapter(Context ct)
    {
        return new SqlAdapter(ct);
    }

    @Provides
    @Singleton
    SqliteAccess provideSQLAccess(SqlAdapter sqlAdapter){
        return sqlAdapter;
    }

    /*@Provides
    PresentationWorkDomain providePresentationWorkDomain(MakeAuthorization makeAuthorization){
        return new PresentationWorkDomain(makeAuthorization);
    }*/

}
