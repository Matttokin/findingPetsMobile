package com.aSem.findingPetsMobile.SqlIte;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.aSem.findingPetsMobile.Security.MD5;
import com.aSem.findingPetsMobile.Server.ApiSetting;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;


public class SqlDataManager extends SQLiteOpenHelper {
    private static String DB_NAME = "PassAppLite.db3";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 4;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;


    public SqlDataManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 19)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
        createDB();
        //copyDataBase();
        this.getReadableDatabase();

    }

    public void createDB() {
        if (!checkDataBase()) {
            mDataBase = mContext.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            mDataBase.execSQL("CREATE TABLE IF NOT EXISTS \"user\" (\n" +
                    "\t\"idUser\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"tokenUser\"\tVARCHAR(32) " +
                    ");");


            mDataBase.execSQL("INSERT INTO \"user\" (\"idUser\",\"tokenUser\") VALUES (1,'rrgrthrtdgytretuseytsd');");
        }
    }

    public void SetDb(SQLiteDatabase db) {
        mDataBase = db;
    }


    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private String trStr(String inStr) {
        return inStr.substring(1, inStr.length() - 1);
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    public Cursor getReqest(String sql) {

        return this.mDataBase.rawQuery(sql, null);

    }

    public void setReqest(String sql) {
        this.mDataBase.execSQL(sql);
    }

    public String authUserOnServer(String login, String password) throws IOException {

        String data = "login=" + login + "&password=" + MD5.getHash(password);
        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiAuthUser + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String response = trStr(reader.readLine());

        if (response.length() == 32) {
            mDataBase.execSQL("UPDATE user SET tokenUser = '" + response + "' WHERE idUser = 1");
        }

        return response;
    }

    public String regUserOnServer(String login, String fio, String phone, String password) throws IOException {

        String data = "login=" + login + "&fio=" + fio + "&phone=" + phone + "&password=" + MD5.getHash(password);

        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiRegUser + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String response = trStr(reader.readLine());
        if (response.equals("200")) {
            return authUserOnServer(login, password);
        }

        return response;
    }

    public String getUserInfo() throws IOException {

        Cursor Faf = getReqest("Select tokenUser from user WHERE idUser = 1 ;");
        String token;
        if (Faf.moveToNext()) {
            token = Faf.getString(0);
        } else {
            token = "";
        }

        String data = "token=" + token;
        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiGetUserInfo + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");

        return response;
    }

    public String changePassword(String login, String oldPassword, String newPassword) throws IOException {

        String data = "login=" + login + "&password=" + MD5.getHash(oldPassword) + "&newPassword=" + MD5.getHash(newPassword);

        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiChangePassword + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String response = trStr(reader.readLine());
        if (response.equals("200")) {
            return authUserOnServer(login, newPassword);
        }

        return response;
    }

    public String getWallInfoAll(String offset, String limit, String searchQry) throws IOException {


        String data = "offset=" + offset + "&limit=" + limit + "&searchQry=" + searchQry;

        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiGetWallInfoAll + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");

        return response;
    }

    public String getFullInfoWall(String idWalls) throws IOException {


        String data = "idWalls=" + idWalls;

        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiGetFullInfoWall + data);
        Log.d("111", ApiSetting.ApiDomain + ApiSetting.ApiGetFullInfoWall + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");

        return response;
    }

    public String getWallInfoMy() throws IOException {

        Cursor Faf = getReqest("Select tokenUser from user WHERE idUser = 1 ;");
        String token;
        if (Faf.moveToNext()) {
            token = Faf.getString(0);
        } else {
            token = "";
        }

        String data = "token=" + token;

        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiGetWallInfoMy + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");

        return response;
    }

    public String delWall(String idWall) throws IOException {

        Cursor Faf = getReqest("Select tokenUser from user WHERE idUser = 1 ;");
        String token;
        if (Faf.moveToNext()) {
            token = Faf.getString(0);
        } else {
            token = "";
        }

        String data = "token=" + token + "&idWall=" + idWall;
        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiDelWall + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");

        return response;
    }

    public String addWall(String idCategory, String titleWalls, String descriptionWalls, String latitude, String longitude, String radius, String nearestTown, String urlImageAvatar) throws IOException {

        Cursor Faf = getReqest("Select tokenUser from user WHERE idUser = 1 ;");
        String token;
        if (Faf.moveToNext()) {
            token = Faf.getString(0);
        } else {
            token = "";
        }
        String imgName = "";
        if(!urlImageAvatar.equals("")) {
            imgName = uploadImg(urlImageAvatar);
        }
        String data = "token=" + token + "&idCategory=" + idCategory
                + "&titleWalls=" + titleWalls + "&descriptionWalls=" + descriptionWalls
                + "&latitude=" + latitude + "&longitude=" + longitude
                + "&radius=" + radius + "&nearestTown=" + nearestTown
                + "&urlImageAvatar=" + imgName.substring(1,imgName.length() -2);

        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiAddWall + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");

        return response;
    }

    public String getListCategory() throws IOException {
        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiGetListCategory);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");

        return response;
    }

    public String getListTown() throws IOException {
        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiGetListTown);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");

        return response;
    }

    public String getWallInfoAllWithParam(String offset, String limit, String searchQry, String category, String town, String sortBy) throws IOException {


        String data = "offset=" + offset + "&limit=" + limit + "&searchQry=" + searchQry
                + "&category=" + category + "&town=" + town + "&sortBy=" + sortBy;

        Log.d("!1", ApiSetting.ApiDomain + ApiSetting.ApiGetWallInfoAllWithParam + data);
        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiGetWallInfoAllWithParam + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");

        return response;
    }

    public String editWall(String idWall, String titleWalls, String descriptionWalls) throws IOException {

        Cursor Faf = getReqest("Select tokenUser from user WHERE idUser = 1 ;");
        String token;
        if (Faf.moveToNext()) {
            token = Faf.getString(0);
        } else {
            token = "";
        }

        String data = "token=" + token + "&idWall=" + idWall + "&titleWalls=" + titleWalls
                + "&descriptionWalls=" + descriptionWalls;

        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.ApiEditWall + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");

        return response;
    }

    public String sendImageProfile(String urlImg) throws IOException {

        Cursor Faf = getReqest("Select tokenUser from user WHERE idUser = 1 ;");
        String token;
        if (Faf.moveToNext()) {
            token = Faf.getString(0);
        } else {
            token = "";
        }
        String imgName = "";
        if(!urlImg.equals("")) {
            imgName = uploadImg(urlImg);
        }

        String data = "token=" + token + "&imgName=" + imgName;

        URL url = new URL(ApiSetting.ApiDomain + ApiSetting.SetImgProfile + data);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = trStr(reader.readLine());
        response = response.replace("\\\"", "\"");
        return response;
    }

    private String uploadImg(String urlImg)
    {
        String url = ApiSetting.ApiDomain + ApiSetting.SendImgProfile;
        InputStream is = null;
        String response = "";
        MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        mpEntity.addPart("profile_pic", new FileBody(new File(urlImg)));

        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            httpPost.setEntity(mpEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            return sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return null;
    }

}
