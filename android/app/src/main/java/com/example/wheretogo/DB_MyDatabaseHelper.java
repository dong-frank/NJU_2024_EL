package com.example.wheretogo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;



import androidx.annotation.Nullable;

public class DB_MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BoolLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME ="my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_PAGES = "book_pages";
    private static final String COLUMN_AUTHOR = "book_author";


    DB_MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context; // losing this line cost me a great deal of time to fix it;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // much attention needed to take care of the format
        String query =  "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER);";
        db.execSQL(query);

        String title_default1 = "TitleDefault1";
        String title_default2 = "TitleDefault2";
        String title_default3 = "TitleDefault3";
        String author_default1 = "AuthorDefault1";
        String author_default2 = "AuthorDefault2";
        String author_default3 = "AuthorDefault3";
        int pages_default1 = 1;
        int pages_default2 = 2;
        int pages_default3 = 3;
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title_default1);
        cv.put(COLUMN_AUTHOR, author_default1);
        cv.put(COLUMN_PAGES, pages_default1);
        db.insert(TABLE_NAME,null, cv);
        cv.put(COLUMN_TITLE, title_default2);
        cv.put(COLUMN_AUTHOR, author_default2);
        cv.put(COLUMN_PAGES, pages_default2);
        db.insert(TABLE_NAME,null, cv);
        cv.put(COLUMN_TITLE, title_default3);
        cv.put(COLUMN_AUTHOR, author_default3);
        cv.put(COLUMN_PAGES, pages_default3);
        db.insert(TABLE_NAME,null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addBook(String title, String author, int pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor get_Default_Table(){
        String query = "SELECT * FROM sqlite_master where name = '"+TABLE_NAME +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor _cursor = null;
        if(db != null){
            _cursor = db.rawQuery(query,null);
            Toast.makeText(context,"cursor generated",Toast.LENGTH_SHORT).show();
        }
        return _cursor;
    }

    // called in mainActivity;
    Cursor readAllData(){
        String query = "SELECT * FROM "+ TABLE_NAME; // once again LETHAL mistake
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id,String title,String author, String pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_AUTHOR,author);
        cv.put(COLUMN_PAGES,pages);

        long result = db.update(TABLE_NAME,cv,"_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Failed to update!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Successful!",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getReadableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Didn't succeed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Delete Successfully!",Toast.LENGTH_SHORT).show();
        }
    }
}
