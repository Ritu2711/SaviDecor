package proj.savidecor.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.List;

import proj.savidecor.Models.Products;
import proj.savidecor.R;


public class Testadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static int viewFormat;
    private List<Products> products;
    private Context context;

    public Testadapter(List<Products> products, Context context) {
        this.products = products;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder vh;
        if (viewType== 5){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items,parent,false);
            vh=new MovieViewHolder(view);
        }else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_view,parent,false);
            vh=new ProgressHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MovieViewHolder){
            ((MovieViewHolder)holder).pName.setText(products.get(position).getPName().trim());
        }else {
            ((ProgressHolder)holder).pbb.setIndeterminate(true);
        }
    }


    private static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView pName,pSizevalue,pPrice,pLprice;
        ImageView iv;

        MovieViewHolder(View v) {
            super(v);
            this.pName = (TextView) v.findViewById(R.id.pname);
            this.pSizevalue = (TextView) v.findViewById(R.id.sizevalue);
            this.pPrice = (TextView) v.findViewById(R.id.pprice);
            this.pLprice = (TextView) v.findViewById(R.id.myerror);
            this.iv=(ImageView)v.findViewById(R.id.iv);
        }
    }

    private static class ProgressHolder extends RecyclerView.ViewHolder {
            ProgressBar pbb;

        ProgressHolder(View v) {
            super(v);
            this.pbb=(ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    public Products getItemAtPosition(int position)
    {
        return products.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return products.get(position)!=null?5:10;
    }
}