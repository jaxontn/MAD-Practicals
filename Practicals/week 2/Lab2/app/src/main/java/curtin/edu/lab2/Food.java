package curtin.edu.lab2;

import java.io.Serializable;

public class Food extends Item implements Serializable {

    private double health;

    public Food( double health, String desc, int value )
    {
        setHealth( health );
        setDecs( desc );
        setValue( value );
    }

    public void setHealth( double health )
    {
        this.health = health;
    }

    //GETTER
    public double getHealth()
    {
        return health;
    }
}
