
package com.thebyteguru.game;

import com.thebyteguru.display.Display;
import com.thebyteguru.io.Input;
import com.thebyteguru.utils.Time;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;


public class Game implements Runnable{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Tanks";
    public static final int CLEAR_COLOR = 0xff000000;
    public static final int NUM_BUFFERS = 3;
    
    public static final float UPDATE_RATE = 60.0f; //Сколько обновлений в секунду будет производиться;
    public static final float UPDATE_INTERVAL = Time.SECOND/UPDATE_RATE; //Сколько времени будет проходить между обновлениями;
    public static final long IDLE_TIME = 1; //Для остановки процесса, чтобы другие процессы тоже могли действовать, а не спать;
    public static final String ATLAS_FILE_NAME = "Texture_atlas.png";
    
    private boolean         running; //Состояние игры;
    private Thread          gameThread; //Процесс (новый поток) игры;
    private Graphics2D      graphics; //Чтобы рисовать объекты;
    private Input           input; //Управление объектами, точнее считывание нажатия клавиш;
    private TextureAtlas    atlas; //Класс для работы с загруженной картой изображений;
    private Player          player;
    
 
    
    public Game(){
        running = false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input); //Передаём нажаные клавиши;
        atlas = new TextureAtlas(ATLAS_FILE_NAME); //Для загрузки нужного изображения атлас_мап;
        player = new Player(300,300,2,3,atlas);
       
        
        
    }
    
    public synchronized void start(){
        if(running)
            return;
        
        running = true;
        gameThread = new Thread(this); //Создаём новый поток игры с текущими параметрами(this);
        gameThread.start(); //Запускаем процесс;
    }
    
    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try{
            gameThread.join(); //Чтобы правильно остановить процесс и дождаться завершения работы кода, используем join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        
        cleanUp();
    }
    
    public void run(){
        int fps = 0; //ФПС 
        int upd = 0; //Кол-во обновлений;
        int updl = 0; //Кол-во пропущенных обновлений;
        
      
        float delta = 0; //Количество апдэйтов, которые делаются;
        long count = 0; //Для вывода параметров фпс и апдэйт;
        long lastTime = Time.get(); //ТЕкущее время;
        while(running){
            long now = Time.get(); //Время в самый данный момент, всегда больше lastTime;
            long elapsedTime = now - lastTime; //Количество времени, которое прошло с момента последней итерации;
            lastTime = now; //Обновляем для следующего прохода;
            
            count += elapsedTime; //Раз в секунду обновления окна;
            
            delta += elapsedTime / UPDATE_INTERVAL; //Изменяем дельту;
            boolean render = false;
            //****Данный луп нужен для того, что бы игра вовремя обновлялась, если дельта > 1, после обновления уменьшаем дельту, т.е. контроль апдейтов; 
            while(delta > 1){
                update();
                upd++;
                if(render){ //Если рендер уже был, тогда увеличиваем updl на еденицу, этим мы покажем, что игра запаздывает;
                    updl++;
                }else{
                    render = true;
                }
                delta--;
            }
            
            if(render){ //Если были изменения, рисуем их;
                render();
                fps++;
            }else{
                try{
                    Thread.sleep(IDLE_TIME); //Если не рисуем, тогда засыпаем на 1 мл.с, чтобы дать другим процессам работать;
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            
            if(count >= Time.SECOND){ //Когда прошла одна секунда нашего времени, нужно отрисовать титл и вывести всю инфу;
                Display.setTitle(TITLE + " |FPS: " + fps + " |UPD :" + upd + " |UPDL: " + updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
                
            }
            
            
        }
    }
    
    private void update(){
        player.update(input);
    }
    
    private void render(){
        Display.clear();
        player.render(graphics);
        Display.swapBuffers();
        
    }
    
    private void cleanUp(){
       Display.distroy();
    }
}
