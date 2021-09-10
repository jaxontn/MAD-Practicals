package curtin.edu.lab4;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/**
 * Maintains the overall dataset; specifically of course all the different factions.
 */
public class FactionList
{
    private List<Faction> factions = new ArrayList<>();
    private DBModel dbModel = new DBModel();

    public FactionList()
    {

    }

    public void load(Context context)
    {
        // ...
        dbModel.load(context); //ADDED
    }

    public int size()
    {
        return factions.size();
    }

    public Faction get(int i)
    {
        return factions.get(i);
    }

    public int add(Faction newFaction)
    {
        factions.add(newFaction);
        // ...
        //ADDED--------------------------------------
        if( dbModel != null )
        {
            dbModel.addFaction(newFaction);
        }
        else
        {
            throw new NullPointerException("Database doesn't exist");
        }
        //--------------------------------------------

        return factions.size() - 1; // Return insertion point
    }

    public void edit(Faction newFaction)
    {
        // ...
        //ADDED--------------------------------------
        for(Faction f : factions)
        {
            if( newFaction == f )
            {
                f = newFaction;
                dbModel.updateFaction(f);
            }
        }
        //-------------------------------------------
    }

    public void remove(Faction rmFaction)
    {
        factions.remove(rmFaction);
        // ...
        dbModel.removeFaction(rmFaction); //ADDED
    }
}
