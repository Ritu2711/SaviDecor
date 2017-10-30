package proj.savidecor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import proj.savidecor.Adapters.CartAdapter;
import proj.savidecor.Models.AllCARTProducts;
import proj.savidecor.Models.CartItem;
import proj.savidecor.Models.UpdateCART;
import proj.savidecor.Utils.Apiclient;
import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.ItemAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static proj.savidecor.Utils.Constants.retryNum;


public class CartFragment extends Fragment {

    View view;
    EventBus bus = EventBus.getDefault();
    private RecyclerView recyclerView;
    public static List<CartItem> cartPRODUCTS;
    CartAdapter adapter;
    int count;
    float sum = 0;
    Button cartBUTTON;
    TextView finalPRICE, nocart;
    LinearLayout totalfrag;
    private SharedPreferences sharedPreferences;
    private String imei;
    FrameLayout cartRel;

    /*CartItem c;
    ArrayList arrayList=new ArrayList();*/

    public CartFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initialize(view);
        recyclerView.setHasFixedSize(true);

        cartPRODUCTS = new ArrayList<>();
        adapter = new CartAdapter(cartPRODUCTS, getActivity());


        cartBUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent payIntent = new Intent(getActivity(), PaymentPage.class);



                startActivity(payIntent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void getall() {
        if (Constants.isNetworkAvailable(getActivity())) {
            //   cartPRODUCTS = new ArrayList<>();
            //  adapter = new CartAdapter(cartPRODUCTS, getActivity());
            recyclerView.setAdapter(adapter);
            fetchCARTITEMS();
        } else {
            //   adapter=new CartAdapter(cartPRODUCTS,getActivity());
            recyclerView.setAdapter(adapter);
            Constants.showToast(getActivity(), "No Internet!!");
        }
    }

    @SuppressLint("HardwareIds")
    private void initialize(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.cartRECYCLE);
        cartBUTTON = (Button) view.findViewById(R.id.proCARTbutton);
        finalPRICE = (TextView) view.findViewById(R.id.finalPrice);
        totalfrag = (LinearLayout) view.findViewById(R.id.linearcart);
        nocart = (TextView) view.findViewById(R.id.noprod);
        cartRel = (FrameLayout) view.findViewById(R.id.cartrel);
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
        nocart.setVisibility(View.GONE);
    }

    private void fetchCARTITEMS() {
        cartRel.setVisibility(View.VISIBLE);
        ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
        Call<AllCARTProducts> call = itemAPI.getALLPRO( imei);
        call.enqueue(new Constants.BackoffCallback<AllCARTProducts>(retryNum) {
            @Override
            public void onResponse(Call<AllCARTProducts> call, Response<AllCARTProducts> response) {
                try {
                    Gson gson = new Gson();
                    Log.e("cartFragment log", " " + gson.toJson(response.body()));
                    if (response.body().getItem().size() < 1) {
                        cartRel.setVisibility(View.INVISIBLE);
                        nocart.setVisibility(View.VISIBLE);

                    } else {

                        cartPRODUCTS.clear();

                        cartPRODUCTS.addAll(response.body().getItem());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        cartRel.setVisibility(View.INVISIBLE);
                        count = recyclerView.getChildCount();

                            for (int i = 0; i < adapter.getItemCount(); i++) {
                                sum = Float.parseFloat(cartPRODUCTS.get(i).getCartSubTotal()) + sum;
                            }

                        finalPRICE.setText(String.valueOf(sum));
                        totalfrag.setVisibility(View.VISIBLE);
                    }
                    if (getActivity() != null) {
                        sharedPreferences = getActivity().getSharedPreferences("VIEW", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(Constants.CartValue, cartPRODUCTS.size());
                        MainActivity.badgeValue = cartPRODUCTS.size();
                        editor.apply();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailedAfterRetry(Throwable t) {
                cartRel.setVisibility(View.INVISIBLE);
                nocart.setVisibility(View.VISIBLE);
            }
        });

    }

    @Subscribe
    public void onCHANGE(final UpdateCART model) {

        cartPRODUCTS.clear();
        cartRel.setVisibility(View.VISIBLE);
        sum = 0;
        totalfrag.setVisibility(View.GONE);


        ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
        Call<ResponseBody> cartcall = itemAPI.ChangeDB(imei, model.getpID(), model.getpNAME(), model.getPPRICE(), String.valueOf(model.getpQUANT()), "hii");

        cartcall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                getall();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                cartRel.setVisibility(View.INVISIBLE);
                Constants.showToast(getActivity(), "Can't change quantity");
            }
        });

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.cart);
        MenuItem item2 = menu.findItem(R.id.searchedt);
        item2.setVisible(false);
        item.setVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
        sum = 0;
        totalfrag.setVisibility(View.GONE);
        getall();
    }

    @Override
    public void onPause() {
        super.onPause();
        cartPRODUCTS.clear();
        bus.unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
