package proj.savidecor;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import proj.savidecor.Adapters.ProductsAdapter;
import proj.savidecor.Models.AllProducts;
import proj.savidecor.Models.Products;
import proj.savidecor.Utils.Apiclient;
import proj.savidecor.Utils.ClickListener;
import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.GlobalListener;
import proj.savidecor.Utils.ItemAPI;
import retrofit2.Call;
import retrofit2.Response;

import static proj.savidecor.Utils.Constants.retryNum;


public class ProductFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    List<Products> prod;
    LinearLayout pFSV;
    ProductsAdapter adapter;
    GlobalListener mCallback;
    String title;
    int h;
    String STRINGSORT="price_asc";
    public int selected=0;
    ProgressBar pbdload;
    TextView sorting,sortValue;
    GridLayoutManager GlayoutManager;
    LinearLayoutManager LlayoutManager;
    ImageView changeLayout;
    AlertDialog alertDialog;
    Boolean isLayout=true;
    CharSequence[] sval;
    LinearLayout slay;
    private CharSequence bv;
    Boolean loading=true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int count=1,lastp;
    int i=0;
    FrameLayout prorel;
    RelativeLayout productLayout;


    public ProductFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle pb=getArguments();
        h=pb.getInt("ID");
        title=pb.getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initialize(view);
        setHasOptionsMenu(true);
        pbdload.setVisibility(View.INVISIBLE);
        sval=getResources().getStringArray(R.array.SORTITEMS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new Constants.MyAnim());
        recyclerView.addItemDecoration(new HomeFragment.SpacesItemDecoration(1));

        if (adapter==null) {
            prod = new ArrayList<>();
            adapter = new ProductsAdapter(prod, getActivity());

            if (Constants.isNetworkAvailable(getActivity())){
                ProductsAdapter.viewFormat=1;

                recyclerView.setLayoutManager(GlayoutManager);

                recyclerView.setAdapter(adapter);
               // adapter.notifyDataSetChanged();
                fetchList(1,h,STRINGSORT, i);
            }else {
                pFSV.setVisibility(View.GONE);
                Constants.showToast(getActivity(),"No Internet!!");
                recyclerView.setLayoutManager(GlayoutManager);
              //  adapter = new ProductsAdapter(prod, getActivity());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }else{

            if (!isLayout){
                recyclerView.setLayoutManager(LlayoutManager);
            }else{
                recyclerView.setLayoutManager(GlayoutManager);
            }
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        recyclerView.addOnItemTouchListener(new Constants.RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                mCallback.onExpandItemClick(adapter.getItemAtPosition(position).getPID(),adapter.getItemAtPosition(position).getUdf2());
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0)
                {
                    if (ProductsAdapter.viewFormat==0) {
                        visibleItemCount = LlayoutManager.getChildCount();
                        totalItemCount = LlayoutManager.getItemCount();
                        pastVisiblesItems = LlayoutManager.findFirstVisibleItemPosition();
                        lastp=prod.size()-1;
                    }else {
                        visibleItemCount = GlayoutManager.getChildCount();
                        totalItemCount = GlayoutManager.getItemCount();
                        pastVisiblesItems = GlayoutManager.findFirstVisibleItemPosition();
                        lastp=prod.size()-1;
                    }

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount &&
                                recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom() <= recyclerView.getHeight()) {
                            loading = false;
                            count++;
                            fetchList2(count,h,STRINGSORT);
                            loading=true;
                        }
                    }
                }
            }
        });

        slay.setOnClickListener(this);
        changeLayout.setOnClickListener(this);
    }

    private void fetchList2(final int count, int h, String STRINGSORT) {
        pbdload.setVisibility(View.VISIBLE);

        ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
        Call<AllProducts> call=itemAPI.getALL(count, h,STRINGSORT);

        call.enqueue(new Constants.BackoffCallback<AllProducts>(retryNum) {
            @Override
            public void onResponse(Call<AllProducts> call, Response<AllProducts> response) {
                try {
                    if (response.body().getItem().size()>0){
                        prod.addAll(response.body().getItem());
                      //  recyclerView.setAdapter(adapter);

                        adapter.notifyItemInserted(prod.size()-1);
                        recyclerView.scrollToPosition(lastp);

                    }

                    pbdload.setVisibility(View.INVISIBLE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailedAfterRetry(Throwable t) {
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        MenuInflater minflater=getActivity().getMenuInflater();
        minflater.inflate(R.menu.menu_main,menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchedt));
        searchView.setQueryHint("Filter Products....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText=newText.toLowerCase();
                final List<Products> filter=new ArrayList<>();
                for (int i=0;i<prod.size();i++){
                    final String text=prod.get(i).getPName().toLowerCase().trim();
                    if (text.contains(newText)){
                        filter.add(prod.get(i));
                    }
                }
                adapter = new ProductsAdapter(filter,getActivity());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void Initialize(View view) {
        recyclerView=(RecyclerView)view.findViewById(R.id.pRecycle);
        pbdload=(ProgressBar)view.findViewById(R.id.pbdload);
        pbdload.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1565C0"), PorterDuff.Mode.SRC_ATOP);
        pFSV=(LinearLayout)view.findViewById(R.id.fsv);
        sorting=(TextView)view.findViewById(R.id.sorting);
        sortValue=(TextView)view.findViewById(R.id.sortValue);
        changeLayout=(ImageView) view.findViewById(R.id.changeLayout);
        prorel=(FrameLayout)view.findViewById(R.id.prorel);
        slay=(LinearLayout)view.findViewById(R.id.slay);
        GlayoutManager = new GridLayoutManager(getActivity(),2);
        LlayoutManager=new LinearLayoutManager(getActivity());
        productLayout=(RelativeLayout)view.findViewById(R.id.productLayout);
    }

    private void fetchList(int count, int h, String STRINGSORT, final int i) {
        pFSV.setVisibility(View.GONE);
        prorel.setVisibility(View.VISIBLE);
        productLayout.setEnabled(false);


        ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
        Call<AllProducts> call=itemAPI.getALL(
                count, h, STRINGSORT);

        call.enqueue(new Constants.BackoffCallback<AllProducts>(retryNum) {
            @Override
            public void onResponse(Call<AllProducts> call, Response<AllProducts> response) {
                try {
                    prod.clear();
                    prod=response.body().getItem();
                     adapter = new ProductsAdapter(prod,getActivity());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    sortValue.setText(sval[i]);
                    prorel.setVisibility(View.INVISIBLE);
                    productLayout.setEnabled(true);
                    pFSV.setVisibility(View.VISIBLE);
                    Constants.showToast(getActivity(),response.body().getCount().trim()+" products");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailedAfterRetry(Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.slay:
                CreateSortDialog();
                break;

            case R.id.changeLayout:

                if (ProductsAdapter.viewFormat==1) {
                    ProductsAdapter.viewFormat=0;
                    changeLayout.setImageResource(R.mipmap.ic_grid);
                    recyclerView.setLayoutManager(LlayoutManager);
                    isLayout=false;

                }else {
                    ProductsAdapter.viewFormat=1;
                    changeLayout.setImageResource(R.mipmap.ic_list);
                    recyclerView.setLayoutManager(GlayoutManager);
                    isLayout=true;
                }
                break;
            default:
                break;
        }
    }

    private void CreateSortDialog() {

        if (Constants.isNetworkAvailable(getActivity())){
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.RadioDialogTheme);
            builder.setTitle("SORT");
            builder.setSingleChoiceItems(sval, selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0:
                            if (selected==i){
                                Constants.showToast(getActivity(),"already sorted to: "+sval[i]);
                            }else {
                                count = 1;
                                STRINGSORT = "price_asc";
                                alertDialog.dismiss();
                                //  recyclerView.setAdapter(null);
                                prod.clear();
                                fetchList(count, h, STRINGSORT, i);
                                selected = i;

                            }
                            break;
                        case 1:
                            if (selected==i){
                                Constants.showToast(getActivity(),"already sorted to: "+sval[i]);
                            }else {
                                count=1;
                                STRINGSORT="price_des";
                                alertDialog.dismiss();
                                //  recyclerView.setAdapter(null);
                                prod.clear();
                                fetchList(count, h, STRINGSORT, i);
                                selected = i;
                            }
                            break;

                        case 2:
                            if (selected==i){
                                Constants.showToast(getActivity(),"already sorted to: "+sval[i]);
                            }else {
                                count = 1;
                                STRINGSORT = "name_asc";
                                alertDialog.dismiss();
                              //  recyclerView.setAdapter(null);
                                prod.clear();
                                fetchList(count, h, STRINGSORT, i);
                                selected = i;
                            }
                            break;
                        case 3:
                            if (selected==i){
                                Constants.showToast(getActivity(),"already sorted to: "+sval[i]);
                            }else {
                                count = 1;
                                STRINGSORT = "name_des";
                                alertDialog.dismiss();
                                //  recyclerView.setAdapter(null);
                                prod.clear();
                                fetchList(count, h, STRINGSORT, i);
                                selected = i;
                            }
                            break;
                    }
                    alertDialog.dismiss();
                }
            });

            alertDialog = builder.create();
            //noinspection ConstantConditions
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.SORTTHEME;

            alertDialog.show();
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.height= WindowManager.LayoutParams.WRAP_CONTENT;
            alertDialog.getWindow().setAttributes(lp);
        }else {
            Constants.showToast(getActivity(),"Please turn on Internet");
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        bv=sortValue.getText();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bv!=null){
            sortValue.setText(bv);
        }
        AppCompatActivity activity=(AppCompatActivity)getActivity();
        ActionBar actionBar=activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void onAttach(Context con) {
        super.onAttach(con);
        try {
            mCallback = (GlobalListener) con;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        mCallback=null;
        super.onDetach();
    }

}