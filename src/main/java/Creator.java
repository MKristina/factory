import java.lang.reflect.InvocationTargetException;

public class Creator<T> extends Thread{
    private CarStore<T> storage;
    private Class<? extends T> detailCreator;
    private int delay;

    public Creator(int delay, CarStore<T> storage, Class<? extends T> detailCreator) {
        this.delay = delay;
        this.storage = storage;
        this.detailCreator = detailCreator;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            try {
                storage.add(detailCreator.getDeclaredConstructor().newInstance());
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                break;
            }
            catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}