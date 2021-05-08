package com.example.eazyshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanItem extends AppCompatActivity {
    private String process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_item);
        process=getIntent().getExtras().get("Shop").toString();
        scanProduct();
    }

    private void scanProduct() {
        IntentIntegrator integrator=new IntentIntegrator(this);
        integrator.setCaptureActivity(Scanning.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning");
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()!=null){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Scanning Result");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanProduct();
                    }
                }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(process.equals("Item")) {
                            finish();
                            Intent i = new Intent(ScanItem.this, ProductDetails.class);
                            i.putExtra("barcode", result.getContents().toString());
                            startActivity(i);
                        }
                        if(process.equals("Shop")){
                            if(result.getContents().toString().equals("10012345678902")){
                                Intent i = new Intent(ScanItem.this,LoginActivity.class);
                                startActivity(i);
                            }
                            else{
                                builder.setMessage("No such shop is registered");
                            }
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                finish();
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}