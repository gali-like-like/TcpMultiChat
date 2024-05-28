package TcpDemo;

import java.io.IOException;

public class ServerMain
{
    public static void main(String[] args) throws IOException {
        Server s = new Server(26666);
        boolean start = s.start();
        System.out.println(start);
    }
}
