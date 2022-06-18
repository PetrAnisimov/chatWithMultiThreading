package courseWork.client.src;

import courseWork.common.src.ru.itmo.Connection;
import courseWork.common.src.ru.itmo.SimpleMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final String ANSI_YELLOW = "\u001B[33m";



    private int port;
    private String ip;
    private Scanner scanner;
    public String userName;

    public Client(int port, String ip) {
        this.port = port;
        this.ip = ip;
        scanner = new Scanner(System.in);
    }



    public void start() {

        System.out.println("""
                Клиент запущен...
                Введите имя: """);
        userName = scanner.nextLine();
        String messageText;
        try (Connection connection = new Connection(new Socket("127.0.0.1", 8090))) {
            new Thread(new ReadFromClient(connection), userName).start();

            while (true) {
                System.out.println("Введите сообщение");
                messageText = scanner.nextLine();
                connection.sendMessage(SimpleMessage.getMessage(userName, messageText));
            }
        } catch (IOException e) {
            System.out.println(ANSI_YELLOW + " == > Скорее всего не запущен сервер < == " + ANSI_YELLOW);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        int port = 8090;
        String ip = "127.0.0.1";

        try {
            new Client(port, ip).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private Socket getSocket() throws IOException {
//        Socket socket = new Socket(port, ip);
//        return socket;
//    }
}
