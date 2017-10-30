package proj.savidecor;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import proj.savidecor.Adapters.NavigationDrawerAdapter;
import proj.savidecor.Models.AllItems;
import proj.savidecor.Models.Item;
import proj.savidecor.Utils.Apiclient;
import proj.savidecor.Utils.ClickListener;
import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.ItemAPI;
import proj.savidecor.Utils.ItemsSQLDB;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static proj.savidecor.Utils.Constants.retryNum;

public class AppFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    public static NavigationDrawerAdapter adapter;
    private View containerView;
    private List<Item> items;
    private List<Item> sItems;
    private FragmentDrawerListener drawerListener;
    ItemsSQLDB mDB;
    Cursor catC;
    EditText searchCat;


    public AppFragment() {
        super();
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDB= ItemsSQLDB.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nav_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchCat=(EditText)view.findViewById(R.id.searchcat);
        recyclerView = (RecyclerView) view.findViewById(R.id.drawerList);

        VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller) view.findViewById(R.id.fast_scroller);

        // Connect the recycler to the scroller (to let the scroller scroll the list)
        fastScroller.setRecyclerView(recyclerView);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        recyclerView.setOnScrollListener(fastScroller.getOnScrollListener());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        items=new ArrayList<>();
        adapter=new NavigationDrawerAdapter(getActivity(),items);

            if (Constants.isNetworkAvailable(getActivity())) {
                recyclerView.setAdapter(adapter);

                final ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
                //Call<AllItems> call = itemAPI.getJSON(Constants.SITE_ID,0);
                Call<AllItems> call = itemAPI.getJSON();


                call.enqueue(new Callback<AllItems>() {
                    @Override
                    public void onResponse(Call<AllItems> call, Response<AllItems> response) {
                        Log.d("called",""+response.errorBody());
                        items.clear();
                        items.addAll(response.body().getItem());
                        for (int g=0;g<items.size();g++){
                            if (items.get(g).getSectionName().equals("Custom")){
                                Item df=new Item(items.get(g).getSectionName(),items.get(g).getSectionID());
                                items.remove(g);
                                items.add(0,df);
                            }
                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        searchCat.setVisibility(View.VISIBLE);
                        new fetchMAIN().execute();
                    }

                    @Override
                    public void onFailure(Call<AllItems> call, Throwable t) {

                    }
                });
            }else {

                recyclerView.setAdapter(adapter);
                new fetchLOCAL().execute();
            }

        recyclerView.addOnItemTouchListener(new Constants.RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (getActivity().getCurrentFocus()!=null){
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
                searchCat.setText("");
                searchCat.clearFocus();
                recyclerView.setSelected(true);
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        searchCat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String mys=charSequence.toString().toLowerCase().trim();

                    if (mys.length() >0){
                        sItems=new ArrayList<>();
                        for (int s=0;s<items.size();s++){
                            final String text=items.get(s).getSectionName().toLowerCase().trim();
                            if (text.contains(mys)){
                                sItems.add(items.get(s));
                            }
                        }
                        adapter = new NavigationDrawerAdapter(getActivity(),sItems);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        try {
                            adapter=new NavigationDrawerAdapter(getActivity(),items);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();

            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView,0);
                getActivity().invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }



    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }

    private class fetchLOCAL extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                catC = mDB.fetchall();
                items.clear();
                while (catC.moveToNext()) {
                    String cAT = catC.getString(catC.getColumnIndex(ItemsSQLDB.MainCategory));
                    String cID = catC.getString(catC.getColumnIndex(ItemsSQLDB.MainID));
                    Item item = new Item(cAT, cID);
                    items.add(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(catC !=null && !catC.isClosed()){
                    catC.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

          //  adapter = new NavigationDrawerAdapter(getActivity(),items);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            searchCat.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            drawerListener = (FragmentDrawerListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        drawerListener=null;
        super.onDetach();
    }

    private class fetchMAIN  extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            catC=mDB.fetchall();
            if (items.size()!=catC.getCount()){
                Log.d("okk","maincat");
                try {
                    mDB.delete();
                    for (int i=0;i<items.size();i++){
                        mDB.insertall(items.get(i).getSectionName(),items.get(i).getSectionID());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(catC !=null && !catC.isClosed()){
                        catC.close();
                    }
                }
            }
            return null;
        }
        }
}
