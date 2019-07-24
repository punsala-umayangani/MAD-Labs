package com.example.mtrsliit.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table BookDetail(_ID INTEGER PRIMARY KEY AUTOINCREMENT,bookName text, authorID int, bookCategory text)");
        db.execSQL("create table AuthorDetails(_ID INTEGER PRIMARY KEY AUTOINCREMENT,authorName text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addBook(String name,int authorid,String bookcategory){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("bookName",name);
        contentValues.put("authorID",authorid);
        contentValues.put("bookCategory",bookcategory);

        long res=db.insert("BookDetail",null,contentValues);

        if(res==-1){
            return false;
        }else{
            return true;
        }

    }

    public boolean addAuthor(String name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("authorName",name);

        long res=db.insert("AuthorDetails",null,contentValues);

        if(res==-1){
            return false;
        }else{
            return true;
        }

    }

    public boolean updateBook(int id,String name,int authorid,String bookcategory){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("bookName",name);
        contentValues.put("authorID",authorid);
        contentValues.put("bookCategory",bookcategory);

        long res=db.update("BookDetail",contentValues,"_ID="+id,null);

        if(res==-1){
            return false;
        }else{
            return true;
        }

    }

    public ArrayList<author> loadAuthor(){

        ArrayList<author> models= new ArrayList<>();
        SQLiteDatabase dbres = this.getReadableDatabase();
        Cursor results = dbres.rawQuery("select * from AuthorDetails",null);
        results.moveToFirst();

        while (results.isAfterLast()==false){

            author smodel= new author();

            smodel.setName(results.getString(1));

            models.add(smodel);
            results.moveToNext();

        }

        return models;
    }

    public ArrayList<book> searchBook(String authorname){

        ArrayList<book> models= new ArrayList<>();
        SQLiteDatabase resdb = this.getReadableDatabase();
        Cursor results = resdb.rawQuery("select * from BookDetail b,AuthorDetails a where a._ID=b.authorID and a.authorName='"+authorname+"'",null);
        results.moveToFirst();

        while (results.isAfterLast()==false){

            book smodel= new book();
            smodel.setId(results.getInt(0));
            smodel.setBookname(results.getString(1));
            smodel.setAuthorid(results.getInt(2));
            smodel.setBookcategory(results.getString(3));

            models.add(smodel);
            results.moveToNext();

        }

        return models;
    }

}
