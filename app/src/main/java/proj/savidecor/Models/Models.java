
package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Models {

    @SerializedName("item")
    @Expose
    private ItemDetails item;

    public ItemDetails getItem() {
        return item;
    }

    public void setItem(ItemDetails item) {
        this.item=item;
    }

}
