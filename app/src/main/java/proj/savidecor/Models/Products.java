package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {


    @SerializedName("price_range")
    @Expose
    private String price_range;


    @SerializedName("pID")
    @Expose
    private String pID;
    @SerializedName("pName")
    @Expose
    private String pName;
    @SerializedName("pPrice")
    @Expose
    private String pPrice;
    @SerializedName("pListPrice")
    @Expose
    private String pListPrice;
    @SerializedName("Udf2")
    @Expose
    private String udf2 = null;
    @SerializedName("pUDF2")
    @Expose
    private String pudf2 = null;
    @SerializedName("imageSrc")
    @Expose
    private String imageSrc;

    public String getPID() {
        return pID;
    }

    public String getPName() {
        return pName;
    }

    public String getPPrice() {
        return pPrice;
    }

    public String getPrice_range() {
        return price_range;
    }

    public String getPListPrice() {
        return pListPrice;
    }

    public String getUdf2() {
        return udf2;
    }

    public String getpUdf2() {
        return pudf2;
    }

    public String getImageSrc() {
        return imageSrc;
    }
}
