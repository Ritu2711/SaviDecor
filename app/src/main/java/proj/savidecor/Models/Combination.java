package proj.savidecor.Models;

/**
 * Created by sai on 1/11/17.
 */

public class Combination {

    public String materia;
    public String type;
    public String size;
    String price;
    String id;

    public Combination(String s, String s1) {
        id = s;
        materia = s1;
    }

    public Combination(String materia, String type, String size, String price, String id) {
        this.materia = materia;
        this.type = type;
        this.size = size;
        this.price = price;
        this.id = id;
    }

    public Combination(String materia, String type, String size) {
        this.materia = materia;
        this.type = type;
        this.size = size;
    }


    public Combination() {
    }

    public Combination(String s) {
        materia = s;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Combination{" +
                "materia='" + materia + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
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
