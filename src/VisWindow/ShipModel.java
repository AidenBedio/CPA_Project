package VisWindow;

import DBWindow.Ship;
import com.sun.javafx.geom.RoundRectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class ShipModel extends Rectangle{

    private final RoundRectangle2D shape = new RoundRectangle2D();

    private Ship shipData;

    ShipModel(Ship data, double x, int y, double w, double h){
        System.out.println("HELLOOO I WAS CREATED");
        this.shipData = data;
        //rec = new Rectangle(x,y,w,h);
        //rec.setStroke(Color.GREEN);

        setWidth(w);
        setHeight(h);
        setX(x);
        setY(y);
    }

//    public Rectangle getRec(){
//        return rec;
//    }

    public Ship getShip(){
        return shipData;
    }
}
