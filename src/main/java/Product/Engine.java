package Product;

public class Engine implements Product {
    private int ID;
    static int count = 0;
    public Engine() {
        ID = count++;
    }
    public int getID() {
        return ID;
    }
}