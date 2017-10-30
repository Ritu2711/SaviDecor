package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CartItem {

        @SerializedName("cartID")
        @Expose
        private String cartID;
        @SerializedName("cartSessionID")
        @Expose
        private String cartSessionID;
        @SerializedName("cartProdID")
        @Expose
        private String cartProdID;
        @SerializedName("cartProdName")
        @Expose
        private String cartProdName;
        @SerializedName("cartProdPrice")
        @Expose
        private String cartProdPrice;
        @SerializedName("cartDateAdded")
        @Expose
        private String cartDateAdded;
        @SerializedName("cartQuantity")
        @Expose
        private String cartQuantity;
        @SerializedName("cartOrderID")
        @Expose
        private String cartOrderID;
        @SerializedName("cartClientID")
        @Expose
        private String cartClientID;
        @SerializedName("cartCompleted")
        @Expose
        private String cartCompleted;
        @SerializedName("cartListID")
        @Expose
        private String cartListID;
        @SerializedName("cartGiftWrap")
        @Expose
        private String cartGiftWrap;
        @SerializedName("cartGiftMessage")
        @Expose
        private Object cartGiftMessage;
        @SerializedName("dump")
        @Expose
        private String dump;
        @SerializedName("cartSubTotal")
        @Expose
        private String cartSubTotal;
        @SerializedName("imageSrc")
        @Expose
        private String imageSrc;
        @SerializedName("options")
        @Expose
        private List<CartOptions> options = new ArrayList<CartOptions>();

        @Override
        public String toString() {
                return "CartItem{" +
                        "cartSessionID='" + cartSessionID + '\'' +
                        ", cartProdID='" + cartProdID + '\'' +
                        ", cartProdName='" + cartProdName + '\'' +
                        ", cartProdPrice='" + cartProdPrice + '\'' +
                        ", cartSubTotal='" + cartSubTotal + '\'' +
                        ", cartQuantity=" + cartQuantity +
                        ", cartGiftMessage='" + cartGiftMessage + '\'' +
                        ", imageSrc='" + imageSrc + '\'' +
                        '}';
        }

        public String getCartProdID() {
                return cartProdID;
        }

        public String getCartProdName() {
                return cartProdName;
        }

        public String getCartProdPrice() {
                return cartProdPrice;
        }

        public String getCartDateAdded() {
                return cartDateAdded;
        }

        public String getCartQuantity() {
                return cartQuantity;
        }

        public String getCartSubTotal() {
                return cartSubTotal;
        }

        public String getImageSrc() {
                return imageSrc;
        }

        public List<CartOptions> getOptions() {
                return options;
        }
}
