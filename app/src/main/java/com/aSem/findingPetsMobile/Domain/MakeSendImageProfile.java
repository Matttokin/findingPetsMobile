package com.aSem.findingPetsMobile.Domain;

import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MakeSendImageProfile extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    private String uriImg;
    @Inject
    MakeSendImageProfile(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    public void setParam(String uriImg){
        this.uriImg = uriImg;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.sendImageProfile(uriImg);
    }
}
