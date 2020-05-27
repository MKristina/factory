package Product;

import Product.Accessory;
import Product.Body;
import Product.Engine;
import Product.Product;

public class Car implements Product {
    private int ID;
    Engine engine;
    Accessory accessory;
    Body body;
    private static int count = 0;

    public Car(Engine engine, Body body, Accessory accessory) {
        ID = count++;
        this.engine = engine;
        this.accessory = accessory;
        this.body = body;
    }

    public int getID() {
        return ID;
    }
    public int getEngineID() {

        return engine.getID();
    }
    public int getBodyID() {

        return body.getID();
    }
    public int getAccessoryID() {

        return accessory.getID();
    }
}
