package com.aSem.findingPetsMobile.Domain;

import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MakeRegUser extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    private String login;
    private String fio;
    private String phone;
    private String password;
    @Inject
    MakeRegUser(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    public void setParam(String login, String fio, String phone, String password){
        this.login = login;
        this.fio = fio;
        this.phone = phone;
        this.password = password;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.regUser(login, fio, phone, password);
    }
}
