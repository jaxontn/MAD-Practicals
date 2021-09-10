package curtin.edu.lab2;

import java.io.Serializable;

public class Equipment extends Item implements Serializable {

    private double mass;


    public Equipment( double mass, String desc, int value )
    {
        setMass( mass );
        setDecs( desc );
        setValue( value );
    }

    public void setMass( double mass )
    {
        this.mass = mass;
    }

    //GETTER
    public double getMass()
    {
        return mass;
    }
}
