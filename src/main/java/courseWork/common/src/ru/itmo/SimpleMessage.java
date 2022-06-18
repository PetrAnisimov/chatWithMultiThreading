package courseWork.common.src.ru.itmo;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "tb_sender_info")
public class SimpleMessage implements Serializable {


    @Getter
    @Setter
    @Column(name = "sender_info",length = 20, nullable = true)
    private String sender;

    @Getter
    @Setter
    @Column(name = "message_text",length = 20, nullable = true)
    private String text;

    @Getter
    @Setter
    @Column(name = "date",length = 20, nullable = true)
    private LocalDateTime dateTime;

    public SimpleMessage(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public void setDateTime(){
        dateTime = LocalDateTime.now();
    }


    public static SimpleMessage getMessage(String sender, String text){
        return new SimpleMessage(sender, text);
    }

    @Override
    public String toString() {

        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM HH.mm.ss"))+ ": " + sender + ": " + text;
    }
}
