package com.aSem.findingPetsMobile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.Fragments.CustomViewPager;
import com.aSem.findingPetsMobile.Fragments.FragmentAdapter;
import com.aSem.findingPetsMobile.Presentation.MainPresentation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public ApplicationComponent applicationComponent;

    public MainPresentation mainPresentation;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        mainPresentation = new MainPresentation(this);

        CustomViewPager vp = findViewById(R.id.viewPager);
        vp.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        BottomNavigationView bottomNavigationView = findViewById(R.id.bnv);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.wall_menu_nav: vp.setCurrentItem(0); break;
                case R.id.profilel_menu_nav: vp.setCurrentItem(1); break;
            }
            return true;
        });
    }

}



