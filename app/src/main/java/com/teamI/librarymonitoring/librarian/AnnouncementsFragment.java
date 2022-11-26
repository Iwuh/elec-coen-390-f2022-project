package com.teamI.librarymonitoring.librarian;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.teamI.helper.FirebaseHelper;
import com.teamI.librarymonitoring.PassAnnouncementInterface;
import com.teamI.librarymonitoring.R;
import com.teamI.librarymonitoring.datacontainers.Announcement;
import com.teamI.librarymonitoring.student.PassDataInterface;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AnnouncementsFragment extends DialogFragment {

    private Button sendannouncement, cancelannouncement;
    private EditText announcement_editTextText;
    private TextView areyousure_textView;
    FirebaseHelper firebaseHelper;
    PassAnnouncementInterface passAnnouncementInterface;

    public AnnouncementsFragment(PassAnnouncementInterface passAnnouncementInterface) {
        // Required empty public constructor
        this.passAnnouncementInterface = passAnnouncementInterface;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_announcements, container, false);

        sendannouncement = view.findViewById(R.id.sendannouncement);
        cancelannouncement = view.findViewById(R.id.cancelannouncement);
        announcement_editTextText = view.findViewById(R.id.announcement_editTextText);
        areyousure_textView = view.findViewById(R.id.areyousure_textView);

        cancelannouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cancelannouncement.getText().equals("Cancel"))
                {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Returned to Main Screen",Toast.LENGTH_LONG).show();
                    dismiss();

                }
                else if(cancelannouncement.getText().equals("NO"))
                {
                    sendannouncement.setText("Send");
                    cancelannouncement.setText("Cancel");
                    announcement_editTextText.setEnabled(true);

                }

            }
        });

        sendannouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String add_announcements = announcement_editTextText.getText().toString();
                announcement_editTextText.setEnabled(true);

                if(add_announcements.equals(""))
                {
                    Toast.makeText(getContext(),"Please fill text", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    if(sendannouncement.getText().equals("Send"))
                    {
                        areyousure_textView.setText("Are you Sure?");
                        sendannouncement.setText("YES");
                        cancelannouncement.setText("NO");
                        announcement_editTextText.setEnabled(false);


                    }

                    else if(sendannouncement.getText().equals("YES"))
                    {
                        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        String timestamp = "Timestamp: " + s.format(new Date());

                        //String final_data = add_announcements + ",," + timestamp;

                        //passAnnouncementInterface.AnnouncementReceived(add_announcements);
                        //passAnnouncementInterface.TimestampReceived(timestamp);
                        Announcement announcement = new Announcement(add_announcements,timestamp);
                        System.out.println(announcement.toString());
                        firebaseHelper.setAnnouncement(announcement);
                        sendannouncement.setText("Send");
                        cancelannouncement.setText("Cancel");
                        dismiss();


                    }
                }




            }
        });
        return view;
    }



}