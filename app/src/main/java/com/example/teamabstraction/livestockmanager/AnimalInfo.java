package com.example.teamabstraction.livestockmanager;


import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;


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

        if(GlobalVariables.getInstance().edit == true){
            Cursor br = mydb.getAnimalBreed();
            Cursor gen = mydb.getAnimalGender();
            Cursor nC = mydb.getAnimalNChildren();
            Cursor pr = mydb.getAnimalProduct();
            Cursor pd = mydb.getAnimalPurchaseDate();
            Cursor pp = mydb.getAnimalPurchasePrice();
            Cursor fn = mydb.getAnimalFeedName();
            Cursor fr = mydb.getAnimalFeedRegiment();
            Cursor fa = mydb.getAnimalFeedAmount();
            Cursor fc = mydb.getAnimalFeedCost();


            editName.setText(GlobalVariables.getInstance().aName);
            editBreed.setText(br.getString(0));
            editGender.setText(gen.getString(0));
            editNChildren.setText(nC.getString(0));
            editProduct.setText(pr.getString(0));
            editPurchaseDate.setText(pd.getString(0));
            editPurchasePrice.setText(pp.getString(0));
            editFeedName.setText(fn.getString(0));
            editFeedAmount.setText(fa.getString(0));
            editFeedRegiment.setText(fr.getString(0));
            editFeedCost.setText(fc.getString(0));
            button_add.setText("Save");

        }
        AddData();
    }

    public void AddData() {
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(GlobalVariables.getInstance().edit == true){
                    boolean isUpdated = mydb.updateAnimalTable(
                            editName.getText().toString(),
                            editBreed.getText().toString(),
                            editGender.getText().toString(),
                            editNChildren.getText().toString(),
                            editProduct.getText().toString(),
                            editPurchaseDate.getText().toString(),
                            editPurchasePrice.getText().toString(),
                            GlobalVariables.getInstance().aType);



                    boolean feedUpdated = mydb.updateFeedData(editFeedName.getText().toString(),
                            editFeedAmount.getText().toString(),
                            editFeedRegiment.getText().toString(),
                            editFeedCost.getText().toString());


                    if (isUpdated && feedUpdated) {
                        Toast.makeText(AnimalInfo.this, "Data Updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(AnimalInfo.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }else {
                    boolean isInserted = mydb.insertAnimalData(
                            editName.getText().toString(),
                            editBreed.getText().toString(),
                            editGender.getText().toString(),
                            editNChildren.getText().toString(),
                            editProduct.getText().toString(),
                            editPurchaseDate.getText().toString(),
                            editPurchasePrice.getText().toString(),
                            GlobalVariables.getInstance().aType);


                    boolean feedInserted = mydb.insertFeedData(editFeedName.getText().toString(),
                            editFeedAmount.getText().toString(),
                            editFeedRegiment.getText().toString(),
                            editFeedCost.getText().toString());


                    if (isInserted && feedInserted) {
                        Toast.makeText(AnimalInfo.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        GlobalVariables.getInstance().change = true;
                        finish();
                    } else {
                        Toast.makeText(AnimalInfo.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
    }
}
