package com.teamI.librarymonitoring.librarian;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.student.PassDataInterface;


public class AddSensorsFragment extends DialogFragment {

    private Button Add_Button, Cancel_Button;
    private EditText addsensor;
    PassDataInterface passDataInterface;

    public AddSensorsFragment(PassDataInterface passDataInterface) {
        // Required empty public constructor
        this.passDataInterface = passDataInterface;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_sensors, container, false);

        Add_Button = view.findViewById(R.id.add_sensorbtn);
        Cancel_Button = view.findViewById(R.id.Acancelbtn);
        addsensor = view.findViewById(R.id.addsensor_edittext);

        Cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Returned to Sensors Connected Screen",Toast.LENGTH_LONG).show();
                dismiss();
            }
        });

        Add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add_sensor = addsensor.getText().toString();

                if(add_sensor.equals(""))
                {
                    Toast.makeText(getContext(),"Please fill text", Toast.LENGTH_SHORT).show();
                    return;
                }
                

                dismiss();
            }
        });


        return view;
    }
}