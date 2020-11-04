package com.aSem.findingPetsMobile.Presentation;


import javax.inject.Inject;

import com.aSem.findingPetsMobile.Domain.DefaultObserver;
import com.aSem.findingPetsMobile.Domain.MakeAddWall;
import com.aSem.findingPetsMobile.Domain.MakeAuthUser;
import com.aSem.findingPetsMobile.Domain.MakeChangePassword;
import com.aSem.findingPetsMobile.Domain.MakeDelWall;
import com.aSem.findingPetsMobile.Domain.MakeEditWall;
import com.aSem.findingPetsMobile.Domain.MakeExitUser;
import com.aSem.findingPetsMobile.Domain.MakeGetFullInfoWall;
import com.aSem.findingPetsMobile.Domain.MakeGetListCategory;
import com.aSem.findingPetsMobile.Domain.MakeGetListTown;
import com.aSem.findingPetsMobile.Domain.MakeGetToken;
import com.aSem.findingPetsMobile.Domain.MakeGetUserInfo;
import com.aSem.findingPetsMobile.Domain.MakeGetWallInfoAll;
import com.aSem.findingPetsMobile.Domain.MakeGetWallInfoAllWithParam;
import com.aSem.findingPetsMobile.Domain.MakeGetWallInfoMy;
import com.aSem.findingPetsMobile.Domain.MakeRegUser;
import com.aSem.findingPetsMobile.Domain.MakeSendImageProfile;


public class PresentationWorkDomain {

    public MainPresentation mainPresentation;
    public AuthPresentation authPresentation;
    public RegPresentation regPresentation;
    public ChangePasswordPresentation changePasswordPresentation;
    public ProfileFragmentPresentation profileFragmentPresentation;
    public WallsFragmentPresentation wallsFragmentPresentation;
    public FullInfoWallPresentation fullInfoWallPresentation;
    public MyWallsPresentation myWallsPresentation;
    public FullInfoWallMyPresentation fullInfoWallMyPresentation;
    public AddWallPresentation addWallPresentation;
    public EditWallPresentation editWallPresentation;
    public ChangeImageProfilePresentation changeImageProfilePresentation;

    private final MakeGetToken makeGetToken;
    private final MakeAuthUser makeAuthUser;
    private final MakeExitUser makeExitUser;
    private final MakeRegUser makeRegUser;
    private final MakeChangePassword makeChangePassword;
    private final MakeGetUserInfo makeGetUserInfo;
    private final MakeGetWallInfoAll makeGetWallInfoAll;
    private final MakeGetFullInfoWall makeGetFullInfoWall;
    private final MakeGetWallInfoMy makeGetWallInfoMy;
    private final MakeDelWall makeDelWall;
    private final MakeAddWall makeAddWall;
    private final MakeGetListCategory makeGetListCategory;
    private final MakeGetListTown makeGetListTown;
    private final MakeGetWallInfoAllWithParam makeGetWallInfoAllWithParam;
    private final MakeEditWall makeEditWall;
    private final MakeSendImageProfile makeSendImageProfile;



    // Инцилизация
    @Inject
    public PresentationWorkDomain(MakeGetToken makeGetToken, MakeAuthUser makeAuthUser, MakeExitUser makeExitUser, MakeRegUser makeRegUser, MakeChangePassword makeChangePassword, MakeGetUserInfo makeGetUserInfo, MakeGetWallInfoAll makeGetWallInfoAll, MakeGetFullInfoWall makeGetFullInfoWall, MakeGetWallInfoMy makeGetWallInfoMy, MakeDelWall makeDelWall, MakeAddWall makeAddWall, MakeGetListCategory makeGetListCategory, MakeGetListTown makeGetListTown, MakeGetWallInfoAllWithParam makeGetWallInfoAllWithParam, MakeEditWall makeEditWall, MakeSendImageProfile makeSendImageProfile) {
        this.makeGetToken = makeGetToken;
        this.makeAuthUser = makeAuthUser;
        this.makeExitUser = makeExitUser;
        this.makeRegUser = makeRegUser;
        this.makeChangePassword = makeChangePassword;
        this.makeGetUserInfo = makeGetUserInfo;
        this.makeGetWallInfoAll = makeGetWallInfoAll;
        this.makeGetFullInfoWall = makeGetFullInfoWall;
        this.makeGetWallInfoMy = makeGetWallInfoMy;
        this.makeDelWall = makeDelWall;
        this.makeAddWall = makeAddWall;
        this.makeGetListCategory = makeGetListCategory;
        this.makeGetListTown = makeGetListTown;
        this.makeGetWallInfoAllWithParam = makeGetWallInfoAllWithParam;
        this.makeEditWall = makeEditWall;
        this.makeSendImageProfile = makeSendImageProfile;
    }

