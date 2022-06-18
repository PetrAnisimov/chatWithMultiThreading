package courseWork.server.src.ru.itmo;

import courseWork.common.src.ru.itmo.Connection;
import courseWork.common.src.ru.itmo.SimpleMessage;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class SenderServer implements Runnable{

    public SenderServer(List<Connection> connections, ArrayBlockingQueue<SimpleMessage> arrQueue) {
        this.connections = connections;
        this.arrQueue = arrQueue;
    }

    private final List<Connection> connections;
    private final ArrayBlockingQueue<SimpleMessage> arrQueue;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                SimpleMessage message = null;
                try {
                    message = arrQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": " + message);
                for (Connection connection : connections) {
                    if (connection.getSender().equals(message.getSender())) continue;
                    try {
                        connection.sendMessage(message);
                    }catch (EOFException | SocketException e) {
                        connection.close();
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


