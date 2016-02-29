package com.thebyteguru.display;

import com.sun.java.swing.plaf.windows.resources.windows;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
    //temp
    private static float delta = 0; //Для тестирования движения;
    //temp end
    
    public static void create(int width, int height, String title, int _clearColor){
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
        clearColor = _clearColor; //Инициализируем цвет заполнения;
        
        created = true;
    }
    
    public static void clear(){
        Arrays.fill(bufferData,clearColor); //Функция заполняет весь переданный ей массив выбранным значением, в нашем случае, это цвет clearColor;
    }
    
    public static void render(){
        bufferGraphics.setColor(new Color(0xff0000ff)); //Задаем цвет объекта, который хотим передать;
        bufferGraphics.fillOval((int)(350 + Math.sin(delta) * 200), 250, 100, 100); //Рисуем круг;
        
        delta += 0.02f; //Для движения Шарика;
    }
    
    public static void swapBuffers(){
        Graphics g = content.getGraphics(); //Вытаскиваем всё то что есть у нас сейчас в Канвасе через контент;
        g.drawImage(buffer, 0, 0, null); //Запихиваем в графикс текущий объект в буфере: Шарик;
    }
    
    
}
