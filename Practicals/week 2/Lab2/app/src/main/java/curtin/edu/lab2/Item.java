package curtin.edu.lab2;

import java.io.Serializable;

public class Item implements Serializable {

    private String description; //each item has a description. e.g. what it is
    private int value; //the price.

    public Item()
    {
    }

    //SETTERS
    public void setDecs( String decs )
    {
        description = decs;
    }


    public void setValue( int val )
    {
        value = val;
    }

    //GETTERS
    public String getDecs()
    {
        return description;
    }

    public int getValue()
    {
        return value;
    }
    //The item can be either Food(increase player's health) ,or Equipment(that the player carries)
    //health max is 100. (0 to 100)
}
