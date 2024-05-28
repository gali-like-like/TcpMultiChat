package TcpDemo;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
public class WriteServerRunnable implements Runnable {
    private List<Socket> sockets;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Scanner scanner = new Scanner(System.in);
    public WriteServerRunnable(List<Socket> ss) throws IOException {
        this.sockets = ss;
    }

    public void run()
    {
        while (true)
        {
            LocalDateTime now = LocalDateTime.now();
            String nowStr = now.format(formatter);
            System.out.println(nowStr+" 请说:");
            String line = scanner.nextLine();
            try {
                if (line.equals("exit") && !sockets.isEmpty())
                {
                    break;
                }
                else if (!line.isEmpty() && !line.equals("exit"))
                {
                    for (Socket s : sockets)
                    {
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        dos.writeUTF(line);
                    }
                }
            } catch (IOException e) {
                System.out.println(nowStr+" "+Thread.currentThread().getName()+e.getMessage());
                break;
            }
        }
    }
}
