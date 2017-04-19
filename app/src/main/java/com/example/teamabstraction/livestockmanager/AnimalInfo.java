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




//package com.example.teamabstraction.livestockmanager;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.database.Cursor;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.EditText;
//import android.widget.Toast;
//
//
//// This activity is the input page that will record the basic
//// info about the animal to the database.
//public class AnimalInfo extends AppCompatActivity {
//
//    static {
//        System.loadLibrary("native-lib");
//    }
//
//    DatabaseHelper mydb;
//    EditText editName, editBreed, editGender, editNChildren, editProduct;
//    Button button_add;
//    Button buttonV;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_animal_info);
//        getIntent();
//
//        mydb = new DatabaseHelper(this);
//
//        editName = (EditText) findViewById(R.id.editTextName);
//        editBreed = (EditText) findViewById(R.id.editTextBreed);
//        editGender = (EditText) findViewById(R.id.editTextGender);
//        editNChildren = (EditText) findViewById(R.id.editTextNChildren);
//        editProduct = (EditText) findViewById(R.id.editTextProduct);
//        button_add = (Button) findViewById(R.id.button_Add);
//        buttonV = (Button) findViewById(R.id.buttonView);
//        AddData();
//        viewAnimal();
//    }
//
//    public void AddData() {
//        button_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isinserted =
//                        mydb.insertData(editName.getText().toString(),
//                                editBreed.getText().toString(),
//                                editGender.getText().toString(),
//                                editNChildren.getText().toString(),
//                                editProduct.getText().toString());
//
//                // TODO: is this working?
//                if (isinserted == true)
//                    Toast.makeText(AnimalInfo.this, "Data Inserted", Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(AnimalInfo.this, "Data not Inserted", Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }
//
//    public void viewAnimal() {
//        buttonV.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Cursor res = mydb.getAnimalData();
//                        if(res.getCount() == 0) {
//                            showMessage("No Data");
//                            return;
//                        }
//
//                        StringBuffer buffer = new StringBuffer();
//                        while(res.moveToNext()){
//                            buffer.append("Name :" + res.getString(0) + "\n");
//                            buffer.append("Breed :" + res.getString(1) + "\n");
//                            buffer.append("Gender :" + res.getString(2) + "\n");
//                            buffer.append("NumChildren :" + res.getString(3) + "\n");
//                            buffer.append("Product :" + res.getString(4) + "\n");
//                            // This code wont work until DBHelper is fixed
////                            buffer.append("Feed Name: " + res.getString(5) + "\n");
////                            buffer.append("Feed Regiment: " + res.getString(6) + "\n");
////                            buffer.append("Feed Amount: " + res.getString(7) + "\n");
////                            buffer.append("Feed Cost: " + res.getString(8) + "\n");
////                            buffer.append("Purchase Price: " + res.getString(9) + "\n");
//                        }
//
//                        //show data
//                        showMessage(buffer.toString());
//                    }
//                });
//    }
//    public void showMessage(String Message){
//        TextView textViewData = (TextView)findViewById(R.id.textViewData);
//        textViewData.setText(Message);
//    }
//}
