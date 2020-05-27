import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press ENTER to stop");
        CarFactory factory = new CarFactory();
        factory.start();
        scanner.nextLine();
        factory.stop();
    }
}
