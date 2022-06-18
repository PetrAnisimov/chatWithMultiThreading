package courseWork.server.src.ru.itmo;

import courseWork.common.src.ru.itmo.Connection;
import courseWork.common.src.ru.itmo.SimpleMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class EchoServer {
    private int port;
    private final List<Connection> connections = new ArrayList<>();
    private final ArrayBlockingQueue<SimpleMessage> arrQueue = new ArrayBlockingQueue<>(30, true);


    public EchoServer(int port) {
        this.port = port;
    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен...");
            new Thread(new SenderServer(connections, arrQueue), "Отправитель").start();
            while (true) {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connections.add(connection);
                new Thread(new Accepter(connection)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Accepter implements Runnable {
        private final Connection connection;
        public Accepter(Connection connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    SimpleMessage message = connection.readMessage();
                    if (!message.getText().isEmpty()) {
                        try {
                            arrQueue.put(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SocketException e) {
                    connections.remove(connection);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static void main(String[] args) {
        int port = 8090;
        EchoServer messageServer = new EchoServer(port);
        messageServer.start();
    }

}
