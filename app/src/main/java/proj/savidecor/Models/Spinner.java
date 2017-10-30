
package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Spinner {

    @SerializedName("material")
    @Expose
    private String material;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
