package com.aSem.findingPetsMobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aSem.findingPetsMobile.CallbackInterface.CallbackToFullInfoWall;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.Presentation.FullInfoWallPresentation;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class FullInfoWall extends AppCompatActivity implements CallbackToFullInfoWall {
    public ApplicationComponent applicationComponent;
    public FullInfoWallPresentation fullInfoWallPresentation;
    final int DIALOG_EXIT = 1;
    private String phoneNumber = "";
    private String phoneNumber4Show = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_info_wall);
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        fullInfoWallPresentation = new FullInfoWallPresentation(this);
        Bundle arguments = getIntent().getExtras();
        String idWalls = arguments.get("idWalls").toString();
        fullInfoWallPresentation.getFullInfoWall(idWalls);


    }

    @Override
    public void completeGetFullInfoWall(String response) {
        Gson gson = new Gson();
        com.aSem.findingPetsMobile.GsonClasses.FullInfoWall fullInfoWall = gson.fromJson(response, com.aSem.findingPetsMobile.GsonClasses.FullInfoWall.class);

        TextView tvTitle = findViewById(R.id.fullInfo_title);
        TextView tvDesc = findViewById(R.id.fullInfo_desc);
        ImageView img = findViewById(R.id.imageView_full);


        tvTitle.setText( fullInfoWall.titleWalls);
        tvDesc.setText(fullInfoWall.descriptionWalls);
        this.phoneNumber = fullInfoWall.numberPhone;

        Glide
                .with(this)
                .load(Uri.parse(fullInfoWall.urlImageAvatar))
                .into(img);

        findViewById(R.id.fullInfo_showMap).setOnClickListener(v->{
            Intent intent = new Intent(getBaseContext(), ShowOnMapActivity.class);
            intent.putExtra("latitude", fullInfoWall.latitude);
            intent.putExtra("longitude", fullInfoWall.longitude);
            intent.putExtra("radius", fullInfoWall.radius);
            intent.putExtra("title", fullInfoWall.titleWalls);
            startActivity(intent);
        });

        findViewById(R.id.fullInfo_showPhone).setOnClickListener(v->{
            fullInfoWallPresentation.getToken();
        });
    }

    @Override
    public void completeGetToken(String response) {
        if(response.length() == 32){
            phoneNumber4Show = phoneNumber;
        } else {
            phoneNumber4Show = "Для просмотра номера необходимо авторизоваться";
        }

        showDialog(DIALOG_EXIT);
    }
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_EXIT) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            // заголовок
            adb.setTitle("Номер телефона");
            // сообщение
            adb.setMessage(phoneNumber4Show);
            // иконка
            adb.setIcon(android.R.drawable.ic_dialog_info);
            // кнопка положительного ответа
            adb.setPositiveButton("Ок", myClickListener);
            // создаем диалог
            return adb.create();
        }
        return super.onCreateDialog(id);
    }
    DialogInterface.OnClickListener myClickListener = (dialog, which) -> {
        switch (which) {
            // положительная кнопка
            case Dialog.BUTTON_POSITIVE:
                //finish();
                break;
        }
    };
}