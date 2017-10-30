package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Link {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("href")
    @Expose
    private String href;

    public String getLabel() {
        return label;
    }

    public String getHref() {
        return href;
    }
}