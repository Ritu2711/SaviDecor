package proj.savidecor.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import proj.savidecor.Models.CartItem;
import proj.savidecor.Models.UpdateCART;
import proj.savidecor.R;
import proj.savidecor.Utils.Constants;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private static List<CartItem> cartPRO;
    private Context context;
    private CartItem prodCART=null;
    private EventBus bus=EventBus.getDefault();
    private AlertDialog alertDialog;

    public CartAdapter(List<CartItem> cartPRODUCTS, Context context) {
        cartPRO = cartPRODUCTS;
        this.context = context;
    }

    @Override
    public CartAdapter.CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_items,parent,false);
        return new CartHolder(view);

    }

    @Override
    public void onBindViewHolder(final CartHolder holder, int position) {
        prodCART= cartPRO.get(holder.getAdapterPosition());

        try {
            holder.cartUPDTPrice.setText(String.valueOf(prodCART.getCartQuantity()));
            holder.cartPRICE.setText(String.format("$%s", prodCART.getCartProdPrice().trim()));
            holder.cartTITLE.setText(prodCART.getCartProdName().trim());
            holder.subTOTAL.setText(String.format("$%s",prodCART.getCartSubTotal().trim()));

            try {
                StringBuilder sb = new StringBuilder();
                String mnv = null;
                for (int c=0;c<prodCART.getOptions().size();c++){
                    if (!prodCART.getOptions().get(c).getCoCartOption().equals("")){
                        if (prodCART.getOptions().get(c).getCoPriceDiff().equals("0")){
                            mnv=prodCART.getOptions().get(c).getCoOptGroup()+"\n   -"+
                                    prodCART.getOptions().get(c).getCoCartOption()+"\n\n";
                        }else {
                            mnv=prodCART.getOptions().get(c).getCoOptGroup()+"\n   -"+
                                    prodCART.getOptions().get(c).getCoCartOption()+" (+"+ String.format("$%s", prodCART.getOptions().get(c).getCoPriceDiff().trim()+")\n\n"
                            );
                        }

                        sb.append(mnv);
                    }
                }
                holder.viewDetailsCart.setText(String.valueOf(sb).trim());
            }catch (Exception e){
                e.printStackTrace();
            }

            if (Constants.isNetworkAvailable(context)) {
                if (prodCART.getImageSrc().trim().startsWith("http")){
                    Glide.with(context)
                            .load(prodCART.getImageSrc().trim())
                            .placeholder(R.mipmap.loading)
                            .error(R.drawable.search)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.cartIV);
                }
                else {
                    Glide.with(context)
                            .load("http:"+prodCART.getImageSrc().trim())
                            .placeholder(R.mipmap.loading)
                            .error(R.drawable.search)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.cartIV);
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }


        holder.removeIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Are you sure,You wanted to Remove\n"+prodCART.getCartProdName().trim());
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            bus.post(new UpdateCART(holder.getAdapterPosition(),cartPRO.get(holder.getAdapterPosition()).getCartProdID().trim(),
                                    cartPRO.get(holder.getAdapterPosition()).getCartProdName().trim(),
                                    cartPRO.get(holder.getAdapterPosition()).getCartSubTotal().trim(),
                                    0
                            ));
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                        }
                    });
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        holder.plusIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(holder.cartUPDTPrice.getText().toString())==10){
                    Constants.showToast(context,"Can't add more than 10");
                }else{

                    bus.post(new UpdateCART(holder.getAdapterPosition(),cartPRO.get(holder.getAdapterPosition()).getCartProdID().trim(),
                            cartPRO.get(holder.getAdapterPosition()).getCartProdName().trim(),
                            cartPRO.get(holder.getAdapterPosition()).getCartProdPrice().trim(),
                            Integer.parseInt(cartPRO.get(holder.getAdapterPosition()).getCartQuantity())+1
                    ));

                }

            }
        });
        holder.minusIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(holder.cartUPDTPrice.getText().toString())==1){
                    Constants.showToast(context,"Quantity can't be 0");
               }else {
                    bus.post(new UpdateCART(holder.getAdapterPosition(),cartPRO.get(holder.getAdapterPosition()).getCartProdID().trim(),
                            cartPRO.get(holder.getAdapterPosition()).getCartProdName().trim(),
                            cartPRO.get(holder.getAdapterPosition()).getCartProdPrice().trim(),
                            Integer.parseInt(cartPRO.get(holder.getAdapterPosition()).getCartQuantity())-1
                    ));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (cartPRO !=null)
           return cartPRO.size();
        else
            return 0;
    }

    static class CartHolder extends RecyclerView.ViewHolder {

            TextView cartUPDTPrice,cartPRICE,cartTITLE,subTOTAL,viewDetailsCart;
            ImageView cartIV,removeIMG,minusIMG,plusIMG;

        CartHolder(View itemView) {
            super(itemView);
            cartUPDTPrice = (TextView) itemView.findViewById(R.id.cartUPDTPrice);
            cartPRICE = (TextView) itemView.findViewById(R.id.cartPRICE);
            subTOTAL = (TextView) itemView.findViewById(R.id.subtotal);
            cartTITLE = (TextView) itemView.findViewById(R.id.cartTITLE);
            cartIV=(ImageView)itemView.findViewById(R.id.cartIV);
            removeIMG=(ImageView)itemView.findViewById(R.id.removeIMG);
            minusIMG=(ImageView)itemView.findViewById(R.id.minusIMG);
            plusIMG=(ImageView)itemView.findViewById(R.id.plusIMG);
            viewDetailsCart=(TextView)itemView.findViewById(R.id.viewDetailsCart);
        }
    }
}
