package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();//fragment manager keeps track of fragments

        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragmentContainer);//attach the fragment via fragment's id (same as button and view)
        if(mapFragment==null)//check if it's already there or not
        {
            mapFragment = new MapFragment();//if not create the fragment
            fm.beginTransaction().add(R.id.mapFragmentContainer,mapFragment).commit();//put mapFragment into mapFragmentContainer
        }
        SelectFragment selectFragment = (SelectFragment) fm.findFragmentById(R.id.selectFragmentContainer);//attach the fragment via fragment's id
        if(selectFragment==null)
        {
            selectFragment = new SelectFragment();
            fm.beginTransaction().add(R.id.selectFragmentContainer,selectFragment).commit();
        }
        mapFragment.setSelector(selectFragment);
    }
}