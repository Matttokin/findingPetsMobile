package com.aSem.findingPetsMobile.Domain;

import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MakeGetFullInfoWall extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    private String idWall;
    @Inject
    MakeGetFullInfoWall(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    public void setParam(String idWall){
        this.idWall = idWall;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.getFullInfoWall(idWall);
    }
}
