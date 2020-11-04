package com.aSem.findingPetsMobile.Server;

public class respCode {
    public static String getMess(String code){
        switch (code){
            case "000" : return "Данные отсутствуют";
            case "200" : return "Успешно";
            case "301" : return "Логин занят";
            case "302" : return "Номер телефона занят";
            case "303" : return "Пользователь заблокирован";
            case "304" : return "Пользователь не найден";
            case "305" : return "Пароль неверен";
            case "401" : return "Доступ запрещен";
        }
        return "Неизвестная ошибка";
    }
}
