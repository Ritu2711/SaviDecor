package proj.savidecor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.google.gson.Gson;

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


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    List<Products> homeProducts;
    ProductsAdapter adapter;
    GlobalListener mCallback;
    ProgressBar pbdload;
    GridLayoutManager GlayoutManager;

    Boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int count = 1, lastp;
    int rID;
    String stickyMSG;
    String STRINGSORT = "price_asc";
    SwipeLayout swipeLayout;
    TextView bannertv;
    FrameLayout hrel;
    RelativeLayout homeLayout;
ImageButton scrollup;
    public HomeFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("VIEW", Context.MODE_PRIVATE);
        rID = sharedPreferences.getInt(Constants.RANDOMID, 2);
        Log.e("rid", "" + rID);
        stickyMSG = sharedPreferences.getString(Constants.STICKYMSG, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homepage_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Initialize(view);

        if (stickyMSG.equalsIgnoreCase("")) {
            swipeLayout.setVisibility(View.GONE);
        } else {
            swipeLayout.setVisibility(View.VISIBLE);
            bannertv.setText(stickyMSG);
        }
        pbdload.setVisibility(View.INVISIBLE);
        //   pbd.setVisibility(View.INVISIBLE);
        recyclerView.setHasFixedSize(true);
        //  recyclerView.setItemAnimator(new LandingAnimator(new OvershootInterpolator()));
        recyclerView.setItemAnimator(new Constants.MyAnim());
        // recyclerView.setItemAnimator(new FlipInBottomXAnimator());
        recyclerView.addItemDecoration(new SpacesItemDecoration(1));
        recyclerView.setLayoutManager(GlayoutManager);

        if (adapter == null) {
            homeProducts = new ArrayList<>();
            adapter = new ProductsAdapter(homeProducts, getActivity());

            if (Constants.isNetworkAvailable(getActivity())) {
                ProductsAdapter.viewFormat = 1;
                recyclerView.setAdapter(adapter);
                fetchList(rID);
            } else {
                Constants.showToast(getActivity(), "No Internet!!");
                recyclerView.setLayoutManager(GlayoutManager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        } else {
            recyclerView.setAdapter(adapter);
            //   pbd.setVisibility(View.INVISIBLE);
        }
        scrollup.setVisibility(View.INVISIBLE);
        scrollup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        recyclerView.addOnItemTouchListener(new Constants.RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                mCallback.onExpandItemClick(homeProducts.get(position).getPID(),homeProducts.get(position).getpUdf2());
                if (homeProducts.get(position).getUdf2()!=null){

                }


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

                    visibleItemCount = GlayoutManager.getChildCount();
                totalItemCount = GlayoutManager.getItemCount();
                pastVisiblesItems = GlayoutManager.findFirstVisibleItemPosition();
                lastp = homeProducts.size() - 1;

                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount &&
                            recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom() <= recyclerView.getHeight()) {
                        loading = false;
                        count++;
                        fetchList2(count, rID);
                        loading = true;
                    }
                }
            }
        });
        bannertv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.showToast(getActivity(), stickyMSG);
            }
        });
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                swipeLayout.setVisibility(View.GONE);
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

    }

    private void fetchList2(int count, int rID) {
        pbdload.setVisibility(View.VISIBLE);
        ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
        Call<AllProducts> call = itemAPI.getALL(count, rID, STRINGSORT);

        call.enqueue(new Constants.BackoffCallback<AllProducts>(retryNum) {
            @Override
            public void onResponse(Call<AllProducts> call, Response<AllProducts> response) {
                try {
                    if (response.body().getItem().size() > 0) {
                        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                        homeProducts.addAll(response.body().getItem());

                        //  recyclerView.setAdapter(adapter);

                        adapter.notifyItemInserted(homeProducts.size() - 1);
                    }

                    pbdload.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailedAfterRetry(Throwable t) {

            }
        });
    }

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int mSpace;

        SpacesItemDecoration(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = mSpace;
        }
    }

    private void fetchList(int rID) {

        //  pbd.setVisibility(View.VISIBLE);
        hrel.setVisibility(View.VISIBLE);
        homeLayout.setEnabled(false);

        ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
        Call<AllProducts> call = itemAPI.getALL(count, rID, STRINGSORT);

        call.enqueue(new Constants.BackoffCallback<AllProducts>(retryNum) {
            @Override
            public void onResponse(Call<AllProducts> call, Response<AllProducts> response) {
                try {
                    homeProducts.clear();
                    homeProducts = response.body().getItem();
                    adapter = new ProductsAdapter(homeProducts, getActivity());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    //  pbd.setVisibility(View.INVISIBLE);
                    hrel.setVisibility(View.INVISIBLE);
                    hrel.setEnabled(true);

                    Gson gson = new Gson();
                    Log.e("response-home", "" + gson.toJson(response.body()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailedAfterRetry(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void Initialize(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.pRecycle);
        //   pbd=(ProgressBar)view.findViewById(R.id.pbd);
        //   pbd.getIndeterminateDrawable().setColorFilter(Color.parseColor("#3D5AFE"), PorterDuff.Mode.SRC_ATOP);
        pbdload = (ProgressBar) view.findViewById(R.id.pbdload);
        scrollup = (ImageButton) view.findViewById(R.id.scrollup);

        pbdload.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1565C0"), PorterDuff.Mode.SRC_ATOP);
        GlayoutManager = new GridLayoutManager(getActivity(), 2);
        GlayoutManager.setReverseLayout(false);
        homeLayout = (RelativeLayout) view.findViewById(R.id.homeLayout);
        swipeLayout = (SwipeLayout) view.findViewById(R.id.swypel);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, view.findViewById(R.id.bottom_wrapper));
        bannertv = (TextView) view.findViewById(R.id.bannertv);
        hrel = (FrameLayout) view.findViewById(R.id.hrel);
    }

    @Override
    public void onPause() {
        super.onPause();
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
}