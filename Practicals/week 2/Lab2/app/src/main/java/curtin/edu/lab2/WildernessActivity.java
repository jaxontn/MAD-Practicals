package curtin.edu.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WildernessActivity extends AppCompatActivity {

        TextView statusBar, cash, health, equipmentMass, currentPickupItem, currentDropItem;
        Button next, prev, pickup, drop, leave, nextEquipment, prevEquipment;
        Player player;
        GameMap map;
        PreciousItem preciousItem;
        // List<Equipment> equipmentList;
        // List<Food> foodList;

        private final int IS_EQUIPMENT = 2134;
        private final int IS_FOOD = 5306;
        private final int IS_NEXT = 8584;
        private final int IS_PREV = 5748;
        private final int NONE = 7303;
        private int currentItemCategory; //either IS_FOOD or IS_EQUIPMENT
        private int currentItemIndex; //the current item index of the specified category.
        private int currentDropingIndex; //for player's inventory equipment list indexes.

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.wilderness_activity);

                Intent intent = getIntent();
                //player = intent.getParcelableExtra("playerObject");
                //map = intent.getParcelableExtra("mapObject");
                player = (Player) intent.getSerializableExtra("playerObject");
                map = (GameMap) intent.getSerializableExtra("mapObject");
                preciousItem = (PreciousItem) intent.getSerializableExtra("preciousItemObject");


                //to deal with rotation data loss (Object Restoration)
                if( savedInstanceState != null )
                {
                        //player = savedInstanceState.getParcelable("playerObject" );
                        // map = savedInstanceState.getParcelable("mapObject");
                        player = (Player) savedInstanceState.getSerializable("playerObject");
                        map = (GameMap) savedInstanceState.getSerializable("mapObject");
                        preciousItem = (PreciousItem) savedInstanceState.getSerializable("preciousItemObject");
                        currentItemCategory = savedInstanceState.getInt("currentItemCategory");
                        currentItemIndex = savedInstanceState.getInt("currentItemIndex");
                        currentDropingIndex = savedInstanceState.getInt("currentDropingIndex");
                }

                next = findViewById(R.id.next);
                prev = findViewById(R.id.prev);
                pickup = findViewById(R.id.pickup);
                drop = findViewById(R.id.drop);
                leave = findViewById(R.id.leave);
                prevEquipment = findViewById(R.id.prevEquipment);
                nextEquipment = findViewById(R.id.nextEquipment);

                statusBar = findViewById(R.id.statusBar);
                cash = findViewById(R.id.cash);
                health = findViewById(R.id.health);
                equipmentMass = findViewById(R.id.equipmentMass);

                currentPickupItem = findViewById(R.id.currentItem);
                currentDropItem = findViewById(R.id.equipmentToDrop);


                displayStatusBar(player);
                currentItemCategory = IS_EQUIPMENT;
                currentItemIndex = 0;
                currentDropingIndex = 0;
                displayCurrentPickupItem( NONE );
                displayCurrentDropItem( NONE );


                leave.setOnClickListener(new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v) //do something when the button is pressed
                        {
                                Intent intent = new Intent(WildernessActivity.this,
                                        MainActivity.class);
                                intent.putExtra("playerObject", player);
                                intent.putExtra("mapObject", map);
                                intent.putExtra("preciousItemObject", preciousItem);
                                startActivity(intent);
                        }
                });


                next.setOnClickListener(new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v) //do something when the button is pressed
                        {
                                currentItemIndex++;
                                displayCurrentPickupItem( IS_NEXT );
                        }
                });

                prev.setOnClickListener(new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v) //do something when the button is pressed
                        {
                                currentItemIndex--;
                                displayCurrentPickupItem( IS_PREV );
                        }
                });

                pickup.setOnClickListener(new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v) //do something when the button is pressed
                        {
                                String itemName = null;
                                int itemValue = 0;
                                //when pickup, pop up an alert about the value, and mass or health.

                                if( currentItemCategory == IS_EQUIPMENT ) //means equipment category
                                {
                                        double mass;
                                        //1. get equipment name
                                        itemName = map.getEquipmentName(player.getRowLocation(),
                                                player.getColLocation(),
                                                currentItemIndex);

                                        //2. get equipment value
                                        itemValue = map.getEquipmentValue(player.getRowLocation(),
                                                player.getColLocation(),
                                                currentItemIndex);

                                        //3. get equipment mass
                                        mass = map.getEquipmentMass(player.getRowLocation(), player.getColLocation(),
                                                currentItemIndex);

                                        //bring to Alert pop up (TO CONFIRM PICKUP DECISION)
                                        if( itemName != null && itemValue != 0 && mass != 0.0 )
                                        {
                                                confirmPickup( itemName, itemValue, mass, IS_EQUIPMENT);
                                        }
                                        else if( itemName == null && itemValue == 0 && mass == 0.0 )
                                        {
                                                // to get Context
                                                Context context = getApplicationContext();
                                                // toast time duration, can also set manual value
                                                int duration = Toast.LENGTH_LONG;
                                                Toast toast = Toast.makeText(context, "NO EQUIPMENT TO PICKUP",
                                                        duration);
                                                // to show the toast
                                                toast.show();
                                        }
                                }
                                else if( currentItemCategory == IS_FOOD ) //means food category
                                {
                                        double health;
                                        //1. get name
                                        itemName = map.getFoodName(player.getRowLocation(), player.getColLocation(),
                                                currentItemIndex);

                                        //2. get food value
                                        itemValue = map.getFoodValue(player.getRowLocation(), player.getColLocation(),
                                                currentItemIndex);

                                        //3. get food health
                                        health = map.getFoodHealth(player.getRowLocation(), player.getColLocation(),
                                                currentItemIndex);

                                        //bring to alert pop up (TO CONFIRM PICKUP DECISION)
                                        if( itemName != null && itemValue != 0 && health != 0.0 )
                                        {
                                                confirmPickup(itemName, itemValue, health, IS_FOOD);
                                        }
                                        else if( itemName == null && itemValue == 0 && health == 0.0 )
                                        {
                                                // to get Context
                                                Context context = getApplicationContext();
                                                // toast time duration, can also set manual value
                                                int duration = Toast.LENGTH_LONG;
                                                Toast toast = Toast.makeText(context, "NO FOOD TO PICKUP",
                                                        duration);
                                                // to show the toast
                                                toast.show();
                                        }
                                }

                                //displayStatusBar(player);



                        }
                });

                drop.setOnClickListener(new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v) //do something when the button is pressed
                        {
                                //only drops equipments that the player has in his inventory.
                                String itemName = null;
                                int itemValue = 0;
                                double mass = 0.0;

                                //1. get equipment name
                                itemName = player.getEquipmentName( currentDropingIndex );

                                //2. get equipment value
                                itemValue = player.getEquipmentValue(currentDropingIndex);

                                //3. get equipment mass
                                mass = player.getEquipmentMass(currentDropingIndex);

                                //bring to Alert pop up (TO CONFIRM DROP DECISION)
                                if( itemName != null && itemValue != 0 && mass != 0.0 )
                                {
                                        confirmDrop( itemName, itemValue, mass );
                                }
                                else if( itemName == null && itemValue == 0 && mass == 0.0 )
                                {
                                        toastAMessage("NO EQUIPMENT TO DROP");
                                }
                        }
                });


                prevEquipment.setOnClickListener(new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v) //do something when the button is pressed
                        {
                                currentDropingIndex--;
                                displayCurrentDropItem( IS_PREV );
                        }
                });

                nextEquipment.setOnClickListener(new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v) //do something when the button is pressed
                        {
                                currentDropingIndex++;
                                displayCurrentDropItem( IS_NEXT );
                        }
                });


        }


        public void displayStatusBar( Player player )
        {
                //get the player's data for status bar
                statusBar.setText("STATUS BAR");
                cash.setText( "CASH: $" + player.getCash() );
                health.setText( "HEALTH: " + player.getHealth() );
                equipmentMass.setText( "Equipment Mass: " + player.getEquipmentMass() );
        }



        public void displayCurrentPickupItem( int action )
        {
                String itemName = null;
                //equipmentList
                //map.getEquipmentList(player.getRowLocation(), player.getColLocation());
                //foodList
                //map.getFoodList(player.getRowLocation(), player.getColLocation());

                if( currentItemCategory == IS_EQUIPMENT ) //means equipment category
                {
                        itemName = map.getEquipmentName(player.getRowLocation(), player.getColLocation(),
                                currentItemIndex);

                        if( itemName == null ) //time to display first item of next category
                        {
                                currentItemCategory = IS_FOOD; //move to next category

                                if( action == IS_PREV )
                                {
                                        currentItemIndex = map.getFoodListSize(player.getRowLocation(),
                                                player.getColLocation()) - 1;
                                }
                                else
                                {
                                        currentItemIndex = 0;
                                }
                                //start from the last index of the list
                                itemName = map.getFoodName(player.getRowLocation(), player.getColLocation(),
                                        currentItemIndex);
                        }
                }
                else if( currentItemCategory == IS_FOOD ) //means food category
                {
                        itemName = map.getFoodName(player.getRowLocation(), player.getColLocation(),
                                currentItemIndex);

                        if( itemName == null ) //time to display first item of next category
                        {
                                currentItemCategory = IS_EQUIPMENT; //move to next category

                                if( action == IS_NEXT )
                                {
                                        currentItemIndex = 0;
                                }
                                else
                                {
                                        currentItemIndex = map.getEquipmentListSize(player.getRowLocation(),
                                                player.getColLocation()) - 1;
                                }
                                //start from the last index of the list
                                itemName = map.getEquipmentName(player.getRowLocation(), player.getColLocation(),
                                        currentItemIndex);
                        }
                }
                currentPickupItem.setText(itemName);
        }



        public void displayCurrentDropItem( int action )
        {
                String itemName = null;

                itemName = player.getEquipmentName(currentDropingIndex);

                if( itemName == null ) //time to display first item index
                {
                        if( action == IS_PREV )
                        {
                                currentDropingIndex = player.getEquipmentListSize() - 1;
                        }
                        else if( action == IS_NEXT )//time to display first item index
                        {
                                currentDropingIndex = 0;
                        }

                        itemName = player.getEquipmentName(currentDropingIndex);
                }

                /*try
                {*/
                        currentDropItem.setText(itemName);
               /* }
                catch( NullPointerException e)
                {
                }*/

        }


        public void confirmPickup( String itemName, int itemValue, double num, int type )
        {

                //SUBTRACT THE PLAYERS'S CASH for bought item.
                //if equipment, add weight. (equipmentMass)
                //if food, add or decrease player's health (poisonous or non-poisonous)

                //and remove the item from the map area.

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("CONFIRM PICKUP DECISION");
                if( type == IS_EQUIPMENT )
                {
                        alert.setMessage("NAME: " + itemName + "\nVALUE: $ FREE"  + "\nMASS: " + num);
                }
                else if( type == IS_FOOD )
                {
                        alert.setMessage("NAME: " + itemName + "\nVALUE: $ FREE" + "\nHEALTH: " + num);
                }
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                //do something
                                List<Equipment> equipmentList = null;
                                //1. Update Cash (purchase item if only have enough cash)

                                /*if( player.getCash() > itemValue )
                                {
                                        player.setCash(player.getCash() - itemValue);
*/

                                        if( type == IS_EQUIPMENT )
                                        {
                                                //2. Add equipment
                                                player.setEquipmentMass( player.getEquipmentMass() + num);
                                                //REMOVE ITEM FROM MAPS, AND ADD ITEMS TO PLAYER'S INVENTORY!!! ⚠
                                                equipmentList = map.removeEquipment(player.getRowLocation(),
                                                        player.getColLocation(),
                                                        currentItemIndex);

                                                //if the item is what the player wants to pickup, then add to player's
                                                //equipment Inventory.
                                                // if( equipmentList.get(currentItemIndex).equals(itemName) )
                                                //  {
                                                player.addEquipment(new Equipment( num, itemName, itemValue));
                                                // }
                                                displayCurrentDropItem( NONE );
                                        }
                                        else if( type == IS_FOOD )
                                        {
                                                if( player.getHealth() >= 100 ) //if health is 100 (full)
                                                {                             //cannot pickup anymore.
                                                        toastAMessage("Health is full, cannot consume more food");
                                                }
                                                else //pickup if only health is not full
                                                {
                                                        String foodNameRemoved = null;
                                                        //2. Add health
                                                        player.setHealth(player.getHealth() + num);
                                                        //REMOVE ITEM FROM MAPS, AND ADD ITEMS TO PLAYER'S INVENTORY!!! ⚠
                                                        foodNameRemoved = map.removeFood(player.getRowLocation(),
                                                                player.getColLocation(),
                                                                currentItemIndex);

                                                        //if the item is what the player wants to pickup, show that its removed and
                                                        //consumed by player.
                                                        if (foodNameRemoved.equals(itemName))
                                                        {
                                                                toastAMessage( itemName + " is removed.");
                                                        }

                                                        //if health exceeds 100, then set the health to 100 (as full)
                                                        if( player.getHealth() > 100 )
                                                        {
                                                                player.setHealth(100);
                                                        }
                                                }

                                                if( player.getHealth() <= 0 )
                                                {
                                                        displayGameOver();
                                                }
                                        }
                               /* }
                                else if( player.getCash() < itemValue )
                                {
                                        displayCashInsufficient();
                                }*/
                                displayStatusBar(player);
                                displayCurrentPickupItem( NONE );
                                checkSought(); //CHECK FOR VICTORY

                        }
                });

                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                //do nothing
                        }
                });
                alert.create().show();
        }


        public void confirmDrop( String itemName, int itemValue, double mass )
        {
               // double returnedCash = (double)itemValue * 0.75;
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("CONFIRM DROP ITEM");
                alert.setMessage("NAME: " + itemName + "\nVALUE: $ " + itemValue + "\nMASS: " + mass +
                        "\nCASH TO RECEIVE AFTER DROP: $ 0");

                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                //add item to the current area
                                map.addEquipment(player.getRowLocation(), player.getColLocation(), itemName,
                                        itemValue, mass);
                                displayCurrentPickupItem( NONE );
                                toastAMessage("ADDED " + itemName + " to current Area");

                                //remove the item from player's inventory
                                String removedItemName = player.removeEquipment(currentDropingIndex);
                                if( removedItemName.equals(itemName) )
                                {
                                        toastAMessage("REMOVED " + itemName + " from Player's Inventory");
                                }
                                displayCurrentDropItem( NONE );

                                //reduce mass carried by player.
                                player.setEquipmentMass(player.getEquipmentMass() - mass);
                                //add cash to player.
                               // player.setCash( player.getCash() + (int)returnedCash);
                                displayStatusBar( player );

                                if( player.getEquipmentMass() < 0 ) //IF MASS EXCEEDS BELOW 0
                                {
                                        player.setEquipmentMass(0);
                                }
                        }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                //Do nothing
                        }
                });
                alert.create().show();

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


        public void displayCashInsufficient()
        {
                // to get Context
                Context context = getApplicationContext();
                // toast time duration, can also set manual value
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "INSUFFICIENT CASH, CANNOT PICKUP", duration);
                // to show the toast
                toast.show();
        }

        public void toastAMessage( String message )
        {
                Context context = getApplicationContext();
                // toast time duration, can also set manual value
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, message, duration);
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
                        Intent intent = new Intent(WildernessActivity.this, MainActivity.class);
                        intent.putExtra("playerObject", player);
                        intent.putExtra("mapObject", map);
                        intent.putExtra("preciousItemObject", preciousItem);
                        startActivity(intent);
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
                outState.putInt("currentItemCategory", currentItemCategory);
                outState.putInt("currentItemIndex", currentItemIndex);
                outState.putInt("currentDropingIndex", currentDropingIndex);
        }

}
