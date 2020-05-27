import Product.Accessory;
import Product.Body;
import Product.Product;
import Product.Engine;
import Product.Car;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CarFactory {
    private CarStore<Engine> EngineStore;
    private CarStore<Accessory> AccessoryStore;
    private CarStore<Body> BodyStore;
    private CarStore<Car> CarStore;
    ArrayList<Creator<Accessory>> AccessoryCreators;
    private ThreadPoolExecutor workers;
    private final ArrayList<Creator<? extends Product>> creators = new ArrayList<>();
    private final ArrayList<Dealer> dealers = new ArrayList<>();

    Properties config = new Properties();

    public CarFactory() {

        try {
            InputStream IStream = CarFactory.class.getResourceAsStream("/parameters.config");
            if (IStream == null) {
                throw new IOException();
            } else {
                config.load(IStream);
            }
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        int bodyStore  = Integer.parseInt(config.getProperty("bodyStore"));
        int engineStore  = Integer.parseInt(config.getProperty("engineStore"));
        int accessoryStore  = Integer.parseInt(config.getProperty("accessoryStore"));
        int carStore  = Integer.parseInt(config.getProperty("carStore"));
        int WorkersCount = Integer.parseInt(config.getProperty("Workers"));

        EngineStore = new CarStore<>(engineStore );
        AccessoryStore = new CarStore<>(bodyStore );
        BodyStore = new CarStore<>(accessoryStore );
        CarStore = new CarStore<>(carStore );

        workers = (ThreadPoolExecutor) Executors.newFixedThreadPool(WorkersCount);
        CarStore.addObserver(new CarStoreController(BodyStore, EngineStore, AccessoryStore, CarStore, workers));
        creators.add(new Creator<Body>(1000, BodyStore, Body.class));
        creators.add(new Creator<Engine>(1000, EngineStore, Engine.class));
        creators.add(new Creator<Accessory>(1000, AccessoryStore, Accessory.class));


    }
    public void start() {
        for (Creator<? extends Product> Creator : creators) {
            Creator.start();
        }
        int DealersCount = Integer.parseInt(config.getProperty("Dealers"));
        for (int i = 0; i < DealersCount; i++) {
            dealers.add(new Dealer(5000, CarStore));
            dealers.get(i).start();
        }
        int accessorySuppliers = Integer.parseInt(config.getProperty("AccessoryCreators"));
        AccessoryCreators = new ArrayList<>(accessorySuppliers);
        for (int i = 0; i < accessorySuppliers; i++) {
            AccessoryCreators.add(new Creator<>(5000, AccessoryStore, Accessory.class ));
            AccessoryCreators.get(i).start();
        }
    }

    public void stop() {
        for (Creator<? extends Product> Creator : creators) {
            Creator.interrupt();
        }
        for (Dealer dealer : dealers) {
            dealer.interrupt();
        }
        for (Creator<Accessory> supplier : AccessoryCreators) {
            supplier.interrupt();
        }
        workers.shutdownNow();
    }

}