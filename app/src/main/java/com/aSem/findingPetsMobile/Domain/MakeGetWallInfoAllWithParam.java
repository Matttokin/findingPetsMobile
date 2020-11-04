package com.aSem.findingPetsMobile.Domain;

import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MakeGetWallInfoAllWithParam extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    private String offset;
    private String limit;
    private String searchQry;
    private String category;
    private String town;
    private String sortBy;
    @Inject
    MakeGetWallInfoAllWithParam(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    public void setParam(String offset, String limit, String searchQry,String category, String town, String sortBy){
        this.offset = offset;
        this.limit = limit;
        this.searchQry = searchQry;
        this.category = category;
        this.town = town;
        this.sortBy = sortBy;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.getWallInfoAllWithParam(offset,limit,searchQry,category, town, sortBy);
    }
}
