/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidbackend;

import java.awt.image.BufferedImage;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import javax.imageio.ImageIO;

class TCPClient {

    private String currentMessage;
    private static InputStream in;
    private static final String SENDIMAGE = "SEND\n";
    private static final String OKAY = "OKAY";
    private static final String OKAYSEND = "OKAY\n";
    private static final String DOWNLOADIMAGES = "DOWNLOAD\n";
    private static final String EMPTY = "EMPTY";
    private static final String SENDING = "SENDING";
    private static final String DONE = "DONE";
    private static final String RECIEVED = "RECEIVED\n";
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        TCPClient cp = new TCPClient();
        cp.ConnectAndSendImage();
        cp.ConnectAndDownloadImages();
        cp.ConnectAndSendImage();
        cp.ConnectAndDownloadImages();
        cp.ConnectAndSendImage();
        cp.ConnectAndDownloadImages();
        cp.ConnectAndSendImage();
        cp.ConnectAndDownloadImages();
        cp.ConnectAndSendImage();
        cp.ConnectAndDownloadImages();
        cp.ConnectAndSendImage();
        cp.ConnectAndDownloadImages();
        cp.ConnectAndSendImage();
        cp.ConnectAndDownloadImages();
        cp.ConnectAndSendImage();
        cp.ConnectAndDownloadImages();
    }

    public void ConnectAndSendImage() throws InterruptedException {
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
                    out.write(SENDIMAGE.getBytes());
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
                        File file = new File("DB_onCreate.PNG");
                        int write;
                        in = new FileInputStream(file);
                        while ((write = in.read()) != -1) {
                            out.write(write);
                        }

                    } else {

                    }
                    in.close();
                    out.close();
                    dos.close();
                    reader.close();
                    client.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        t.join();

    }
    
    public LinkedList<BufferedImage> ConnectAndDownloadImages() throws InterruptedException {
        LinkedList<BufferedImage> images = new LinkedList();
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
                    out.write(DOWNLOADIMAGES.getBytes());
                    currentMessage = reader.readLine();
                    if (currentMessage.equals(OKAY)) {
                        dos.writeBytes(String.valueOf(lat) + "\n");
                        currentMessage = reader.readLine();
                        dos.writeBytes(String.valueOf(lng) + "\n");
                        currentMessage = reader.readLine();
                        if(currentMessage.equals(OKAY)) {
                        out.write(OKAYSEND.getBytes());
                        currentMessage = reader.readLine();
                        while(currentMessage.equals(SENDING)) {
                            out.write(OKAYSEND.getBytes());
                            BufferedImage image = ImageIO.read(in);
                            images.add(image);
                            out.write(RECIEVED.getBytes());
                            currentMessage = reader.readLine();
                        }
                        } else {
                            //No images on server for lat lng deal with it
                        }
                        

                    } else {

                    }
                    in.close();
                    out.close();
                    dos.close();
                    reader.close();
                    client.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        t.join();
        return images;
    }
}
