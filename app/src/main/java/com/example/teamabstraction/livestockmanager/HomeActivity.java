package com.example.teamabstraction.livestockmanager;

//These are used for the ListView
import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
<<<<<<< HEAD
import android.content.Intent;
import android.support.annotation.IdRes;
=======
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
>>>>>>> 9403000075c3e888f8829e0274555460d6cbc327
import android.support.v7.app.AppCompatActivity;
import android.view.View;
<<<<<<< HEAD
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity
{
    private ListView animalListView;
    private ArrayAdapter<String> animalListAdapter;
=======
import android.view.Menu;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.InputType;
import android.view.MenuItem;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity {
>>>>>>> 9403000075c3e888f8829e0274555460d6cbc327


    private Button addAnimal;
    private String m_Text = "";

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

<<<<<<< HEAD
//Set the ArrayAdapter as the ListView's Adapter
        animalListView.setAdapter(animalListAdapter);
        //This will display the animal information once the user clicks on it.
        animalListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
               //Create code to view the list of a specific animal Activity.
=======
        final Button addAnimal = (Button) findViewById(R.id.add_animal);
        addAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle(R.string.dialog_title);

                // Set up the input
                final EditText input = new EditText(HomeActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton(R.string.add_animal_continue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        // to do- m_text to database
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
>>>>>>> 9403000075c3e888f8829e0274555460d6cbc327
            }
        });

    }
}



