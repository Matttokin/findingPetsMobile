package com.aSem.findingPetsMobile;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aSem.findingPetsMobile.CallbackInterface.CallbackToReg;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.Presentation.AuthPresentation;
import com.aSem.findingPetsMobile.Presentation.RegPresentation;
import com.aSem.findingPetsMobile.Server.respCode;

public class RegActivity extends AppCompatActivity implements CallbackToReg {

    public ApplicationComponent applicationComponent;
    public RegPresentation regPresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        regPresentation = new RegPresentation(this);

        findViewById(R.id.button_reg).setOnClickListener(v -> {
            String login = ((EditText)findViewById(R.id.login_reg)).getText().toString();
            String fio = ((EditText)findViewById(R.id.fio_reg)).getText().toString();
            String phone = ((EditText)findViewById(R.id.phone_reg)).getText().toString();
            String password = ((EditText)findViewById(R.id.password_reg)).getText().toString();

            if(login.length() > 5){
                if(fio.length() > 10) {
                    if(phone.length() == 18){
                        if(password.length() > 5){
                            regPresentation.regUser(login,fio,phone,password);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Пароль должен быть длиной не менее 6 символов", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Введите номер телефона", Toast.LENGTH_SHORT).show();
                    }
                }else {
                        Toast.makeText(getApplicationContext(),
                                "Введите ФИО полностью", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Логин должен быть длиной не менее 6 символов", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void completeRegUser(String response) {
        if(response.length() == 32){
            finish();
        } else {

        }
    }
}