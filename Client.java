package TcpDemo;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.io.OutputStream;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Client
{
    private Socket socket;
    private Scanner in;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new Scanner(System.in);
    }

    public boolean start()  {
        ClientReadThread rt = new ClientReadThread(this.socket);
        rt.start();
        ClientWriteThread wt = new ClientWriteThread(this.socket);
        wt.start();
        return false;
    }
}
