package com.alok.pandemic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView t1, t2, t3, t4;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        t1 = view.findViewById(R.id.t1);
        t1.setMovementMethod(LinkMovementMethod.getInstance());
        t2 = view.findViewById(R.id.t2);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        t3 = view.findViewById(R.id.t3);
        t3.setMovementMethod(LinkMovementMethod.getInstance());
        t4 = view.findViewById(R.id.t4);
        t4.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}
