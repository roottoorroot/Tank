package com.thebyteguru.display;

import com.sun.java.swing.plaf.windows.resources.windows;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;

public abstract class Display {
    private static boolean kreated = false; //Контроль создания окна;
    private static JFrame window; //Наше окно из библиотеки;
    private static Canvas content; //Свободный член, нужен для описания характеристик окна;
    
    public static void create(int width, int height, String title){
        //Если окно уже создано, не зоздаём еще одно, завершаем функцию;
        if(kreated){ 
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
    }
    
}
