package com.example.romankolinnotes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TheNewNoteActivity extends AppCompatActivity
{
    EditText editText1title;
    EditText editText2content;

    int notepos;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thenewnoteactivity);

        editText1title = findViewById(R.id.editText1title);
        editText2content = findViewById(R.id.editText2content);

        Intent noteact = getIntent();
        notepos = noteact.getIntExtra("newnoteactnotepos", 0);
        editText1title.setText(noteact.getStringExtra("newnoteacttitl"));
        editText2content.setText(noteact.getStringExtra("newnoteactcont"));
    }

    public void onclicksave(View save)
    {
        Intent noteactsav = new Intent();
        noteactsav.putExtra("noteactnotepos", notepos);
        noteactsav.putExtra("noteacttitl", editText1title.getText().toString());
        noteactsav.putExtra("noteactcont", editText2content.getText().toString());

        setResult(RESULT_OK, noteactsav);
        finish();
    }

    public void onclickcancel(View cancel)
    {
        Intent noteactdel = new Intent();
        noteactdel.putExtra("noteactdelnote", notepos);

        setResult(RESULT_CANCELED, noteactdel);
        finish();
    }
}