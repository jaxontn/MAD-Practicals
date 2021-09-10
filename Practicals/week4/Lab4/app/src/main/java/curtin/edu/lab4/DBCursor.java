package curtin.edu.lab4;

import android.database.Cursor;
import android.database.CursorWrapper;
import curtin.edu.lab4.DBSchema.DBTable;

//PURPOSE: A cursor is (broadly) the same idea as an iterator.
//         - At any given time, the cursor is "at" a particular row (of the returned data)
//            - You tell it to move between rows.
//         - For any given row, you can access the column values.
//            - But you first have to obtain the "column index" for a given column name.
//Therefore, it is best to create OUR OWN CURSOR SUBCLASS, "to hide the low-level details"! :)

//DEFINING A CURSOR SUBCLASS
public class DBCursor extends CursorWrapper {

    //CONSTRUCTOR
    public DBCursor(Cursor cursor)
    {
        super(cursor);
    }

    //GETTER
    public Faction getFaction() {
        int id = getInt(getColumnIndex(DBTable.Cols.ID));
        String name = getString(getColumnIndex(DBTable.Cols.NAME));
        int str = getInt(getColumnIndex(DBTable.Cols.STR));
        int rel = getInt(getColumnIndex(DBTable.Cols.REL));
        return new Faction(id, name, str, rel);
    }
}
