package com.example.prescriptionapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrescriptionLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrescriptionLogFragment extends Fragment {

TextView drugView1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int progress = 0;
    private int max = 100;
    private int id = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrescriptionLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrescriptionLog.
     */
    // TODO: Rename and change types and number of parameters
    public static PrescriptionLogFragment newInstance(String param1, String param2) {
        PrescriptionLogFragment fragment = new PrescriptionLogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_prescription_log, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            setText1(((MainActivity)getActivity()).file1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            setText2(((MainActivity)getActivity()).file2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            setText3(((MainActivity)getActivity()).file3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            setText4(((MainActivity)getActivity()).file4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setText1(String text) throws InterruptedException {
        TextView view = (TextView) getView().findViewById(R.id.drug1);
        view.setText(text);
        progress+=10;
        show_Notification(text);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setText2(String text) throws InterruptedException {
        TextView view = (TextView) getView().findViewById(R.id.drug2);
        view.setText(text);
        progress+=10;
        show_Notification(text);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setText3(String text) throws InterruptedException {
        TextView view = (TextView) getView().findViewById(R.id.drug3);
        view.setText(text);
        progress+=10;
        show_Notification(text);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setText4(String text) throws InterruptedException {
        progress+=10;
        TextView view = (TextView) getView().findViewById(R.id.drug4);
        view.setText(text);
        show_Notification(text);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String text) throws InterruptedException {

        if (progress >= 100){
            progress = 0;
            max = 0;
        }

        Intent intent=new Intent(this.getContext(),MainActivity.class);
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_HIGH);
        PendingIntent pendingIntent=PendingIntent.getActivity(getContext(),1,intent,0);
        Notification notification=new Notification.Builder(getContext(),CHANNEL_ID)
                .setContentText(text)
                .setContentTitle("Prescription Reminder")
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Okay",pendingIntent)
                .setChannelId(CHANNEL_ID).setProgress(max,progress+=10,false)
                .setSmallIcon(android.R.drawable.ic_dialog_alert).setColor(22222).setColorized(true)
                .build();



        NotificationManager notificationManager=(NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(id++,notification);

    }

    public void startTimer() throws InterruptedException {
        while (progress <100){
            progress++;
        }
    }
}
