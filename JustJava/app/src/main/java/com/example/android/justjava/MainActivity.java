package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    String name;

    boolean hasWhippedCream = false;
    boolean hasChocolate = false;

    final int CONSTANT_PRICE = 5;
    final int PRICE_CREAM = 1;
    final int PRICE_CHOCOLATE = 2;

    CheckBox whippedCreamCheckBox;
    CheckBox chocolateCheckBox;
    EditText etName;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        whippedCreamCheckBox = findViewById(R.id.cream);
        chocolateCheckBox = findViewById(R.id.chocolate);
        etName = findViewById(R.id.ClientName);
    }

    public void submitOrder(View view) {
        name = etName.getText().toString();
        hasWhippedCream = whippedCreamCheckBox.isChecked();
        hasChocolate = chocolateCheckBox.isChecked();

        String message = (createOrderSummary(calculatePrice()));
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "denisignatjevs@live.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.java_order_for) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, getString(R.string.сhoose_email_app)));
        }
    }



    private int calculatePrice(){
        if(hasWhippedCream && hasChocolate){
            return quantity * CONSTANT_PRICE + PRICE_CREAM * quantity + PRICE_CHOCOLATE * quantity;
        } else if(hasWhippedCream){
            return quantity * CONSTANT_PRICE + PRICE_CREAM * quantity;
        } else if(hasChocolate){
            return quantity * CONSTANT_PRICE + PRICE_CHOCOLATE * quantity;
        }
        return quantity * CONSTANT_PRICE;
    }

    public String createOrderSummary(int price) {
        String priceMessage = getString(R.string.order_name) + "Name: " + name;
        priceMessage += "\n" + getString(R.string.cream_order) + "Add whipped cream? " + chooseCream();
        priceMessage += "\n" + getString(R.string.chocolate_order) + "Add chocolate? " + chooseChocolate();
        priceMessage += "\n" + getString(R.string.quantity_order) + "Quantity: " + quantity;
        priceMessage += "\n" + getString(R.string.total_price_order) + "Total: " + price + "€";
        priceMessage += "\n" + getString(R.string.thank_you) + "Thank you!";
        return priceMessage;
    }

    private String chooseCream(){
        if(this.hasWhippedCream) {
            return "Yes";
        } else {
            return "No";
        }
    }

    private String chooseChocolate(){
        if(this.hasChocolate) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public void increment(View view) {
        if(quantity < 10){
            quantity++;
        } else if(quantity == 10){
            Toast.makeText(getApplicationContext(), "Can't but more then 10 coffees",  Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity > 1){
            quantity--;
        } else if(quantity == 1){
            Toast.makeText(getApplicationContext(), "Can't but less then 1 coffees", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

    @Override

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("quantity", quantity);
        outState.putBoolean("whippedCream", hasWhippedCream);
        outState.putBoolean("chocolate", hasChocolate);
        outState.putString("name", name);
    }

    @Override

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantity = savedInstanceState.getInt("quantity");
        displayQuantity(quantity);
        name = savedInstanceState.getString("name");
        hasWhippedCream = savedInstanceState.getBoolean("whippedCream");
        hasChocolate = savedInstanceState.getBoolean("chocolate");
    }
}