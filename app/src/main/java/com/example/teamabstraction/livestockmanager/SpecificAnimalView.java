package com.example.teamabstraction.livestockmanager;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;


// This activity is will display information about specific animal
// and will allow for that info to be edited or the animal to be
// deleted.
public class SpecificAnimalView extends AppCompatActivity {

    private Button deleteAnimal;
    private Button editAnimal;
    private Button markAsSold;
    private String s_text= "";
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_animal_view);
        getIntent();

        // creates delete button to remove specific animal from DB
        deleteAnimal = (Button) findViewById(R.id.delete_animal);
        deleteAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog deleteConfirmation = AskOption();
                deleteConfirmation.show();
            }
        });

        // creates edit button, sends all info back to AnimalInfo activity for editing
        editAnimal = (Button) findViewById(R.id.edit_animal);
        editAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO: Create intent that passes all values back to the AnimalInfo activity
            }
        });
        markAsSold = (Button) findViewById(R.id.mark_as_sold);
        markAsSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SpecificAnimalView.this);
                builder.setTitle(R.string.sold_dialog_title);

                // Set up the input
                final EditText input = new EditText(SpecificAnimalView.this);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton(R.string.sold, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        s_text = input.getText().toString();
                        Toast.makeText(SpecificAnimalView.this, "Selling Price Inserted", Toast.LENGTH_LONG).show();
                        // TODO: s_text to database- should this be an int? Gray out item in list
                        // http://stackoverflow.com/questions/1246613/android-list-with-grayed-out-items


//                        boolean isinserted = mydb.insertData(input.getText().toString()); TODO: insert into correct table
//                        if(isinserted = true) {
//                            Toast.makeText(SpecificAnimalView.this, "Selling Price Inserted", Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(SpecificAnimalView.this, "Data not Inserted", Toast.LENGTH_LONG).show();
//                            finish();
//
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




    // Function that shows alert dialog box
    private AlertDialog AskOption() {
        AlertDialog deleteConfirmation =new AlertDialog.Builder(this)
                //set message and title
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete (animal_name)?")
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

}
