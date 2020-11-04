package com.aSem.findingPetsMobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.aSem.findingPetsMobile.CallbackInterface.CallbackToChangeImageProfile;
import com.aSem.findingPetsMobile.Dagger.AndroidApplication;
import com.aSem.findingPetsMobile.Dagger.ApplicationComponent;
import com.aSem.findingPetsMobile.Presentation.ChangeImageProfilePresentation;
import com.aSem.findingPetsMobile.Presentation.ChangePasswordPresentation;
import com.aSem.findingPetsMobile.Server.respCode;
import com.aSem.findingPetsMobile.SqlIte.StaticSetting;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import 	java.util.Base64.Encoder;

public class ChangeImgProfileActivity extends AppCompatActivity implements CallbackToChangeImageProfile {


    private ImageView imageView;
    private final int Pick_image = 1;
    String imageUriStr = "";

    public ApplicationComponent applicationComponent;
    public ChangeImageProfilePresentation changeImageProfilePresentation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_img_profile);
        this.applicationComponent = AndroidApplication.getApplicationComponent();
        changeImageProfilePresentation = new ChangeImageProfilePresentation(this);

        Button sendBt = (Button) findViewById(R.id.send_changeImg);
        //Связываемся с нашим ImageView:
        imageView = (ImageView) findViewById(R.id.imageView_changeImg);

        //Связываемся с нашей кнопкой Button:
        Button PickImage = (Button) findViewById(R.id.select_changeImg);
        //Настраиваем для нее обработчик нажатий OnClickListener:
        PickImage.setOnClickListener(view -> {

            //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //Тип получаемых объектов - image:
            photoPickerIntent.setType("image/*");
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(photoPickerIntent, Pick_image);
        });

        sendBt.setOnClickListener(v -> {
            if(!imageUriStr.equals("")){
                changeImageProfilePresentation.sendImageProfile(imageUriStr);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Выберите изображение", Toast.LENGTH_SHORT).show();
            }
        });


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

    @Override
    public void completeSendImageProfile(String response) {
        if(response.equals("200")){
            StaticSetting.needUpdateWall = true;
            finish();
        } else {
            Toast.makeText(getApplicationContext(),
                    new respCode().getMess(response), Toast.LENGTH_SHORT).show();
        }
    }
}