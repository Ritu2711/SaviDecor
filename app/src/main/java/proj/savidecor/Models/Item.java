package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {


    @SerializedName("sectionID")
    @Expose
    private String sectionID;
    @SerializedName("sectionName")
    @Expose
    private String sectionName;
    @SerializedName("sectionName2")
    @Expose
    private Object sectionName2;
    @SerializedName("sectionName3")
    @Expose
    private Object sectionName3;
    @SerializedName("sectionWorkingName")
    @Expose
    private String sectionWorkingName;
    @SerializedName("sectionurl")
    @Expose
    private String sectionurl;
    @SerializedName("sectionImage")
    @Expose
    private String sectionImage;
    @SerializedName("sectionDescription")
    @Expose
    private String sectionDescription;
    @SerializedName("sectionDescription2")
    @Expose
    private Object sectionDescription2;
    @SerializedName("sectionDescription3")
    @Expose
    private Object sectionDescription3;
    @SerializedName("topSection")
    @Expose
    private String topSection;
    @SerializedName("rootSection")
    @Expose
    private String rootSection;
    @SerializedName("sectionOrder")
    @Expose
    private String sectionOrder;
    @SerializedName("sectionDisabled")
    @Expose
    private String sectionDisabled;
    @SerializedName("sectionurl2")
    @Expose
    private Object sectionurl2;
    @SerializedName("sectionurl3")
    @Expose
    private Object sectionurl3;
    @SerializedName("sectionHeader")
    @Expose
    private String sectionHeader;
    @SerializedName("sectionHeader2")
    @Expose
    private String sectionHeader2;
    @SerializedName("sectionHeader3")
    @Expose
    private String sectionHeader3;

    public Item(String catNAME, String cID){
        this.sectionName=catNAME;
        this.sectionID=cID;
    }

    public Item(String cAT, String cIDs, String cATs) {
        this.sectionName=cATs;
        this.sectionID=cIDs;
    }

    public String getSectionID() {
        return sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSectionImage() {
        return sectionImage;
    }


}