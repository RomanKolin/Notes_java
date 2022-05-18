package com.example.romankolinnotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper
{

    public Database(@Nullable Context cont, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory fact, int ver)
    {
        super(cont, name, fact, ver);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int nv, int ov)
    {

    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB)
    {
        String db = "CREATE TABLE Notesapp (p INTEGER, t TEXT, c TEXT)";
        sqlDB.execSQL(db);
    }

    public void getallnotes(ArrayList<Note> lnotes)
    {
        SQLiteDatabase sqlDB = getReadableDatabase();
        String not = "SELECT p, t, c FROM Notesapp";

        @SuppressLint("Recycle") Cursor curs = sqlDB.rawQuery(not, null);
        if (curs.moveToFirst() == true)
        {
            do
            {
                Note newnote = new Note();
                newnote.pos = curs.getInt(0);
                newnote.title = curs.getString(1);
                newnote.content = curs.getString(2);
                lnotes.add(newnote);
            }
            while (curs.moveToNext() == true);
        }
    }

    public void newnote(Integer pos, String titl, String cont)
    {
        String ins = "INSERT INTO Notesapp VALUES ('" + pos + "', '" + titl + "', '" + cont + "');";
        SQLiteDatabase sqlDB = getWritableDatabase();
        sqlDB.execSQL(ins);
    }

    public void editnote(Integer pos, String titl, String cont)
    {
        String upd = "UPDATE Notesapp SET t = '" + titl + "', c = '" + cont + "' WHERE p = '" + pos + "';";
        SQLiteDatabase sqlDB = getWritableDatabase();
        sqlDB.execSQL(upd);
    }

    public void deletenote(Integer pos)
    {
        String del = "DELETE FROM Notesapp WHERE p = '" + pos + "';";
        SQLiteDatabase sqlDB = getWritableDatabase();
        sqlDB.execSQL(del);
    }
}