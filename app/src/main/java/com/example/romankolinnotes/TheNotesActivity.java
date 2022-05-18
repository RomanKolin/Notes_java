package com.example.romankolinnotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class TheNotesActivity extends AppCompatActivity
{
    ArrayAdapter<Note> note;
    ListView listView1;
    ArrayList<Note> lnotes = new ArrayList<>();

    void listupdate()
    {
        lnotes.clear();
        NewNote.newnote.getallnotes(lnotes);
        note.notifyDataSetChanged();
    }

    int notepos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thenotesactivity);

        listView1 = findViewById(R.id.listView1);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NewNote.newnote = new Database(this, "notesapp", null, 1);

        note = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, lnotes);
        listView1.setAdapter(note);

        listView1.setOnItemClickListener((adapterView, vie, number, l) -> notepos = number);

        listupdate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu notesmenu)
    {
        getMenuInflater().inflate(R.menu.thenotesactivitymenu, notesmenu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem notesmenuitem)
    {
        int itemid = notesmenuitem.getItemId();
        switch (itemid)
        {
            case R.id.item1newnote:
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
                return true;
            case R.id.item2watchoredit:
                if (notepos >= 0)
                {
                    Note notecont = note.getItem(notepos);

                    Intent notecontentact = new Intent(TheNotesActivity.this, TheNoteContentActivity.class);
                    notecontentact.putExtra("notecontentactnotepos", notepos);
                    notecontentact.putExtra("notecontentacttitl", notecont.title);
                    notecontentact.putExtra("notecontentactcont", notecont.content);

                    startActivityForResult(notecontentact, 2);
                }
                return true;
            case R.id.item3deletenote:
                if (notepos >= 0)
                {
                    AlertDialog.Builder del = new AlertDialog.Builder(TheNotesActivity.this).setTitle("Note's delete");
                    del.setMessage("Do you wanna delete this note?");
                    del.setPositiveButton("No", ((dialogInterface, n) -> dialogInterface.cancel()));
                    del.setNegativeButton("Yes", ((dialogInterface, y) ->
                    {
                        Note delnote = note.getItem(notepos);
                        note.remove(delnote);
                        note.notifyDataSetChanged();
                        NewNote.newnote.deletenote(delnote.pos);

                        notepos = -1;
                    }));
                    del.show();
                }
                return true;
        }
        listupdate();
        return super.onOptionsItemSelected(notesmenuitem);
    }

    @Override
    protected void onActivityResult(int reqcod, int rescod, @Nullable Intent newnoteact) {
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
                    newnot.pos = notepos;
                    newnot.title = titl;
                    newnot.content = cont;
                    NewNote.newnote.newnote(newnot.pos, newnot.title, newnot.content);
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
                newnot.pos = notepos;
                newnot.title = titl;
                newnot.content = cont;
                NewNote.newnote.editnote(newnot.pos, newnot.title, newnot.content);
            }
            listupdate();
        }
    }
}