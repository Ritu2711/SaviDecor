package proj.savidecor;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import proj.savidecor.Adapters.SubProAdapter;
import proj.savidecor.Adapters.SubSub;
import proj.savidecor.Models.AllItems;
import proj.savidecor.Models.AllProducts;
import proj.savidecor.Models.Item;
import proj.savidecor.Models.Products;
import proj.savidecor.Utils.Apiclient;
import proj.savidecor.Utils.ClickListener;
import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.GlobalListener;
import proj.savidecor.Utils.ItemAPI;
import proj.savidecor.Utils.ItemsSQLDB;
import retrofit2.Call;
import retrofit2.Response;

import static proj.savidecor.Utils.Constants.retryNum;


public class SubProductFragment extends Fragment implements View.OnClickListener {

    ProgressBar subCatPBD, subProPBD, SubMorePBD;
    RecyclerView subRecyclerView, proRecyclerView;
    GridLayoutManager SubGridLayout, ProGridLayout;
    private SubSub Subadapter;
    private SubProAdapter Proadapter;
    Bundle bundle;
    String title;
    int cid;
    ImageButton scrollup;
    static List<Item> items;
    GlobalListener mCallback;
    List<Products> prod;
    Boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int count = 1, lastposition;
    ItemsSQLDB mDBs;
    Cursor cs;
    LinearLayout pFSV2;
    LinearLayout slay2;
    CharSequence[] sortvalues;
    public int selected = 0;
    String STRINGSORT = "price_asc";
    ImageView changeLayout2;
    AlertDialog alertDialog;
    LinearLayoutManager LlayoutManager;
    Boolean isLayout = true;
    int i = 0;
    TextView sortValue2;
    private boolean isSHOW = true;
    private CharSequence bv;

    public SubProductFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        cid = bundle.getInt("cid");

        Log.d("exe",""+cid);
        title = bundle.getString("title");
        mDBs = ItemsSQLDB.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.newdata, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        sortvalues = getResources().getStringArray(R.array.SORTITEMS);
        pFSV2.setVisibility(View.GONE);

        subCatPBD.getIndeterminateDrawable().setColorFilter(Color.parseColor("#3D5AFE"), PorterDuff.Mode.SRC_ATOP);
        subProPBD.getIndeterminateDrawable().setColorFilter(Color.parseColor("#3D5AFE"), PorterDuff.Mode.SRC_ATOP);
        SubMorePBD.getIndeterminateDrawable().setColorFilter(Color.parseColor("#578BFE"), PorterDuff.Mode.SRC_ATOP);
        SubMorePBD.setVisibility(View.INVISIBLE);
        subCatPBD.setVisibility(View.GONE);
        subProPBD.setVisibility(View.INVISIBLE);

