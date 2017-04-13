package com.example.teamabstraction.livestockmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


// This activity will have the list of the specific animals
// (list of animals names i.e betsy, john, sue) and
// a button to add a new instance of the animal
public class IndAnimalList extends AppCompatActivity {
    private Button add_ind_animal;
    private ListView individualAnimalListView;
    private ArrayAdapter<String> individualAnimalListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ind_animal_list);
        getIntent();

        individualAnimalListView = (ListView) findViewById(R.id.individualAnimalList);
        individualAnimalListView.setClickable(true);

        //Creates and populates a list of animals (May need to import from a database later
        String[] indvidualAnimals = new String[]{"Betsie", "George", "Elizabeth", "Bartholemew"};
        final ArrayList<String> listOfIndvidualAnimalsArray = new ArrayList<String>();
        listOfIndvidualAnimalsArray.addAll(Arrays.asList(indvidualAnimals));
        individualAnimalListAdapter = new ArrayAdapter<String>(this, R.layout.animal_list_text_view, listOfIndvidualAnimalsArray);

        //Set the ArrayAdapter as the ListView's Adapter
        individualAnimalListView.setAdapter(individualAnimalListAdapter);


        //This will display the animal information once the user clicks on it.
        individualAnimalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //This will be an intent to Jimmies ListView of the animal information.
                Intent indAnimalIntent = new Intent(IndAnimalList.this, SpecificAnimalView.class);
                startActivity(indAnimalIntent);
            }
        });

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

