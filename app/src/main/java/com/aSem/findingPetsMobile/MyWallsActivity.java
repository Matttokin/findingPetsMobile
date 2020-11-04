package com.aSem.findingPetsMobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aSem.findingPetsMobile.CallbackInterface.CallbackToWallsFragment;
import com.aSem.findingPetsMobile.CallbackInterface.CallbackToWallsMy;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.GsonClasses.PreviewWallInfo;
import com.aSem.findingPetsMobile.Presentation.FullInfoWallPresentation;
import com.aSem.findingPetsMobile.Presentation.MyWallsPresentation;
import com.aSem.findingPetsMobile.Presentation.WallsFragmentPresentation;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyWallsActivity extends AppCompatActivity implements CallbackToWallsMy {

    public ApplicationComponent applicationComponent;
    public MyWallsPresentation myWallsPresentation;
    private ListView listView;
    PreviewWallInfo[] previewWallInfo;

    public MyWallsActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_walls);
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        myWallsPresentation = new MyWallsPresentation(this);
        myWallsPresentation.getWallInfoMy();
        listView = findViewById(R.id.myWallsList);

    }



    @Override
    public void completeGetWallInfoMy(String response) {
        Gson gson = new Gson();
        if(response.length() > 5){
            previewWallInfo = gson.fromJson(response, PreviewWallInfo[].class);

            ArrayList<PreviewWallInfo> arrayWallPrev = new ArrayList<PreviewWallInfo>();
            for (PreviewWallInfo elem:
                    previewWallInfo) {
                arrayWallPrev.add(elem);
            }

            StateAdapter adapter = new StateAdapter(getBaseContext(), R.layout.wall_adapter, arrayWallPrev);

            listView.setAdapter(adapter);
        } else {
            StateAdapter adapter = new StateAdapter(getBaseContext(), R.layout.wall_adapter, new ArrayList<PreviewWallInfo>());

            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getBaseContext(), FullInfoWallMy.class);
            intent.putExtra("idWalls", previewWallInfo[(int) id].idWalls);
            startActivity(intent);
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        myWallsPresentation.getWallInfoMy();
    }
}
