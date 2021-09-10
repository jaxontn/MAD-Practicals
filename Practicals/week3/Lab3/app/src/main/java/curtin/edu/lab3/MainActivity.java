package curtin.edu.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ADDED------------------------------------------------------------------------
        //1. FINDING ATTACHED FRAGMENTS
        FragmentManager fm = getSupportFragmentManager();//fragment manager keeps track of fragments
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragmentContainer);
        //^^attach the fragment via fragment's id (same as button and view)

        if(mapFragment==null)//check if it's already there or not
        {
            //2. CREATING AND ATTACHING FRAGMENTS
            mapFragment = new MapFragment();//create the fragment
            fm.beginTransaction().add(R.id.mapFragmentContainer,mapFragment).commit();
            //^^put mapFragment into mapFragmentContainer
            //add(...), detach(...), show(...), hide(...), replace(...)
            //commit() makes it actually happen.
        }

        //1. FINDING ATTACHED FRAGMENTS
        SelectFragment selectFragment = (SelectFragment) fm.findFragmentById(R.id.selectFragmentContainer);
        //^^attach the fragment via fragment's id

        if(selectFragment==null) //check if it's already there or not
        {
            //2. CREATING AND ATTACHING FRAGMENTS
            selectFragment = new SelectFragment(); //create the fragment
            fm.beginTransaction().add(R.id.selectFragmentContainer,selectFragment).commit();
            //^^put selectFragment into selectFragmentContainer
            //add(...), detach(...), show(...), hide(...), replace(...)
            //commit() makes it actually happen.
        }

        mapFragment.setSelector(selectFragment);
        //-----------------------------------------------------------------------------
    }
}