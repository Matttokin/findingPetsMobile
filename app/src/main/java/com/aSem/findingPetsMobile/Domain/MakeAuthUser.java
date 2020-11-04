package com.aSem.findingPetsMobile.Domain;

import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MakeAuthUser extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    private String login;
    private String password;
    @Inject
    MakeAuthUser(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    public void setParam(String login, String password){
        this.login = login;
        this.password = password;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.authUser(login, password);
    }
}
