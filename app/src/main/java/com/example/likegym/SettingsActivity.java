package com.example.likegym;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    EditText editname;
    EditText editnick;
    ImageButton backarrow2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ((EditText)findViewById(R.id.editname)).setText(getIntent().getStringExtra("name"));
        ((EditText)findViewById(R.id.editnick)).setText(getIntent().getStringExtra("nick"));
        backarrow2 = findViewById(R.id.backarrow);
        editname = findViewById(R.id.editname);
        editnick = findViewById(R.id.editnick);

        backarrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(((EditText)findViewById(R.id.editname)).getText());
                String nick = String.valueOf(((EditText)findViewById(R.id.editnick)).getText());
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("nick", nick);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

        });
    }
}

