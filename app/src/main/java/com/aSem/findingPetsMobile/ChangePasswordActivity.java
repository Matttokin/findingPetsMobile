package com.aSem.findingPetsMobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.aSem.findingPetsMobile.CallbackInterface.CallbackToChangePassword;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.Presentation.ChangePasswordPresentation;
import com.aSem.findingPetsMobile.Presentation.MainPresentation;
import com.aSem.findingPetsMobile.Server.respCode;

public class ChangePasswordActivity extends AppCompatActivity implements CallbackToChangePassword {

    public ApplicationComponent applicationComponent;

    public ChangePasswordPresentation changePasswordPresentation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        changePasswordPresentation = new ChangePasswordPresentation(this);

        findViewById(R.id.changePassword_button).setOnClickListener(v -> {
            Bundle arguments = getIntent().getExtras();
            String login = arguments.get("login").toString();
            String oldPass = ((EditText)findViewById(R.id.changePassword_oldPassword)).getText().toString();
            String newPass = ((EditText)findViewById(R.id.changePassword_newPassword)).getText().toString();

            if( oldPass.length() > 5 && newPass.length() > 5 ){
                changePasswordPresentation.changePassword(login,oldPass,newPass);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Пароль должен быть длиной не менее 6 символов", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void completeChangePassword(String response) {
        if(response.length() == 32){
            finish();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    new respCode().getMess(response), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}