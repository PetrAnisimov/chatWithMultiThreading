package courseWork.client.src;

import courseWork.common.src.ru.itmo.Connection;
import courseWork.common.src.ru.itmo.SimpleMessage;

import java.io.IOException;
import java.net.SocketException;

public class ReadFromClient implements Runnable {
    private final Connection connection;

    public ReadFromClient(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        while (true) {
            try {
                SimpleMessage message = connection.readMessage();
                System.out.println(message);
            } catch (SocketException e) {
                e.printStackTrace();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}