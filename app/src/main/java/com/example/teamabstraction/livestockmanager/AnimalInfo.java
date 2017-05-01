package com.example.teamabstraction.livestockmanager;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




// This activity is the input page that will record the basic
// info about the animal to the database.
public class AnimalInfo extends AppCompatActivity
{

    DatabaseHelper mydb;
    Button button_add;

    EditText editName, editBreed, editGender, editNChildren, editProduct,
            editPurchaseDate, editPurchasePrice, editFeedName, editFeedAmount,
            editFeedRegiment, editFeedCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_info);
        getIntent();
        mydb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editTextName);
        editBreed = (EditText) findViewById(R.id.editTextBreed);
        editGender = (EditText) findViewById(R.id.editTextGender);
        editNChildren = (EditText) findViewById(R.id.editTextNChildren);
        editProduct = (EditText) findViewById(R.id.editTextProduct);
        editPurchaseDate = (EditText) findViewById(R.id.editTextPurchaseDate);
        editPurchasePrice = (EditText) findViewById(R.id.editTextPurchasePrice);
        editFeedName = (EditText) findViewById(R.id.editTextFeedName);
        editFeedAmount = (EditText) findViewById(R.id.editTextFeedAmount);
        editFeedRegiment = (EditText) findViewById(R.id.editTextFeedRegiment);
        editFeedCost = (EditText) findViewById(R.id.editTextFeedCost);
        button_add = (Button) findViewById(R.id.button_Add);
        AddData();
    }

    public void AddData() {
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: call methods for insert to each table, rename this to animal insert
                boolean isInserted = mydb.insertAnimalData(
                        editName.getText().toString(),
                        editBreed.getText().toString(),
                        editGender.getText().toString(),
                        editNChildren.getText().toString(),
                        editProduct.getText().toString(),
                        editPurchaseDate.getText().toString(),
                        editPurchasePrice.getText().toString(),
                        GlobalVariables.getInstance().aType);



                // TODO: call insert feed method- not working
                boolean feedInserted = mydb.insertFeedData(editFeedName.getText().toString(),
                        editFeedAmount.getText().toString(),
                        editFeedRegiment.getText().toString(),
                        editFeedCost.getText().toString());



                if(isInserted && feedInserted) {
                    Toast.makeText(AnimalInfo.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AnimalInfo.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            // TODO: remove finish and go back to list of ind animal
        });
    }
}
