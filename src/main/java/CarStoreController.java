import Product.Accessory;
import Product.Body;
import Product.Car;
import Product.Engine;
import java.util.concurrent.ThreadPoolExecutor;

public class CarStoreController implements Observer {

    private final CarStore<Body> bodyStore;
    private final CarStore<Engine> engineStore;
    private final CarStore<Accessory> accessoryStore;
    private final CarStore<Car> carStore;
    private final ThreadPoolExecutor workers;

    public CarStoreController(CarStore<Body> bodyStore, CarStore<Engine> engineStore,
                              CarStore<Accessory> accessoryStore, CarStore<Car> carStore, ThreadPoolExecutor workers) {
        this.bodyStore = bodyStore;
        this.engineStore = engineStore;
        this.accessoryStore = accessoryStore;
        this.carStore = carStore;
        this.workers = workers;
    }

    @Override
    public void update() {
        long carsInProgress = workers.getTaskCount() - workers.getCompletedTaskCount();
        if ((double) (carStore.getSize() + carsInProgress) / carStore.getCapacity() < 0.2) {
            System.out.println(("New task!"));
            for (int i = 0; i < 0.2 * carStore.getCapacity() + 1; i++){
                workers.execute(new Task());
            }
        }
    }

    private class Task implements Runnable {
        @Override
        public void run() {
            try {
                Body body = bodyStore.get();
                Engine engine = engineStore.get();
                Accessory accessory = accessoryStore.get();
                Car auto = new Car(engine, body, accessory);
                carStore.add(auto);
                System.out.println("Car created: ID = " + auto.getID() + " EngineID = " + auto.getEngineID() +
                        "; BodyID = " + auto.getBodyID() + "; AccessoryID = " + auto.getAccessoryID());
                Thread.sleep(3000);
            } catch (InterruptedException ex){
                System.out.println(("Thread was interrupted"));
            }
        }
    }

}