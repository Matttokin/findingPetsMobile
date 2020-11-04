package com.aSem.findingPetsMobile.SqlIte;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import android.widget.Toast;
import java.util.ArrayList;

import io.reactivex.Observable;


public class SqlAdapter implements SqliteAccess {

    private SqlDataManager sqlDataManager;
    private Context context;


    public SqlAdapter (Context Ct)
    {
        sqlDataManager= new SqlDataManager(Ct);
        try {
            sqlDataManager.SetDb(sqlDataManager.getWritableDatabase());
        } catch (SQLException mSQLException) {
            Toast.makeText(context,
                    mSQLException.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        context=Ct;
    }





    @Override
    public Observable<String> getTokenInfo() {
        return Observable.create(emitter -> {
            Cursor Faf = sqlDataManager.getReqest("Select tokenUser from user WHERE idUser = 1 ;");

            String tokenUser;
            if(Faf.moveToNext()) {
                tokenUser = Faf.getString(0);
            } else {
                tokenUser = "";
            }
            emitter.onNext(tokenUser);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> authUser(String login, String password) {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.authUserOnServer(login, password ));
            emitter.onComplete();
        });

    }

    @Override
    public Observable<Boolean> exitUser() {
        return Observable.create(emitter -> {
            sqlDataManager.setReqest("UPDATE user SET tokenUser = ''  WHERE idUser = 1");
            emitter.onNext(true);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> regUser(String login, String fio, String phone, String password) {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.regUserOnServer(login, fio, phone, password));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> getUserInfo() {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.getUserInfo());
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> changePassword(String login, String oldPassword, String newPassword) {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.changePassword(login, oldPassword, newPassword));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> getWallInfoAll(String offset, String limit, String searchQry) {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.getWallInfoAll(offset, limit, searchQry));
            emitter.onComplete();
        });
    }
    @Override
    public Observable<String> getFullInfoWall(String idWalls) {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.getFullInfoWall(idWalls));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> getWallInfoMy() {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.getWallInfoMy());
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> delWall(String idWall) {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.delWall(idWall));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> addWall(String idCategory, String titleWalls, String descriptionWalls, String latitude, String longitude, String radius, String nearestTown, String urlImageAvatar) {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.addWall(idCategory, titleWalls, descriptionWalls,
                    latitude, longitude, radius, nearestTown, urlImageAvatar));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> getListCategory() {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.getListCategory());
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> getListTown() {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.getListTown());
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> getWallInfoAllWithParam(String offset, String limit, String searchQry, String category, String town, String sortBy) {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.getWallInfoAllWithParam(offset, limit, searchQry, category, town, sortBy));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> editWall(String idWall, String titleWalls, String descriptionWalls) {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.editWall(idWall,titleWalls,descriptionWalls));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<String> sendImageProfile(String uriImg) {
        return Observable.create(emitter -> {
            emitter.onNext(sqlDataManager.sendImageProfile(uriImg));
            emitter.onComplete();
        });
    }

}
