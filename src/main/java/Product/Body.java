package Product;

public class Body implements Product {
    private int ID;
    static int count = 0;
    public Body() {
        ID = count++;
    }
    public int getID() {
        return ID;
    }
}