        SubGridLayout = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.HORIZONTAL, false);
        ProGridLayout = new GridLayoutManager(getActivity(), 2, 1, false);
        LlayoutManager = new LinearLayoutManager(getActivity());

        proRecyclerView.setHasFixedSize(true);
        subRecyclerView.setHasFixedSize(true);

        proRecyclerView.setItemAnimator(new Constants.MyAnim());
        subRecyclerView.setLayoutManager(SubGridLayout);

        subRecyclerView.addItemDecoration(new HomeFragment.SpacesItemDecoration(1));
        proRecyclerView.addItemDecoration(new HomeFragment.SpacesItemDecoration(1));
        scrollup.setVisibility(View.INVISIBLE);
        scrollup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proRecyclerView.smoothScrollToPosition(0);
            }
        });
        proRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0 ){
                    if(!scrollup.isShown()) scrollup.setVisibility(View.VISIBLE);
                } else {
                    if(scrollup.isShown()) scrollup.setVisibility(View.GONE);
                }
            }
        });
        if (Subadapter == null) {
            items = new ArrayList<>();
            Subadapter = new SubSub(getActivity(), items);
            if (Constants.isNetworkAvailable(getActivity())) {
                subRecyclerView.setAdapter(Subadapter);
                fetchSUB();
            } else {
                new SubProductFragment.showSUB().execute();
                //  Constants.showToast(getActivity(),"No Internet!!");
            }

        } else {
            if (items.size() < 3) {
                SubGridLayout = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);
            }
            subRecyclerView.setLayoutManager(SubGridLayout);
            subRecyclerView.setAdapter(Subadapter);
            subCatPBD.setVisibility(View.GONE);
        }

        if (Proadapter == null) {
            prod = new ArrayList<>();
            Proadapter = new SubProAdapter(prod, getActivity());
            if (Constants.isNetworkAvailable(getActivity())) {
                SubProAdapter.viewFormat = 1;
                proRecyclerView.setLayoutManager(ProGridLayout);
                proRecyclerView.setAdapter(Proadapter);
                fetch(1, cid, STRINGSORT, i);

            } else {
                proRecyclerView.setLayoutManager(ProGridLayout);
                Constants.showToast(getActivity(), "No internet");
                proRecyclerView.setAdapter(Proadapter);
                Proadapter.notifyDataSetChanged();
            }

        } else {

            if (!isSHOW) {
                pFSV2.setVisibility(View.VISIBLE);
                sortValue2.setText(sortvalues[i]);
            }

            if (!isLayout) {
                proRecyclerView.setLayoutManager(LlayoutManager);
            } else {
                proRecyclerView.setLayoutManager(ProGridLayout);
            }
            proRecyclerView.setAdapter(Proadapter);
            subProPBD.setVisibility(View.INVISIBLE);
        }

        subRecyclerView.addOnItemTouchListener(new Constants.RecyclerTouchListener(getActivity(), subRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    mCallback.onItemClick(position, Integer.parseInt(items.get(position).getSectionID()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        proRecyclerView.addOnItemTouchListener(new Constants.RecyclerTouchListener(getActivity(), proRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    mCallback.onExpandItemClick(prod.get(position).getPID(),prod.get(position).getUdf2());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        proRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (SubProAdapter.viewFormat == 0) {
                        visibleItemCount = LlayoutManager.getChildCount();
                        totalItemCount = LlayoutManager.getItemCount();
                        pastVisiblesItems = LlayoutManager.findFirstVisibleItemPosition();
                        lastposition = prod.size() - 1;
                    } else {
                        visibleItemCount = ProGridLayout.getChildCount();
                        totalItemCount = ProGridLayout.getItemCount();
                        pastVisiblesItems = ProGridLayout.findFirstVisibleItemPosition();
                        lastposition = prod.size() - 1;
                    }

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount &&
                                recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom() <= recyclerView.getHeight()) {
                            loading = false;
                            count++;
                            if (Constants.isNetworkAvailable(getActivity())) {
                                fetchmore(count, cid, STRINGSORT);
                            } else {
                                Constants.showToast(getActivity(), "Cant load more items,Internet is slow");
                            }
                            loading = true;
                        }
                    }
                }
            }
        });
        slay2.setOnClickListener(this);
        changeLayout2.setOnClickListener(this);
    }

    private void init(View view) {
        pFSV2 = (LinearLayout) view.findViewById(R.id.fsv2);
        scrollup= (ImageButton) view.findViewById(R.id.scrollup2);
        slay2 = (LinearLayout) view.findViewById(R.id.slay2);
        changeLayout2 = (ImageView) view.findViewById(R.id.changeLayout2);
        sortValue2 = (TextView) view.findViewById(R.id.sortValue2);
        subCatPBD = (ProgressBar) view.findViewById(R.id.subCatPBD);
        subProPBD = (ProgressBar) view.findViewById(R.id.subProPBD);
        SubMorePBD = (ProgressBar) view.findViewById(R.id.SubMorePBD);
        subRecyclerView = (RecyclerView) view.findViewById(R.id.newRecycle);
        proRecyclerView = (RecyclerView) view.findViewById(R.id.newRecycle2);
    }

    private void fetchmore(final int count, int cid, String STRINGSORT) {

        SubMorePBD.setVisibility(View.VISIBLE);
        final ItemAPI itemAPI2 = Apiclient.getClient().create(ItemAPI.class);

        Call<AllProducts> call2 = itemAPI2.getALL( count, cid, STRINGSORT);

        call2.enqueue(new Constants.BackoffCallback<AllProducts>(retryNum) {
            @Override
            public void onFailedAfterRetry(Throwable t) {
            }

            @Override
            public void onResponse(Call<AllProducts> call, Response<AllProducts> response) {
                try {
                    if (response.body().getItem().size() > 0) {
                        prod.addAll(response.body().getItem());
                        proRecyclerView.setAdapter(Proadapter);
                        proRecyclerView.scrollToPosition(lastposition);
                        Proadapter.notifyItemInserted(lastposition);
                    }
                    SubMorePBD.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchSUB() {
        subCatPBD.setVisibility(View.VISIBLE);
        final ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
        Call<AllItems> call = itemAPI.getSUBCAT( cid);
        call.enqueue(new Constants.BackoffCallback<AllItems>(retryNum) {
            @Override
            public void onFailedAfterRetry(Throwable t) {

            }

            @Override
            public void onResponse(Call<AllItems> call, Response<AllItems> response) {
                try {
                    if (response.body().getItem().size() > 0) {
                        items.clear();
                        items.addAll(response.body().getItem());
                        if (items.size() < 3) {
                            SubGridLayout = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);

                        }

                        subRecyclerView.setLayoutManager(SubGridLayout);
                        subRecyclerView.setAdapter(Subadapter);
                        Subadapter.notifyDataSetChanged();
                        new SubProductFragment.fetSUB().execute();
                        subCatPBD.setVisibility(View.GONE);
                    } else {
                        isSHOW = false;
                        //  pFSV2.setVisibility(View.VISIBLE);
                        subCatPBD.setVisibility(View.GONE);
                        Log.d("subresponse","no data 3");

                        //Toast.makeText(getActivity(), "No Products Found", Toast.LENGTH_SHORT).show();
                       // Constants.showToast(getActivity(),"No Products found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                Log.e("suProduct", " " + gson.toJson(response.body()));

            }
        });
    }

    private void fetch(final int count, final int cid, final String STRINGSORT, final int i) {

        subProPBD.setVisibility(View.VISIBLE);
        final ItemAPI itemAPI2 = Apiclient.getClient().create(ItemAPI.class);
        Call<AllProducts> call2 = itemAPI2.getALL( count, cid, STRINGSORT);


        call2.enqueue(new Constants.BackoffCallback<AllProducts>(retryNum) {
            @Override
            public void onFailedAfterRetry(Throwable t) {
                Log.d("subresponse",""+t.getMessage());
            }

            @Override
            public void onResponse(Call<AllProducts> call, Response<AllProducts> response) {
                try {
                    if (response.body().getItem().size() > 0) {
                        prod.clear();
                        prod.addAll(response.body().getItem());
                        proRecyclerView.setAdapter(Proadapter);
                        Proadapter.notifyDataSetChanged();
                        if (!isSHOW) {
                            pFSV2.setVisibility(View.VISIBLE);
                            sortValue2.setText(sortvalues[i]);
                            Log.d("subresponse","no data 1");
                        }  Log.d("subresponse","no data 3");
                        subProPBD.setVisibility(View.INVISIBLE);
                    } else {
                        Log.d("subresponse","no data 2");
                        pFSV2.setVisibility(View.GONE);
                       subProPBD.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                Log.e("subFetch", "" +Constants.SITE_ID+" "+count+" "+ cid+" "+ STRINGSORT+ gson.toJson(response.body()));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.slay2:
                CreateSortDialog();
                break;

            case R.id.changeLayout2:
                if (SubProAdapter.viewFormat == 1) {
                    SubProAdapter.viewFormat = 0;
                    changeLayout2.setImageResource(R.mipmap.ic_grid);
                    proRecyclerView.setLayoutManager(LlayoutManager);
                    isLayout = false;
                } else {
                    SubProAdapter.viewFormat = 1;
                    changeLayout2.setImageResource(R.mipmap.ic_list);
                    proRecyclerView.setLayoutManager(ProGridLayout);
                    isLayout = true;
                }
                break;
            default:
                break;
        }
    }

    private void CreateSortDialog() {
        if (Constants.isNetworkAvailable(getActivity())) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.RadioDialogTheme);
            builder.setTitle("SORT");
            builder.setSingleChoiceItems(sortvalues, selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0:
                            if (selected == i) {
                                Constants.showToast(getActivity(), "already sorted to: " + sortvalues[i]);
                            } else {
                                count = 1;
                                STRINGSORT = "price_asc";
                                alertDialog.dismiss();
                                proRecyclerView.setAdapter(null);
                                fetch(count, cid, STRINGSORT, i);
                                selected = i;
                            }
                            break;
                        case 1:
                            if (selected == i) {
                                Constants.showToast(getActivity(), "already sorted to: " + sortvalues[i]);
                            } else {
                                count = 1;
                                STRINGSORT = "price_des";
                                alertDialog.dismiss();
                                proRecyclerView.setAdapter(null);
                                fetch(count, cid, STRINGSORT, i);
                                selected = i;
                            }
                            break;

                        case 2:
                            if (selected == i) {
                                Constants.showToast(getActivity(), "already sorted to: " + sortvalues[i]);
                            } else {
                                count = 1;
                                STRINGSORT = "name_asc";
                                alertDialog.dismiss();
                                proRecyclerView.setAdapter(null);
                                fetch(count, cid, STRINGSORT, i);
                                selected = i;
                            }
                            break;
                        case 3:
                            if (selected == i) {
                                Constants.showToast(getActivity(), "already sorted to: " + sortvalues[i]);
                            } else {
                                count = 1;
                                STRINGSORT = "name_des";
                                alertDialog.dismiss();
                                proRecyclerView.setAdapter(null);
                                fetch(count, cid, STRINGSORT, i);
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
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            alertDialog.getWindow().setAttributes(lp);
        } else {
            Constants.showToast(getActivity(), "Please turn on Internet");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bv = sortValue2.getText();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bv != null) {
            sortValue2.setText(bv);
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setLogo(null);
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
        mCallback = null;
        super.onDetach();
    }

    private class fetSUB extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            cs = mDBs.fetchS(cid);
            if (items.size() != cs.getCount()) {
                try {
                    mDBs.deleteS(String.valueOf(cid));
                    for (int i = 0; i < items.size(); i++) {
                        mDBs.inse(String.valueOf(cid), items.get(i).getSectionID(), items.get(i).getSectionName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cs != null && !cs.isClosed()) {
                        cs.close();
                    }
                }
            }
            return null;
        }
    }

    private class showSUB extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            subRecyclerView.setAdapter(Subadapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                cs = mDBs.fetchS(cid);
                if (cs.moveToFirst()) {
                    do {
                        String cAT = cs.getString(cs.getColumnIndex(ItemsSQLDB.ParentID));
                        String cIDs = cs.getString(cs.getColumnIndex(ItemsSQLDB.MainIDS));
                        String cATs = cs.getString(cs.getColumnIndex(ItemsSQLDB.MainCategoryS));
                        Item item = new Item(cAT, cIDs, cATs);
                        items.add(item);
                    } while (cs.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cs != null && !cs.isClosed()) {
                    cs.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (items.size() < 3) {
                SubGridLayout = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);
            }
            subRecyclerView.setLayoutManager(SubGridLayout);
            subRecyclerView.setAdapter(Subadapter);
            Subadapter.notifyDataSetChanged();
        }
    }
}
