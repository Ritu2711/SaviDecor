package proj.savidecor.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.util.List;

import proj.savidecor.Models.Products;
import proj.savidecor.R;
import proj.savidecor.Utils.Constants;

import static android.content.Context.MODE_PRIVATE;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MovieViewHolder> {
    public static int viewFormat;
    private List<Products> products;
    private Context context;
    SharedPreferences sharedPreferences;



    public ProductsAdapter(List<Products> products, Context context) {
        this.products = products;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("VIEW", MODE_PRIVATE);
    }

    @Override
    public ProductsAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.gridV) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items, parent, false);
            return new MovieViewHolder(view);
        } else if (viewType == Constants.listV) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_item_view, parent, false);
            return new MovieViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {


        Products prod = products.get(holder.getAdapterPosition());
        SpannableString spannableString = new SpannableString(prod.getPListPrice().trim());
        spannableString.setSpan(new StrikethroughSpan(), 0, prod.getPListPrice().length(), 0);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "Rupee_Foradian.ttf");
        holder.pLprice.setTypeface(face);
        holder.pName.setText(prod.getPName().trim());

        if (prod.getUdf2() != null) {
            holder.pSizevalue.setText(prod.getUdf2().trim());
        } else if (prod.getpUdf2() != null) {
            holder.pSizevalue.setText(prod.getpUdf2().trim());
        }






        if (prod.getPPrice().equals("0")) {
            holder.pPrice.setText(R.string.nullPrice);
            holder.pLprice.setText("");
        } else {
            if (prod.getPListPrice().equals("0")) {
                holder.pPrice.setText(String.format(""+context.getResources().getString(R.string.rs)+" %s", prod.getPPrice().trim()+" / Sq. Ft."));
                holder.pLprice.setText("");
            } else {
                holder.pPrice.setText(String.format(""+context.getResources().getString(R.string.rs)+" %s", prod.getPPrice().trim()+" / Sq. Ft."));
                holder.pLprice.setText(TextUtils.concat(""+context.getResources().getString(R.string.rs)+"", spannableString));
            }
        }


        if (Constants.isNetworkAvailable(context)) {


           if (prod.getImageSrc().trim().startsWith("http")){
               Glide.with(context)
                       .load(prod.getImageSrc().trim())
                       .placeholder(R.drawable.pb_animview)
                       .error(R.drawable.search)
                       .diskCacheStrategy(DiskCacheStrategy.ALL)
                       .into(holder.iv);
           }
           else {
                   Glide.with(context)
                           .load("https:" + prod.getImageSrc().trim())
                           .placeholder(R.drawable.pb_animview)
                           .error(R.drawable.search)
                           .diskCacheStrategy(DiskCacheStrategy.ALL)
                           .into(holder.iv);

               }


        }
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView pName, pSizevalue, pPrice, pLprice;
        ImageView iv;

        MovieViewHolder(View v) {
            super(v);
            this.pName = (TextView) v.findViewById(R.id.pname);
            this.pSizevalue = (TextView) v.findViewById(R.id.sizevalue);
            this.pPrice = (TextView) v.findViewById(R.id.pprice);

            this.pLprice = (TextView) v.findViewById(R.id.myerror);
            this.iv = (ImageView) v.findViewById(R.id.iv);

        }
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public Products getItemAtPosition(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (products.size() == 0) {
            return 0;
        } else if (viewFormat == Constants.listV) {
            return Constants.listV;
        } else if (viewFormat == Constants.gridV) {
            return Constants.gridV;
        }
        return super.getItemViewType(position);
    }
}
