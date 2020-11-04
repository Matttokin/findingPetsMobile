package com.aSem.findingPetsMobile.GsonClasses;

public class UserInfo {
    public String loginUser;
    public String fio;
    public String numberPhone;
    public String dateRegister;
    public String avatarUrl;

    public UserInfo(String loginUser, String fio, String numberPhone, String dateRegister, String avatarUrl) {
        this.loginUser = loginUser;
        this.fio = fio;
        this.numberPhone = numberPhone;
        this.dateRegister = dateRegister;
        this.avatarUrl = avatarUrl;
    }
}
