package curtin.edu.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class Inheritance extends AppCompatActivity implements Serializable  {

    TextView statusBar, cash, health, equipmentMass;
    Button next, prev, buy, sell, leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Player player = intent.getParcelableExtra("playerObject");

        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        buy = findViewById(R.id.buy);
        sell = findViewById(R.id.sell);
        leave = findViewById(R.id.leave);

        statusBar = findViewById(R.id.statusBar);
        cash = findViewById(R.id.cash);
        health = findViewById(R.id.health);
        equipmentMass = findViewById(R.id.equipmentMass);

        displayStatusBar(player);


        leave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //do something when the button is pressed
            {
                Intent intent = new Intent(Inheritance.this, MainActivity.class);
                intent.putExtra("playerObject", player);
                startActivity(intent);
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

    public void contentViewType( String place )
    {
        if( place.equals("Market") )
        {
            setContentView(R.layout.market_activity);
        }
        else if( place.equals("Wilderness"))
        {
            setContentView(R.layout.wilderness_activity);
        }
    }
}