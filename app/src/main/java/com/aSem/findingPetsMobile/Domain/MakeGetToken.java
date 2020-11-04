package com.aSem.findingPetsMobile.Domain;

import javax.inject.Inject;

import io.reactivex.Observable;
import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

public class MakeGetToken extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    @Inject
    MakeGetToken(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.getTokenInfo();
    }
}
