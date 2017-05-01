/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidbackend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;

class TCPClient {

    private Socket client;
    private PrintWriter printWriter;
    private String currentMessage;
    private static InputStream in;
    private static final String SENDIMAGE = "SEND\n";
    private static final String OKAY = "OKAY";

    public static void main(String[] args) {
        TCPClient cp = new TCPClient();
        cp.ConnectAndSendMessage();
    }

    public void ConnectAndSendMessage() {
        Thread t = new Thread() {
            @Override
            public void run() {
                double lat = 100;
                double lng = 100;
                try {
                    Socket client = new Socket("localhost", 9999);
                    InputStream in = client.getInputStream();
                    OutputStream out = client.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(out);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    dos.writeBytes(SENDIMAGE);
                    currentMessage = reader.readLine();
                    System.out.println(currentMessage);
                    if (currentMessage.equals(OKAY)) {
                        dos.writeBytes(String.valueOf(lat) + "\n");
                        currentMessage = reader.readLine();
                        System.out.println(currentMessage);
                        dos.writeBytes(String.valueOf(lng) + "\n");
                        currentMessage = reader.readLine();
                        System.out.println(currentMessage);
                        //todo change for android
                        File file = new File("audiogram_image_large.gif");
                        int write;
                        in = new FileInputStream(file);
                        while ((write = in.read()) != -1) {
                            System.out.println(write);
                            out.write(write);
                        }

                    } else {
                        /*
                        byte[] b = new byte[1024];
                        int read;
                        FileOutputStream out = new FileOutputStream("img.gif");
                        while ((read = in.read()) != -1) {
                            out.write(read);
                        }
*/

                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }
}
