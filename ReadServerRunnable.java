package TcpDemo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
public class ReadServerRunnable implements Runnable
{
    private Socket socket = null;
    private List<Socket> sockets = new LinkedList<Socket>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private InputStream inputStream = null;
    private DataInputStream dataInputStream = null;
    public ReadServerRunnable(Socket s) throws IOException {
        socket = s;
        inputStream = socket.getInputStream();
        dataInputStream = new DataInputStream(inputStream);
    }

    public void setSockets(List<Socket> sockets)
    {
        this.sockets = sockets;
    }

    public void writeTasks(String info) throws IOException {
        for (Socket socket : sockets)
        {
            if (!socket.isConnected() || socket.equals(this.socket))
            {
                continue;
            }
            else if (socket.isConnected() && !socket.equals(this.socket))
            {
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF(info);
            }
        }
    }

    public void run()
    {
        while (true)
        {
            try {
                String info = dataInputStream.readUTF();
                LocalDateTime dateTime = LocalDateTime.now();
                String msg = String.format("%s %s %s",dateTime.format(formatter), socket.getRemoteSocketAddress().toString(), info);
                System.out.println(msg);
                writeTasks(msg);
            } catch (IOException e) {
                LocalDateTime dateTime = LocalDateTime.now();
                System.out.println(dateTime.format(formatter)+" "+Thread.currentThread().getName()+socket.getRemoteSocketAddress()+" "+e.getMessage());
                try {
                    dataInputStream.close();
                    socket.close();
                } catch (IOException ex) {

                }
                finally {
                    System.out.println(dateTime.format(formatter)+" "+Thread.currentThread().getName()+socket.getRemoteSocketAddress()+" "+e.getMessage());
                    break;
                }

            }
        }
    }
}
