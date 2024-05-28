package TcpDemo;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.net.Socket;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class ClientWriteThread extends Thread
{
    private Socket socket;
    private Scanner in = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public ClientWriteThread(Socket s)
    {
        this.socket = s;
    }

    public void run()
    {
        try (
                OutputStream os = socket.getOutputStream();
                DataOutputStream doc = new DataOutputStream(os);
        )
        {
            while (true)
            {
                LocalDateTime now = LocalDateTime.now();
                String nowStr = now.format(formatter);
                System.out.println(nowStr+" 请说:");
                String command = in.nextLine();
                if (!command.isEmpty() && !command.equals("exit"))
                    doc.writeUTF(command);
                else if (command.equals("exit"))
                {
                    doc.close();
                    socket.close();
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("启动失败发生io异常");
        }
    }
}
