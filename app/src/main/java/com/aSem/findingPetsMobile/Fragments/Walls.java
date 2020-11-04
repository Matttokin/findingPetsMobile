package com.aSem.findingPetsMobile.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aSem.findingPetsMobile.CallbackInterface.CallbackToCategoryAndTown;
import com.aSem.findingPetsMobile.CallbackInterface.CallbackToWallsFragment;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.FullInfoWall;
import com.aSem.findingPetsMobile.GsonClasses.PreviewWallInfo;
import com.aSem.findingPetsMobile.Presentation.WallsFragmentPresentation;
import com.aSem.findingPetsMobile.R;
import com.aSem.findingPetsMobile.SqlIte.StaticSetting;
import com.aSem.findingPetsMobile.StateAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Walls extends Fragment implements CallbackToWallsFragment, CallbackToCategoryAndTown {

    public ApplicationComponent applicationComponent;
    public WallsFragmentPresentation wallsFragmentPresentation;
    private ListView listView;
    PreviewWallInfo[] previewWallInfo;
    int MY_INTENT_REQUEST_CODE;
    private SearchView searchView;
    private ConstraintLayout constraintLayout1;
    private ConstraintLayout constraintLayout2;
    private Spinner spinnerCategory;
    private Spinner spinnerTown;

    private String thisCategory = "";
    private String thisTown = "";
    private String thisSortBy = "";

    public Walls() {
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        wallsFragmentPresentation = new WallsFragmentPresentation(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_walls, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wallsFragmentPresentation.getWallInfoAll("0","0","");
        constraintLayout1 = view.findViewById(R.id.inc3);
        constraintLayout2 = view.findViewById(R.id.inc4);
        constraintLayout2.setVisibility(View.GONE);

        spinnerCategory = (Spinner) view.findViewById(R.id.spinner_search_category);
        spinnerTown = (Spinner) view.findViewById(R.id.spinner_search_town);
        listView = view.findViewById(R.id.wallListView);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getContext(), FullInfoWall.class);
            intent.putExtra("idWalls", previewWallInfo[(int) id].idWalls);
            startActivityForResult(intent, MY_INTENT_REQUEST_CODE);
        });
        view.findViewById(R.id.seach_but).setOnClickListener(v->{
            setHasOptionsMenu(true);
            wallsFragmentPresentation.getWallInfoAllWithParam("0","0","",thisCategory,thisTown,thisSortBy);
            constraintLayout1.setVisibility(View.VISIBLE);
            constraintLayout2.setVisibility(View.GONE);
        });

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rGroup_search);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButton_new:
                    thisSortBy = "new";
                    break;
                case R.id.radioButton_old:
                    thisSortBy = "old";
                    break;
                case R.id.radioButton_alph:
                    thisSortBy = "alph";
                    break;
                default:
                    break;
            }
        });
        view.findViewById(R.id.seach_reset).setOnClickListener(v->{
            setHasOptionsMenu(true);
            constraintLayout1.setVisibility(View.VISIBLE);
            constraintLayout2.setVisibility(View.GONE);
            thisCategory = "";
            thisTown = "";
            thisSortBy = "";
            radioGroup.check(R.id.radioButton_new);
            wallsFragmentPresentation.getWallInfoAll("0","0","");
        });
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.walls_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
        final MenuItem searchItem = menu.findItem(R.id.wall_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if(thisCategory.equals("") && thisTown.equals("") && thisSortBy.equals("")){
                    wallsFragmentPresentation.getWallInfoAll("0","0",newText);
                } else {

                    wallsFragmentPresentation.getWallInfoAllWithParam("0","0",newText,thisCategory,thisTown,thisSortBy);
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);
    }

    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.setting_search:
                wallsFragmentPresentation.getListCategory();
                wallsFragmentPresentation.getListTown();
                setHasOptionsMenu(false);
                constraintLayout1.setVisibility(View.GONE);
                constraintLayout2.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void completeGetWallInfoAll(String response) {

        Gson gson = new Gson();
        if(response.length() > 5){
            previewWallInfo = gson.fromJson(response, PreviewWallInfo[].class);

            ArrayList<PreviewWallInfo> arrayWallPrev = new ArrayList<PreviewWallInfo>();
            for (PreviewWallInfo elem:
                    previewWallInfo) {
                arrayWallPrev.add(elem);
            }

            StateAdapter adapter = new StateAdapter(getContext(), R.layout.wall_adapter, arrayWallPrev);

            listView.setAdapter(adapter);
        } else {
            StateAdapter adapter = new StateAdapter(getContext(), R.layout.wall_adapter, new ArrayList<PreviewWallInfo>());

            listView.setAdapter(adapter);
        }

    }

    @Override
    public void completeGetWallInfoAllWithParam(String response) {

        completeGetWallInfoAll(response);
    }

    @Override
    public void completeGetListCategory(String response) {
        Gson gson = new Gson();
        List<String> stList= gson.fromJson(response, List.class);
        stList.add(0,"Все категории");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, stList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setSelection(stList.indexOf(thisCategory));
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                thisCategory = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinnerCategory.setOnItemSelectedListener(itemSelectedListener);
    }

    @Override
    public void completeGetListTown(String response) {
        Gson gson = new Gson();
        List<String> stList= gson.fromJson(response, List.class);
        stList.add(0,"Все города");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, stList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTown.setAdapter(adapter);
        spinnerTown.setSelection(stList.indexOf(thisTown));
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                thisTown = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinnerTown.setOnItemSelectedListener(itemSelectedListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(StaticSetting.needUpdateWall){
            if(thisCategory.equals("") && thisTown.equals("") && thisSortBy.equals("")){
                wallsFragmentPresentation.getWallInfoAll("0","0","");
            } else {

                wallsFragmentPresentation.getWallInfoAllWithParam("0","0","",thisCategory,thisTown,thisSortBy);
            }
        }
    }
}
