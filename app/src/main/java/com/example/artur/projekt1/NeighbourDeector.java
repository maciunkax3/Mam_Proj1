package com.example.artur.projekt1;

import android.graphics.Point;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class NeighbourDeector implements LocationListener {
    Map<String, PointF> coordinates = null;
    Map<String, Float> angles = null;
    boolean locationReaded = false;
    public NeighbourDeector(){
        PointF myCoordinate = new PointF((float)54.356696, (float)18.577544);
        if(coordinates==null){
            coordinates = new HashMap<String, PointF>();
            angles = new HashMap<String, Float>();
            coordinates.put("MyLocation", myCoordinate);
            getNeighboursCoordinates();
            getAngles();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(!locationReaded) {
            coordinates.remove("MyLocation");
            coordinates.put("MyLocation", new PointF((float) location.getLatitude(), (float) location.getLongitude()));
            angles.clear();
            getAngles();
            locationReaded = true;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    private void getNeighboursCoordinates(){
        coordinates.put("Russia", new PointF((float)54.454984, (float)19.644219));
        coordinates.put("Lithuania", new PointF((float)54.363221, (float)22.791186));
        coordinates.put("Belarus", new PointF((float)53.956196, (float)23.514964));
        coordinates.put("Ukraine", new PointF((float)51.507485, (float)23.618568));
        coordinates.put("Slovakia", new PointF((float)49.087868, (float)22.565807));
        coordinates.put("Czech", new PointF((float)49.517086, (float)18.850968));
        coordinates.put("Germany", new PointF((float)50.868774, (float)14.826662));
        coordinates.put("Sea", new PointF((float)53.928690, (float)14.226226));
    }
    private void getAngles(){
        for (Map.Entry<String, PointF> entry : coordinates.entrySet())
        {
            angles.put(entry.getKey(), getAngle(coordinates.get("MyLocation"), coordinates.get(entry.getKey())));
        }

    }
    private float getAngle(PointF p1, PointF p2){
        PointF p3 = new PointF(90, p1.y);
        float p1p2 = (float) Math.sqrt((float) (Math.pow((p2.y - p1.y), 2) + Math.pow((p2.x - p1.x), 2)));
        float p1p3 = (float) Math.sqrt((float) (Math.pow((p3.y - p1.y), 2) + Math.pow((p3.x - p1.x), 2)));
        float p2p3 = ((p3.y-p1.y)*(p2.y-p1.y))+(((p3.x-p1.x)*(p2.x-p1.x)));
        double cos = p2p3/(p1p2*p1p3);
        float deg = (float) Math.toDegrees(Math.acos(cos));
        if(p1.y>p2.y)
            deg=360 - deg;
        return deg;
    }
    public String getCountry(float angle){
        float min = 360;
        String country = "Sea";
        for (Map.Entry<String, Float> entry : angles.entrySet())
        {
            float diff = angle - angles.get(entry.getKey());
            if(diff > 0 && diff < min){
                min = diff;
                country = entry.getKey();
            }
        }
        if (min == 360)
            country = "Sea";

        return country;
    }
    public int getCountryId(float angle){
        String name = getCountry(angle);
        int id =0;
        switch (name){
            case "Russia":
                id = R.raw.rosja;
                break;
            case "Lithuania":
                id = R.raw.litwa;
                break;
            case "Belarus":
                id = R.raw.bialorus;
                break;
            case "Ukraine":
                id = R.raw.ukraina;
                break;
            case "Slovakia":
                id = R.raw.slowacja;
                break;
            case "Czech":
                id = R.raw.czechy;
                break;
            case "Germany":
                id = R.raw.niemcy;
                break;
            case "Sea":
                id = R.raw.morze;
                break;

        }
        return id;
    }
}
