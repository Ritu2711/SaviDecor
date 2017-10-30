package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class AllProducts {

    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("item")
    @Expose
    private List<Products> item = new ArrayList<Products>();

    public List<Products> getItem() {
        return item;
    }

    public String getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "AllProducts{" +
                "item=" + item +
                '}';
    }
    public void setItem(List<Products> item) {
        this.item = item;
    }
}