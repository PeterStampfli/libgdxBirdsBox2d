package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.Constants;
import com.mygdx.game.utilities.Device;

/**
 * Created by peter on 1/19/17.
 */

public class GameScreen extends ScreenAdapter {

    private TheGame theGame;
    private Device device;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthographicCamera box2dCamera;


    public GameScreen(TheGame theGame){
        this.theGame=theGame;
        device=theGame.device.createShapeRenderer();
        shapeRenderer=device.shapeRenderer;
        spriteBatch=device.spriteBatch;
        viewport=device.viewport;
        viewport.apply(true);
        world=new World(new Vector2(0,-10f),true);
        box2DDebugRenderer=new Box2DDebugRenderer();
        //box2DDebugRenderer.SHAPE_KINEMATIC=Color.RED;
        box2dCamera=new OrthographicCamera(Constants.WORLD_WIDTH*Constants.METER,Constants.WORLD_HEIGHT*Constants.METER);

    }

    private void createBullet(){
        CircleShape circleShape=new CircleShape();
        circleShape.setRadius(0.5f);
        circleShape.setPosition(new Vector2(3,6));
        BodyDef bodyDef=new BodyDef();
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        Body body=world.createBody(bodyDef);
        body.createFixture(circleShape,1);
        body.setLinearVelocity(10,6);
        circleShape.dispose();
    }

    @Override
    public void show(){
        device.createOrthogonalTiledMapRenderer(viewport,device.assets.tiledMap);
        TiledObjectBodyBuilder.buildingBodies(device.assets.tiledMap,world);
        TiledObjectBodyBuilder.buildFloor(device.assets.tiledMap,world);
        TiledObjectBodyBuilder.buildBirdBodies(device.assets.tiledMap,world);
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX,int screenY,int p,int bu){
                createBullet();
                return true;
            }
        });

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        box2dCamera.position.set(0.5f*Constants.WORLD_WIDTH*Constants.METER,0.5f*Constants.WORLD_HEIGHT*Constants.METER,0f);
        box2dCamera.update();
    }

    public void drawDebug(){
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.end();
    }

    public void draw(){
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        device.orthogonalTiledMapRenderer.render();
        spriteBatch.begin();

        spriteBatch.end();
    }

    @Override
    public void render(float delta){

        world.step(delta,6,2);

        viewport.apply(true);

        Basic.clearBackground(Color.CYAN);

        draw();
        drawDebug();
        box2DDebugRenderer.render(world,box2dCamera.combined);
    }

}
