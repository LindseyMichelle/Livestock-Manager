package com.example.teamabstraction.livestockmanager;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.InputType;

public class HomeActivity extends AppCompatActivity
{

    private ListView animalListView;
    private ArrayAdapter<String> animalListAdapter;
    private Button addAnimal;
    private Button nextAct;
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Find the ListView resource
        animalListView = (ListView) findViewById(R.id.animalList);
        animalListView.setClickable(true);

        //Creates and populates a list of animals (May need to import from a database later
        String[] animals = new String[]{"Sheep", "Goats", "Cows", "Chickens"};
        final ArrayList<String> listOfAnimalsArray = new ArrayList<String>();
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
                //This is where I get the error. When the item is clicked
                Intent myIntent = new Intent(HomeActivity.this, IndAnimalList.class);
                startActivity(myIntent);
            }
        });

        nextAct = (Button) findViewById(R.id.next_act);
        nextAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(HomeActivity.this, IndAnimalList.class);
                startActivity(myIntent);
            }
        });


        // Creates an alert dialog to pop up for user to input information when clicked.
        addAnimal = (Button) findViewById(R.id.add_animal);
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
                        // TODO: m_text to database
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

    }
}



