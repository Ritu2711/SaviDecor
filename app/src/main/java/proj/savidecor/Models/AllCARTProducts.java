package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllCARTProducts {

        @SerializedName("item")
        @Expose
        private List<CartItem> item = new ArrayList<CartItem>();

        public List<CartItem> getItem() {
            return item;
        }

        public void setItem(List<CartItem> item) {
            this.item = item;
        }

    }
