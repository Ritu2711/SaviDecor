package proj.savidecor.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import proj.savidecor.Models.Item;
import proj.savidecor.R;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {

    private List<Item> data;
    private int selected_position = -1;


    public NavigationDrawerAdapter(Context context, List<Item> data) {
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navdrawer_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Item datas=data.get(holder.getAdapterPosition());

        if (selected_position==holder.getAdapterPosition()){
            holder.itemView.setBackgroundResource(R.color.selected);
        }else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(selected_position);
                selected_position = holder.getAdapterPosition();
                notifyItemChanged(selected_position);
            }
        });

        if (datas.getSectionName().equals("Custom")){
            holder.title.setBackgroundResource(R.drawable.custom_effect);
            holder.title.setTextColor(Color.WHITE);
            holder.title.setTextSize(19);
        }
            holder.title.setText(datas.getSectionName());
            int sell = 0;
            holder.title.setSelected(sell ==position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public int getItemCount() {
        if (data !=null)
            return data.size();
        else
            return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public Item getItemAtPosition(int position)
    {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}