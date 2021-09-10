package curtin.edu.lab2;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Area implements Serializable {

    private boolean town; //either a town, or a wilderness area.
    private List<Equipment> equipments; //WAITING FOR TUTOR TO APPROVE IF CAN MODIFY LIKE THIS
    private List<Food> foods; //WAITING FOR TUTOR TO APPROVE IF CAN MODIFY LIKE THIS.


    public Area(boolean isTown)
    {
        town = isTown;
        equipments = new LinkedList<Equipment>();
        foods = new LinkedList<Food>();
    }


    public void updateFoodList( List<Food> foodList )
    {
        foods = foodList;
    }

    public void updateEquipmentList( List<Equipment> equipmentList )
    {
        equipments = equipmentList;
    }



    public void addFood(Food food)
    {
        this.foods.add(food);
    }

    public void addEquipment(Equipment equipment)
    {
        this.equipments.add(equipment);
    }


    public String removeFood(int idx)
    {
        String removedFoodName = null;
        if( idx < foods.size() && idx >= 0)
        {
            removedFoodName = foods.get(idx).getDecs();
            foods.remove(idx);
        }
        return removedFoodName;
    }


    public List<Equipment> removeEquipment(int idx)
    {
        List<Equipment> equipmentList = null;
        if( idx < equipments.size() && idx >= 0 )
        {
            equipmentList = equipments;
            equipments.remove(idx);
        }
        return equipmentList;
    }


    /*public void removeEquipment(Equipment equipment)
    {
        int index = -1;
        for (int i = 0; i < this.equipments.size(); i++)
        {
            //found the item
            if (this.equipments.get(i) == equipment)
            {
                index = i;
            }
        }

        if (index == -1) //means item not found
        {
            //do a toast
        }
        else if (index >= 0) //item found
        {
            this.equipments.remove(index);// remove the item
        }
    }*/



    //GETTERS
    public boolean getAreaType()
    {
        return town;
    }

    public List<Food> getFoods()
    {
        return foods;
    }

    public List<Equipment> getEquipments()
    {
        return equipments;
    }

    //get food name based on index
    public String getFoodName(int id)
    {
        String name = null;
        if( id <= foods.size() && id>= 0)
        {
            try
            {
                name = foods.get(id).getDecs();
            }
            catch(IndexOutOfBoundsException e)
            {
                name = null;
            }
        }
        return name; //returns null if exceeded linkedlist
    }



    public String getEquipmentName(int id)
    {
        String name = null;
        if( id <= equipments.size() && id>= 0)
        {
            try
            {
                name = equipments.get(id).getDecs();
            }
            catch(IndexOutOfBoundsException e)
            {
                name = null;
            }
        }
        return name;
    }



    public int getEquipmentValue(int id)
    {
        int value = 0;
        if( id < equipments.size() && id>= 0)
        {
            value = equipments.get(id).getValue();
        }
        return value;
    }


    public int getFoodValue(int id)
    {
        int value = 0;
        if( id < foods.size() && id>= 0)
        {
            value = foods.get(id).getValue();
        }
        return value;
    }


    public double getHealth(int id)
    {
        double value = 0;
        if( id < foods.size() && id>= 0)
        {
            value = foods.get(id).getHealth();
        }
        return value;
    }


    public double getMass(int id)
    {
        double value = 0;
        if( id < equipments.size() && id>= 0)
        {
            value = equipments.get(id).getMass();
        }
        return value;
    }



}
