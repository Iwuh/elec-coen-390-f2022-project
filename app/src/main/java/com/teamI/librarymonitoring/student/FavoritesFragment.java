package com.teamI.librarymonitoring.student;

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


public class FavoritesFragment extends DialogFragment {

    private Button Save_Button, Cancel_Button;
    private EditText favortieroom;
    PassDataInterface passDataInterface;


    public FavoritesFragment(PassDataInterface passDataInterface) {
        this.passDataInterface = passDataInterface;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        Save_Button = view.findViewById(R.id.savebtn);
        Cancel_Button = view.findViewById(R.id.cancelbtn);
        favortieroom = view.findViewById(R.id.favoriteroom_edittext);

        Cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Returned to Favorites Screen",Toast.LENGTH_LONG).show();
                dismiss();
            }
        });

        Save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String room = favortieroom.getText().toString();

                if(room.equals(""))
                {
                    Toast.makeText(getContext(),"Please fill text", Toast.LENGTH_SHORT).show();
                    return;
                }

                passDataInterface.DataReceived(room);

                dismiss();
            }
        });








        return view;
    }

}