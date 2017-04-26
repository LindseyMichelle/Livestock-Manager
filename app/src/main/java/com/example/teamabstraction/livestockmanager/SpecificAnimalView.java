package com.example.teamabstraction.livestockmanager;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


// This activity is will display information about specific animal
// and will allow for that info to be edited or the animal to be
// deleted.
public class SpecificAnimalView extends AppCompatActivity {

    private Button deleteAnimal;
    private Button editAnimal;
    private Button markAsSold;
    private String s_text= "";
    // These variables need to come from the DB;
    public Integer purchasePrice = 100; // AnimalInfo: purchase price DB: animal (price)
    public Integer sellingPrice = 0; // SpecificAnimalView: selling price; DB: profits(gain)
    public Integer feedCostPerBag = 50; // AnimalInfo: Cost/Bag of Feed; DB: feed(cost)
    public Integer feedPerDay = 1; // AnimalInfo: Feed Amount/Day; DB: feed(regiment)
    public Integer feedLbsPerBag = 50; // AnimalInfo: Cost/Bag of Feed DB: feed(amount)
    public Integer profit = 0; // TODO: needs to store in DB somewhere. Make new variable for Profit in profit table
    public Integer daysOwned = 200; // TODO: AnimalInfo: Purchase Date (need to calculate): DB: Animal(purchase date)
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_animal_view);
        getIntent();
        mydb = new DatabaseHelper(this);
        TextView tv = (TextView)findViewById(R.id.specAnimalProfit);
        tv.setText("Profit to date: $" + calculateProfit(feedCostPerBag, feedLbsPerBag,
                feedPerDay, purchasePrice, sellingPrice, daysOwned));

        /*
        TextView tcost = (TextView)findViewById(R.id.textFeedCostDisplay);
        Cursor Fcost = mydb.getFeedDataCost();
        tcost.setText(Fcost.getString(3));
*/

        // creates delete button to remove specific animal from DB
        deleteAnimal = (Button) findViewById(R.id.delete_animal);
        deleteAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog deleteConfirmation = AskOption();
                deleteConfirmation.show();
                // TODO: insert code to delete from DB
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
                        Toast.makeText(SpecificAnimalView.this, "Selling Price Recorded", Toast.LENGTH_LONG).show();
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
        final String name = GlobalVariables.getInstance().aName;
        AlertDialog deleteConfirmation =new AlertDialog.Builder(this)
                //set message and title
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete " + name + "?" )
                        // TODO: insert animals name that will be deleted

                        .setPositiveButton ("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mydb.deleteAnimal(name);
                                finish();
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




    public int calculateProfit (int feedCostPerBag, int feedLbsPerBag, int feedPoundsPerDay,
                                 int purchasePrice, int sellingPrice, int daysOwned) {

        int costPerPound = feedCostPerBag/feedLbsPerBag;
        int feedCostPerDay = feedPoundsPerDay*costPerPound;
        int totalRunningFeedCost = daysOwned*feedCostPerDay;

        profit = -1*(totalRunningFeedCost+purchasePrice)+sellingPrice;

        return profit;
    }

}
