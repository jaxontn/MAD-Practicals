package curtin.edu.lab2;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Player implements Serializable/*Parcelable*/ {

    //the player at any given point in time, is at a particular area,
    //has certain amount of cash,
    //has a health rating (0 to 100),
    //and is carrying a certain amount of equipment.
    private int rowLocation;
    private int colLocation;
    private int cash;
    private double health;
    private double equipmentMass; //could be calculated on-the-fly(while in motion or progress)
    //from the Equipment objects, rather than being stored & updated.
    private List<Equipment> equipment;


    public Player(int rowLocation, int colLocation, int cash, double health, double equipmentMass)
    {
        this.rowLocation = rowLocation;
        this.colLocation = colLocation;
        this.cash = cash;
        this.health = health;
        this.equipmentMass = equipmentMass;
        this.equipment = new LinkedList<Equipment>();
    }


    //-----------------DONE BY ANDROID STUDIO FOR PARCELABLE--------------
   /* protected Player(Parcel in) {
        rowLocation = in.readInt();
        colLocation = in.readInt();
        cash = in.readInt();
        health = in.readDouble();
        equipmentMass = in.readDouble();
        equipment = in.readArrayList(Equipment.class.getClassLoader()); //NEW
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };*/
    //-------------------------------------------------------------------


    //SETTERS
    public void setRowLocation(int rowLocation) {

        this.rowLocation = rowLocation;
    }

    public void setColLocation(int colLocation) {
        this.colLocation = colLocation;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setEquipmentMass(double equipmentMass) {
        this.equipmentMass = equipmentMass;
    }

    public void addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
    }

    public void removeEquipment(Equipment equipment)
    {
        int index = -1;
        for(int i = 0; i < this.equipment.size(); i++)
        {
            //found the equipment
            if( this.equipment.get(i) == equipment)
            {
                index = i;
            }
        }

        if( index == -1 ) //means equipment not found
        {

        }
        else if( index >= 0 ) //equipment found
        {
            this.equipment.remove(index);// remove the equipment
            //...put another function here to remove the equipmentMass too.
        }
    }

    public String removeEquipment(int idx)
    {
        String removedEquipmentName = null;
        if( idx < equipment.size() && idx >= 0 )
        {
            removedEquipmentName = equipment.get(idx).getDecs();
            equipment.remove(idx);
        }
        return removedEquipmentName;
    }


    //GETTERS
    public int getRowLocation()
    {
        return rowLocation;
    }

    public int getColLocation()
    {
        return colLocation;
    }

    public int getCash()
    {
        return cash;
    }

    public double getHealth()
    {
        return health;
    }

    public double getEquipmentMass()
    {
        return equipmentMass;
    }

    public List<Equipment> getEquipment()
    {
        return equipment;
    }

    public int getEquipmentListSize()
    {
        return equipment.size();
    }

    public String getEquipmentName( int index )
    {
        String name = null;

        if(index < equipment.size() && index >=0 )
        {
            name = equipment.get(index).getDecs();
        }
        return name;
    }

    public int getEquipmentValue( int index )
    {
        int val = 0;

        if(index < equipment.size() && index >=0 )
        {
            val = equipment.get(index).getValue();
        }
        return val;
    }


    public double getEquipmentMass( int index )
    {
        double mass = 0;

        if(index < equipment.size() && index >=0 )
        {
            mass = equipment.get(index).getMass();
        }
        return mass;
    }

    public boolean findEquipmentName( String name )
    {
        boolean found = false;
        for( int i = 0; i < equipment.size(); i++ )
        {
            if( equipment.get(i).getDecs().equals(name) )
            {
                found = true;
            }
        }
        return found;
    }


    //-----------------DONE BY ANDROID STUDIO FOR PARCELABLE-----
   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(rowLocation);
        parcel.writeInt(colLocation);
        parcel.writeInt(cash);
        parcel.writeDouble(health);
        parcel.writeDouble(equipmentMass);
        parcel.writeList(equipment);  //NEW
    }*/
    //----------------------------------------------------------
}
