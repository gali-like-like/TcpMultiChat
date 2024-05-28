package TcpDemo;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class ClientReadThread extends Thread
{
    private Socket socket;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public ClientReadThread(Socket s)
    {
        this.socket = s;
    }

    @Override
    public void run()
    {
        try {
            InputStream is = this.socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            while(true)
            {
                LocalDateTime dateTime = LocalDateTime.now();
                String dateTimeInfo = dateTime.format(formatter);
                String info = dis.readUTF();
                if(info.isEmpty() == false)
                    System.out.println(dateTimeInfo+" "+socket.getRemoteSocketAddress()+" "+info);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
