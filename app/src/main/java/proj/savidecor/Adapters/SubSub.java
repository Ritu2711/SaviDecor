package proj.savidecor.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import proj.savidecor.Models.Item;
import proj.savidecor.R;
import proj.savidecor.Utils.Constants;


public class SubSub extends RecyclerView.Adapter<SubSub.SubViewHolder>{

    private List<Item> data;
    private Context context;

    public SubSub(Context context, List<Item> data) {
        this.data = data;
        this.context=context;
    }

    @Override
    public SubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newdata_item, parent,false);
        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubViewHolder holder, int position) {
        Item datas = data.get(holder.getAdapterPosition());
        holder.title.setText(datas.getSectionName());

        if (Constants.isNetworkAvailable(context)){
            try {
                if (!datas.getSectionImage().equals("")) {
                    if (Constants.isNetworkAvailable(context)) {

                        if (datas.getSectionImage().trim().startsWith("http")) {
                            Glide.with(context)
                                    .load(datas.getSectionImage().trim())
                                    .placeholder(R.mipmap.loading)
                                    .error(R.drawable.search)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.iv);
                        }
                        else {
                            Glide.with(context)
                                    .load("http:"+datas.getSectionImage().trim())
                                    .placeholder(R.mipmap.loading)
                                    .error(R.drawable.search)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.iv);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }
    class SubViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView iv;

        SubViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.subNAME);
            iv=(ImageView)itemView.findViewById(R.id.ivv);
        }
    }

}
