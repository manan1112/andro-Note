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
    //private EditText tv_Speech_to_text;
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
        //tv_Speech_to_text = findViewById(R.id.ed2);
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
                pos=ed2.getSelectionStart();
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

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
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                str.insert(pos,Objects.requireNonNull(result).get(0));
                ed2.setText(str);
            }
        }
    }


}