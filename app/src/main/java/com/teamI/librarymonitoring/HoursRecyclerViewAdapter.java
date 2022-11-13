package com.teamI.librarymonitoring;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamI.librarymonitoring.datacontainers.ServiceHours;

import org.w3c.dom.Text;

import java.util.List;

public class HoursRecyclerViewAdapter extends RecyclerView.Adapter<HoursRecyclerViewAdapter.ViewHolder>{

    private List<ServiceHours> lstServiceHours;

    public HoursRecyclerViewAdapter(List<ServiceHours> lstServiceHours) {
        this.lstServiceHours = lstServiceHours;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewServiceName;
        private TextView textViewHours;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHours = itemView.findViewById(R.id.textViewHours);
            textViewServiceName = itemView.findViewById(R.id.textViewServiceName);
        }

        public TextView getServiceNameTextView(){
            return textViewServiceName;
        }

        public TextView getHoursTextView(){
            return textViewHours;
        }
    }

    @NonNull
    @Override
    public HoursRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getServiceNameTextView().setText(lstServiceHours.get(position).getService());
        holder.getHoursTextView().setText(lstServiceHours.get(position).getHoursText());
    }

    @Override
    public int getItemCount() {
        return lstServiceHours.size();
    }
}
