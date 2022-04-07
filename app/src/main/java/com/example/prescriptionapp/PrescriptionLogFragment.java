package com.example.prescriptionapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setText1(((MainActivity)getActivity()).file1);
        setText2(((MainActivity)getActivity()).file2);
        setText3(((MainActivity)getActivity()).file3);
        setText4(((MainActivity)getActivity()).file4);
    }

    public void setText1(String text) {
        TextView view = (TextView) getView().findViewById(R.id.drug1);
        view.setText(text);
    }

    public void setText2(String text) {
        TextView view = (TextView) getView().findViewById(R.id.drug2);
        view.setText(text);
    }

    public void setText3(String text) {
        TextView view = (TextView) getView().findViewById(R.id.drug3);
        view.setText(text);
    }

    public void setText4(String text) {
        TextView view = (TextView) getView().findViewById(R.id.drug4);
        view.setText(text);
    }


}