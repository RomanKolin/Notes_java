package com.example.romankolinnotes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TheNoteContentActivity extends AppCompatActivity
{
    EditText editText1title;
    EditText editText2content;

    int notepos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thenotecontentactivity);

        editText1title = findViewById(R.id.editText1title);
        editText2content = findViewById(R.id.editText2content);

        Intent notesact = getIntent();
        notepos = notesact.getIntExtra("notecontentactnotepos", 2);
        editText1title.setText(notesact.getStringExtra("notecontentacttitl"));
        editText2content.setText(notesact.getStringExtra("notecontentactcont"));
    }

    public void onclickedit(View edit)
    {
        Intent noteactedit = new Intent();
        noteactedit.putExtra("noteactnotepos", notepos);
        noteactedit.putExtra("noteacttitl", editText1title.getText().toString());
        noteactedit.putExtra("noteactcont", editText2content.getText().toString());

        setResult(RESULT_OK, noteactedit);
        finish();
    }

    public void onclickcancel(View cancel)
    {
        setResult(RESULT_CANCELED);
        finish();
    }
}