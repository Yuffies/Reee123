/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidbackend;

import java.io.OutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author Bogs
 */
public class AndroidBackend {

    private static final String SENDIMAGE = "SEND";
    private static final String DOWNLOADIMAGES = "DOWNLOAD";
    private static final String OKAY = "OKAY";
    private static final String OKAYSEND = "OKAY\n";
    private static final String EMPTY = "EMPTY\n";
    private static final String DONE = "DONE\n";
    private static final String SENDING = "SENDING\n";

    public static void main(String[] args) throws IOException {
        HashMap<LatLng, LinkedList<ImageContainer>> images = new HashMap();
        ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                try {
                String currentMessage = "hest";
                Socket clientSocket = serverSocket.accept();
                OutputStream out = clientSocket.getOutputStream();
                InputStream in = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                currentMessage = reader.readLine();
                if (currentMessage.equals(SENDIMAGE)) {
                    out.write(OKAYSEND.getBytes());
                    double lat = Double.parseDouble(reader.readLine());
                    out.write(OKAYSEND.getBytes());
                    double lng = Double.parseDouble(reader.readLine());
                    out.write(OKAYSEND.getBytes());
                    BufferedImage image = ImageIO.read(in);
                    LatLng latLng = new LatLng(lat, lng);
                    ImageContainer ic = new ImageContainer(image, new Date());
                    if (images.containsKey(latLng)) {
                            LinkedList<ImageContainer> list = images.get(latLng);
                            list.add(ic);
                            Date compareDate = new Date();
                            for (ImageContainer imageContainer : list) {
                                if ((imageContainer.getTimestamp().getTime() + 3600000) <= compareDate.getTime()) {
                                    list.remove(imageContainer);
                                }
                            }
                            images.put(latLng, list);
                    } else {
                        LinkedList<ImageContainer> list = new LinkedList();
                        list.add(ic);
                        Date compareDate = new Date();
                        images.put(latLng, list);
                        for (ImageContainer imageContainer : list) {
                            if ((imageContainer.getTimestamp().getTime() + 3600000) <= compareDate.getTime()) {
                                list.remove(imageContainer);
                            }
                        }
                    }

                } else if (currentMessage.equals(DOWNLOADIMAGES)) {
                    out.write(OKAYSEND.getBytes());
                    double lat = Double.parseDouble(reader.readLine());
                    out.write(OKAYSEND.getBytes());
                    double lng = Double.parseDouble(reader.readLine());
                    LatLng latLng = new LatLng(lat, lng);
                    if (images.get(latLng) != null) {
                        LinkedList<ImageContainer> list = images.get(latLng);
                        out.write(OKAYSEND.getBytes());
                        reader.readLine();
                        for(ImageContainer imageContainer : list) {
                            out.write(SENDING.getBytes());
                            reader.readLine();
                            ImageIO.write(imageContainer.getImage(), "jpg", out);
                        }
                        out.write(DONE.getBytes());
                        
                    } else {
                        out.write(EMPTY.getBytes());
                        reader.readLine();
                    }  

                } else {

                }
                clientSocket.close();
                    System.out.println("REQUEST HANDLED MAYBE");
                } catch (Exception ex) {
        }
            }
    }
}
