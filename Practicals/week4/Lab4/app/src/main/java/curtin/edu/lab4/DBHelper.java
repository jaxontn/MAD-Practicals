package curtin.edu.lab4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import curtin.edu.lab4.DBSchema.DBTable; //IMPORT DBTable

//PURPOSE: This is a starting point for creating/updating your database

public class DBHelper extends SQLiteOpenHelper {

    //STEP 1.
    private static final int VERSION=3;
    private static final String DATABASE_NAME = "factions.db";

    //STEP 2.
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }


    //STEP 3.
    @Override //CREATE A TABLE USING SQL STATEMENT
    public void onCreate(SQLiteDatabase db ) {

        db.execSQL("CREATE TABLE " + DBTable.NAME + "(" +
                DBTable.Cols.ID + " INTEGER, " +
                DBTable.Cols.NAME + " TEXT, " +
                DBTable.Cols.STR + " INTEGER, " +
                DBTable.Cols.REL + " INTEGER)");

    } //CREATE TABLE factions( faction_id INTEGER, faction_name TEXT, faction_strength INTEGER,
      //                       faction_relationship INTEGER);

      //factions
      /*| faction_id | faction_name | faction_strength | faction_relationship |
        -----------------------------------------------------------------------
        |            |              |                  |                      |
        |------------|--------------|------------------|----------------------|
        |            |              |                  |                      |
        -----------------------------------------------------------------------
       */


    //STEP 4.  TOLD NOT TO DO IN THIS LAB (About Upgrading)
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        throw new UnsupportedOperationException("Sorry");
    }
}
