package com.example.teamabstraction.livestockmanager;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.InputType;
import android.widget.TextView;
import android.widget.Toast;



public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "AnimalTypeActivity";

    private ListView animalListView;
    private ArrayAdapter<String> animalListAdapter;
    private Button addAnimal;

    private Button deleteAnimal;
    private String m_Text = "";

    private TextView welcomeMessage;


    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mydb = new DatabaseHelper(this);
        welcomeMessage = (TextView) findViewById(R.id.displayWelcomeInformation);
        welcomeMessage.setText(R.string.displayWelcomeInformation);
        //Find the ListView resource
        animalListView = (ListView) findViewById(R.id.animalList);


        //Creates and populates a list of animals (May need to import from a database later
        //String[] animals = new String[]{"Sheep", "Goats", "Cows", "Chickens"};
        //final ArrayList<String> listOfAnimalsArray = new ArrayList<String>();
        //listOfAnimalsArray.addAll(Arrays.asList(animals));
        //animalListAdapter = new ArrayAdapter<String>(this, R.layout.animal_list_text_view, listOfAnimalsArray);

        populateListView();
        //Set the ArrayAdapter as the ListView's Adapter
        //animalListView.setAdapter(animalListAdapter);

        //This will display the animal information once the user clicks on it.
        animalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(HomeActivity.this, IndAnimalList.class);
                    GlobalVariables.getInstance().aType = (String)parent.getItemAtPosition(position);
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
                        boolean isinserted = mydb.insertAnimalType(m_Text);
                        if(isinserted = true)
                            Toast.makeText(HomeActivity.this, "Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(HomeActivity.this, "Data not Inserted",Toast.LENGTH_LONG).show();
                        recreate();
                        //finish();
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

        // Creates an alert dialog to pop up for user to input information when clicked.
        deleteAnimal = (Button) findViewById(R.id.delete_animal_type);
        deleteAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle(R.string.delete_dialog_title);

                // Set up the input
                final EditText input = new EditText(HomeActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton(R.string.delete_animal_continue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        AlertDialog deleteConfirmation = AskOption();
                        deleteConfirmation.show();
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

    private AlertDialog AskOption() {
        AlertDialog deleteConfirmation =new AlertDialog.Builder(this)
                //set message and title
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete the animal type: " + m_Text + "?")
                // TODO: insert animals name that will be deleted

                .setPositiveButton ("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //code to remove from database
                        // TODO: insert code to remove animal from DB
                        dialog.dismiss();
                    }

                })

                .setNegativeButton ("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return deleteConfirmation;

    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in ListView");

        Cursor data = mydb.getAnimalTypes();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(0));
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.animal_list_text_view, listData);
        animalListView.setAdapter(adapter);
    }
}



