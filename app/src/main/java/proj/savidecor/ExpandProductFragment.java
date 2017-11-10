package proj.savidecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.ResponseBody;
import proj.savidecor.Adapters.ImageAdapter;
import proj.savidecor.Models.Combination;
import proj.savidecor.Models.ItemDetails;
import proj.savidecor.Models.MaterialDetail;
import proj.savidecor.Models.Models;
import proj.savidecor.Models.Option;
import proj.savidecor.Utils.Apiclient;
import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.ItemAPI;
import retrofit2.Call;
import retrofit2.Response;

import static proj.savidecor.Utils.Constants.retryNum;


public class ExpandProductFragment extends Fragment implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {
Gallery gallery;
    private static final int REQUEST_CODE = 1;
    View view;
    SharedPreferences selection;
    RelativeLayout expandrel;
    ItemDetails itemDetailses;
    Button customize, cart;
    String cName, cEmail, cPhone, cText;
    String prodNAME, prodSize;
    FrameLayout exRel;
    String imei;
    ArrayList<String>imgarray=new ArrayList<>();
    TextView t1;
    List<MaterialDetail> materialList=new ArrayList<>();

    TextView yarn,yarncount,totalweight,totalheight,tuftspersqinch,primarybackingcontent,insectresistance,flammabilitytests,
            tufting,pilefinish,yarnply,pileweight,pileheight,tuftbindingstrength,secondarybackingcontent,finish,colour_fastedness_light,
            colour_fastedness_water,colour_fastedness_shampooing,dye_stuff,method_of_dyeing;





    List<MaterialDetail> materialDetails=new ArrayList<>();
    ArrayList<Combination> arrayListComb = new ArrayList<>();
    ImageView expandIMG, manufacture_img, freeshipimg, warrantyimg, lowpriceimg;
    TextView title, price, lprice, sku, smallsize, smallcolor, pInStock, pLongDescription,t2;
    AlertDialog alertDialog, choiceAlertDialog, cameraDialog;
    String materialselectcion, typeselection, sizeselection;
    ArrayList<Option> opArray = new ArrayList<>();
    List<Combination> combinationArrayList = new ArrayList<>();
    ArrayList alm = new ArrayList();
    ArrayList als = new ArrayList();
    ArrayList alt = new ArrayList();

    List<Option> arrayList;
    List<View> allVIEWS = new ArrayList<>();
    List<View> allVIEWS2 = new ArrayList<>();
    LinearLayout linearLayout;
    //GridLayout tabDynamic;
    Spinner sp1;
    JSONObject itemJObject;
    JSONArray itemJArray;
    JSONObject ks = new JSONObject();
    int mainPrice = 0;
    int spinnerValue = 0;
    int radioValue = 0;
    TouchImageView img;
    Animator mCurrentAnimator;
    int mShortAnimationDuration;
    private boolean isOptionAvailable = true;
    private SharedPreferences sharedPreferences;
    private String pid, pname, pprice, pquantity;
    private String sp1v = "0";
    private View optionLayout;
    private View DescButtonLayout;
    private boolean isAbleAddtocart = true;
    private boolean isIMG = false;
    private ImageView share;
    private TableLayout tabDyanmic;
    private Spinner material;
    private Spinner type;
    private Spinner size;
    private TableLayout main_table;
    private LinearLayout pCareInstruction,pShippingInfo;


