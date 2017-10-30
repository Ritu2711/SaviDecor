package proj.savidecor;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import okhttp3.ResponseBody;
import proj.savidecor.Utils.Apiclient;
import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.ItemAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar pbar;
    String URL, TITLE;
    TextView tv;
    ImageView ivToolbarBackground;
    String sToolbarBg = "";
    SharedPreferences sharedPreferences;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desc_view);
        tv = (TextView) findViewById(R.id.toolbar_title);
        ivToolbarBackground = (ImageView) findViewById(R.id.desc_view_iv_toolbar_background);

        sharedPreferences = getSharedPreferences("VIEW", MODE_PRIVATE);
        sToolbarBg = sharedPreferences.getString(Constants.ToolbarBackground, "");

        Glide.with(this).load(sToolbarBg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivToolbarBackground);

        URL = getIntent().getStringExtra("WebURL");
        TITLE = getIntent().getStringExtra("Title");

        webView = (WebView) findViewById(R.id.descWebView);
        pbar = (ProgressBar) findViewById(R.id.webpbar);
        pbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#E64A19"), PorterDuff.Mode.SRC_ATOP);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        try {
            if (getSupportActionBar() != null) {
                tv.setText(TITLE);
                //getSupportActionBar().setTitle(TITLE);
                getSupportActionBar().setIcon(null);
            }
        } catch (NullPointerException r) {
            r.printStackTrace();
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
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
        Call<ResponseBody> responseBodyCall = itemAPI.getHtml(URL, "", "", "");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    viewWEB(response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constants.showToast(getApplicationContext(), "Please retry");
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void viewWEB(String s) {

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
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webView.setVisibility(View.INVISIBLE);
                if (pbar.getVisibility() == View.INVISIBLE)
                    pbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Constants.showToast(getApplicationContext(), "Please retry");
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
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
