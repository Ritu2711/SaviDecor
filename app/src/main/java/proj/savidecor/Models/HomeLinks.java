package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HomeLinks {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sites_id")
    @Expose
    private String sitesId;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("href")
    @Expose
    private String href;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public String getHref() {
        return href;
    }

}
