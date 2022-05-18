package com.example.romankolinnotes;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent notesact = getIntent();
        notepos = notesact.getIntExtra("notecontentactnotepos", 2);
        editText1title.setText(notesact.getStringExtra("notecontentacttitl"));
        editText2content.setText(notesact.getStringExtra("notecontentactcont"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu notesmenu)
    {
        getMenuInflater().inflate(R.menu.thenewnotenotecontentactivitymenu, notesmenu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem notesmenuitem)
    {
        int itemid = notesmenuitem.getItemId();
        switch (itemid)
        {
            case R.id.item1savenote:
                Intent noteactedit = new Intent();
                noteactedit.putExtra("noteactnotepos", notepos);
                noteactedit.putExtra("noteacttitl", editText1title.getText().toString());
                noteactedit.putExtra("noteactcont", editText2content.getText().toString());

                setResult(RESULT_OK, noteactedit);
                finish();
            case R.id.item2cancel:
                setResult(RESULT_CANCELED);
                finish();
        }
        return super.onOptionsItemSelected(notesmenuitem);
    }
}