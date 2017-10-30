
package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Option {

    @SerializedName("optGrpID")
    @Expose
    private String optGrpID;
    @SerializedName("optGrpName")
    @Expose
    private String optGrpName;
    @SerializedName("optType")
    @Expose
    private String optType;
    @SerializedName("poOptionGroup")
    @Expose
    private String poOptionGroup;
    @SerializedName("values")
    @Expose
    private List<Value> values = new ArrayList<Value>();

    public String getOptType() {
        return optType;
    }

    public String getOptGrpID() {
        return optGrpID;
    }

    public String getOptGrpName() {
        return optGrpName;
    }

    public List<Value> getValues() {
        return values;
    }
}
