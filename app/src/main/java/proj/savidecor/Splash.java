package proj.savidecor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import proj.savidecor.Models.SetupDetails;
import proj.savidecor.Utils.Apiclient;
import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.ItemAPI;
import retrofit2.Call;
import retrofit2.Response;

public class Splash extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static SetupDetails setupdata;
    private String imei;
    RelativeLayout rel_splash_background;
    TextView splashtv;
    final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 5;
    ImageView splashTitle, splashBackground;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Fabric.with(this, new Crashlytics());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
      //  Fabric.with(this, new Crashlytics());
        splashtv = (TextView) findViewById(R.id.splashtext);
        rel_splash_background = (RelativeLayout) findViewById(R.id.rel_splash_background);
        ProgressBar pbd = (ProgressBar) findViewById(R.id.splashPBD);

        splashTitle = (ImageView) findViewById(R.id.splashtop);
        splashBackground = (ImageView) findViewById(R.id.splash_screen_background);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        LinearLayout.LayoutParams splashTitleIvParams = (LinearLayout.LayoutParams) splashTitle
                .getLayoutParams();
        splashTitleIvParams.width = (int) (displayMetrics.widthPixels / 1.4);


        sharedPreferences = getSharedPreferences("VIEW", MODE_PRIVATE);
        Glide.with(this).load(sharedPreferences.getString(Constants.SplashTitle, "")).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(splashTitle);
        Glide.with(this).load(sharedPreferences.getString(Constants.SplashBackground, "")).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(splashBackground);


        try {
            pbd.getIndeterminateDrawable().setColorFilter(Color.parseColor("#59a4af"), PorterDuff.Mode.SRC_ATOP);
            String text = "<font color='#59a4af'>SHOP FROM </font><font color='#00000'>OUR UNIQUE COLLECTION</font>";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                splashtv.setText(Html.fromHtml(text, 0), TextView.BufferType.SPANNABLE);
            } else {
                //noinspection deprecation
                splashtv.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setPermission();
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
            try {
//                fetchSetup();
                //fetchImage();
                Intent mainact = new Intent(Splash.this, MainActivity.class);
                mainact.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainact);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("HardwareIds")
    @TargetApi(Build.VERSION_CODES.M)
    private void setPermission() {

        List<String> permissionsNeeded = new ArrayList<>();

        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("Read Phone State");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write External Storage");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);

                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
        Log.e("imeino ", "" + imei);

        try {

//            fetchSetup();
            //fetchImage();
            Intent mainact = new Intent(Splash.this, MainActivity.class);
            mainact.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainact);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Splash.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission)) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint("HardwareIds")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();

                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        ) {

                    TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                    imei = telephonyManager.getDeviceId();
                    try {
//                        fetchSetup();
                        Intent mainact = new Intent(Splash.this, MainActivity.class);
                        mainact.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainact);
                    } catch (Exception e) {
                        Log.w("WAHH3", e.getMessage());
                    }

                } else {
                    Toast.makeText(Splash.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

   /* private void fetchImage() {
//        fetchSetup();
        ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
        final Call<SetupDetails> call = itemAPI.getSetup(Constants.SITE_ID, imei);
        try {
            call.enqueue(new Constants.BackoffCallback<SetupDetails>(3) {
                @Override
                public void onResponse(final Call<SetupDetails> call, Response<SetupDetails> response) {
                    try {

                        setupdata = response.body();

                        Log.d("fetImage",""+setupdata);
                        if (response.isSuccessful()) {
                            SharedPreferences.Editor editor;
                            editor = sharedPreferences.edit();
                            editor.putString(Constants.SplashTitle, setupdata.getSplashScreenTitle());
                            editor.putString(Constants.SplashBackground, setupdata.getSplashScreenBackground());
                            editor.apply();
                            Glide.with(Splash.this).load(setupdata.getSplashScreenTitle()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(splashTitle);
                            *//*Picasso.with(Splash.this)
                                    .load(setupdata.getSplashScreenTitle())
                                    .into(splashTitle);
*//*

                                if(setupdata.getSplashScreenBackground() != null && !setupdata.getSplashScreenBackground().isEmpty()){

                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inJustDecodeBounds = true;


                                    Glide.with(Splash.this).
                                            load(setupdata.getSplashScreenBackground())

                                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                            .into(splashBackground);

                                }
                                else {


                                    rel_splash_background.setBackgroundColor(Color.WHITE);
                                    //Glide.with(Splash.this).load("https://s3.amazonaws.com/neonmobile/splash_screen_background.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(splashBackground);


                                }

                            fetchSetup();

                        } else {
                            Constants.showToast(getBaseContext(), "Network error");
                        }
                    } catch (Exception r) {
                        r.printStackTrace();
                    }
                }

                @Override
                public void onFailedAfterRetry(Throwable t) {
                    t.printStackTrace();

                    Intent mainact = new Intent(Splash.this, MainActivity.class);
                    mainact.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainact);
                    // finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*private void fetchSetup() {
        ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
        final Call<SetupDetails> call = itemAPI.getSetup(Constants.SITE_ID, imei);
        try {
            call.enqueue(new Constants.BackoffCallback<SetupDetails>(3) {
                @Override
                public void onResponse(final Call<SetupDetails> call, Response<SetupDetails> response) {
                    try {
                        setupdata = response.body();
                        Log.e("response", "" + setupdata);
                        if (response.isSuccessful()) {

                            SharedPreferences.Editor editor;
                            editor = sharedPreferences.edit();
                            editor.putBoolean("LayoutVIEW", true);
                            editor.putString(Constants.APIURL, setupdata.getWebUrl().trim());
//                            editor.putString(Constants.STICKYMSG, setupdata.getStickyMessageLabel().trim());
                            editor.putInt(Constants.RANDOMID, Integer.parseInt(setupdata.getDefaultCatId()));
                            editor.putString(Constants.SITEID, setupdata.getId());
                            editor.putString(Constants.ANAME, setupdata.getName());
                            editor.putString(Constants.DID, imei);
                            editor.putString(Constants.CallNumber, setupdata.getPhoneNumber().trim());
                            editor.putInt(Constants.CartValue, Integer.parseInt(setupdata.getCart().trim()));
                            editor.putString(Constants.ToolbarIcon, setupdata.getToolbarIcon());
                            editor.putString(Constants.ToolbarBackground, setupdata.getToolbarBackgroundImage());
                            editor.apply();

                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent mainact = new Intent(Splash.this, MainActivity.class);
                                    mainact.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainact);
                                }
                            }, 2100);

                        } else {
                            Constants.showToast(getBaseContext(), "Network error");
                        }
                        Log.e("response", "" + setupdata);
                    } catch (Exception r) {
                        r.printStackTrace();
                    }
                }

                @Override
                public void onFailedAfterRetry(Throwable t) {
                    t.printStackTrace();

                    Intent mainact = new Intent(Splash.this, MainActivity.class);
                    mainact.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainact);
                    // finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void onPause() {
        super.onPause();
    }
}


