package fpoly.pro1121.asmand103.Model;

public class Product {
    private String _id;
    private String name;
    private String description;
    private String image;
    private int price;
    private String _id_category;

    public Product(String _id, String name, String description, String image, int price, String _id_category) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this._id_category = _id_category;
    }

    public Product() {
    }

    public Product(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String get_id_category() {
        return _id_category;
    }

    public void set_id_category(String _id_category) {
        this._id_category = _id_category;
    }
}
