
package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("optID")
    @Expose
    private String optID;
    @SerializedName("optName")
    @Expose
    private String optName;
    @SerializedName("optPriceDiff")
    @Expose
    private String optPriceDiff;

    public String getOptID() {
        return optID;
    }

    public String getOptName() {
        return optName;
    }

    public String getOptPriceDiff() {
        return optPriceDiff;
    }

}
