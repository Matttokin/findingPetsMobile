package com.aSem.findingPetsMobile.Domain;

import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MakeChangePassword extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    private String login;
    private String oldPassword;
    private String newPassword;
    @Inject
    MakeChangePassword(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    public void setParam(String login, String oldPassword, String newPassword){
        this.login = login;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.changePassword(login, oldPassword, newPassword);
    }
}
