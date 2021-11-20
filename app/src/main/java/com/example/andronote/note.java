package com.example.andronote;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.view.View;
import android.widget.*;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class note extends AppCompatActivity
{
    EditText ed1;
    EditText ed2;
    ImageView iv1;
    ImageView iv_mic;
    public Editable str;
    int pos;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        iv1=findViewById(R.id.iv1);
        iv_mic = findViewById(R.id.iv_mic);
        dbHandler = new DBHandler(note.this);

        iv1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String message=ed1.getText().toString();
                String message2=ed2.getText().toString();
                dbHandler.addItem(message,message2);
                Toast.makeText(getApplicationContext(), " Note Saved ",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),home.class);
                startActivity(intent);
                finish();
            }
        });


        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str=ed2.getText();
                pos=ed2.getSelectionStart(); //cursor
                //Intent to listen to user vocal input and return result in same activity....
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //ACTION_RECOGNIZE_SPEECH starts an activity that will prompt the user for speech input and sent it through speech recognizer.
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //It requires some extra values like EXTRA_LANGUAGE_MODEL for selecting the language.
                //LANGUAGE_MODEL_FREE_FORM is the value assigned to EXTRA_LANGUAGE_MODEL key.
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                // set the language to be recognized as default language selected for your android
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");
                //This text will appear at dialog box.

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast.makeText(note.this, " " + e.getMessage(), Toast.LENGTH_SHORT)
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
            if (resultCode == RESULT_OK && data != null)
            {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); // Contains the result which is stored in array list.
                str.insert(pos,Objects.requireNonNull(result).get(0));
                ed2.setText(str);
            }
        }
    }


}