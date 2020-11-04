package com.aSem.findingPetsMobile.Domain;

import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MakeEditWall extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    private String idWall;
    private String titleWalls;
    private String descriptionWalls;
    @Inject
    MakeEditWall(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    public void setParam(String idWall, String titleWalls, String descriptionWalls){
        this.idWall = idWall;
        this.titleWalls = titleWalls;
        this.descriptionWalls = descriptionWalls;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.editWall(idWall, titleWalls, descriptionWalls);
    }
}