    public ExpandProductFragment() {
        super();
    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, "View More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle pb = getArguments();
        prodNAME = pb.getString("itemID");
        prodSize = pb.getString("prodSize");
        alm.add("Select Material");
        alt.add("Select Type");
        als.add("Select Size");
        combinationArrayList.add(new Combination("","",""));
        setHasOptionsMenu(true);
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alldetail_view, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("HardwareIds")
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        listner();
        exRel.setVisibility(View.VISIBLE);
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();

        Log.e("productName", "" + prodNAME);

        if (Constants.isNetworkAvailable(getActivity())) {
            ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
            Call<Models> call = itemAPI.getAllItemDetails(prodNAME);
            call.enqueue(new Constants.BackoffCallback<Models>(retryNum) {
                @Override
                public void onResponse(final Call<Models> call, Response<Models> response) {
                    try {
                        itemDetailses = response.body().getItem();

                        Gson gson = new Gson();
                        Log.e("sub response", " " + gson.toJson(response.body()));
                        Log.e("sub response2", " " + itemDetailses.getOption());


                        //
                        setDATA();
                    } catch (Exception e) {
                        expandrel.setVisibility(View.INVISIBLE);

                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailedAfterRetry(Throwable t) {
                    exRel.setVisibility(View.INVISIBLE);
                    Log.e("failure message:", t.getMessage());
                }
            });

        } else {

            Constants.showToast(getActivity(), "No Internet!!");
        }

    }

    private void listner() {
        expandIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIMG)
                    zoomImageFromThumb(v);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HardwareIds")
            @Override
            public void onClick(View view) {
                cart.setClickable(false);
                if (isAbleAddtocart) {

                    if (Constants.isNetworkAvailable(getActivity())) {
                        exRel.setVisibility(View.VISIBLE);
                        show();
                        try {
                            if (isOptionAvailable) {
                                ks.put("options", itemJArray);
                            } else {
                                ks.put("options", "null");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pid = itemDetailses.getPID().trim();
                        pname = itemDetailses.getPName().trim();
                        pprice = itemDetailses.getPPrice().trim();
                        pquantity = "1";


                        ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
                        Call<ResponseBody> cartcall = itemAPI.insertINcart(imei, pid, pname, pprice, pquantity, String.valueOf(itemJArray));

                        Log.e("send add to cart", " " + imei + "*" + pid + "*" + pname + " " + pprice + " " + pquantity + " " + String.valueOf(itemJArray));
                        cartcall.enqueue(new Constants.BackoffCallback<ResponseBody>(retryNum) {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                /*BufferedReader reader;
                                String output = "";

                                try {
                                    reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                                    output = reader.readLine();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }*/
                                exRel.setVisibility(View.INVISIBLE);
                                Log.e("resp code add to cart", " " + response.code());
                                if (response.code() == 200) {
                                    Constants.showToast(getActivity(), "Successfully added in cart");
                                    int f = MainActivity.badgeValue += 1;


                                    sharedPreferences = getActivity().getSharedPreferences("VIEW", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("badge", f);
                                    editor.apply();
                                    ((MainActivity) getActivity()).createCartBadge(f);
                                }
                                cart.setClickable(true);

                                try {
                                    Fragment cartFragment;
                                    cartFragment = new CartFragment();

                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.container_body, cartFragment, "CARTF");
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                                    ActionBar actionBar = activity.getSupportActionBar();
                                    actionBar.setLogo(null);
                                    actionBar.setTitle("Cart");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailedAfterRetry(Throwable t) {
                                Constants.showToast(getActivity(), "Can't add to cart, please try again.");
                                cart.setClickable(true);
                            }
                        });

                    } else {
                        Constants.showToast(getActivity(), "Internet is slow, try again");
                        cart.setEnabled(true);
                    }

                } else {
                    Constants.showToast(getActivity(), "add to cart not available, please submit customization request");
                    cart.setEnabled(true);
                }
            }
        });

        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  creteDIL();
                createCustomizeDialog();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "I bought rugs from Savidecor and I loved it. I thought you would like to check it out. http://www.savidecor.com/ ";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

    }

    private void zoomImageFromThumb(final View v) {
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }


        img.setImageDrawable(expandIMG.getDrawable());


        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        v.getGlobalVisibleRect(startBounds);
        getActivity().findViewById(R.id.expandrelmain).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        expandrel.setAlpha(0.5f);
        img.setVisibility(View.VISIBLE);
        img.setPivotX(0f);
        img.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(img, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(img, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(img, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(img,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(img, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(img,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(img,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(img,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        expandrel.setAlpha(1f);
                        img.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        expandrel.setAlpha(1f);
                        img.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });


    }

    private void creteDIL() {
        final AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") final View alertLayout2 = inflater2.inflate(R.layout.askdialog, null);
        Button camera = (Button) alertLayout2.findViewById(R.id.btncam);
        Button form = (Button) alertLayout2.findViewById(R.id.btnform);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceAlertDialog.dismiss();
                openCamera();
            }
        });
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceAlertDialog.dismiss();
                createCustomizeDialog();
            }
        });
        builder2.setView(alertLayout2);
        choiceAlertDialog = builder2.create();
        //noinspection ConstantConditions
        choiceAlertDialog.getWindow().setBackgroundDrawableResource(R.color.cardview_light_background);
        choiceAlertDialog.show();
    }

    private void createCustomizeDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") final View alertLayout = inflater.inflate(R.layout.customize_dialog, null);
        Button submit = (Button) alertLayout.findViewById(R.id.sButton);
        final RelativeLayout rel = (RelativeLayout) alertLayout.findViewById(R.id.linDialog);
        final TextView pIDtext = (TextView) alertLayout.findViewById(R.id.productID);
        pIDtext.setText(itemDetailses.getPName());
        final EditText eName = (EditText) alertLayout.findViewById(R.id.nEdt);
        final EditText eEmail = (EditText) alertLayout.findViewById(R.id.eEdt);
        final EditText ePhone = (EditText) alertLayout.findViewById(R.id.pEdt);
        final EditText eCustom = (EditText) alertLayout.findViewById(R.id.cEdt);
        final ProgressBar pbd2 = (ProgressBar) alertLayout.findViewById(R.id.DialogPBD);
        pbd2.getIndeterminateDrawable().setColorFilter(Color.parseColor("#3D5AFE"), PorterDuff.Mode.SRC_ATOP);
        pbd2.setIndeterminate(false);
        pbd2.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Constants.isNetworkAvailable(getActivity())) {
                    cName = eName.getText().toString();
                    cEmail = eEmail.getText().toString();
                    cPhone = ePhone.getText().toString();
                    cText = eCustom.getText().toString();

                    if (TextUtils.isEmpty(cName)) {
                        Constants.showToast(getActivity(), "Name is empty");
                    } else if (TextUtils.isEmpty(cEmail)) {
                        Constants.showToast(getActivity(), "Email is empty");
                    } else if (!Constants.isValidEmail(cEmail)) {
                        Constants.showToast(getActivity(), "Email format error");
                    } else if (TextUtils.isEmpty(cPhone)) {
                        Constants.showToast(getActivity(), "Phone is empty");
                    } else if (TextUtils.isEmpty(cText)) {
                        Constants.showToast(getActivity(), "Request data is empty");

                    } else {
                        pbd2.setVisibility(View.VISIBLE);

                        rel.setVisibility(View.INVISIBLE);
                        ItemAPI itemAPI = Apiclient.postData().create(ItemAPI.class);
                        Call<ResponseBody> cal = itemAPI.insertData(imei, itemDetailses.getPID().trim(), itemDetailses.getPName().trim(), cName, cEmail, cPhone, cText);

                        cal.enqueue(new Constants.BackoffCallback<ResponseBody>(retryNum) {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                BufferedReader reader;
                                String output = "";
                                try {
                                    reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                                    output = reader.readLine();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                pbd2.setVisibility(View.GONE);
                                alertDialog.dismiss();
                                Constants.showToast(getActivity(), output);
                            }

                            @Override
                            public void onFailedAfterRetry(Throwable t) {
                                pbd2.setVisibility(View.GONE);
                                alertDialog.dismiss();
                                Constants.showToast(getActivity(), t.getMessage());
                            }
                        });
                    }

                } else {
                    Constants.showToast(getActivity(), "No internet Detected!");
                }
            }
        });
        builder.setView(alertLayout);
        alertDialog = builder.create();
        //noinspection ConstantConditions
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.FormTheme;
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.cardview_light_background);
        alertDialog.show();
    }

    private void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                createCAMdialog(bmp);
            }
        }
    }

    private void createCAMdialog(Bitmap bmp) {
        final AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") final View alertLayout3 = inflater.inflate(R.layout.cam_dialog, null);
        final ImageView iv = (ImageView) alertLayout3.findViewById(R.id.camimg);
        iv.setImageBitmap(bmp);
        final EditText camName = (EditText) alertLayout3.findViewById(R.id.camnameedt);
        final EditText camMail = (EditText) alertLayout3.findViewById(R.id.cammailedt);
        final Button sButton = (Button) alertLayout3.findViewById(R.id.Sbutton);
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.isNetworkAvailable(getActivity())) {
                    cameraDialog.dismiss();
                } else {
                    Constants.showToast(getActivity(), "No internet Detected!");
                }

            }
        });
        builder3.setView(alertLayout3);
        cameraDialog = builder3.create();
        cameraDialog.show();
    }

    private void init(View view) {
        gallery = (Gallery) view.findViewById(R.id.gallery1);
        expandIMG = (ImageView) view.findViewById(R.id.expandIMG);
        title = (TextView) view.findViewById(R.id.title);
        yarn = (TextView) view.findViewById(R.id.yarn);
        yarncount = (TextView) view.findViewById(R.id.yarncount);
        t1 = (TextView) view.findViewById(R.id.t1);
        t2 = (TextView) view.findViewById(R.id.t2);
        Drawable img2 = getContext().getResources().getDrawable( R.mipmap.downarrow );
        img2.setBounds( 0, 0, 60, 60 );
        t1.setCompoundDrawables( img2, null, null, null );
        t2.setCompoundDrawables( img2, null, null, null );

        totalweight = (TextView) view.findViewById(R.id.totalweight);
        totalheight = (TextView) view.findViewById(R.id.totalheight);
        tuftspersqinch = (TextView) view.findViewById(R.id.tuftspersqinch);
        primarybackingcontent = (TextView) view.findViewById(R.id.primarybackingcontent);
        insectresistance = (TextView) view.findViewById(R.id.insectresistance);
        flammabilitytests = (TextView) view.findViewById(R.id.flammabilitytests);
        price = (TextView) view.findViewById(R.id.price);
        material = (Spinner) view.findViewById(R.id.material);
        type = (Spinner) view.findViewById(R.id.type);
        size = (Spinner) view.findViewById(R.id.size);
        lprice = (TextView) view.findViewById(R.id.lprice);
        pLongDescription = (TextView) view.findViewById(R.id.pLongDescription);
        pCareInstruction = (LinearLayout) view.findViewById(R.id.pCareInstruction);
        pShippingInfo = (LinearLayout) view.findViewById(R.id.pShippingInfo);
        sku = (TextView) view.findViewById(R.id.sku);
        smallcolor = (TextView) view.findViewById(R.id.smallcolor);
        smallsize = (TextView) view.findViewById(R.id.smallsize);
        pInStock = (TextView) view.findViewById(R.id.pInStock);
        customize = (Button) view.findViewById(R.id.custombuttons);
        cart = (Button) view.findViewById(R.id.cartbuttons);
        exRel = (FrameLayout) view.findViewById(R.id.exRel);
        linearLayout = (LinearLayout) view.findViewById(R.id.dynamiclin);
        main_table = (TableLayout) view.findViewById(R.id.main_table);
        optionLayout = view.findViewById(R.id.l3);
        //
        // DescButtonLayout = view.findViewById(R.id.l5);
       // tabDyanmic = (TableLayout) view.findViewById(R.id.tab_host);
        expandrel = (RelativeLayout) view.findViewById(R.id.expandrel);
        share = (ImageView) view.findViewById(R.id.share);

        img = (TouchImageView) view.findViewById(R.id.expanded_image);

        tufting=(TextView) view.findViewById(R.id.tufting);
        pilefinish=(TextView) view.findViewById(R.id.pilefinish);
        yarnply=(TextView) view.findViewById(R.id.yarnply);
        pileweight=(TextView) view.findViewById(R.id.pileweight);
        pileheight=(TextView) view.findViewById(R.id.pileheight);
        tuftbindingstrength=(TextView) view.findViewById(R.id.tuftbindingstrength);
        secondarybackingcontent=(TextView) view.findViewById(R.id.secondarybackingcontent);
        finish=(TextView) view.findViewById(R.id.finish);
        colour_fastedness_light=(TextView) view.findViewById(R.id.colour_fastedness_light);
        colour_fastedness_water=(TextView) view.findViewById(R.id.colour_fastedness_water);
        colour_fastedness_shampooing=(TextView) view.findViewById(R.id.colour_fastedness_shampooing);
        dye_stuff=(TextView) view.findViewById(R.id.dye_stuff);
        method_of_dyeing=(TextView) view.findViewById(R.id.method_of_dyeing);



        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position,long id)
            {

                // display the images selected

                Glide.with(getActivity()).load(imgarray.get(position)).into(expandIMG);
            }
        });

        //manufacture_img=(ImageView)view.findViewById(R.id.manufacture_img);
        //<------------------------------------------------------------------------------------------------->
        //This is to be set programmatically
        //cart.setBackgroundColor(Color.CYAN);
        // <------------------------------------------------------------------------------------------------->
