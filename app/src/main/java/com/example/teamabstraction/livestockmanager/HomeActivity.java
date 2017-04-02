package com.example.teamabstraction.livestockmanager;

//These are used for the ListView
import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity
{
    private ListView animalListView;
    private ArrayAdapter<String> animalListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Find the LisView resource
        animalListView = (ListView) findViewById(R.id.animalList);

        //Creates and populates a List of animals (May need to import from a database later
        String[] animals = new String[]{"Sheep", "Goats", "Cows", "Chickens"};
        ArrayList<String> listOfAnimalsArray = new ArrayList<String>();
        listOfAnimalsArray.addAll(Arrays.asList(animals));

        animalListAdapter = new ArrayAdapter<String>(this, R.layout.animal_list_text_view, listOfAnimalsArray);

//Set the ArrayAdapter as the ListView's Adapter
        animalListView.setAdapter(animalListAdapter);
        //This will display the animal information once the user clicks on it.
        animalListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
               //Create code to view the list of a specific animal Activity. 
            }
        });

    }
}



