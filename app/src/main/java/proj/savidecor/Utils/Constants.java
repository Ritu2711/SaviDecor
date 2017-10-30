package proj.savidecor.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import proj.savidecor.R;
import retrofit2.Call;
import retrofit2.Callback;

public class Constants {

    public static int SITE_ID = 1;
    public static int listV = 0;
    public static int gridV = 1;
    public static String APIURL = "ApiUrl";
    public static String STICKYMSG = "StickyMsg";
    public static String RANDOMID = "RandomID";
    public static String SITEID = "SiteId";
    public static String ANAME = "Name";
    public static String DID = "DeviceID";
    public static String CallNumber = "CallNumber";
    public static String CartValue = "CartValue";
    public static String SplashBackground = "SplashBackground";
    public static String SplashTitle = "SplashTitle";
    public static String ToolbarBackground = "ToolbarBackground";
    public static String ToolbarIcon = "ToolbarIcon";
    public static int retryNum = 2;

    public static boolean isNetworkAvailable(Context activity) {
        ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isValidEmail(String strEmail) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    public static void showToast(Context ctx, CharSequence message) {

        if (ctx != null) {
            LayoutInflater mylf = LayoutInflater.from(ctx);
            View myview = mylf.inflate(R.layout.custom_toast, null);
            Toast mytoast = new Toast(ctx);
            TextView vbv = (TextView) myview.findViewById(R.id.toastmsg);
            vbv.setText(message);
            mytoast.setDuration(Toast.LENGTH_SHORT);
            mytoast.setView(myview);
            mytoast.show();
        }
    }

    /*public static void ShowSnakBar(String s, ScrollView scrollmain) {
        final Snackbar snackbar = Snackbar.make(scrollmain,s, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(Color.parseColor("#FEC309"));
        snackbar.show();
    }*/
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    public abstract static class BackoffCallback<T> implements Callback<T> {

        private static int RETRY_COUNT = 3;
        private static final double RETRY_DELAY = 6000;
        private int retryCount = 0;

        protected BackoffCallback(int retryNum) {
            RETRY_COUNT = retryNum;
        }

        @Override
        public void onFailure(final Call<T> call, Throwable t) {
            retryCount++;
            if (retryCount <= RETRY_COUNT) {
                int expDelay = (int) (RETRY_DELAY);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        retry(call);
                    }
                }, expDelay);
            } else {
                onFailedAfterRetry(t);
            }
        }

        private void retry(Call<T> call) {
            call.clone().enqueue(this);
        }

        public abstract void onFailedAfterRetry(Throwable t);

    }

    public static class MyAnim extends RecyclerView.ItemAnimator {
        @Override
        public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
            return false;
        }

        @Override
        public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
            return false;
        }

        @Override
        public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
            return false;
        }

        @Override
        public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
            final float prevAlpha = ViewCompat.getAlpha(oldHolder.itemView);

            ViewCompat.setAlpha(oldHolder.itemView, prevAlpha);

            ViewCompat.setAlpha(newHolder.itemView, 0);

            return true;
        }

        @Override
        public void runPendingAnimations() {

        }

        @Override
        public void endAnimation(RecyclerView.ViewHolder item) {

        }

        @Override
        public void endAnimations() {

        }

        @Override
        public boolean isRunning() {
            return false;
        }
    }
}
