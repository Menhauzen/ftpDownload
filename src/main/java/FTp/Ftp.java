package FTp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Ftp {
    List<String> flist = new ArrayList<String>();
    List<String> llist = new ArrayList<String>();
    List<String> llistCopy = new ArrayList<String>();
    String remoteFile1 = "";
    boolean check = true;
    boolean download = false;
    String filePath = "C:\\XMLDownloadMaster\\target\\LOG.txt";
    String downloadedName = "";
    String pathDirectory = "\\\\wmsbd\\inbound\\tikkurila\\new\\";

    public synchronized void PrintFileDescription(FTPClient client) throws IOException {


        FTPFile[] file = client.listFiles("/", new FTPFileFilter() {

            public boolean accept(FTPFile ftpFile) {
                return ftpFile.isFile();
            }
        });
        File files = new File("C:\\Users\\Notebook\\Desktop\\success");
        for (int i = 0; i < file.length; i++) {
            flist.add(i, file[i].getName());
        }
        System.out.println();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        System.out.println("read txt lines!");

        while ((line = reader.readLine()) != null) {
            llist.add(line);
        }

        if (flist.size() != llist.size()) {
            for (int i = 0; i < flist.size();i++) {
                for (int l = 0; l < llist.size(); l++) {
                    if (flist.get(i).contains(llist.get(l))) {
                        llistCopy.add(llist.get(l));
                    }
                    if (flist.size() > llist.size()) {
                        flist.removeAll(llist);
                    } else if (flist.size() == llist.size()) {
                        flist = null;

                    }
                }

            }
        }


    }

    public synchronized void download(FTPClient client) {
        String b = "";

        if (flist.size() != llist.size()) {
            if(flist!=null)
            try {


                for (int i = 0; i < flist.size(); i++) {
                    b = flist.get(i);


                    remoteFile1 = "/" + b;
                    File downloadFile1 = new File(pathDirectory + b);
                    OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
                    boolean success = client.retrieveFile(remoteFile1, outputStream1);
                    outputStream1.close();

                    if (success) {
                        try {
                            FileWriter writer = new FileWriter(filePath, true);
                            writer.write(b + "\n");
                            System.out.println("write to txt file");
                            writer.close();
                        } catch (IOException ex) {
                            System.out.println("Write to txt file error");
                        }
                        System.out.println("File download error missing!");
                    }
                }


            } catch (IOException ex) {
                System.out.println("download error");
            }
        }
    }
}


