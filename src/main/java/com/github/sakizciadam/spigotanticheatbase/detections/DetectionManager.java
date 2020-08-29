package com.github.sakizciadam.spigotanticheatbase.detections;

import com.github.sakizciadam.spigotanticheatbase.detections.movement.FlyA;

import java.util.ArrayList;
import java.util.List;

public class DetectionManager {
    private List<Detection> detections;

     public DetectionManager(){
         detections=new ArrayList<Detection>();
     }

    public void registerDetections(){
         if(!detections.isEmpty()) detections.clear();
        add(new FlyA("Fly A",Category.MOVEMENT));
    }

    public void add(Detection detection){
        detections.add(detection);
    }
}
