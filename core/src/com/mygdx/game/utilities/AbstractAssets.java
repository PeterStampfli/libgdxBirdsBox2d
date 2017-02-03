package com.mygdx.game.utilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by peter on 1/22/17.
 */

public class AbstractAssets {
    AssetManager assetManager;
    public String soundFileType="wav";
    public String musicFileType="mp3";
    private HashMap<String,Sound> sounds=new HashMap<String, Sound>();
    private HashMap<String,Music> music=new HashMap<String, Music>();
    private Sound noSound=null;
    private Music noMusic=null;
    private boolean soundIsOn=true;

    private boolean hasTmxMapLoader=false;

    // general

    public void setAssetManager(AssetManager assetManager){
        this.assetManager=assetManager;
    }

    public void finishLoading(){
        assetManager.finishLoading();
    }

    public boolean update(){
        return assetManager.update();
    }

    public float getProgress(){
        return assetManager.getProgress();
    }

    public void loadTexture(String name){
        assetManager.load(name, Texture.class);
    }

    public void loadSound(String name){
        assetManager.load(name, Sound.class);
    }

    public  void loadMusic(String name){
        assetManager.load(name, Music.class);
    }

    // special items

    public void loadTmxMap(String name){
        if (!hasTmxMapLoader) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            hasTmxMapLoader=true;
        }
        assetManager.load(name,TiledMap.class);
    }
    // sounds and music

    public void setSoundIsOn(boolean value){
        soundIsOn=value;
    }

    // sounds
    public void addSounds(String... names){
        for (String name: names) {
            assetManager.load(name + "." + soundFileType, Sound.class);
            sounds.put(name, noSound);
        }

    }

    public void getAllSounds(){
        Set<String> names=sounds.keySet();
        for (String name:names) {
            sounds.put(name,assetManager.get(name+"."+soundFileType,Sound.class));
        }
    }

    public void playSound(String name){
        if (soundIsOn){
            sounds.get(name).play();
        }
    }

    // music

    // sounds
    public void addMusic(String... names){
        for (String name: names) {
            assetManager.load(name + "." + musicFileType, Music.class);
            music.put(name, noMusic);
        }
    }

    public void getAllMusic(){
        Set<String> names=music.keySet();
        for (String name:names) {
            music.put(name,assetManager.get(name+"."+musicFileType,Music.class));
        }
    }

    public Music getMusic(String name){
        return music.get(name);
    }


}
