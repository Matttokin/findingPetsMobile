package com.aSem.findingPetsMobile;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aSem.findingPetsMobile.GsonClasses.PreviewWallInfo;
import com.aSem.findingPetsMobile.Server.ApiSetting;
import com.bumptech.glide.Glide;

import java.util.List;

public class StateAdapter extends ArrayAdapter<PreviewWallInfo> {

    private LayoutInflater inflater;
    private int layout;
    private List<PreviewWallInfo> previewWallInfoList;

    public StateAdapter(Context context, int resource, List<PreviewWallInfo> previewWallInfoList) {
        super(context, resource, previewWallInfoList);
        this.previewWallInfoList = previewWallInfoList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PreviewWallInfo previewWallInfo = previewWallInfoList.get(position);

        if(previewWallInfo.avatarUrl.equals("")){
            viewHolder.imgView.setImageResource(R.drawable.no_avatar);
        } else {
            Glide
                    .with(convertView)
                    .load(Uri.parse(ApiSetting.Domain+previewWallInfo.avatarUrl))
                    .into(viewHolder.imgView);
        }

        viewHolder.titleView.setText(previewWallInfo.titleWalls);
        viewHolder.townView.setText(previewWallInfo.nearestTown);



        return convertView;
    }
    private class ViewHolder {
        final ImageView imgView;
        final TextView titleView, townView;
        ViewHolder(View view){
            imgView = (ImageView)view.findViewById(R.id.img);
            titleView = (TextView) view.findViewById(R.id.title);
            townView = (TextView) view.findViewById(R.id.town);
        }
    }
}
