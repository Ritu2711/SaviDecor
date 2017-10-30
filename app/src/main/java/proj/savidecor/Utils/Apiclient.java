package proj.savidecor.Utils;

import android.content.Context;


import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import proj.savidecor.MainActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apiclient {

    private static final String BASE_URL = "http://www.savidecor.com/api/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;
    private static Retrofit retrofit3 = null;
    private static Retrofit retrofit4 = null;
    private static Retrofit retrofit5 = null;
    public static Context mContext;

    public static Retrofit getClient() {

        OkHttpClient.Builder httpClient2 = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES);
        httpClient2.addNetworkInterceptor(new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                /*builder.addHeader("site_id", "1");*/
                return chain.proceed(builder.build());
            }
        });
        OkHttpClient client = httpClient2.build();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static Retrofit postData() {
        if (retrofit4 == null) {
            retrofit4 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit4;
    }


   /* public static Retrofit getSetupup(final String sID) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cookieJar(new WebkitCookieManagerProxy());

        httpClient.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("site_id",sID);
                return chain.proceed(builder.build());
            }
        });
        OkHttpClient client = httpClient.build();

        if (retrofit3 == null) {
            retrofit3 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit3;
    }*/

    public static Retrofit getHtmlUrl() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cookieJar(new WebkitCookieManagerProxy());

        httpClient.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();

                builder.addHeader("MOBILE_UI", "true")
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent", "Android");
                return chain.proceed(builder.build());
            }
        });
        OkHttpClient client = httpClient.build();

        if (retrofit5 == null) {
            retrofit5 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit5;
    }


    public static Retrofit PaymentClient(final String bool, final String sID) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cookieJar(new WebkitCookieManagerProxy());

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Mobileui", bool)
                        .addHeader("sessionid", sID)
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent", "Android");
                return chain.proceed(builder.build());
            }
        });

        OkHttpClient client = httpClient.build();
        if (retrofit2 == null) {
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(MainActivity.webUrlApi)
                    .client(client)
                    .build();
        }

        return retrofit2;
    }

    private static class WebkitCookieManagerProxy extends CookieManager implements CookieJar {
        private android.webkit.CookieManager webkitCookieManager;

        WebkitCookieManagerProxy() {
            this(null);
        }

        WebkitCookieManagerProxy(CookiePolicy cookiePolicy) {
            super(null, cookiePolicy);
            this.webkitCookieManager = android.webkit.CookieManager.getInstance();
        }

        @Override
        public void put(URI uri, Map<String, List<String>> responseHeaders)
                throws IOException {
            if ((uri == null) || (responseHeaders == null))
                return;

            String url = uri.toString();

            for (String headerKey : responseHeaders.keySet()) {
                if ((headerKey == null)
                        || !(headerKey.equalsIgnoreCase("Set-Cookie2") || headerKey
                        .equalsIgnoreCase("Set-Cookie")))
                    continue;

                for (String headerValue : responseHeaders.get(headerKey)) {
                    webkitCookieManager.setCookie(url, headerValue);
                }
            }
        }

        @Override
        public Map<String, List<String>> get(URI uri,
                                             Map<String, List<String>> requestHeaders) throws IOException {
            if ((uri == null) || (requestHeaders == null))
                throw new IllegalArgumentException("Argument is null");

            String url = uri.toString();

            Map<String, List<String>> res = new HashMap<>();

            String cookie = webkitCookieManager.getCookie(url);

            if (cookie != null) {
                res.put("Cookie", Collections.singletonList(cookie));
            }
            return res;
        }

        @Override
        public CookieStore getCookieStore() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            HashMap<String, List<String>> generatedResponseHeaders = new HashMap<>();
            ArrayList<String> cookiesList = new ArrayList<>();
            for (Cookie c : cookies) {
                cookiesList.add(c.toString());
            }

            generatedResponseHeaders.put("Set-Cookie", cookiesList);
            try {
                put(url.uri(), generatedResponseHeaders);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            ArrayList<Cookie> cookieArrayList = new ArrayList<>();
            try {
                Map<String, List<String>> cookieList = get(url.uri(), new HashMap<String, List<String>>());
                for (List<String> ls : cookieList.values()) {
                    for (String s : ls) {
                        String[] cookies = s.split(";");
                        for (String cookie : cookies) {
                            Cookie c = Cookie.parse(url, cookie);
                            cookieArrayList.add(c);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cookieArrayList;
        }

    }
}
