package com.aSem.findingPetsMobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.aSem.findingPetsMobile.CallbackInterface.CallbackToEditWall;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.Presentation.AuthPresentation;
import com.aSem.findingPetsMobile.Presentation.EditWallPresentation;
import com.aSem.findingPetsMobile.Server.respCode;
import com.aSem.findingPetsMobile.SqlIte.StaticSetting;

public class EditWallActivity extends AppCompatActivity implements CallbackToEditWall {

    EditText titleEd;
    EditText descEd;

    public ApplicationComponent applicationComponent;
    public EditWallPresentation editWallPresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wall);
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        editWallPresentation = new EditWallPresentation(this);

        Bundle arguments = getIntent().getExtras();
        String idWalls = arguments.get("idWalls").toString();
        String title = arguments.get("title").toString();
        String description = arguments.get("description").toString();

        titleEd = findViewById(R.id.editWall_title);
        descEd = findViewById(R.id.editWall_desc);

        titleEd.setText(title);
        descEd.setText(description);

        findViewById(R.id.editWall_but).setOnClickListener(v -> {

            editWallPresentation.editWall(idWalls,titleEd.getText().toString(),descEd.getText().toString());
        });
    }

    @Override
    public void completeEditWall(String response) {
        if(response.equals("200")){
            StaticSetting.needUpdateWall = true;
            finish();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    new respCode().getMess(response), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}