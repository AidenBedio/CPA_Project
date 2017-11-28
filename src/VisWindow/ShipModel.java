package VisWindow;

import DBWindow.Ship;
import com.sun.javafx.geom.RoundRectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;


public class ShipModel extends ImageView{

    private final RoundRectangle2D shape = new RoundRectangle2D();

    private Ship shipData;

    ShipModel(Ship data, double x, int y, double w, double h){
        System.out.println("HELLOOO I WAS CREATED");
        this.shipData = data;
        //rec = new Rectangle(x,y,w,h);
        //rec.setStroke(Color.GREEN);

//        setWidth(w);
//        setHeight(h);
        //Image image = new Image(new File("za.png").toURI().toString());
        Image image = new Image("ship.png");
        setImage(image);
        setX(10);
        setY(10);
        setRotate(45);
    }

//    public Rectangle getRec(){
//        return rec;
//    }

    public Ship getShip(){
        return shipData;
    }
}
