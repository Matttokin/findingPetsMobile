package com.aSem.findingPetsMobile.SqlIte;

import io.reactivex.Observable;

public interface SqliteAccess {

    Observable<String> getTokenInfo();
    Observable<String> authUser(String login, String password);
    Observable<Boolean> exitUser();
    Observable<String> regUser(String login, String fio, String phone, String password);
    Observable<String> getUserInfo();
    Observable<String> changePassword(String login, String oldPassword, String newPassword);
    Observable<String> getWallInfoAll(String offset, String limit, String searchQry);
    Observable<String> getFullInfoWall(String idWall);
    Observable<String> getWallInfoMy();
    Observable<String> delWall(String idWall);
    Observable<String> addWall(String idCategory, String titleWalls, String descriptionWalls, String latitude, String longitude, String radius, String nearestTown, String urlImageAvatar);
    Observable<String> getListCategory();
    Observable<String> getListTown();
    Observable<String>  getWallInfoAllWithParam(String offset, String limit, String searchQry, String category, String town, String sortBy);

    Observable<String> editWall(String idWall, String titleWalls, String descriptionWalls);

    Observable<String> sendImageProfile(String uriImg);
}
