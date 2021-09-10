package curtin.edu.lab4;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // We've set everything up inside a fragment. This isn't strictly necessary, but can assist
        // maintainability, because fragments permit greater UI design flexibility.
        //1. Finding Attached Fragments
        FragmentManager fm = getSupportFragmentManager();
        MainFragment frag = (MainFragment) fm.findFragmentById(R.id.fragmentContainer);

        if(frag == null) //It might already be there!
        {
            //2. Creating and Attaching Fragments
            frag = new MainFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, frag)
                    .commit(); //.commit() actually makes it happen
        }
    }
}
