package curtin.edu.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import curtin.edu.lab4.DBSchema.DBTable;

import java.util.ArrayList;
import java.util.List;

//PURPOSE: A class to keep track of your data
//         This is where the database interaction will go.

public class DBModel {

    private List<Faction> factionList; //The Data
    SQLiteDatabase db; //The database connection.

    //ADDITIONAL:
    public DBModel()
    {
        factionList = new ArrayList<>();
    }

    public void load(Context context)
    {
        //open database
        this.db = new DBHelper(context.getApplicationContext()).getWritableDatabase();
        System.out.println("LOADED DATABASE");
        //...read databases contents into 'factionList'
    }


    //1. Inserting Data (one row at a time), using ContentValues
    public void addFaction(Faction faction)
    {
        ContentValues cv = new ContentValues();
        cv.put(DBTable.Cols.ID, faction.getId());
        cv.put(DBTable.Cols.NAME, faction.getName());
        cv.put(DBTable.Cols.STR, faction.getStrength());
        cv.put(DBTable.Cols.REL, faction.getRelationship());

        //Sends an INSERT Statement to the database.
        db.insert(DBTable.NAME, null, cv);
        //INSERT INTO factions (faction_id, faction_name, faction_strength, faction_relationship)
        //VALUES( ..., ..., ..., ... );
    }


    //2. Deleting Data (one row at a time)
    public void removeFaction (Faction faction)
    {
        String[] whereValue = {String.valueOf(faction.getId())};

        //Sends a DELETE Statement to the database.
        db.delete(DBTable.NAME, DBTable.Cols.ID + "=?", whereValue);
        //e.g. is ID is 1.
        //DELETE FROM Faction WHERE faction_id = 1;

        //NOTE: DBTable.Cols.ID is a constant - safe to concatenate
    }


    //3. Updating Data (one row at a time)
    public void updateFaction(Faction faction)
    {
        //Has ContentValues like .insert()
        ContentValues cv = new ContentValues();
        cv.put(DBTable.Cols.ID, faction.getId());
        cv.put(DBTable.Cols.NAME, faction.getName());
        cv.put(DBTable.Cols.STR, faction.getStrength());
        cv.put(DBTable.Cols.REL, faction.getRelationship());

        String[] whereValue = {String.valueOf(faction.getId())};
        //Sends an UPDATE Statement to the database.
        db.update(DBTable.NAME, cv, DBTable.Cols.ID + "=?", whereValue);
        //Has "where clause" like .delete();
    }


    //4. Executing the Query (To retrieve an entire database table, we can set most to null)
    public void executeQuery()
    {
        DBCursor cursor = new DBCursor(db.query(DBTable.NAME, //FROM OUR TABLE
                                        null,  //SELECT ALL COLUMNS
                                        null, //WHERE CLAUSE (null = all rows)
                                     null, //WHERE ARGUMENTS
                                        null, //GROUP BY CLAUSE
                                          null, //HAVING CLAUSE
                                         null)); //ORDER BY CLAUSE
        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                factionList.add(cursor.getFaction()); //from previously
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close(); //This is needed, or your app will "leak" certain resources.
        }
    }
}
