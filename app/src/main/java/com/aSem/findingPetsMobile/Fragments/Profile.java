package com.aSem.findingPetsMobile.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aSem.findingPetsMobile.AddWallActivity;
import com.aSem.findingPetsMobile.CallbackInterface.CallbackToProfileFragment;
import com.aSem.findingPetsMobile.ChangeImgProfileActivity;
import com.aSem.findingPetsMobile.ChangePasswordActivity;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.GsonClasses.UserInfo;
import com.aSem.findingPetsMobile.MyWallsActivity;
import com.aSem.findingPetsMobile.Presentation.ProfileFragmentPresentation;
import com.aSem.findingPetsMobile.R;
import com.aSem.findingPetsMobile.AuthActivity;
import com.aSem.findingPetsMobile.RegActivity;
import com.aSem.findingPetsMobile.Server.ApiSetting;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class Profile extends Fragment implements CallbackToProfileFragment {
    public ApplicationComponent applicationComponent;
    public ProfileFragmentPresentation profileFragmentPresentation;

    ConstraintLayout constraintLayout1;
    LinearLayout linearLayout2;

    Button butAuht4FrProfile;
    Button butReg4FrProfile;
    public int MY_INTENT_REQUEST_CODE;

    TextView fio;
    TextView phone;
    TextView dateReg;
    ImageView imageView_prof;

    String login = "";

    public Profile() {
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        profileFragmentPresentation = new ProfileFragmentPresentation(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


       return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        constraintLayout1 = view.findViewById(R.id.inc1);
        linearLayout2 = view.findViewById(R.id.inc2);
        profileFragmentPresentation.getToken();

        butAuht4FrProfile = view.findViewById(R.id.butAuht4FrProfile);
        butReg4FrProfile = view.findViewById(R.id.butReg4FrProfile);

        fio = view.findViewById(R.id.profile_fio);
        phone = view.findViewById(R.id.profile_phone);
        dateReg = view.findViewById(R.id.profile_dateReg);

        imageView_prof = view.findViewById(R.id.imageView_prof);



        butAuht4FrProfile.setOnClickListener(v -> {
            startActivityForResult(new Intent(getContext(), AuthActivity.class), MY_INTENT_REQUEST_CODE);
        });
        butReg4FrProfile.setOnClickListener(v -> {
            startActivityForResult(new Intent(getContext(), RegActivity.class), MY_INTENT_REQUEST_CODE);
        });

        view.findViewById(R.id.exitBut).setOnClickListener(v -> {
            profileFragmentPresentation.exitUser();
            refreshFragment();
        });
        view.findViewById(R.id.myWallBut).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MyWallsActivity.class);
            startActivityForResult(intent, MY_INTENT_REQUEST_CODE);
        });
        view.findViewById(R.id.addWallBut).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddWallActivity.class);
            startActivityForResult(intent, MY_INTENT_REQUEST_CODE);
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }



    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.profile_change_img:
                Intent intent = new Intent(getContext(), ChangeImgProfileActivity.class);
                startActivityForResult(intent, MY_INTENT_REQUEST_CODE);
                return true;
            case R.id.profile_change_password:
                intent = new Intent(getContext(), ChangePasswordActivity.class);
                intent.putExtra("login", login);
                startActivityForResult(intent, MY_INTENT_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void completeGetToken(String token) {
        if (token.length() != 32){
            constraintLayout1.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
            setHasOptionsMenu(false);

        } else {
            constraintLayout1.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.GONE);
            profileFragmentPresentation.getUserInfo();
            setHasOptionsMenu(true);
        }
    }

    @Override
    public void completeExitUser(Boolean resp) {
        if(!resp){
            Toast toast = Toast.makeText(getContext(),
                    "Неизвестная ошибка", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void competeGetUserInfo(String json) {
        if(json.length() != 3) {
            Gson gson = new Gson();
            UserInfo userInfo = gson.fromJson(json, UserInfo.class);

            fio.setText(userInfo.fio);
            dateReg.setText(userInfo.dateRegister);
            phone.setText(userInfo.numberPhone);
            this.login = userInfo.loginUser;
            if(!userInfo.avatarUrl.equals("")){
                Glide
                        .with(this)
                        .load(Uri.parse(ApiSetting.Domain+userInfo.avatarUrl))
                        .into(imageView_prof);
            }
        } else {
            constraintLayout1.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
            profileFragmentPresentation.exitUser();
        }
    }

    private void refreshFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data); comment this unless you want to pass your result to the activity.
        refreshFragment();
    }


}
