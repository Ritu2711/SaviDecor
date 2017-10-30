package proj.savidecor;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.GlobalListener;
import proj.savidecor.Utils.ItemsSQLDB;


public class MainActivity extends AppCompatActivity implements AppFragment.FragmentDrawerListener, GlobalListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    AppFragment drawerFragment;
    private ActionBarDrawerToggle drawerToggle;
    Fragment fragment, fragment2, fragment3, f4;
    ItemsSQLDB mDBS;
    static Menu mToolbarMenu;
    public static int badgeValue = 0;
    SharedPreferences sharedPreferences;
    static Fragment HomeFragment;
    public static String webUrlApi;
    public static String callNum = "";
    ImageView ivLogo, ivToolbarBackground;
    String sToolbarBg = "", sToolbarIcon = "";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment = (AppFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        setSupportActionBar(toolbar);
        mDBS = ItemsSQLDB.getInstance(this);

        sharedPreferences = getSharedPreferences("VIEW", MODE_PRIVATE);
        webUrlApi = sharedPreferences.getString(Constants.APIURL, "");
        Log.e("webUrlApi", " " + webUrlApi);
        badgeValue = sharedPreferences.getInt(Constants.CartValue, 0);
        callNum = sharedPreferences.getString(Constants.CallNumber, "");
        sToolbarBg = sharedPreferences.getString(Constants.ToolbarBackground, "");
        sToolbarIcon = sharedPreferences.getString(Constants.ToolbarIcon, "");

        ivLogo = (ImageView) findViewById(R.id.media_image);
        ivLogo.setVisibility(View.VISIBLE);
        ivToolbarBackground = (ImageView) findViewById(R.id.activity_main_iv_toolbar_background);

        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
        // <------------------------------------------------------------------------------------------------->
                            //toolbar.setBackgroundColor(Color.CYAN);
        // <------------------------------------------------------------------------------------------------->

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
                } else {
                    assert getSupportActionBar() != null;
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setTitle("");
                    //  getSupportActionBar().setIcon(R.drawable.favicon);
//                    getSupportActionBar().setLogo(R.drawable.favicon);
                    ivLogo.setVisibility(View.VISIBLE);
                    Log.d("cdfjkklf", String.valueOf(getSupportActionBar().getElevation()));

                    // getSupportActionBar().setTitle("Call Us At:512-765-4470");

                    drawerToggle.syncState();
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                                mDrawer.closeDrawer(GravityCompat.START);
                            } else {
                                mDrawer.openDrawer(GravityCompat.START);
                            }
                        }
                    });
                }

            }
        });

       String sActionBarColor = "#59a4af";


        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setIcon(R.drawable.favicon);
