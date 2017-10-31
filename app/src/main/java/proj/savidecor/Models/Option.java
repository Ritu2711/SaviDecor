
package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Option {



    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("price")
    @Expose
    private String price;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


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
