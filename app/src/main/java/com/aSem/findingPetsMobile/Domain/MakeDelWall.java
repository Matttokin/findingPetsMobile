package com.aSem.findingPetsMobile.Domain;

import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MakeDelWall extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    private String idWall;
    @Inject
    MakeDelWall(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    public void setParam(String idWall){
        this.idWall = idWall;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.delWall(idWall);
    }
}
