package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.Basic;
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


    public GameScreen(TheGame theGame){
        this.theGame=theGame;
        device=theGame.device.createShapeRenderer();
        shapeRenderer=device.shapeRenderer;
        spriteBatch=device.spriteBatch;
        viewport=device.viewport;
        viewport.apply(true);
        world=new World(new Vector2(0,-10f),true);
        box2DDebugRenderer=new Box2DDebugRenderer();

    }


    @Override
    public void show(){
        device.createOrthogonalTiledMapRenderer(viewport,device.assets.tiledMap);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
       // box2DDebugRenderer.render(world,device.camera.combined);
    }

}
