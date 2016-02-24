package com.android.invoq;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private static int SPLASH_TIME_OUT = 3000;
    Boolean isConnectionExist = false;
    WIFIInternetConnectionDetector cd;
    private ImageView image_refresh;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        image_refresh = (ImageView) findViewById(R.id.image_refresh);

        cd = new WIFIInternetConnectionDetector(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isConnectionExist = cd.checkMobileInternetConn();

                if (isConnectionExist)
                {
                    launchActivity(ScannerActivity.class);
                    //finish();
                } else
                {
                    showAlertDialog(MainActivity.this, "No Internet Connection",
                            "Your device doesn't have WIFI internet access please connect and try to refresh again", false);
                }
            }
        }, SPLASH_TIME_OUT);

        image_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                isConnectionExist = cd.checkMobileInternetConn();

                if (isConnectionExist)
                {
                    launchActivity(ScannerActivity.class);
                    //finish();

                } else
                {
                    showAlertDialog(MainActivity.this, "No Internet Connection",
                            "Your device doesn't have WIFI internet access", false);
                }
            }
        });
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    public void showAlertDialog(Context context,String title,String message, Boolean status)
    {
        final AlertDialog alertdialog = new AlertDialog.Builder(context).create();

        //Title
        alertdialog.setTitle(title);

        //Message
        alertdialog.setMessage(message);

        //Icon
        alertdialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        //Ok Button
        alertdialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                alertdialog.dismiss();
            }
        });
        // Showing Alert Message
        alertdialog.show();
    }

}
