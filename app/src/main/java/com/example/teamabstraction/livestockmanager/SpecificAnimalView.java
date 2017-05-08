package com.example.teamabstraction.livestockmanager;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


// This activity is will display information about specific animal
// and will allow for that info to be edited or the animal to be
// deleted.
public class SpecificAnimalView extends AppCompatActivity {

    private Button deleteAnimal;
    private Button editAnimal;
    private Button markAsSold;
    private String s_text= "";
    public String profitDisplayString;
    private TextView specificAnimalMessage;

    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_animal_view);
        getIntent();
        mydb = new DatabaseHelper(this);
        specificAnimalMessage = (TextView)  findViewById(R.id.specificAnimalMessage);
        specificAnimalMessage.setText(R.string.specificAnimalMessage);

      final TextView tv = (TextView)findViewById(R.id.specAnimalProfit);
        TextView aName = (TextView)findViewById(R.id.textAnimalNameDB);
        TextView breed = (TextView)findViewById(R.id.textBreedDisplayDB);
        TextView gender = (TextView)findViewById(R.id.textGenderDisplayDB);
        TextView nChildren = (TextView)findViewById(R.id.textChildrenDisplayDB);
        TextView product = (TextView)findViewById(R.id.textProductDisplayDB);
        TextView purchaseDate = (TextView)findViewById(R.id.textPurchaseDateDisplayDB);
        TextView purchaseprice = (TextView)findViewById(R.id.textPurchasePriceDB);
        TextView sellingprice = (TextView)findViewById(R.id.textSellingPriceDisplayDB); // newly added for selling price
        TextView feedname = (TextView)findViewById(R.id.textFeedNameDisplayDB);
        TextView feedregiment = (TextView)findViewById(R.id.textFeedAmountDisplayDB);
        TextView feedamount = (TextView)findViewById(R.id.textFeedPoundsDisplayDB);
        TextView feedcost = (TextView)findViewById(R.id.textFeedCostDisplayDB);

        final Cursor br = mydb.getAnimalBreed();
        final Cursor gen = mydb.getAnimalGender();
        final Cursor nC = mydb.getAnimalNChildren();
        final Cursor pr = mydb.getAnimalProduct();
        final Cursor pd = mydb.getAnimalPurchaseDate();
        final Cursor pp = mydb.getAnimalPurchasePrice();
        final Cursor sp = mydb.getAnimalSellingPrice(); // newly added for selling price
        final Cursor fn = mydb.getAnimalFeedName();
        final Cursor fr = mydb.getAnimalFeedRegiment();
        final Cursor fa = mydb.getAnimalFeedAmount();
        final Cursor fc = mydb.getAnimalFeedCost();

        profitDisplayString = ProfitUtil.calculateProfit(this);


        tv.setText(String.valueOf(profitDisplayString));


        aName.setText(GlobalVariables.getInstance().aName);
        breed.setText(br.getString(0));
        gender.setText(gen.getString(0));
        nChildren.setText(nC.getString(0));
        product.setText(pr.getString(0));
        purchaseDate.setText(pd.getString(0));
        purchaseprice.setText(pp.getString(0));
        sellingprice.setText(sp.getString(0)); // newly added for selling price
        feedname.setText(fn.getString(0));
        feedregiment.setText(fr.getString(0));
        feedamount.setText(fa.getString(0));
        feedcost.setText(fc.getString(0));

        System.out.println("Selling Price: " + sellingprice);


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
                GlobalVariables.getInstance().edit = true;
                Intent editAnimalIntent = new Intent(SpecificAnimalView.this, AnimalInfo.class);
                startActivity(editAnimalIntent);
            }
        });
        markAsSold = (Button) findViewById(R.id.mark_as_sold);
        markAsSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SpecificAnimalView.this);
                builder.setTitle(R.string.sold_dialog_title);

                // Set up the input
                final EditText spInput = new EditText(SpecificAnimalView.this);
                spInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(spInput);

                // Set up the buttons
                builder.setPositiveButton(R.string.sold, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        s_text = spInput.getText().toString();
                        // http://stackoverflow.com/questions/1246613/android-list-with-grayed-out-items

                    boolean priceInserted = mydb.updateSellingPrice(spInput.getText().toString());
                    if(priceInserted) {
                        Toast.makeText(SpecificAnimalView.this, "Selling Price Recorded", Toast.LENGTH_LONG).show();
                        recreate();
                    } else {
                        Toast.makeText(SpecificAnimalView.this, "Selling Price Not Recorded", Toast.LENGTH_LONG).show();
                        finish();
                    }

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

    @Override
    protected void onResume() {
        super.onResume();
        if(GlobalVariables.getInstance().edit == true) {
            GlobalVariables.getInstance().edit = false;
            recreate();
        }
    }



    // Function that shows alert dialog box
    private AlertDialog AskOption() {
        final String name = GlobalVariables.getInstance().aName;
        AlertDialog deleteConfirmation =new AlertDialog.Builder(this)
                //set message and title
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete " + name + "?" )

                        .setPositiveButton ("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mydb.deleteAnimal(name);
                                GlobalVariables.getInstance().change = true;
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
}
