package com.aSem.findingPetsMobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aSem.findingPetsMobile.CallbackInterface.CallbackToAuth;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.Presentation.AuthPresentation;
import com.aSem.findingPetsMobile.Server.respCode;

public class AuthActivity extends AppCompatActivity implements CallbackToAuth {

    public ApplicationComponent applicationComponent;
    public AuthPresentation authPresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        authPresentation = new AuthPresentation(this);

        TextView loginTv = findViewById(R.id.login_auth);
        TextView passwordTv = findViewById(R.id.password_auth);


        findViewById(R.id.button_auth).setOnClickListener(v -> {

            String login = ((EditText)findViewById(R.id.login_auth)).getText().toString();
            String password = ((EditText)findViewById(R.id.password_auth)).getText().toString();

            if(login.length() > 5){
                if(password.length() > 5){
                    authPresentation.authUser(login,password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Пароль должен быть длиной не менее 6 символов", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Логин должен быть длиной не менее 6 символов", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void completeAuthUser(String response) {

        if(response.length() == 32){
            finish();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    new respCode().getMess(response), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}