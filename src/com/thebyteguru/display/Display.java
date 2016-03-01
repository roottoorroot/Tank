package com.thebyteguru.display;

import com.sun.java.swing.plaf.windows.resources.windows;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public abstract class Display {
    private static boolean created = false; //Контроль создания окна;
    private static JFrame window; //Наше окно из библиотеки;
    private static Canvas content; //Свободный член, нужен для описания характеристик окна;
    private static BufferedImage buffer; //Наша картинка; 
    private static int[] bufferData; //В этот массив мы запихаем наше изображение;
    private static Graphics bufferGraphics; //Для рисования и отображения получившихся картинок;
    private static int clearColor; //Цвет для закрашивания; нашего поля;
    private static BufferStrategy bufferStrategy; //Для мультибуфферизации;
    //temp
    private static float delta = 0; //Для тестирования движения;
    //temp end
    
    public static void create(int width, int height, String title, int _clearColor, int numBuffers){
        //Если окно уже создано, не зоздаём еще одно, завершаем функцию;
        if(created){ 
            return;
        } 
        window = new JFrame(title); //Создаём окно и передаём название окну;
        content = new Canvas();//Просто так не можем запихать длинну и ширину, для этого создаём объект Дименшон;
        Dimension size = new Dimension(width, height); //Объект для передачи Канвасу размеров;
        content.setPreferredSize(size); //Передаём в конвас значения окна;
        //****Далее задаём параметры окна****
        window.setResizable(false); //Запрет на маштабирование окна пользователем;
        window.getContentPane().add(content);//Отправляем окну контент и делаем так, чтобы контент не выходил за рамки окна(рамки с крестиками и всякими плюхами);
        window.pack();// Именно *.pack() позволяет упаковать все параметры и передать их окну***********************
        window.setVisible(true); //Обязательно ставим видимость окна;
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Теперь наше окно полностью закрыват программу при нажатии на крестик окна;
        window.setLocationRelativeTo(null); //Позиция созданного окна по дифолту;
        
        buffer = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB); //Для хранения цвета выбираем тип: TYPE_INT_ARGB;
        bufferData = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData(); //Данна конструкция нужна для перехвата массива с цветами и дальнейшего издевательства с ним;
        bufferGraphics = buffer.getGraphics(); //Достаем все параметры и цвета и распооложение;
        ((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Включаем сглаживание и наш объект уже так не выделяется на фоне;
        clearColor = _clearColor; //Инициализируем цвет заполнения;
        
        content.createBufferStrategy(numBuffers); //Для мультибуфферизации;
        bufferStrategy = content.getBufferStrategy();
        
        
        created = true;
    }
    
    public static void clear(){
        Arrays.fill(bufferData,clearColor); //Функция заполняет весь переданный ей массив выбранным значением, в нашем случае, это цвет clearColor;
    }
    
    public static void swapBuffers(){
        Graphics g = bufferStrategy.getDrawGraphics(); //Вытаскиваем всё то что есть у нас сейчас в Канвасе через контент;
        bufferStrategy.show();
        g.drawImage(buffer, 0, 0, null); //Запихиваем в графикс текущий объект в буфере: Шарик;
    }
    
    public static Graphics2D getGraphics(){
        return (Graphics2D)bufferGraphics;
    }
    
    public static void distroy(){
        if(!created) return;
        window.dispose();
    }
    
    public static void setTitle(String title){
        window.setTitle(title);
    }
}
