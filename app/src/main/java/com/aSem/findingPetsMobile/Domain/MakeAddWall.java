package com.aSem.findingPetsMobile.Domain;

import com.aSem.findingPetsMobile.SqlIte.SqliteAccess;
import com.aSem.findingPetsMobile.ThreadInterface.PostExecutionThread;
import com.aSem.findingPetsMobile.ThreadInterface.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MakeAddWall extends UseCase {
    @Inject
    public SqliteAccess sqliteAccess;
    private String idCategory;
    private String titleWalls;
    private String descriptionWalls;
    private String latitude;
    private String longitude;
    private String radius;
    private String nearestTown;
    private String urlImageAvatar;
    @Inject
    MakeAddWall(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SqliteAccess sqliteAccess) {
        super(threadExecutor, postExecutionThread);
        this.sqliteAccess=sqliteAccess;
    }
    public void setParam(String idCategory, String titleWalls, String descriptionWalls,
                         String latitude, String longitude, String radius, String nearestTown, String urlImageAvatar){
        this.idCategory = idCategory;
        this.titleWalls = titleWalls;
        this.descriptionWalls = descriptionWalls;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.nearestTown = nearestTown;
        this.urlImageAvatar = urlImageAvatar;
    }
    @Override
    Observable buildUseCaseObservable(Object o) {
        return sqliteAccess.addWall(idCategory, titleWalls, descriptionWalls,
                latitude, longitude, radius, nearestTown, urlImageAvatar);
    }
}
