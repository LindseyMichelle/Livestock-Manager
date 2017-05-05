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
    // These variables need to come from the DB;
    public Integer purchasePrice = 100; // AnimalInfo: purchase price DB: animal (price)
    public Integer salePrice = 0; // SpecificAnimalView: selling price; DB: profits(gain)
    public Integer feedCostPerBag = 50; // AnimalInfo: Cost/Bag of Feed; DB: feed(cost)
    public Integer feedPerDay = 1; // AnimalInfo: Feed Amount/Day; DB: feed(regiment)
    public Integer feedLbsPerBag = 50; // AnimalInfo: Cost/Bag of Feed DB: feed(amount)
//    public Double profit = 0.0; // TODO: needs to store in DB somewhere. Make new variable for Profit in profit table
    public String daysOwned = "200.0";// TODO: AnimalInfo: Purchase Date (need to calculate): DB: Animal(purchase date)
    public String profitDisplayString;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_animal_view);
        getIntent();
        mydb = new DatabaseHelper(this);
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
//        final Cursor ptd = mydb.getProfit();

        profitDisplayString = calculateProfit(pd.getString(0), pp.getString(0),
                sp.getString(0), fr.getString(0), fa.getString(0), fc.getString(0));

        tv.setText(String.valueOf(profitDisplayString));
//        TODO: cannot be in the onCreate- where to move it? 

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
                // TODO: insert code to delete from DB
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
                final EditText spInput = new EditText(SpecificAnimalView.this);
                spInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(spInput);

                // Set up the buttons
                builder.setPositiveButton(R.string.sold, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        s_text = spInput.getText().toString();
                        // TODO: Gray out item in list
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
                        // TODO: insert animals name that will be deleted

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


    // used to round the Doubles for the profit calculation
//    public static double round(double value, int places) {
//        if (places < 0) throw new IllegalArgumentException();
//
//        BigDecimal bd = new BigDecimal(value);
//        bd = bd.setScale(places, RoundingMode.HALF_UP);
//        return bd.doubleValue();
//    }



    public String calculateProfit (String pd, String pp, String sp, String fr, String fa, String fc) {

        Double profit = 0.0;
        String profitString;

        if (pp == null || pp.equalsIgnoreCase("")) {
            pp = "0.0";
        }

        if (sp == null || sp.equalsIgnoreCase("")) {
            sp = "0.0";
        }

        if (fr == null || fr.equalsIgnoreCase("")) {
            fr = "0.0";
        }

        if (fa == null || fa.equalsIgnoreCase("")) {
            fa = "0.0";
        }

        if (fc == null || fc.equalsIgnoreCase("")) {
            fc = "0.0";
        }



        Double feedCostPerBag = Double.parseDouble(fc);
        Double feedLbsPerBag = Double.parseDouble(fa);
        Double feedPoundsPerDay = Double.parseDouble(fr);
        Double purchasePrice = Double.parseDouble(pp);
        //Double sellingPrice = 0.0;
        Double sellingPrice = Double.parseDouble(sp); // This does not work. DB sellingPriceInsert at fault?
        Date purchaseDateDate = DateUtil.stringToDate(pd);
        Calendar purchaseDateCal = DateUtil.dateToCalendar(purchaseDateDate);
        Calendar currentDate = Calendar.getInstance(Locale.getDefault());
        int daysOwned = DateComparator.daysOwned(currentDate, purchaseDateCal);
        double daysOwnedDouble = (double) daysOwned;



        Double costPerPound = feedCostPerBag/feedLbsPerBag;
//        System.out.println("Cost Per Pound: " + costPerPound);

        Double feedCostPerDay = feedPoundsPerDay*costPerPound;
//        System.out.println("feedCostPerDay: " + feedCostPerDay);

        System.out.println("Days Owned: " + daysOwnedDouble);
        Double totalRunningFeedCost = daysOwnedDouble*feedCostPerDay;
//        System.out.println("Total Running Feed Cost: " + totalRunningFeedCost);

//        System.out.println("Profit Before Calc: " + profit);
        profit = -1*(totalRunningFeedCost+purchasePrice)+sellingPrice;
        System.out.println("Profit After Calc: " + profit);

        profitString = profit.toString();
        mydb.insertProfit(profitString);


        // remove this toast once it is for sure inserted into the DB
//        boolean profitInserted = mydb.insertProfit(profitString);
//        if (profitInserted) {
//            Toast.makeText(SpecificAnimalView.this, "Profit Inserted", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(SpecificAnimalView.this, "Profit not Inserted", Toast.LENGTH_LONG).show();
//        }

        // There are just to test intermediate steps- not used in calculation.
//        System.out.println("Days Profit After Calc: " + profit);
//        System.out.println("Cost Per Pound: " + costPerPound);
//        System.out.println("feedCostPerDay: " + feedCostPerDay);
//        System.out.println("Selling Price: " + sellingPrice);
//        System.out.println("Days Owned: " + daysOwnedDouble);
//        System.out.println("Total Running Feed Cost: " + totalRunningFeedCost);
//        System.out.println("Days Profit Before Calc: " + profit);

        return profitString;
    }
}
