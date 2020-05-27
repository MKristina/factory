import Product.Car;

public class Dealer extends Thread {
    private CarStore<Car> carStore;
    private int DealerID;
    static int DealerCount = 0;
    private int delay;

    public Dealer(int delay, CarStore<Car> carStore) {
        this.carStore = carStore;
        this.DealerID = this.DealerCount;
        this.DealerCount++;
        this.delay = delay;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            try {
                Thread.sleep(delay);
                Car car = carStore.get();
                System.out.println("Car solden! ID = " + car.getID());
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}