    //Для обратной связи
    public void setMainPresentation(MainPresentation mainPresentation) {
        this.mainPresentation = mainPresentation;
    }

    public void setProfileFragmentPresentation(ProfileFragmentPresentation profileFragmentPresentation) {
        this.profileFragmentPresentation = profileFragmentPresentation;
    }

    public void setAuthPresentation(AuthPresentation authPresentation) {
        this.authPresentation = authPresentation;
    }

    public void setRegPresentation(RegPresentation regPresentation) {
        this.regPresentation = regPresentation;
    }

    public void setChangePasswordPresentation(ChangePasswordPresentation changePasswordPresentation) {
        this.changePasswordPresentation = changePasswordPresentation;
    }

    public void setWallsFragmentPresentation(WallsFragmentPresentation wallsFragmentPresentation) {
        this.wallsFragmentPresentation = wallsFragmentPresentation;
    }
    public void setFullInfoWallPresentation(FullInfoWallPresentation fullInfoWallPresentation) {
        this.fullInfoWallPresentation = fullInfoWallPresentation;
    }
    public void setMyWallsPresentation(MyWallsPresentation myWallsPresentation) {
        this.myWallsPresentation = myWallsPresentation;
    }
    public void setFullInfoWallMyPresentation(FullInfoWallMyPresentation fullInfoWallMyPresentation) {
        this.fullInfoWallMyPresentation = fullInfoWallMyPresentation;
    }
    public void setAddWallPresentation(AddWallPresentation addWallPresentation) {
        this.addWallPresentation = addWallPresentation;
    }
    public void setEditWallPresentation(EditWallPresentation editWallPresentation) {
        this.editWallPresentation = editWallPresentation;
    }
    public void setChangeImageProfilePresentation(ChangeImageProfilePresentation changeImageProfilePresentation) {
        this.changeImageProfilePresentation = changeImageProfilePresentation;
    }





    /*
     * Получить токен
     */
    public void getToken() {
        this.makeGetToken.execute(new ObservedGetToken(), null);
    }



    private final class ObservedGetToken extends DefaultObserver<String> {
        String token;
        @Override
        public void onComplete() {
            profileFragmentPresentation.completeGetToken(token);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String returnToken) {
            token = returnToken;
        }
    }
    /***/

    /*
     * Получить токен для показа номера
     */
    public void getToken4FullInfoWall() {
        this.makeGetToken.execute(new ObservedGetToken4FullInfoWall(), null);
    }

    private final class ObservedGetToken4FullInfoWall extends DefaultObserver<String> {
        String token;
        @Override
        public void onComplete() {
            fullInfoWallPresentation.completeGetToken(token);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String returnToken) {
            token = returnToken;
        }
    }
    /***/

    /*
     * Авторизовать пользователя
     */
    public void authUser(String login, String password) {
        this.makeAuthUser.setParam(login,password);
        this.makeAuthUser.execute(new ObservedAuthUser(), null);
    }

