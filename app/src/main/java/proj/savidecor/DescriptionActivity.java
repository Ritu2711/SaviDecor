package proj.savidecor;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import proj.savidecor.Utils.Apiclient;
import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.ItemAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar pbar;
    String URL,TITLE,NAME,UDF2,UDF3;
    private SharedPreferences sharedPreferences;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = 1.0f;
        params.dimAmount = 0.5f;
        this.getWindow().setAttributes(params);

        setContentView(R.layout.tryy);
        sharedPreferences = getSharedPreferences("VIEW", MODE_PRIVATE);
        URL = getIntent().getStringExtra("DescriptionUrl");
        TITLE=getIntent().getStringExtra("Title");
        NAME=getIntent().getStringExtra("Htname");


        Log.d("url name",""+sharedPreferences.getString(Constants.APIURL,""));
        Log.d("namee",""+NAME);
        UDF2=getIntent().getStringExtra("Htudf2");
        UDF3=getIntent().getStringExtra("htudf3");


        webView = (WebView)findViewById(R.id.descWebView);
        pbar = (ProgressBar)findViewById(R.id.webpbar);
        pbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#E64A19"), PorterDuff.Mode.SRC_ATOP);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        pbar.setVisibility(View.VISIBLE);
        try{
            //noinspection ConstantConditions
            getSupportActionBar().setTitle(TITLE);
            getSupportActionBar().setIcon(null);
        }catch (NullPointerException r){
            r.printStackTrace();
        }

        // overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);

        }
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        ItemAPI itemAPI = Apiclient.getHtmlUrl().create(ItemAPI.class);
        Call<ResponseBody> responseBodyCall=itemAPI.getHtml(URL,UDF2,UDF3,NAME);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    viewWEB(response.body().string());
                    Log.d("descripage",""+response.body().string());
                    Log.d("descripage",""+new GsonBuilder().setPrettyPrinting().create().toJson(response));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constants.showToast(getApplicationContext(),"Please retry");
                pbar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void viewWEB(String s) {
        webView.loadDataWithBaseURL(sharedPreferences.getString(Constants.APIURL,""),s,"text/html","UTF-8",null);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        });
        webView.setWebViewClient( new WebViewClient(){
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (pbar.getVisibility()== View.INVISIBLE)
                    pbar.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Constants.showToast(getApplicationContext(),"Please retry");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}