package com.example.teamabstraction.livestockmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


// This activity will have the list of the specific animals
// (list of animals names i.e betsy, john, sue) and
// a button to add a new instance of the animal
public class IndAnimalList extends AppCompatActivity {
    private Button add_ind_animal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ind_animal_list);
        getIntent();



        add_ind_animal = (Button) findViewById(R.id.add_ind_animal);
        add_ind_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAnimalIntent = new Intent(IndAnimalList.this, AnimalInfo.class);
                startActivity(addAnimalIntent);
            }
        });

    }


}

