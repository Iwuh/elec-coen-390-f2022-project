package com.teamI.librarymonitoring.librarian;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.student.PassDataInterface;


public class DeleteSensorsFragment extends DialogFragment {

    private Button Delete_Button, Cancel_Button;
    private EditText deletesensor;
    PassDataInterface passDataInterface;

    public DeleteSensorsFragment(PassDataInterface passDataInterface) {
        // Required empty public constructor
        this.passDataInterface = passDataInterface;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_sensors, container, false);

        Delete_Button = view.findViewById(R.id.delete_sensorbtn);
        Cancel_Button = view.findViewById(R.id.Dcancel_sensorbtn);
        deletesensor = view.findViewById(R.id.deletesensor_edittext);

        Cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Returned to Sensors Connected Screen",Toast.LENGTH_LONG).show();
                dismiss();
            }
        });

        Delete_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delete_sensor = deletesensor.getText().toString();

                if(delete_sensor.equals(""))
                {
                    Toast.makeText(getContext(),"Please fill text", Toast.LENGTH_SHORT).show();
                    return;
                }

                passDataInterface.DataReceived(delete_sensor);

                dismiss();
            }
        });

        return view;
    }
}