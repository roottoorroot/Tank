
package com.thebyteguru.game;

import com.thebyteguru.display.Display;
import com.thebyteguru.utils.Time;


public class Game implements Runnable{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Tanks";
    public static final int CLEAR_COLOR = 0xff000000;
    public static final int NUM_BUFFERS = 3;
    
    public static final float UPDATE_RATE = 60.0f; //Сколько обновлений в секунду будет производиться;
    public static final float UPDATE_INTERVAL = Time.SECOND/UPDATE_RATE; //Сколько времени будет проходить между обновлениями;
    public static final long IDLE_TIME = 1; //Для остановки процесса, чтобы другие процессы тоже могли действовать, а не спать;
    
    private boolean running; //Состояние игры;
    private Thread gameThread; //Процесс (новый поток) игры;
    
    public Game(){
        running = false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
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
        int fps = 0;
        int upd = 0;
        int updl = 0;
        
      
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
                if(render){
                    updl++;
                }else{
                    render = true;
                }
                delta--;
            }
            
            if(render){
                render();
                fps++;
            }else{
                try{
                    Thread.sleep(IDLE_TIME); //Если не рисуем, тогда засыпаем на 1 мл.с, чтобы дать другим процессам работать;
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            
            if(count >= Time.SECOND){
                Display.setTitle(TITLE + " |FPS: " + fps + " |UPD :" + upd + " |UPDL: " + updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
                
            }
            
            
        }
    }
    
    private void update(){
        
    }
    
    private void render(){
        
    }
    
    private void cleanUp(){
       Display.distroy();
    }
}
