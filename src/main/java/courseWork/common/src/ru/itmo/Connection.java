package courseWork.common.src.ru.itmo;

import lombok.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Data
public class Connection implements AutoCloseable {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String sender;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        output = new ObjectOutputStream(this.socket.getOutputStream());
        input = new ObjectInputStream(this.socket.getInputStream());
    }

    public void sendMessage(SimpleMessage message) throws IOException {
        message.setDateTime();
        output.writeObject(message);
        output.flush();
    }

    public SimpleMessage readMessage() throws IOException, ClassNotFoundException {
        SimpleMessage simpleMessage = (SimpleMessage) input.readObject();
        sender = simpleMessage.getSender();
        return (SimpleMessage) input.readObject();
    }

    @Override
    public void close() throws Exception {
        input.close();
        output.close();
        socket.close();
    }
}
