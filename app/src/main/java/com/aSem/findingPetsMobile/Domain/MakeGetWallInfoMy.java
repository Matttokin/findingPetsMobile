package com.aSem.findingPetsMobile.Domain;

import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MakeGetWallInfoMy extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    private String token;
    @Inject
    MakeGetWallInfoMy(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.getWallInfoMy();
    }
}
