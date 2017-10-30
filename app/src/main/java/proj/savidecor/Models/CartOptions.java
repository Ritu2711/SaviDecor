package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartOptions {

        @SerializedName("coID")
        @Expose
        private String coID;
        @SerializedName("coCartID")
        @Expose
        private String coCartID;
        @SerializedName("coOptID")
        @Expose
        private String coOptID;
        @SerializedName("coOptGroup")
        @Expose
        private String coOptGroup;
        @SerializedName("coCartOption")
        @Expose
        private String coCartOption;
        @SerializedName("coPriceDiff")
        @Expose
        private String coPriceDiff;
        @SerializedName("coWeightDiff")
        @Expose
        private String coWeightDiff;
        @SerializedName("coMultiply")
        @Expose
        private String coMultiply;

    public String getCoOptGroup() {
        return coOptGroup;
    }

    public String getCoCartOption() {
        return coCartOption;
    }

    public String getCoPriceDiff() {
        return coPriceDiff;
    }

    public String getCoWeightDiff() {
        return coWeightDiff;
    }

    public String getCoMultiply() {
        return coMultiply;
    }
}
