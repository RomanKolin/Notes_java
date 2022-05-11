package com.example.romankolinnotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TheNoteActivity extends AppCompatActivity
{
    ArrayAdapter<Note> note;
    ListView listView1;

    int notepos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thenoteactivity);

        listView1 = findViewById(R.id.listView1);

        note = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1);
        listView1.setAdapter(note);

        listView1.setOnItemClickListener((adapterView, vie, number, l) -> notepos = number);
    }

    public void onclicknewnote(View newnote)
    {
        Note newnot = new Note();
        newnot.title = "";
        newnot.content = "";
        note.add(newnot);

        Intent newnoteactnew = new Intent(this, TheNewNoteActivity.class);
        notepos = note.getPosition(newnot);
        newnoteactnew.putExtra("newnoteactnotepos", notepos);
        newnoteactnew.putExtra("newnoteacttitl", newnot.title);
        newnoteactnew.putExtra("newnoteactcont", newnot.content);

        startActivityForResult(newnoteactnew, 1);
    }

    public void onclickwatchoredit(View watchoredit)
    {
        if (notepos >= 0)
        {
            Note notecont = note.getItem(notepos);

            Intent notecontentact = new Intent(TheNoteActivity.this, TheNoteContentActivity.class);
            notecontentact.putExtra("notecontentactnotepos", notepos);
            notecontentact.putExtra("notecontentacttitl", notecont.title);
            notecontentact.putExtra("notecontentactcont", notecont.content);

            startActivityForResult(notecontentact, 2);
        }
    }

    @Override
    protected void onActivityResult(int reqcod, int rescod, @Nullable Intent newnoteact)
    {
        super.onActivityResult(reqcod, rescod, newnoteact);

        if (newnoteact != null)
        {
            if (reqcod == 1)
            {
                if (rescod == -1)
                {
                    notepos = newnoteact.getIntExtra("noteactnotepos", 0);
                    String titl = newnoteact.getStringExtra("noteacttitl");
                    String cont = newnoteact.getStringExtra("noteactcont");

                    Note newnot = note.getItem(notepos);
                    newnot.title = titl;
                    newnot.content = cont;
                }
                else if (rescod == 0)
                {
                    notepos = newnoteact.getIntExtra("noteactdelnote", 0);

                    Note delnote = note.getItem(notepos);
                    note.remove(delnote);

                    notepos = -1;
                }
            }
            else if (reqcod == 2)
            {
                notepos = newnoteact.getIntExtra("noteactnotepos", 0);
                String titl = newnoteact.getStringExtra("noteacttitl");
                String cont = newnoteact.getStringExtra("noteactcont");

                Note newnot = note.getItem(notepos);
                newnot.title = titl;
                newnot.content = cont;
            }
        }
        note.notifyDataSetChanged();
    }

    public void onclickdelete(View delete)
    {
        if (notepos >= 0)
        {
            AlertDialog.Builder del = new AlertDialog.Builder(TheNoteActivity.this).setTitle("Note's delete");
            del.setMessage("Do you wanna delete this note?");
            del.setPositiveButton("No", ((dialogInterface, n) -> dialogInterface.cancel()));
            del.setNegativeButton("Yes", ((dialogInterface, y) ->
            {
                Note delnote = note.getItem(notepos);
                note.remove(delnote);
                note.notifyDataSetChanged();

                notepos = -1;
            }));
            del.show();
        }
    }
}