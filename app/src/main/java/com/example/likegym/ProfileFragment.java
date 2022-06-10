package com.example.likegym;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    String name = data.getStringExtra("name");
                    ((TextView)getView().findViewById(R.id.name)).setText(name);

                    String nick = data.getStringExtra("nick");
                    ((TextView)getView().findViewById(R.id.nickname)).setText(nick);
                }
                break;
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        view.findViewById(R.id.btnsettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                intent.putExtra("name", ((TextView)getView().findViewById(R.id.name)).getText().toString());
                intent.putExtra("nick", ((TextView)getView().findViewById(R.id.nickname)).getText().toString());
                startActivityForResult(intent, 1);
            }
        });
        return view;
    }
}