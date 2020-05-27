package Product;

public class Accessory implements Product {
    private int ID;
    static int count = 0;
    public Accessory() {
        ID = count++;
    }
    public int getID() {
        return ID;
    }
}