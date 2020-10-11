package com.example.justjava;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numOfSugar = 2;
    int price = 0;
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.espresso:
                if (checked)
                    price +=2;
                    str = "Espresso";
                    break;
            case R.id.americano:
                if (checked)
                    price +=3;
                    str = "Americano";
                    break;
            case R.id.cappuccino:
                if (checked)
                    price +=3.5;
                    str = "Cappuccino";
                    break;
            case R.id.latte:
                if (checked)
                    price +=4;
                    str = "Latte";
                    break;
        }
    }



        public void submitOrder(View view) {
        //Adding name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //Checkboxes
        CheckBox whippedCreamCheck = (CheckBox) findViewById(R.id.whippedCreamCheck);
        boolean hasWhippedCream = whippedCreamCheck.isChecked();

        CheckBox chocolateCheck = (CheckBox) findViewById(R.id.chocolateCheck);
        boolean hasChocolate = chocolateCheck.isChecked();

        CheckBox caramelCheck = (CheckBox) findViewById(R.id.caramelCheck);
        boolean hasCaramel = caramelCheck.isChecked();

        CheckBox tallCheck = (CheckBox) findViewById(R.id.tallCheck);
        boolean isTall = tallCheck.isChecked();

        CheckBox grandeCheck = (CheckBox) findViewById(R.id.grandeCheck);
        boolean isGrande = grandeCheck.isChecked();

        CheckBox ventiCheck = (CheckBox) findViewById(R.id.ventiCheck);
        boolean isVenti = ventiCheck.isChecked();

        //Hot or iced checkboxes
        CheckBox hotCheck = (CheckBox) findViewById(R.id.hot);
        boolean isHot = hotCheck.isChecked();

        CheckBox icedCheck = (CheckBox) findViewById(R.id.iced);
        boolean isIced = icedCheck.isChecked();

        //Order summary creation
        String order = orderSummary(name, str, numOfSugar, hasWhippedCream, hasChocolate, hasCaramel, isTall, isGrande, isVenti, isHot, isIced);

        //Sending order summary to Gmail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Starbucks order for: " + name);
        intent.putExtra(Intent.EXTRA_TEXT, order);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }




    /**
     * Plus button is clicked.
     */
    public void increment(View view) {
        numOfSugar = numOfSugar + 1;
        display(numOfSugar);
    }

    /**
     * Minus button is clicked.
     */
    public void decrement(View view) {
        if (numOfSugar == 0) {
            return;
        }
        numOfSugar = numOfSugar - 1;
        display(numOfSugar);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate,
                               boolean hasCaramel, boolean isTall, boolean isGrande, boolean isVenti) {

        //Price calc sizes
        if (isTall) {
            price += 1;
        }
        if (isVenti) {
            price += 2;
        }
        if (isGrande) {
            price += 3;
        }

        //Price calc plus toppings
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 2;
        }
        if (hasCaramel) {
            price += 2;
        }
        return price;
    }

    private String orderSummary(String name, String str, int numOfSugar, boolean hasWhippedCream,
                                boolean hasChocolate, boolean hasCaramel,
                                boolean isTall, boolean isGrande, boolean isVenti, boolean isHot, boolean isIced) {
        String orderSum = "Your order summary:";
        orderSum += "\n ";
        orderSum += "\nName: " + name;


        //Hot/cold
        if (isHot) {
            orderSum += "\nHot";
        } else orderSum += "\nIced";

        //What
        orderSum += "\n"+ str;

        //Size
        if (isTall) {
            orderSum += "\nTall";
        }
        if (isGrande) {
            orderSum += "\nGrande";
        }
        if (isVenti) {
            orderSum += "\nVenti";
        }

        //Toppings
        if (hasWhippedCream) {
            orderSum += "\nWith whipped cream";
        }
        if (hasChocolate) {
            orderSum += "\nWith chocolate drizzle";
        }
        if (hasCaramel) {
            orderSum += "\nWith caramel drizzle";
        }
        //Price and thanks
        orderSum += "\nAmount of sugar: " + numOfSugar;
        orderSum += "\n ";
        orderSum += "\nTotal: $" + calculatePrice(hasWhippedCream, hasChocolate, hasCaramel, isTall, isGrande, isVenti);
        orderSum += "\n ";
        orderSum += "\nThank you!";
        return orderSum;
    }
}