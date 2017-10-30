package proj.savidecor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.google.gson.GsonBuilder;

import java.net.CookieHandler;

import okhttp3.ResponseBody;
import proj.savidecor.Utils.Apiclient;
import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.ItemAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static proj.savidecor.MainActivity.badgeValue;
import static proj.savidecor.MainActivity.callNum;


public class PaymentPage extends AppCompatActivity {

    private Toolbar toolbar;
    WebView webView;
    FrameLayout paymentRel;
    WebSettings webSettings;
    Fragment f4;
    static Menu mToolbarMenu;
SharedPreferences sharedPreferences;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        paymentRel = (FrameLayout) findViewById(R.id.paymentRel);
sharedPreferences=getSharedPreferences("VIEW", Context.MODE_PRIVATE);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.Checkout);
        }
        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String imei = telephonyManager.getDeviceId();

        webView = (WebView) findViewById(R.id.webView);

        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }

        webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);



        //just added
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        //just added
        CookieHandler.setDefault(new java.net.CookieManager());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }

        webView.setHorizontalScrollBarEnabled(false);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        ItemAPI itemAPI = Apiclient.PaymentClient("true", telephonyManager.getDeviceId()).create(ItemAPI.class);

        Call<ResponseBody> responseBodyCall = itemAPI.getTasks(Constants.SITE_ID);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        viewWEB(response.body().string());

                        Log.d("checkout",""+response.body().string());
                        Log.d("paymentpage",""+new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constants.showToast(getApplicationContext(), "Please retry");
                paymentRel.setVisibility(View.INVISIBLE);
            }

        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    assert getSupportActionBar() != null;
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                }
            }
        });
    }

    private void viewWEB(String s) {

        //webView.loadDataWithBaseURL("http://www.capitolareatechnology.com", s, "text/html", "UTF-8", null);
       //webView.loadDataWithBaseURL("http://www.everythingneon.com/", s, "text/html", "UTF-8", null);
       webView.loadDataWithBaseURL(sharedPreferences.getString(Constants.APIURL,""), s, "text/html", "UTF-8", null);


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (paymentRel.getVisibility() == View.INVISIBLE)
                    paymentRel.setVisibility(View.VISIBLE);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Constants.showToast(getApplicationContext(), "Please retry");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                paymentRel.setVisibility(View.INVISIBLE);
            }
        });

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        webView.freeMemory();
        webView.clearCache(true);
        webView.clearHistory();
        webView = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean aBoolean) {
                }
            });
            CookieManager.getInstance().flush();
        } else {

            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(this);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.callmenu);
        if (callNum.equalsIgnoreCase("")) {
            item.setVisible(false);
        } else {
            item.setVisible(true);
        }

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchedt));
        searchView.setQueryHint("Search Products...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                if (getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                if (query.length() < 3) {
                    Constants.showToast(PaymentPage.this, "Please enter atleast 3 character");
                } else {

                    f4 = new SearchProduct();
                    Bundle arg3 = new Bundle();
                    arg3.putString("title", query.trim());
                    f4.setArguments(arg3);

                    if (f4 != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_left_enter, R.anim.slide_left_exit);
                        fragmentTransaction.replace(R.id.container_body, f4, "Search");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        assert getSupportActionBar() != null;
                        //getSupportActionBar().setIcon(null);
                        getSupportActionBar().setLogo(null);
                    }
                }
                menu.findItem(R.id.searchedt).collapseActionView();


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mToolbarMenu = menu;
        MenuItem item2 = menu.findItem(R.id.searchedt);
        item2.setVisible(false);
        try {
            createCartBadge(badgeValue);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void createCartBadge(int i) {
        MenuItem cartItem = mToolbarMenu.findItem(R.id.cart);
        LayerDrawable localLayerDrawable = (LayerDrawable) cartItem.getIcon();
        Drawable cartBadgeDrawable = localLayerDrawable
                .findDrawableByLayerId(R.id.ic_badge);
        MainActivity.BadgeDrawable badgeDrawable;
        if ((cartBadgeDrawable != null)
                && ((cartBadgeDrawable instanceof MainActivity.BadgeDrawable))
                && (i < 10)) {
            badgeDrawable = (MainActivity.BadgeDrawable) cartBadgeDrawable;
        } else {
            badgeDrawable = new MainActivity.BadgeDrawable(PaymentPage.this);
        }
        badgeDrawable.setCount(i);
        localLayerDrawable.mutate();
        localLayerDrawable.setDrawableByLayerId(R.id.ic_badge, badgeDrawable);
        cartItem.setIcon(localLayerDrawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        switch (item.getItemId()) {
            case R.id.searchedt:
                return true;
            case android.R.id.home:
                return true;
            case R.id.cart:
                onBackPressed();
                return true;

            case R.id.action_settings:
                showpopup();
                return true;

            case R.id.callmenu:
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel: " + callNum));
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showpopup() {
        PopupMenu popup;

        View menuItemView = findViewById(R.id.action_settings);
        popup = new PopupMenu(PaymentPage.this, menuItemView);
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent webintent = new Intent(PaymentPage.this, SupportActivity.class);
                webintent.putExtra("WebURL", Splash.setupdata.getLinks().get(item.getItemId()).getHref().trim());
                webintent.putExtra("Title", Splash.setupdata.getLinks().get(item.getItemId()).getLabel().trim());
                startActivity(webintent);
                return true;
            }
        });

        try {
            for (int m = 0; m < Splash.setupdata.getLinks().size(); m++) {
                popup.getMenu().add(Menu.NONE, m, Menu.NONE, Splash.setupdata.getLinks().get(m).getLabel());

            }
            popup.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.show();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
