package com.teamI.librarymonitoring;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamI.librarymonitoring.datacontainers.DesktopRoom;

import java.util.List;

public class DesktopsRecyclerViewAdapter extends RecyclerView.Adapter<DesktopsRecyclerViewAdapter.ViewHolder>{

    private List<DesktopRoom> lstDesktopRooms;

    public DesktopsRecyclerViewAdapter(List<DesktopRoom> lstDesktopRooms) {
        this.lstDesktopRooms = lstDesktopRooms;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewRoomCode;
        private TextView textViewNbrDesktops;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewRoomCode = itemView.findViewById(R.id.textViewRoomCode);
            textViewNbrDesktops = itemView.findViewById(R.id.textViewNbrDesktops);
        }

        public TextView getRoomCodeTextView(){
            return textViewRoomCode;
        }

        public TextView getNbrDesktopsTextView(){
            return textViewNbrDesktops;
        }
    }

    @NonNull
    @Override
    public DesktopsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desktop_room_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesktopsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getRoomCodeTextView().setText(lstDesktopRooms.get(position).getRoomCodeString());
        holder.getNbrDesktopsTextView().setText(Integer.toString(lstDesktopRooms.get(position).getNumberOfDesktops()) + " desktops");
    }

    @Override
    public int getItemCount() {
        return lstDesktopRooms.size();
    }
}

