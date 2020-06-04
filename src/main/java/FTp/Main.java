package FTp;

import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        for (; ;) {
            Ftp ftp = new Ftp();
            FTPClient client = new FTPClient();
            try {
                client.connect("185.98.7.178");
                client.login("tikkurila", "U1awz^39");
                client.enterLocalPassiveMode();


                if (client.isConnected()) {
                    System.out.println("------Connection successfully!!!--------");
                    ftp.PrintFileDescription(client);
                    System.out.println("list of file received");
                }
                if(ftp.flist!= null) {
                    ftp.download(client);
                    System.out.println("list of files downloaded");
                } else System.out.println("No new files found");

                client.logout();
                client.disconnect();
                System.out.println("--------Connection closed error mising!!!------------");
                try {
                    Thread.sleep(100000);

                }catch (InterruptedException in){
                    System.out.println("Ошибка таймера");
                }
            } catch (IOException ex) {
                System.out.println("connection error");

            }
        }
    }
}
