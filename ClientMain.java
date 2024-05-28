package TcpDemo;

import java.io.IOException;

public class ClientMain
{
    public static void main(String[] args) throws IOException {
        Client c = new Client("127.0.0.1",26666);
        c.start();
    }
}
