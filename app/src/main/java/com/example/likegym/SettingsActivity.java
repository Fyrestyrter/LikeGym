package com.example.likegym;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_settings);

        FragmentContainerView fcv = findViewById(R.id.bottomNavigationView);
        fcv.setVisibility(View.INVISIBLE);

        findViewById(R.id.backarrow).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.flFragment, new ProfileFragment()).
                    addToBackStack(null).commit();
            fcv.setVisibility(View.VISIBLE);
        });
    }


}