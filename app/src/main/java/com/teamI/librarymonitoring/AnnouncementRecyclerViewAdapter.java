package com.teamI.librarymonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamI.librarymonitoring.datacontainers.Announcement;
import com.teamI.librarymonitoring.datacontainers.SensorReading;

import java.util.List;

public class AnnouncementRecyclerViewAdapter extends RecyclerView.Adapter<AnnouncementRecyclerViewAdapter.ViewHolder> {

    List<Announcement> announcementList;

    public AnnouncementRecyclerViewAdapter(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewAnnouncementData;
        private TextView textViewAnnouncementTimestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAnnouncementData = itemView.findViewById(R.id.announcemnt_textView);
            textViewAnnouncementTimestamp = itemView.findViewById(R.id.announcemnt_timestamp_textView);
        }

        public TextView getAnnouncementdataTextView(){
            return textViewAnnouncementData;
        }

        public TextView getAnnouncementTimestampTextView(){
            return textViewAnnouncementTimestamp;
        }
    }

    @NonNull
    @Override
    public AnnouncementRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_announcement_recycler_view_adapter, parent, false);
        return new AnnouncementRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementRecyclerViewAdapter.ViewHolder holder, int position) {
        // display the readings from the list
        holder.getAnnouncementdataTextView().setText(announcementList.get(position).getMessage());
        holder.getAnnouncementTimestampTextView().setText(announcementList.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }
}