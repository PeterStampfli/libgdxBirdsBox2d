package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utilities.Constants;

/**
 * Created by peter on 1/26/17.
 */

public class TiledObjectBodyBuilder {


    private static PolygonShape getRectangle(RectangleMapObject rectangleMapObject){
        Rectangle rectangle=rectangleMapObject.getRectangle();
        PolygonShape polygonShape=new PolygonShape();
        Vector2 center=new Vector2((rectangle.x+0.5f*rectangle.width)* Constants.METER,
                                    (rectangle.y+0.5f*rectangle.height)*Constants.METER);
        polygonShape.setAsBox(0.5f*rectangle.width*Constants.METER,
                               0.5f* rectangle.height*Constants.METER,
                                center,0.0f);
        return polygonShape;
    }

    public static void buildingBodies(TiledMap tiledMap, World world){
        MapObjects objects=tiledMap.getLayers().get("Physics_Buildings").getObjects();
        for (MapObject object:objects){
            PolygonShape rectangle=getRectangle((RectangleMapObject)object);
            BodyDef bodyDef=new BodyDef();
            bodyDef.type= BodyDef.BodyType.DynamicBody;
            Body body=world.createBody(bodyDef);
            body.createFixture(rectangle,1);
            rectangle.dispose();
        }
    }

    public static void buildFloor(TiledMap tiledMap, World world){
        MapObjects objects=tiledMap.getLayers().get("Physics_Floor").getObjects();
        for (MapObject object:objects){
            PolygonShape rectangle=getRectangle((RectangleMapObject)object);
            BodyDef bodyDef=new BodyDef();
            bodyDef.type= BodyDef.BodyType.StaticBody;
            Body body=world.createBody(bodyDef);
            body.createFixture(rectangle,0);
            rectangle.dispose();
        }
    }

    public static CircleShape getCircle(EllipseMapObject ellipseMapObject){
        Ellipse ellipse=ellipseMapObject.getEllipse();
        CircleShape circleShape=new CircleShape();
        circleShape.setRadius(ellipse.width*0.5f*Constants.METER);
        circleShape.setPosition(new Vector2((ellipse.x+ellipse.width*0.5f)*Constants.METER,
                (ellipse.y+ellipse.height*0.5f)*Constants.METER));
        return circleShape;
    }

    public static void buildBirdBodies(TiledMap tiledMap,World world){
        MapObjects objects=tiledMap.getLayers().get("Physics_Birds").getObjects();
        for (MapObject object:objects) {
            CircleShape circleShape=getCircle((EllipseMapObject) object);
            BodyDef bodyDef=new BodyDef();
            bodyDef.type=BodyDef.BodyType.DynamicBody;
            Body body=world.createBody(bodyDef);
            body.createFixture(circleShape,1);
            circleShape.dispose();
        }
    }

}
