package com.example.pepe.tipcalc.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pepe.tipcalc.R;
import com.example.pepe.tipcalc.TipCalcClass;
import com.example.pepe.tipcalc.fragments.TipHistoryListFragment;
import com.example.pepe.tipcalc.fragments.TipHistoryListFragmentListener;
import com.example.pepe.tipcalc.model.TipRecord;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText inputBill;
    EditText inputPercentage;
    Button btnIncrease;
    Button btnDecrease;
    Button btnClear;
    TextView txtTip;

    private TipHistoryListFragmentListener fragmentListener;
    private final static int TIP_STEP_CHANGE = 1;
    private final static int DEFAULT_TIP_PERCENTAGE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        inputBill = (EditText)findViewById(R.id.inputBill);
        inputPercentage = (EditText)findViewById(R.id.inputPercentage);
        btnIncrease = (Button)findViewById(R.id.btnIncrease);
        btnDecrease = (Button)findViewById(R.id.btnDecrease);
        btnClear = (Button)findViewById(R.id.btnClear);
        txtTip = (TextView)findViewById(R.id.txtTip);
        TipHistoryListFragment fragment = (TipHistoryListFragment) getSupportFragmentManager()
                                                            .findFragmentById(R.id.fragmentList);
        fragment.setRetainInstance(true);
        fragmentListener = (TipHistoryListFragmentListener) fragment;
        Log.e(getLocalClassName(),"fragmentListener created");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                String strInputTotal = inputBill.getText().toString().trim();
                if (!strInputTotal.isEmpty()){
                    double total = Double.parseDouble(strInputTotal);
                    int tipPercentage = getTipPercentage();

                    TipRecord tipRecord = new TipRecord();
                    tipRecord.setBill(total);
                    tipRecord.setTipPercentage(tipPercentage);
                    tipRecord.setTimeStamp(new Date());

                    String strTip = String.format(getString(R.string.global_message_tip),tipRecord.getTip());
                    fragmentListener.addToList(tipRecord);
                    txtTip.setVisibility(View.VISIBLE);
                    txtTip.setText(strTip);
                }
//                Log.e(getLocalClassName(),"click on Submit");
            }
        });
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                handleTipChange(TIP_STEP_CHANGE);
            }
        });
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                handleTipChange(-TIP_STEP_CHANGE);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickClear();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void handleClickClear() {
        fragmentListener.clearList();
    }

    private void handleTipChange(int stepSize) {
        int currentPercentage = getTipPercentage();
        currentPercentage += stepSize;
        if (currentPercentage > 0) {
            inputPercentage.setText(String.valueOf(currentPercentage));
        }
    }

    private int getTipPercentage() {
        int tipPercentage = DEFAULT_TIP_PERCENTAGE;
        String strInputPercentage = inputPercentage.getText().toString().trim();
        if (!strInputPercentage.isEmpty()){
            tipPercentage = Integer.parseInt(strInputPercentage);
        } else {
            inputPercentage.setText(String.valueOf(tipPercentage));
        }
        return tipPercentage;
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        try{
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            Log.e(getLocalClassName(),Log.getStackTraceString(npe));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_about:
                about();
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void about() {
        TipCalcClass app = (TipCalcClass)getApplication();
        String strUrl = app.getAboutUrl();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(strUrl));
        startActivity(intent);
    }
}