    private final class ObservedAuthUser extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            authPresentation.completeAuthUser(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Выход пользователя
     */
    public void exitUser() {
        this.makeExitUser.execute(new ObservedExitUser(), null);
    }

    private final class ObservedExitUser extends DefaultObserver<Boolean> {
        Boolean response;
        @Override
        public void onComplete() {
            profileFragmentPresentation.completeExitUser(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Boolean r) {
            response = r;
        }
    }
    /***/

    /*
     * Регистрация пользователя
     */
    public void regUser(String login, String fio, String phone, String password) {
        this.makeRegUser.setParam(login, fio, phone, password);
        this.makeRegUser.execute(new ObservedRegUser(), null);
    }

    private final class ObservedRegUser extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            regPresentation.completeRegUser(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Получить информацию о пользователе
     */
    public void getUserInfo() {
        this.makeGetUserInfo.execute(new ObservedGetUserInfo(), null);
    }

    private final class ObservedGetUserInfo extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            profileFragmentPresentation.completeGetUserInfo(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Смена пароля
     */
    public void changePassword(String login, String oldPassword, String newPassword) {
        this.makeChangePassword.setParam(login, oldPassword, newPassword);
        this.makeChangePassword.execute(new ObservedChangePassword(), null);
    }

    private final class ObservedChangePassword extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            changePasswordPresentation.completeChangePassword(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Получить все объявления
     */
    public void getWallInfoAll(String offset, String limit, String searchQry) {
        this.makeGetWallInfoAll.setParam(offset,limit,searchQry);
        this.makeGetWallInfoAll.execute(new ObservedGetWallInfoAll(), null);
    }

    private final class ObservedGetWallInfoAll extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            wallsFragmentPresentation.completeGetWallInfoAll(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Получить все объявления c параметрами
     */
    public void getWallInfoAllWithParam(String offset, String limit, String searchQry,String category, String town, String sortBy) {
        this.makeGetWallInfoAllWithParam.setParam(offset,limit,searchQry,category, town, sortBy);
        this.makeGetWallInfoAllWithParam.execute(new ObservedGetWallInfoAllWithParam(), null);
    }

    private final class ObservedGetWallInfoAllWithParam extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            wallsFragmentPresentation.completeGetWallInfoAllWithParam(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Получить подробную информацию об объявлении
     */
    public void getFullInfoWall(String idWalls) {
        this.makeGetFullInfoWall.setParam(idWalls);
        this.makeGetFullInfoWall.execute(new ObservedGetFullInfoWall(), null);
    }

    private final class ObservedGetFullInfoWall extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            fullInfoWallPresentation.completeGetFullInfoWall(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Получить объявления авторизованного пользователя
     */
    public void getWallInfoMy() {
        this.makeGetWallInfoMy.execute(new ObservedGetWallInfoMy(), null);
    }

    private final class ObservedGetWallInfoMy extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            myWallsPresentation.completeGetWallInfoMy(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Получить подробную информацию об объявлении
     */
    public void getFullInfoWallMy(String idWalls) {
        this.makeGetFullInfoWall.setParam(idWalls);
        this.makeGetFullInfoWall.execute(new ObservedGetFullInfoWallMy(), null);
    }

    private final class ObservedGetFullInfoWallMy extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            fullInfoWallMyPresentation.completeGetFullInfoWallMy(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Удалить запись
     */
    public void delWall(String idWalls) {
        this.makeDelWall.setParam(idWalls);
        this.makeDelWall.execute(new ObservedDelWall(), null);
    }

    private final class ObservedDelWall extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            fullInfoWallMyPresentation.completeDelWall(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/
    /*
     * Добавить запись
     */
    public void addWall(String idCategory, String titleWalls, String descriptionWalls,
                        String latitude, String longitude, String radius, String nearestTown, String urlImageAvatar) {
        this.makeAddWall.setParam(idCategory, titleWalls, descriptionWalls,
                latitude, longitude, radius, nearestTown, urlImageAvatar);
        this.makeAddWall.execute(new ObservedAddWall(), null);
    }

    private final class ObservedAddWall extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            addWallPresentation.completeAddWall(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Получить категории
     */
    public void getListCategory() {
        this.makeGetListCategory.execute(new ObservedGetListCategory(), null);
    }

    private final class ObservedGetListCategory extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            wallsFragmentPresentation.completeGetListCategory(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Получить города
     */
    public void getListTown() {
        this.makeGetListTown.execute(new ObservedGetListTown(), null);
    }

    private final class ObservedGetListTown extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            wallsFragmentPresentation.completeGetListTown(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Получить категории
     */
    public void getListCategoryAdd() {
        this.makeGetListCategory.execute(new ObservedGetListCategoryAdd(), null);
    }

    private final class ObservedGetListCategoryAdd extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            addWallPresentation.completeGetListCategory(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Получить города
     */
    public void getListTownAdd() {
        this.makeGetListTown.execute(new ObservedGetListTownAdd(), null);
    }

    private final class ObservedGetListTownAdd extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            addWallPresentation.completeGetListTown(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Редактировать запись
     */
    public void editWall(String idWall, String title, String desc) {
        this.makeEditWall.setParam(idWall,title,desc);
        this.makeEditWall.execute(new ObservedEditWall(), null);
    }

    private final class ObservedEditWall extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            editWallPresentation.completeEditWall(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/

    /*
     * Отправка фото профиля
     */
    public void sendImageProfile(String uriImg) {
        this.makeSendImageProfile.setParam(uriImg);
        this.makeSendImageProfile.execute(new ObservedeSendImageProfile(), null);
    }

    private final class ObservedeSendImageProfile extends DefaultObserver<String> {
        String response;
        @Override
        public void onComplete() {
            changeImageProfilePresentation.completeSendImageProfile(response);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String r) {
            response = r;
        }
    }
    /***/








}