//        getSupportActionBar().setLogo(R.drawable.favicon);

        Glide.with(this).load(sToolbarIcon).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivLogo);
        Glide.with(this).load(sToolbarBg).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivToolbarBackground);
      toolbar.setBackgroundColor(Color.parseColor(sActionBarColor));

        getSupportActionBar().setTitle(null);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        HomeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_left_enter, R.anim.slide_left_exit);
        fragmentTransaction.replace(R.id.container_body, HomeFragment, "TAG");
        fragmentTransaction.commit();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.callmenu);
        item.setVisible(false);
        if (callNum.length() > 0) {
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
                    Constants.showToast(MainActivity.this, "Please enter atleast 3 character");
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
                        ivLogo.setVisibility(View.GONE);
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
        BadgeDrawable badgeDrawable;
        if ((cartBadgeDrawable != null)
                && ((cartBadgeDrawable instanceof BadgeDrawable))
                && (i < 10)) {
            badgeDrawable = (BadgeDrawable) cartBadgeDrawable;
        } else {
            badgeDrawable = new BadgeDrawable(MainActivity.this);
        }
        badgeDrawable.setCount(i);
        localLayerDrawable.mutate();
        localLayerDrawable.setDrawableByLayerId(R.id.ic_badge, badgeDrawable);
        cartItem.setIcon(localLayerDrawable);
    }

    public static class BadgeDrawable extends Drawable {

        private Paint mBadgePaint;
        private String mCount = "";
        private Paint mTextPaint;
        private float mTextSize;
        private Rect mTxtRect = new Rect();
        private boolean mWillDraw = false;

        BadgeDrawable(Context paramContext) {
            this.mTextSize = paramContext.getResources().getDimension(
                    R.dimen.textsize_14);
            this.mBadgePaint = new Paint();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.mBadgePaint.setColor(paramContext.getColor(R.color.badgecolor));
            } else {
                //noinspection deprecation
                this.mBadgePaint.setColor(paramContext.getResources().getColor(R.color.badgecolor));
            }

            this.mBadgePaint.setAntiAlias(true);
            this.mBadgePaint.setStyle(Paint.Style.FILL);
            this.mTextPaint = new Paint();
            this.mTextPaint.setColor(-1);
            this.mTextPaint.setTypeface(Typeface.DEFAULT);
            this.mTextPaint.setTextSize(this.mTextSize);
            this.mTextPaint.setAntiAlias(true);
            this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        }

        public void draw(@NonNull Canvas paramCanvas) {
            if (!this.mWillDraw) {
                return;
            }
            Rect localRect = getBounds();
            float width = localRect.right - localRect.left;
            float height = localRect.bottom - localRect.top;
            float circleRadius;
            if (Integer.parseInt(this.mCount) < 10) {
                circleRadius = Math.min(width, height) / 4.0f + 2.5F;
            } else {
                circleRadius = Math.min(width, height) / 4.0f + 4.5F;
            }
            float circleX = width - circleRadius + 6.2F;
            float circleY = circleRadius - 9.5f;
            paramCanvas.drawCircle(circleX, circleY, circleRadius, this.mBadgePaint);
            this.mTextPaint.getTextBounds(this.mCount, 0, this.mCount.length(), this.mTxtRect);
            float textY = circleY + (this.mTxtRect.bottom - this.mTxtRect.top) / 2.0F;
            float textX = circleX;
            if (Integer.parseInt(this.mCount) >= 10) {
                textX = textX - 1.0F;
                textY = textY - 1.0F;
            }
            paramCanvas.drawText(this.mCount, textX, textY, this.mTextPaint);
        }

        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        public void setAlpha(int paramInt) {
        }

        public void setColorFilter(ColorFilter paramColorFilter) {
        }

        void setCount(int paramInt) {
            this.mCount = Integer.toString(paramInt);
            if (paramInt > 0) {
                this.mWillDraw = true;
                invalidateSelf();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        }
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
                Fragment cartFragment;
                cartFragment = new CartFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, cartFragment, "CARTF");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                assert getSupportActionBar() != null;
                // getSupportActionBar().setIcon(null);
                getSupportActionBar().setLogo(null);
                getSupportActionBar().setTitle("Cart");
                ivLogo.setVisibility(View.GONE);
                return true;

            case R.id.action_settings:
                showpopup();
                return true;

            case R.id.callmenu:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
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
        popup = new PopupMenu(MainActivity.this, menuItemView);
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent webintent = new Intent(MainActivity.this, SupportActivity.class);
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

       /* popup.getMenu().add(Menu.NONE,0,Menu.NONE,"ZERO");
        popup.getMenu().add(Menu.NONE,1,Menu.NONE,"ONE");
        popup.getMenu().add(Menu.NONE,2,Menu.NONE,"TWO");
        popup.getMenu().add(Menu.NONE,3,Menu.NONE,"THREE");
        popup.getMenu().add(Menu.NONE,4,Menu.NONE,"FOUR");*/
        popup.show();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {

        fragment = new SubProductFragment();
        Bundle b = new Bundle();

        //  Log.d("aicikck",AppFragment.adapter.getItemAtPosition(position).getSectionName());
        b.putInt("cid", Integer.parseInt(AppFragment.adapter.getItemAtPosition(position).getSectionID()));
        b.putString("title", AppFragment.adapter.getItemAtPosition(position).getSectionName());
        fragment.setArguments(b);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_left_enter, R.anim.slide_left_exit);
            fragmentTransaction.replace(R.id.container_body, fragment, "SUBvfF");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            assert getSupportActionBar() != null;
            //getSupportActionBar().setIcon(null);
            getSupportActionBar().setLogo(null);
            ivLogo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(int position, int ID) {
        fragment2 = new ProductFragment();
        Bundle arg = new Bundle();
        arg.putInt("ID", ID);
        arg.putString("title", SubProductFragment.items.get(position).getSectionName());
        fragment2.setArguments(arg);

        if (fragment2 != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_left_enter, R.anim.slide_left_exit);
            fragmentTransaction.replace(R.id.container_body, fragment2, "PROF");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            assert getSupportActionBar() != null;
            //    getSupportActionBar().setIcon(null);
            getSupportActionBar().setLogo(null);
            ivLogo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onExpandItemClick(String itemID, String prodSize) {
        fragment3 = new ExpandProductFragment();
        Bundle arg = new Bundle();
        arg.putString("itemID", itemID);
        arg.putString("prodSize", prodSize);
        fragment3.setArguments(arg);

        if (fragment3 != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_left_enter, R.anim.slide_left_exit);
            fragmentTransaction.replace(R.id.container_body, fragment3, "EXF");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            assert getSupportActionBar() != null;
            // getSupportActionBar().setIcon(null);
            getSupportActionBar().setLogo(null);
            ivLogo.setVisibility(View.GONE);
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}