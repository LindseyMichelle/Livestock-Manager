package com.example.teamabstraction.livestockmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


// This activity is the input page that will record the basic
// info about the animal to the database.
public class AnimalInfo extends AppCompatActivity {

    Button button_add;
    String nameText;
    String breedText;
    String genderText;
    String nChildrenText;
    String productText;

    EditText editName, editBreed, editGender, editNChildren, editProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_info);
        getIntent();

        editName = (EditText) findViewById(R.id.editTextName);
        editBreed = (EditText) findViewById(R.id.editTextBreed);
        editGender = (EditText) findViewById(R.id.editTextGender);
        editNChildren = (EditText) findViewById(R.id.editTextNChildren);
        editProduct = (EditText) findViewById(R.id.editTextProduct);
        button_add = (Button) findViewById(R.id.button_Add);
        AddData();
    }

    public void AddData() {
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameText = editName.getText().toString();
                breedText = editBreed.getText().toString();
                genderText = editGender.getText().toString();
                nChildrenText = editNChildren.getText().toString();
                productText = editProduct.getText().toString();

                if (nameText != null && breedText != null && genderText != null
                        && nChildrenText != null && productText != null) {
                    Toast.makeText(AnimalInfo.this, "Data Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AnimalInfo.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                }
                finish();
            }
            // TODO: remove finish and go back to list of ind animal
        });
    }
}