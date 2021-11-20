package com.example.andronote;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class editnote extends AppCompatActivity {
    private DBHandler dbHandler;
    ImageView iv1;
    EditText ed1,ed2;
    ImageView iv_mic;
    public Editable str;
    int pos;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        iv1=findViewById(R.id.iv1);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        iv1=findViewById(R.id.iv1);
        iv_mic = findViewById(R.id.iv_mic);
        dbHandler=new DBHandler(editnote.this);
        Intent in = getIntent();
        String param= in.getStringExtra("Selected");
        Cursor data = dbHandler.getData();
        while(data.moveToNext()) {
            if(data.getString(0).equals(param))
            {
                ed1.setText(data.getString(0));
                ed2.setText(data.getString(1));
            }
        }

        dbHandler.deleteItem(param);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message=ed1.getText().toString();
                String message2=ed2.getText().toString();
                dbHandler.addItem(message,message2);
                Toast.makeText(getApplicationContext(), " Note Saved ",Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),home.class);
                startActivity(i);
                finish();
            }
        });
        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str=ed2.getText();
                pos=ed2.getSelectionStart();
                //ACTION_RECOGNIZE_SPEECH :Starts an activity that will prompt the user for speech and send it through a speech recognizer.

                Intent intent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // creaTing a New intent.
                //ACTION_RECOGNIZE_SPEECH starts an activity that will prompt the user for speech input and sent it through speech recognizer

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //It requires some extra values like EXTRA_LANGUAGE_MODEL for selecting the language.
                //LANGUAGE_MODEL_FREE_FORM is the value assigned to EXTRA_LANGUAGE_MODEL key.
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault()); // set the language to be recognized as default language selected for your android
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text"); //This text will appear at dialog box.

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast.makeText(editnote.this, " " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS); // Contains the result which is stored in array list.
                str.insert(pos,Objects.requireNonNull(result).get(0));
                ed2.setText(str);
            }
        }
    }

}