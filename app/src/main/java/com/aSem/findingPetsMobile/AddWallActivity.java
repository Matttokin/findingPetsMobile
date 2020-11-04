package com.aSem.findingPetsMobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.aSem.findingPetsMobile.CallbackInterface.CallbackToAddWall;
import com.aSem.findingPetsMobile.CallbackInterface.CallbackToCategoryAndTown;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.Presentation.AddWallPresentation;
import com.aSem.findingPetsMobile.Presentation.AuthPresentation;
import com.aSem.findingPetsMobile.Server.respCode;
import com.aSem.findingPetsMobile.SqlIte.StaticSetting;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static java.security.AccessController.getContext;

public class AddWallActivity extends AppCompatActivity implements CallbackToAddWall, CallbackToCategoryAndTown {

    public ApplicationComponent applicationComponent;
    public AddWallPresentation addWallPresentation;
    private ImageView imageView;
    private final int Pick_image = 1;
    String imageUriStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wall);
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        addWallPresentation = new AddWallPresentation(this);
        addWallPresentation.getListCategory();
        addWallPresentation.getListTown();

        findViewById(R.id.add_but).setOnClickListener(v->{
            String title = ((EditText)findViewById(R.id.add_title)).getText().toString();
            String description = ((EditText)findViewById(R.id.add_disc)).getText().toString();
            String town = ((Spinner)findViewById(R.id.add_town)).getSelectedItem().toString();
            String latitude = ((EditText)findViewById(R.id.add_latitude)).getText().toString();
            String longitude = ((EditText)findViewById(R.id.add_longitude)).getText().toString();
            String radius = ((EditText)findViewById(R.id.add_radius)).getText().toString();
            String category = ((Spinner)findViewById(R.id.add_category)).getSelectedItem().toString();
            if(title.length() > 0){
                if(description.length() > 0){
                    if(Integer.parseInt(radius) >= 0){
                        if(latitude.length() > 0 && longitude.length() > 0){
                            addWallPresentation.addWall(category,title,description,latitude,longitude,radius,town,imageUriStr);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Выберите местоположение", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Радиус введен неверно", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Введите описание", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Введите заголовок", Toast.LENGTH_SHORT).show();
            }

        });

        findViewById(R.id.button_goSelectOnMap).setOnClickListener(v->{
            startActivity(new Intent(this,SelectOnMaps.class));
        });
        imageView = (ImageView) findViewById(R.id.add_wall_imageView);

        //Связываемся с нашей кнопкой Button:
        Button PickImage = (Button) findViewById(R.id.addWall_selImg);
        //Настраиваем для нее обработчик нажатий OnClickListener:
        PickImage.setOnClickListener(view -> {

            //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //Тип получаемых объектов - image:
            photoPickerIntent.setType("image/*");
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(photoPickerIntent, Pick_image);
        });
    }

    @Override
    public void completeAddWall(String response) {
        if(response.equals("200")){
            StaticSetting.needUpdateWall = true;
            finish();
        } else {
            Toast.makeText(getApplicationContext(),
                    new respCode().getMess(response), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void completeGetListCategory(String response) {
        Gson gson = new Gson();
        List<String> stList= gson.fromJson(response, List.class);
        Spinner spinnerTown = (Spinner) findViewById(R.id.add_category);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, stList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTown.setAdapter(adapter);
    }

    @Override
    public void completeGetListTown(String response) {
        Gson gson = new Gson();
        List<String> stList= gson.fromJson(response, List.class);
        Spinner spinnerTown = (Spinner) findViewById(R.id.add_town);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, stList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTown.setAdapter(adapter);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(StaticSetting.needEnterCord){
            ((EditText)findViewById(R.id.add_latitude)).setText(StaticSetting.lastLatitude);
            ((EditText)findViewById(R.id.add_longitude)).setText(StaticSetting.lastLongitude);
            StaticSetting.needEnterCord = false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Pick_image:
                if (resultCode == RESULT_OK) {
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = data.getData();
                        imageUriStr = getPath(data.getData());
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
}