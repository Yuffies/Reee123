/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidbackend;

/**
 *
 * @author Bogs
 */
public class LatLng {
    private double latitude;
    private double longitude;
    
    public LatLng(double lat, double lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    @Override
    public boolean equals(Object obj) {
        LatLng other = (LatLng) obj;
        return this.latitude == other.latitude && this.longitude == other.longitude;
    }
    
    
}
