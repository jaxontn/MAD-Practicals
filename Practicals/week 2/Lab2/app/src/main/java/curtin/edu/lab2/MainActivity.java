package curtin.edu.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //initial location and data.
    private Player player = new Player(1, 2, 350, 100, 0);
    private GameMap map = new GameMap();
    private PreciousItem preciousItem = new PreciousItem();
    //location (1,2)
    //cash $350
    //health: 100
    //equipmentMass: 0kg
    //equipment: null/empty/none.

    TextView desc, placeType, statusBar, cash, health, equipmentMass;
    Button north, south, east, west, option, restart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //involving some important setting up
        setContentView(R.layout.navigation_activity);//the UI screen navigation_activity.xml

        //if coming back from Market or Wilderness Activity
        Intent intent = getIntent();
        //Player playerObject = intent.getParcelableExtra("playerObject");
        //GameMap mapObject = intent.getParcelableExtra("mapObject");
        Player playerObject = (Player) intent.getSerializableExtra("playerObject");
        GameMap mapObject = (GameMap) intent.getSerializableExtra("mapObject");
        PreciousItem preciousItemObject = (PreciousItem)
                                          intent.getSerializableExtra("preciousItemObject");

        if( playerObject != null )
        {
            player = playerObject;
        }

        if( mapObject != null )
        {
            map = mapObject;
        }

        if( preciousItemObject != null )
        {
            preciousItem = preciousItemObject;
        }
        //------------------------------------------------------

        //to deal with rotation data loss (Object Restoration)
        if( savedInstanceState != null )
        {
            //player = savedInstanceState.getParcelable("playerObject" );
            //map = savedInstanceState.getParcelable("mapObject");
            player = (Player) savedInstanceState.getSerializable("playerObject");
            map = (GameMap) savedInstanceState.getSerializable("mapObject");
            preciousItem = (PreciousItem) savedInstanceState.getSerializable("preciousItemObject");
        }

        //the rest of the important code will be below
        desc = findViewById(R.id.description);
        placeType = findViewById(R.id.type);
        statusBar = findViewById(R.id.statusBar);
        cash = findViewById(R.id.cash);
        health = findViewById(R.id.health);
        equipmentMass = findViewById(R.id.equipmentMass);

        north = findViewById(R.id.north);
        west = findViewById(R.id.west);
        east = findViewById(R.id.east);
        south = findViewById(R.id.south);
        option = findViewById(R.id.option);
        restart = findViewById(R.id.restart);


        //show Player's initial coordinate description, placeType, and Player's data
        displayLocation();
        displayStatusBar();


        north.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //do something when the button is pressed
            {

                //move 1 row up is within the map
                int moveNorth = player.getRowLocation() - 1;
                if( moveNorth < map.getMaxRow() && moveNorth >= 0 )
                {
                    player.setRowLocation( moveNorth ); //change location
                    displayLocation();
                    deductHealth();
                    displayStatusBar();
                }
                else
                {
                    displayOutOfGrid();
                }
            }
        });

        south.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //do something when the button is pressed
            {
                //move 1 row down is within the map
                int moveSouth = player.getRowLocation() + 1;
                if( moveSouth < map.getMaxRow() && moveSouth >= 0 )
                {
                    player.setRowLocation( moveSouth ); //change location
                    displayLocation();
                    deductHealth();
                    displayStatusBar();
                }
                else
                {
                    displayOutOfGrid();
                }
            }
        });

        east.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //do something when the button is pressed
            {
                //move 1 column right is within the map
                int moveEast = player.getColLocation() + 1;
                if( moveEast < map.getMaxColumn() && moveEast >= 0 )
                {
                    player.setColLocation( moveEast ); //change location
                    displayLocation();
                    deductHealth();
                    displayStatusBar();
                }
                else
                {
                    displayOutOfGrid();
                }
            }
        });

        west.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //do something when the button is pressed
            {
                //move 1 column left is within the map
                int moveWest = player.getColLocation() - 1;
                if( moveWest < map.getMaxColumn() && moveWest >= 0 )
                {
                    player.setColLocation( moveWest ); //change location
                    displayLocation();
                    deductHealth();
                    displayStatusBar();
                }
                else
                {
                    displayOutOfGrid();
                }
            }
        });

        option.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //do something when the button is pressed
            {
                if( placeEnvironment().equals("Town") ) //for town
                {
                    Intent intent = new Intent(MainActivity.this, MarketActivity.class);
                    intent.putExtra("playerObject", player);
                    intent.putExtra("mapObject", map);
                    intent.putExtra("preciousItemObject", preciousItem);
                    startActivity(intent);
                }
                else if( placeEnvironment().equals("Wilderness") ) //for wilderness
                {
                    Intent intent = new Intent(MainActivity.this, WildernessActivity.class);
                    intent.putExtra("playerObject", player);
                    intent.putExtra("mapObject", map);
                    intent.putExtra("preciousItemObject", preciousItem);
                    startActivity(intent);
                }
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player = new Player(1, 2, 350, 100, 0);
                map = new GameMap();

                displayLocation();
                displayStatusBar();
            }
        });


    }

    public String placeEnvironment()
    {
        return map.getPlaceType(player.getRowLocation(), player.getColLocation());
        //return map.getEquipmentName(player.getRowLocation(), player.getColLocation(), 0);
    }

    public void displayLocation()
    {
        //get the player's location
        //1. show the description of the current area (Coordinates)
        //2. state its a town or wilderness.
        desc.setText( "( " + player.getRowLocation() + ", " + player.getColLocation() + " )");
        placeType.setText(placeEnvironment());
    }

    public void displayStatusBar()
    {
        //get the player's data for status bar
        statusBar.setText("STATUS BAR");
        cash.setText( "CASH: $" + player.getCash() );
        health.setText( "HEALTH: " + player.getHealth() );
        equipmentMass.setText( "Equipment Mass: " + player.getEquipmentMass() );

        checkSought();// CHECK VICTORY
    }

    //display a toast when out of the grid.
    public void displayOutOfGrid()
    {
        // to get Context
        Context context = getApplicationContext();
        // toast time duration, can also set manual value
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, "Grid Limit Reached, Go another way", duration);
        // to show the toast
        toast.show();
    }

    public void deductHealth()
    {
        if( player.getHealth() > 0) //still alive
        {
            double wellbeing = Math.max(0.0, player.getHealth() - 5.0 -
                                        (player.getEquipmentMass() / 2.0));
            player.setHealth(wellbeing);

            if( wellbeing == 0 ) //dead, health = 0
            {
                displayGameOver();
            }
        }
        else
        {
            displayGameOver();
        }

    }

    public void displayGameOver()
    {
        // to get Context
        Context context = getApplicationContext();
        // toast time duration, can also set manual value
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, "GAME OVER (Health <= 0)", duration);
        // to show the toast
        toast.show();
    }


    public void checkSought() //CHECK FOR VICTORY
    {
        //get the items to sought.
        String[] itemToSought = preciousItem.getItems();
        boolean found = false;
        int itemFound = 0;
        //look through the Player's inventory

        for( int i = 0; i < itemToSought.length; i++ )
        {
            found = player.findEquipmentName( itemToSought[i] );

            if( found )
            {
                itemFound++;
            }
        }

        if( itemFound == itemToSought.length ) //VICTORY
        {
            statusBar.setText("VICTORY"); //VICTORY
        }

    }


    //FOR THE ROTATION DATA LOSS FIX
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putParcelable("playerObject", player);
        //outState.putParcelable("mapObject", map);
        outState.putSerializable("playerObject", player);
        outState.putSerializable("mapObject", map);
        outState.putSerializable("preciousItemObject", preciousItem);
    }
}