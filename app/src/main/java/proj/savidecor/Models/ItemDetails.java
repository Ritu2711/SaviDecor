package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ItemDetails {

    @SerializedName("pID")
    @Expose
    private String pID;
    @SerializedName("pName")
    @Expose
    private String pName;
    @SerializedName("pSection")
    @Expose
    private String pSection;
    @SerializedName("pDescription")
    @Expose
    private String pDescription;
    @SerializedName("pLongdescription")
    @Expose
    private String pLongdescription;
    @SerializedName("pPrice")
    @Expose
    private String pPrice;
    @SerializedName("pListPrice")
    @Expose
    private String pListPrice;
    @SerializedName("pWholesalePrice")
    @Expose
    private String pWholesalePrice;
    @SerializedName("pManufacturer")
    @Expose
    private String pManufacturer;



    @SerializedName("pDims")
    @Expose
    private String pDims;


    @SerializedName("pSKU")
    @Expose
    private String pSKU;
    @SerializedName("Udf1")
    @Expose
    private String udf1 = null;
    @SerializedName("Udf2")
    @Expose
    private String udf2 = null;
    @SerializedName("Udf3")
    @Expose
    private String udf3 = null;
    @SerializedName("Udf20")
    @Expose
    private String udf20 = null;
    @SerializedName("pUFD1")
    @Expose
    private String pudf1 = null;
    @SerializedName("pUDF2")
    @Expose
    private String pudf2 = null;
    @SerializedName("pUDF4")
    @Expose
    private String pudf3 = null;


    @SerializedName("pUDF7")
    @Expose
    private String pUDF7 = null;
 @SerializedName("pInStock")
    @Expose
    private String pInStock = null;

    public String getpLongdescription() {
        return pLongdescription;
    }

    public void setpLongdescription(String pLongdescription) {
        this.pLongdescription = pLongdescription;
    }

    @SerializedName("pUDF20")
    @Expose
    private String pudf20 = null;
    @SerializedName("imageSrc")
    @Expose
    private String imageSrc;
    @SerializedName("option")
    @Expose
    private List<Option> option = new ArrayList<Option>();
    @SerializedName("links")
    @Expose
    private List<Link> links = new ArrayList<Link>();

    public String getPID() {
        return pID;
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getPName() {
        return pName;
    }

    public String getPPrice() {
        return pPrice;
    }

    public String getPListPrice() {
        return pListPrice;
    }

    public String getPSKU() {
        return pSKU;
    }
    public String getpDims() {
        return pDims;
    }

    public String getUdf1() {
        return udf1;
    }
    public String getpInStock() {
        return pInStock;
    }

    public String getUdf2() {
        return udf2;
    }

    public String getUdf3() {
        return udf3;
    }

    public String getUdf20() {
        return udf20;
    }

    public String getpUDF1() {
        return pudf1;
    }

    public String getpUDF2() {
        return pudf2;
    }

    public String getpUDF3() {
        return pudf3;
    }


    public String getpUDF7() {
        return pUDF7;
    }

    public String getpUDF20() {
        return pudf20;
    }


    public String getImageSrc() {
        return imageSrc;
    }

    public List<Option> getOption() {
        return option;
    }

    @Override
    public String toString() {
        return "Item{" +
                "pID='" + pID + '\'' +
                ", pName='" + pName + '\'' +
                ", pSection='" + pSection + '\'' +
                ", pDescription='" + pDescription + '\'' +
                ", pLongdescription='" + pLongdescription + '\'' +
                ", pPrice='" + pPrice + '\'' +
                ", pListPrice='" + pListPrice + '\'' +
                ", pWholesalePrice='" + pWholesalePrice + '\'' +
                ", pManufacturer='" + pManufacturer + '\'' +
                ", pSKU='" + pSKU + '\'' +
                ", udf1='" + udf1 + '\'' +
                ", udf2='" + udf2 + '\'' +
                ", udf3='" + udf3 + '\'' +
                ", udf20='" + udf20 + '\'' +
                ", pDims='" + pDims + '\'' +
                ", pUDF7='" + pUDF7 + '\'' +
                ", pInStock='" + pInStock + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                ", option=" + option +
                '}';
    }
}