t1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int height = 0;
        if(pCareInstruction.getVisibility() == View.VISIBLE){
            Drawable img = getContext().getResources().getDrawable( R.mipmap.uparrow );
            img.setBounds( 0, 0, 60, 60 );
            t1.setCompoundDrawables( img, null, null, null );
            MyCustomAnimation a = new MyCustomAnimation(pCareInstruction, 1000, MyCustomAnimation.COLLAPSE);
            height = a.getHeight();
            pCareInstruction.startAnimation(a);
        }else{
            MyCustomAnimation a = new MyCustomAnimation(pCareInstruction, 1000, MyCustomAnimation.EXPAND);
            a.setHeight(height);
            Drawable img = getContext().getResources().getDrawable( R.mipmap.downarrow );
            img.setBounds( 0, 0, 60, 60 );
            t1.setCompoundDrawables( img, null, null, null );
            pCareInstruction.startAnimation(a);
        }
    }
});



        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int height = 0;
                if(pShippingInfo.getVisibility() == View.VISIBLE){
                    Drawable img = getContext().getResources().getDrawable( R.mipmap.uparrow );
                    img.setBounds( 0, 0, 60, 60 );
                    t2.setCompoundDrawables( img, null, null, null );
                    MyCustomAnimation a = new MyCustomAnimation(pShippingInfo, 1000, MyCustomAnimation.COLLAPSE);
                    height = a.getHeight();
                    pShippingInfo.startAnimation(a);
                }else{
                    MyCustomAnimation a = new MyCustomAnimation(pShippingInfo, 1000, MyCustomAnimation.EXPAND);
                    a.setHeight(height);
                    Drawable img = getContext().getResources().getDrawable( R.mipmap.downarrow );
                    img.setBounds( 0, 0, 60, 60 );
                    t2.setCompoundDrawables( img, null, null, null );
                    pShippingInfo.startAnimation(a);
                }
            }
        });

        material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position > 0) {
                    materialselectcion = "" + material.getItemAtPosition(position);

                    if ((materialselectcion != null && !materialselectcion.isEmpty()) && ((typeselection != null && !typeselection.isEmpty()) && ((sizeselection != null && !sizeselection.isEmpty())))) {
                        Log.d("this is error", "material");
                        getMoreData();
                        //
                        Toast.makeText(getActivity(), "called", Toast.LENGTH_SHORT).show();

                    }
                    if ((materialselectcion != null && !materialselectcion.isEmpty()) && ((typeselection != null && !typeselection.isEmpty()))) {
                        Log.d("this is error", "material");
                        getmaterial_details(materialselectcion, typeselection);


                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position>0) {
                    typeselection = "" + type.getItemAtPosition(position);


                    if ((materialselectcion != null && !materialselectcion.isEmpty()) && ((typeselection != null && !typeselection.isEmpty()) && ((sizeselection != null && !sizeselection.isEmpty())))) {
                        Log.d("this is error","type");
                        getMoreData();
                        getmaterial_details(materialselectcion,typeselection);

                        Toast.makeText(getActivity(), "called", Toast.LENGTH_SHORT).show();

                    }
                    if ((materialselectcion != null && !materialselectcion.isEmpty()) && ((typeselection != null && !typeselection.isEmpty()))) {

                        getmaterial_details(materialselectcion,typeselection);


                    }
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0) {

                    sizeselection = "" + size.getItemAtPosition(position);

                    if ((materialselectcion != null && !materialselectcion.isEmpty()) && ((typeselection != null && !typeselection.isEmpty()) && ((sizeselection != null && !sizeselection.isEmpty())))) {
                        Log.d("this is error","size");
                        getMoreData();
                        Toast.makeText(getActivity(), "called", Toast.LENGTH_SHORT).show();

                    }
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        /*type.setOnItemSelectedListener(this);
        size.setOnItemSelectedListener(this);*/
    }

    private void getmaterial_details(final String materialselectcion, final String typeselection) {

        optionLayout.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), ""+prodNAME, Toast.LENGTH_SHORT).show();
        ItemAPI itemAPI = Apiclient.getClient().create(ItemAPI.class);
        Call<Models> call = itemAPI.getAllItemDetails(prodNAME);
        call.enqueue(new Constants.BackoffCallback<Models>(retryNum) {
            @Override
            public void onResponse(final Call<Models> call, Response<Models> response) {
                try {
                    itemDetailses = response.body().getItem();
                    Gson gson = new Gson();
                    Log.e("sub response", " " + gson.toJson(response.body()));
                    Log.e("subdetailss", " " + itemDetailses.getMaterialDetails());

int count=1;
                    materialDetails=itemDetailses.getMaterialDetails();
                    for (int i=0;i<materialDetails.size();i++){
                        count=i;
                        MaterialDetail materialDetail=materialDetails.get(i);
                        if ((materialDetail.getYarn().equals(materialselectcion)) && (materialDetail.getTufting().equals(typeselection))){

                            tufting.setText(materialDetail.getTufting());
                            yarn.setText(materialDetail.getYarn());
Log.d("afsfa",""+materialDetail.getYarn());
                            yarncount.setText(materialDetail.getYarnCount());
                            totalweight.setText(materialDetail.getTotalWeight());
                            totalheight.setText(materialDetail.getTotalHeight());
                            tuftspersqinch.setText(materialDetail.getTuftsSqInch());
                            primarybackingcontent.setText(materialDetail.getPrimaryBackingContent());
                            insectresistance.setText(materialDetail.getInsectResistance());
                            flammabilitytests.setText(materialDetail.getFlammabilityTests());

                            pilefinish.setText(materialDetail.getFinish());
                            yarnply.setText(materialDetail.getYarnPly());
                            pileheight.setText(materialDetail.getPileHeight());
                            pileweight.setText(materialDetail.getPileWeight());
                            tuftbindingstrength.setText(materialDetail.getTuftBindingStrength());
                            finish.setText(materialDetail.getFinish2());
                            colour_fastedness_light.setText(materialDetail.getColourFastednessLight());
                            colour_fastedness_water.setText(materialDetail.getColourFastednessWater());
                            colour_fastedness_shampooing.setText(materialDetail.getColourFastednessShampooing());
                            dye_stuff.setText(materialDetail.getDyeStuff());
                            method_of_dyeing.setText(materialDetail.getMethodOfDyeing());

                        }


                    }




                    Toast.makeText(getActivity(), materialselectcion+" "+materialDetails.get(0)+" "+typeselection+" "+sizeselection, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    expandrel.setVisibility(View.INVISIBLE);

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailedAfterRetry(Throwable t) {
                exRel.setVisibility(View.INVISIBLE);
                Log.e("failure message:", t.getMessage());
            }
        });





    }

    private void getMoreData() {
        if ((materialselectcion != null && !materialselectcion.isEmpty()) && ((typeselection != null && !typeselection.isEmpty()) && ((sizeselection != null && !sizeselection.isEmpty())))) {
            Combination searchKey = new Combination(materialselectcion, typeselection, sizeselection);
            //Combination searchKey = new Combination("Art Silk","Hand Tufted","1x1");

            int index = Collections.binarySearch(combinationArrayList, searchKey, new EmpComp());
            Combination combination = combinationArrayList.get(index);
            System.out.println("Index of the searched key: " + combination.getPrice());
            System.out.println("Index of the searched key: " + index);
            price.setText(combination.getPrice());

        }


    }


    private void setDATA() {

        try {
            mainPrice = Integer.parseInt(itemDetailses.getPPrice());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        optionLayout.setVisibility(View.GONE);

        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "Rupee_Foradian.ttf");
        price.setTypeface(face);
        arrayList = itemDetailses.getOption();

        Log.d("myarrayy",""+itemDetailses.getImageSrc2().size());

for (int i=0;i<itemDetailses.getImageSrc2().size();i++){

    imgarray.add(itemDetailses.getImageSrc2().get(i));

}

        gallery.setAdapter(new ImageAdapter(getActivity(),imgarray));

        Log.d("Hellllo",itemDetailses.getCareInstruction());
        String data=itemDetailses.getCareInstruction();
        String[] parts = data.split("\\\n");
        for (int i=0;i<parts.length;i++) {
            TextView text = new TextView(getActivity());
            text.setText(parts[i].replaceAll("^\\s+", "").replaceAll("\\s+$", "")+"\n"); // <-- does it really compile without the + sign?
            text.setTextSize(12);
            text.setGravity(Gravity.LEFT);
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            pCareInstruction.addView(text);


        }



        Log.d("Hellllo",itemDetailses.getShippingInfo());
        String shipdata=itemDetailses.getShippingInfo();
        String[] shipparts = shipdata.split("\\\n");
        for (int i=0;i<shipparts.length;i++) {
            TextView text = new TextView(getActivity());
            text.setText(shipparts[i].replaceAll("^\\s+", "").replaceAll("\\s+$", "")+"\n"); // <-- does it really compile without the + sign?
            text.setTextSize(12);
            text.setGravity(Gravity.LEFT);
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            pShippingInfo.addView(text);


        }
     //   System.getProperty("line.separator");
        //makeTextViewResizable(pCareInstruction, 2, "View More", true);


        if (arrayList.size() == 0) {
            material.setVisibility(View.GONE);
            type.setVisibility(View.GONE);
            size.setVisibility(View.GONE);
        }

        Log.d("mmmmm", "" + arrayList.size());

        for (int i = 0; i < arrayList.size(); i++) {


            Option op = arrayList.get(i);
            opArray.add(op);


            combinationArrayList.add(new Combination(op.getMaterial(), op.getType(), op.getSize(), op.getPrice(), "" + i));


            if (!als.contains(op.getSize())) {
                als.add(op.getSize());
            }

            if (!alt.contains(op.getType())) {
                alt.add(op.getType());
            }

            if (!alm.contains(op.getMaterial())) {
                alm.add(op.getMaterial());

            }


        }
       /* material.setPrompt("Select Material");
        type.setPrompt("Select Type");
        size.setPrompt("Select Size");*/

        material.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, alm));

        type.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, alt));
        size.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, als));


        title.setText(itemDetailses.getPName());
        if (itemDetailses.getPPrice().equals("0")) {
            isAbleAddtocart = false;
            price.setText(R.string.nullPrice);
            lprice.setText("");
        } else {
            if (itemDetailses.getPListPrice().contains("0")) {
                price.setText(TextUtils.concat("" + getActivity().getResources().getString(R.string.rs) + " ", itemDetailses.getPrice_range()));
                lprice.setText("");
            } else {
                SpannableString spannableString = new SpannableString(itemDetailses.getPListPrice());
                spannableString.setSpan(new StrikethroughSpan(), 0, itemDetailses.getPListPrice().length(), 0);
                price.setText(TextUtils.concat("" + getActivity().getResources().getString(R.string.rs) + " ", itemDetailses.getPrice_range()));
                lprice.setText(TextUtils.concat("" + getActivity().getResources().getString(R.string.rs) + " ", spannableString));
            }
        }

        if (itemDetailses.getUdf3() != null && !itemDetailses.getUdf3().isEmpty()) {
            smallcolor.setText(TextUtils.concat("Color: ", itemDetailses.getUdf3()));

        } else {
            smallcolor.setVisibility(View.GONE);
        }


        if (itemDetailses.getpInStock() != null && !itemDetailses.getpInStock().isEmpty()) {
            if (itemDetailses.getpInStock().equals("0")) {
                pLongDescription.setText(itemDetailses.getpLongdescription());
                makeTextViewResizable(pLongDescription, 2, "View More", true);

                cart.setEnabled(false);
                cart.setBackgroundResource((R.drawable.buttonbgdisable));


            } else {
                pInStock.setText(TextUtils.concat("Available: ", itemDetailses.getpInStock()));

            }
        } else {
            pInStock.setVisibility(View.GONE);
        }


        if (itemDetailses.getUdf2() != null && !itemDetailses.getUdf2().isEmpty()) {

            smallsize.setText(TextUtils.concat("Size: ", itemDetailses.getUdf2()));
        } else {
            if (prodSize != null && !prodSize.isEmpty()) {
                smallsize.setText(TextUtils.concat("Size: ", prodSize));

            } else {
                smallsize.setVisibility(View.GONE);

            }

            ;

        }

        if (itemDetailses.getPSKU() != null && !itemDetailses.getPSKU().isEmpty()) {
            sku.setText(TextUtils.concat("SKU: ", itemDetailses.getPSKU()));
        } else {
            sku.setVisibility(View.GONE);
        }


        fetchMORE();
        try {
            if (itemDetailses.getImageSrc().trim().startsWith("http")) {
                Glide.with(getContext())
                        .load(itemDetailses.getImageSrc().trim())
                        .placeholder(R.mipmap.placeholder)
                        .error(R.drawable.search)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                isIMG = true;
                                return false;
                            }
                        })
                        .into(expandIMG);
            } else {
                Glide.with(getContext())
                        .load("http:" + itemDetailses.getImageSrc().trim())
                        .placeholder(R.mipmap.placeholder)
                        .error(R.drawable.search)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                isIMG = true;
                                return false;
                            }
                        })
                        .into(expandIMG);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fetchSomeMore() {
        try {
            if (itemDetailses.getLinks().size() == 0) {
                DescButtonLayout.setVisibility(View.GONE);
            } else {
                // tabDynamic.setColumnCount(3);
                for (int m = 0; m < itemDetailses.getLinks().size(); m++) {
                    Button b = new Button(getActivity());
                    b.setText(itemDetailses.getLinks().get(m).getLabel());
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();

                    params.setMargins(7, 7, 7, 7);
                    b.setLayoutParams(params);
                    b.setLines(2);
                    b.setPadding(10, 10, 10, 10);
                    b.setBackgroundResource(R.drawable.buttonborder);
                    b.setId(m);
                    final int finalM = m;
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), DescriptionActivity.class);
                            intent.putExtra("DescriptionUrl", itemDetailses.getLinks().get(finalM).getHref().trim() + "/prodtab-led.php?id=1");
                            Log.d("expproduct", itemDetailses.getLinks().get(finalM).getHref().trim() + "/prodtab-led.php?id=1");
                            intent.putExtra("Title", itemDetailses.getLinks().get(finalM).getLabel().trim());
                            intent.putExtra("Htname", itemDetailses.getPName().trim());

                            if (itemDetailses.getUdf2() != null) {
                                intent.putExtra("Htudf2", itemDetailses.getUdf2().trim());
                            } else if (itemDetailses.getpUDF2() != null) {
                                intent.putExtra("Htudf2", itemDetailses.getpUDF2().trim());
                            }
                            if (itemDetailses.getUdf3() != null) {
                                intent.putExtra("htudf3", itemDetailses.getUdf3().trim());
                            } else if (itemDetailses.getpUDF3() != null) {
                                intent.putExtra("htudf3", itemDetailses.getpUDF3().trim());
                            }

                            startActivity(intent);
                        }
                    });
                    allVIEWS2.add(b);
                    // tabDynamic.addView(b);
                }
            }
            exRel.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        exRel.setVisibility(View.INVISIBLE);
        expandrel.setVisibility(View.VISIBLE);

    }

    private void fetchMORE() {
        try {
            if (itemDetailses.getOption().size() == 0) {
                //  optionLayout.setVisibility(View.GONE);
                isOptionAvailable = false;
            } else {
                for (int i = 0; i < itemDetailses.getOption().size(); i++) {
                    TextView tv1 = new TextView(getActivity());
                    tv1.setTextSize(17);
                    tv1.setPadding(0, 10, 0, 10);
                    tv1.setGravity(Gravity.START);
                    tv1.setTextColor(Color.BLACK);
                    tv1.setText(itemDetailses.getOption().get(i).getOptGrpName());
                    linearLayout.addView(tv1);

                   /* if (itemDetailses.getOption().get(i).getOptType().equals("-2")) {
                        ArrayList<String> Spin1val = new ArrayList<>();
                        for (int j = 0; j < itemDetailses.getOption().get(i).getValues().size(); j++) {
                            String val = itemDetailses.getOption().get(i).getValues().get(j).getOptName();
                            String val2 = itemDetailses.getOption().get(i).getValues().get(j).getOptPriceDiff();
                            if (val2.equals("0")) {
                                Spin1val.add(val);
                            } else {
                                Spin1val.add(val + " (+$" + val2 + ")");
                            }
                        }

                        ArrayAdapter<String> spinnerArrayAdapter;
                        spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Spin1val);
                        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_itemcustom);
                        sp1 = new Spinner(getActivity(), Spinner.MODE_DROPDOWN);

                        sp1.setPadding(0, 20, 0, 20);
                        allVIEWS.add(sp1);
                        sp1.setAdapter(spinnerArrayAdapter);
                        sp1.setSelection(0, false);
                        sp1.setId(i);
                        sp1.setOnItemSelectedListener(this);
                        *//*View view2=new View(getActivity());
                        view2.setBackgroundColor(Color.BLUE);
                        view2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,4));*//*
                        linearLayout.addView(sp1);
                        //  linearLayout.addView(view2);
                    }
                    if (itemDetailses.getOption().get(i).getOptType().equals("-3")) {
                        EditText ed = new EditText(getActivity());
                        ed.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                        ed.setSingleLine(false);
                        ed.setMaxLines(5);
                        ed.setMinLines(1);
                        ed.setLines(3);
                        ed.setHint("Enter your notes");
                        ed.setVerticalScrollBarEnabled(true);
                        allVIEWS.add(ed);
                        linearLayout.addView(ed);

                    }
                    if (itemDetailses.getOption().get(i).getOptType().equals("3")) {
                        EditText ed2 = new EditText(getActivity());
                        ed2.setInputType(InputType.TYPE_CLASS_TEXT);
                        ed2.setSingleLine(true);
                        ed2.setMaxLines(1);
                        allVIEWS.add(ed2);
                        linearLayout.addView(ed2);
                    }

                    if (itemDetailses.getOption().get(i).getOptType().equals("-1")) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.topMargin = 5;
                        params.bottomMargin = 5;

                        RadioGroup rg = new RadioGroup(getActivity());
                        allVIEWS.add(rg);
                        for (int k = 0; k < itemDetailses.getOption().get(i).getValues().size(); k++) {
                            RadioButton rb = new RadioButton(getActivity());
                            rg.addView(rb, params);
                            if (k == 0)
                                rb.setChecked(true);
                            rb.setLayoutParams(params);
                            rb.setTag(itemDetailses.getOption().get(i).getValues().get(k).getOptPriceDiff() + "," +
                                    itemDetailses.getOption().get(i).getValues().get(k).getOptName()
                            );

                            String radioval1 = itemDetailses.getOption().get(i).getValues().get(k).getOptName();
                            String radioval2 = itemDetailses.getOption().get(i).getValues().get(k).getOptPriceDiff();

                            rb.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            if (itemDetailses.getOption().get(i).getValues().get(k).getOptPriceDiff().equals("0")) {
                                rb.setText(itemDetailses.getOption().get(i).getValues().get(k).getOptName());
                            } else {
                                rb.setText(TextUtils.concat(radioval1, " ($+" + radioval2 + ")"));
                            }
                            rg.setOnCheckedChangeListener(this);
                        }
                        linearLayout.addView(rg, params);
                    }*/
                }
            }
            fetchSomeMore();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void show() {
        itemJArray = new JSONArray();

        try {
            for (int noOfViews = 0; noOfViews < itemDetailses.getOption().size(); noOfViews++) {

                if (itemDetailses.getOption().get(noOfViews).getOptType().equals("-2")) {
                    itemJObject = new JSONObject();
                    Spinner s = (Spinner) allVIEWS.get(noOfViews);
                    sp1v = itemDetailses.getOption().get(noOfViews).getValues().get(s.getSelectedItemPosition()).getOptPriceDiff();

                    itemJObject.put("optID", itemDetailses.getOption().get(noOfViews).getOptGrpID());
                    itemJObject.put("option_name", itemDetailses.getOption().get(noOfViews).getOptGrpName());
                    itemJObject.put("option_value", itemDetailses.getOption().get(noOfViews).getValues().get(s.getSelectedItemPosition()).getOptName());
                    itemJObject.put("price_diff", itemDetailses.getOption().get(noOfViews).getValues().get(s.getSelectedItemPosition()).getOptPriceDiff());

                    itemJArray.put(itemJObject);
                }
                if (itemDetailses.getOption().get(noOfViews).getOptType().equals("-3")) {
                    itemJObject = new JSONObject();
                    EditText ed = (EditText) allVIEWS.get(noOfViews);
                    itemJObject.put("optID", itemDetailses.getOption().get(noOfViews).getOptGrpID());
                    itemJObject.put("option_name", itemDetailses.getOption().get(noOfViews).getOptGrpName());
                    itemJObject.put("option_value", ed.getText().toString().trim());

                    itemJArray.put(itemJObject);
                }
                if (itemDetailses.getOption().get(noOfViews).getOptType().equals("3")) {
                    itemJObject = new JSONObject();
                    EditText ed2 = (EditText) allVIEWS.get(noOfViews);
                    itemJObject.put("optID", itemDetailses.getOption().get(noOfViews).getOptGrpID());
                    itemJObject.put("option_name", itemDetailses.getOption().get(noOfViews).getOptGrpName());
                    itemJObject.put("option_value", ed2.getText().toString().trim());

                    itemJArray.put(itemJObject);
                }

                if (itemDetailses.getOption().get(noOfViews).getOptType().equals("-1")) {
                    itemJObject = new JSONObject();
                    RadioGroup radioGroup = (RadioGroup) allVIEWS.get(noOfViews);
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

                    String rval1[] = radioButton.getTag().toString().split(",");
                    itemJObject.put("optID", itemDetailses.getOption().get(noOfViews).getOptGrpID());
                    itemJObject.put("option_name", itemDetailses.getOption().get(noOfViews).getOptGrpName());
                    itemJObject.put("option_value", rval1[1]);
                    itemJObject.put("price_diff", rval1[0]);

                    itemJArray.put(itemJObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(null);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerValue = 0;
        for (int noOfViews = 0; noOfViews < itemDetailses.getOption().size(); noOfViews++) {
            if (itemDetailses.getOption().get(noOfViews).getOptType().equals("-2")) {
                Spinner s = (Spinner) allVIEWS.get(noOfViews);
                sp1v = itemDetailses.getOption().get(noOfViews).getValues().get(s.getSelectedItemPosition()).getOptPriceDiff();
                spinnerValue += Integer.parseInt(sp1v);
            }
        }

        if (isAbleAddtocart) {
            price.setText(TextUtils.concat("$", String.valueOf(spinnerValue + mainPrice + radioValue)));
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        radioValue = 0;
        for (int noOfViews = 0; noOfViews < itemDetailses.getOption().size(); noOfViews++) {
            if (itemDetailses.getOption().get(noOfViews).getOptType().equals("-1")) {
                RadioGroup radioGroup2 = (RadioGroup) allVIEWS.get(noOfViews);
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioGroup2.getCheckedRadioButtonId());
                String rval1[] = radioButton.getTag().toString().split(",");
                radioValue += Integer.parseInt(rval1[0]);
            }
        }
        if (isAbleAddtocart) {
            price.setText(TextUtils.concat("$", String.valueOf(spinnerValue + radioValue + mainPrice)));
        }
    }

    public static class MySpannable extends ClickableSpan {

        private boolean isUnderline = false;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {

            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#343434"));

        }

        @Override
        public void onClick(View widget) {

        }
    }

    public class EmpComp implements Comparator<Combination> {

        public int compare(Combination e1, Combination e2) {

            return new CompareToBuilder()
                    .append(e1.materia, e2.materia)
                    .append(e1.type, e2.type)
                    .append(e1.size, e2.size)
                    .toComparison();

        }
    }


    public class MaterialComp implements Comparator<MaterialDetail> {

        public int compare(MaterialDetail e1, MaterialDetail e2) {

            return new CompareToBuilder()

                    .append(e1.getYarn(), e2.getYarn())
                    .append(e1.getTufting(), e2.getTufting())
                    .toComparison();

        }
    }
}
