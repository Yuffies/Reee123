/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidbackend;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Bogs
 */
public class AndroidBackend {

    private static final String SENDIMAGE = "SEND";
    private static final String DOWNLOADIMAGES = "DOWN";
    private static final String OKAY = "OKAY\n";

    public static void main(String[] args) {
        HashMap<LatLng, LinkedList<ImageContainer>> images = new HashMap();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(9999);
            String currentMessage = "";
            while (true) {
                Socket clientSocket = serverSocket.accept();
                OutputStream out = clientSocket.getOutputStream();
                InputStream in = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                currentMessage = reader.readLine();
                System.out.println(currentMessage);

                if (currentMessage.equals(SENDIMAGE)) {
                    out.write(OKAY.getBytes());
                    double lat = Double.parseDouble(reader.readLine());
                    out.write(OKAY.getBytes());
                    double lng = Double.parseDouble(reader.readLine());
                    out.write(OKAY.getBytes());
                    BufferedImage image = ImageIO.read(in);
                    LatLng latLng = new LatLng(lat, lng);
                    ImageContainer ic = new ImageContainer(image, new Date());
                    if (images.containsKey(latLng)) {
                        if (images.get(latLng) == null) {
                            LinkedList<ImageContainer> list = new LinkedList();
                            list.add(ic);
                            images.put(latLng, list);
                        } else {
                            LinkedList<ImageContainer> list = images.get(latLng);
                            list.add(ic);
                            images.put(latLng, list);
                        }
                    } else {
                        LinkedList<ImageContainer> list = new LinkedList();
                        list.add(ic);
                        images.put(latLng, list);
                    }
                    FileOutputStream outTemp = new FileOutputStream("img.gif");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(images.get(latLng).get(0).getImage(), "gif", baos);
                    byte[] bytes = baos.toByteArray();
                    for(byte b : bytes) {
                        System.out.println(b);
                    }
                    outTemp.write(bytes);
                    outTemp.close();

                } else if (currentMessage.equals(DOWNLOADIMAGES)) {
                    File file = new File("audiogram_image_large.gif");
                    int write;
                    in = new FileInputStream(file);
                    while ((write = in.read()) != -1) {
                        System.out.println(write);
                        out.write(write);
                    }

                } else {

                }
                clientSocket.close();
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

}
