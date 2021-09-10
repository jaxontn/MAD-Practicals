package curtin.edu.lab2;



import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GameMap implements /*Parcelable*/ Serializable {

    private int MAX_COLUMN = 4;
    private int MAX_ROW = 4;
    private Area[][] grid = new Area[MAX_ROW][MAX_COLUMN]; //each element is an Area.

    public GameMap() //Default Constructor
    {
        //set the town and wilderness throughout the map
        grid[0][0] = new Area(true);
        grid[0][1] = new Area(false);
        grid[0][2] = new Area(false);
        grid[0][3] = new Area(true);

        grid[1][0] = new Area(false);
        grid[1][1] = new Area(false);
        grid[1][2] = new Area(true);
        grid[1][3] = new Area(false);

        grid[2][0] = new Area(false);
        grid[2][1] = new Area(true);
        grid[2][2] = new Area(true);
        grid[2][3] = new Area(false);

        grid[3][0] = new Area(true);
        grid[3][1] = new Area(false);
        grid[3][2] = new Area(false);
        grid[3][3] = new Area(false);


        //set items for each grid
        grid[0][0].addEquipment(new Equipment(20.4, "jade monkey", 20));
        grid[0][0].addEquipment(new Equipment(20.4, "diamond", 100));
        grid[0][0].addFood(new Food(10.5, "Cookie", 15));
        grid[0][0].addFood(new Food(20.5, "Musk Melon", 259));

        grid[0][2].addEquipment(new Equipment(5.7, "Pickaxe", 15));
        grid[0][2].addFood(new Food(20, "Milk", 9));

        grid[2][1].addEquipment(new Equipment(25.0, "roadmap", 90));
        grid[2][1].addFood(new Food(17.6, "Blueberry", 70));

        grid[3][3].addEquipment(new Equipment(5.7, "ice scraper", 100));
        grid[3][3].addFood(new Food(5, "Mango", 300));

        grid[3][0].addEquipment(new Equipment(5.7, "Wagon", 50));
        grid[3][0].addFood(new Food(-50, "Tree Bark", 9));

        grid[1][2].addFood(new Food(50.6, "Banana", 25));


        //IF IN WILDERNESS, NO NEED TO PAY FOR THE EQUIPMENTS OR FOOD.
        //IF IN TOWN, NEED TO PAY.

    }


    //-----------------DONE BY ANDROID STUDIO FOR PARCELABLE--------------
  /*  protected GameMap(Parcel in) {
        MAX_COLUMN = in.readInt();
        MAX_ROW = in.readInt();
        //grid = in.readArray(Area.class.getClassLoader());
    }

    public static final Creator<GameMap> CREATOR = new Creator<GameMap>() {
        @Override
        public GameMap createFromParcel(Parcel in) {
            return new GameMap(in);
        }

        @Override
        public GameMap[] newArray(int size) {
            return new GameMap[size];
        }
    };*/
    //--------------------------------------------------------------------

    public int getMaxColumn() {
        return MAX_COLUMN;
    }

    public int getMaxRow() {
        return MAX_ROW;
    }


    public String getPlaceType(int row, int column) {
        String place = null;
        //if valid coordinate within the map
        if (row <= MAX_ROW && column <= MAX_COLUMN && row >= 0 && column >= 0) {
            boolean town = grid[row][column].getAreaType();
            if (town) {
                place = "Town";
            } else if (!town) {
                place = "Wilderness";
            }
        } else {
            place = "Error: Undefined Place";
        }
        return place;
    }


    public List<Food> getFoodList(int row, int column) {
        List<Food> foodList = new LinkedList<Food>();

        //if valid coordinate within the map
        if (row <= MAX_ROW && column <= MAX_COLUMN && row >= 0 && column >= 0) {
            foodList = grid[row][column].getFoods();
        }
        return foodList;
    }


    public List<Equipment> getEquipmentList(int row, int column) {
        List<Equipment> equipmentList = new LinkedList<Equipment>();

        //if valid coordinate within the map
        if (row <= MAX_ROW && column <= MAX_COLUMN && row >= 0 && column >= 0) {
            equipmentList = grid[row][column].getEquipments();
        }
        return equipmentList;

    }


    public int getEquipmentListSize(int row, int column) {
        int size = 0;

        //if valid coordinate within the map
        if (row <= MAX_ROW && column <= MAX_COLUMN && row >= 0 && column >= 0) {
            size = grid[row][column].getEquipments().size();
        }
        return size;
    }


    public int getFoodListSize(int row, int column) {
        int size = 0;

        //if valid coordinate within the map
        if (row <= MAX_ROW && column <= MAX_COLUMN && row >= 0 && column >= 0) {
            size = grid[row][column].getFoods().size();
        }
        return size;
    }

    public String getFoodName(int row, int column, int idx) {
        return grid[row][column].getFoodName(idx);
    }


    public String getEquipmentName(int row, int column, int idx) {
        return grid[row][column].getEquipmentName(idx);
    }

    public int getEquipmentValue(int row, int column, int idx) {
        return grid[row][column].getEquipmentValue(idx);
    }

    public int getFoodValue(int row, int column, int idx) {
        return grid[row][column].getFoodValue(idx);
    }

    public double getFoodHealth(int row, int column, int idx) {
        return grid[row][column].getHealth(idx);
    }

    public double getEquipmentMass(int row, int column, int id) {
        return grid[row][column].getMass(id);
    }


    //remove by index
    public String removeFood(int row, int column, int idx) {
        String removedFoodName = null;
        if (row < MAX_ROW && column < MAX_COLUMN && row >= 0 && column >= 0) {
            removedFoodName = grid[row][column].removeFood(idx);
        }
        return removedFoodName;
    }


    //remove by index (AND RETURNS THE PREVIOUS EQUIPMENT LIST BEFORE )
    public List<Equipment> removeEquipment(int row, int column, int idx) {
        List<Equipment> equipmentList = null;
        if (row < MAX_ROW && column < MAX_COLUMN && row >= 0 && column >= 0) {
            equipmentList = grid[row][column].removeEquipment(idx);
        }
        return equipmentList;
    }


    public void addEquipment( int row, int column, String itemName, int itemValue, double mass )
    {
        if (row < MAX_ROW && column < MAX_COLUMN && row >= 0 && column >= 0)
        {
            grid[row][column].addEquipment( new Equipment(mass, itemName, itemValue));
        }
    }



    //-----------------DONE BY ANDROID STUDIO FOR PARCELABLE-----
    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(MAX_COLUMN);
        parcel.writeInt(MAX_ROW);
        parcel.writeArray(grid); //NEW
    }*/
    //-----------------------------------------------------------
}
