package TcpDemo;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
public class Server
{
    private ServerSocket serverSocket;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Scanner scanner = new Scanner(System.in);
    private ThreadPoolExecutor pool = new ThreadPoolExecutor(8,
            16,60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());
    private List<Socket> clients = new ArrayList<>();
    public Server(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
    }

    public boolean start()
    {
        while(true)
        {
            try {
                Socket socket = serverSocket.accept();
                clients.add(socket);
                ReadServerRunnable readServerRunnable = new ReadServerRunnable(socket);
                readServerRunnable.setSockets(clients);
                WriteServerRunnable writeServerRunnable = new WriteServerRunnable(clients);
                pool.execute(readServerRunnable);
                pool.execute(writeServerRunnable);
            } catch (IOException e) {
                LocalDateTime now = LocalDateTime.now();
                String info = now.format(dtf);
                System.out.println(info+" "+e.getMessage());
                return false;
            }
        }
    }
}
