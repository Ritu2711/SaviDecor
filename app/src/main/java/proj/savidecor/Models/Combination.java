package proj.savidecor.Models;

/**
 * Created by sai on 31/10/17.
 */

public class Combination {
String Material,type,size,price;


    public Combination(String material, String type, String size, String price) {
        Material = material;
        this.type = type;
        this.size = size;
        this.price = price;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
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
}
