package com.aSem.findingPetsMobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aSem.findingPetsMobile.CallbackInterface.CallbackToFullInfoWall;
import com.aSem.findingPetsMobile.CallbackInterface.CallbackToFullInfoWallMy;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.Presentation.FullInfoWallMyPresentation;
import com.aSem.findingPetsMobile.Presentation.FullInfoWallPresentation;
import com.aSem.findingPetsMobile.Server.respCode;
import com.aSem.findingPetsMobile.SqlIte.StaticSetting;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import static android.view.View.inflate;

public class FullInfoWallMy extends AppCompatActivity implements CallbackToFullInfoWallMy {
    public ApplicationComponent applicationComponent;
    public FullInfoWallMyPresentation fullInfoWallMyPresentation;
    final int DIALOG_EXIT = 1;
    private String phoneNumber = "";
    private String phoneNumber4Show = "";
    String idWalls;
    com.aSem.findingPetsMobile.GsonClasses.FullInfoWall fullInfoWall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_info_wall);
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        fullInfoWallMyPresentation = new FullInfoWallMyPresentation(this);
        Bundle arguments = getIntent().getExtras();
        idWalls = arguments.get("idWalls").toString();
        fullInfoWallMyPresentation.getFullInfoWallMy(idWalls);

        findViewById(R.id.fullInfo_showPhone).setVisibility(View.INVISIBLE);

    }

    @Override
    public void completeGetFullInfoWall(String response) {
        Gson gson = new Gson();
        fullInfoWall = gson.fromJson(response, com.aSem.findingPetsMobile.GsonClasses.FullInfoWall.class);

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

    }



    @Override
    public void completeDelWall(String response) {
        if (response.equals("200")){
            finish();
        } else {
            respCode.getMess(response);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_walls_info_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.myWallsInfo_delete:
                fullInfoWallMyPresentation.delWall(idWalls);
                StaticSetting.needUpdateWall = true;
                return true;
            case R.id.myWallsInfo_edit:
                Intent intent = new Intent(getBaseContext(), EditWallActivity.class);
                intent.putExtra("idWalls", idWalls);
                intent.putExtra("title", fullInfoWall.titleWalls);
                intent.putExtra("description", fullInfoWall.descriptionWalls);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(StaticSetting.needUpdateWall){
            fullInfoWallMyPresentation.getFullInfoWallMy(idWalls);
        }
    }
}