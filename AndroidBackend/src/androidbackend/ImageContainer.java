/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidbackend;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 *
 * @author Bogs
 */
public class ImageContainer {
    private BufferedImage image;
    private Date timestamp;
    private String comments;

    public ImageContainer(BufferedImage image, Date timestamp) {
        this.image = image;
        this.timestamp = timestamp;
    }
    

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
}
