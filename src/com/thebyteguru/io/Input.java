
package com.thebyteguru.io;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Input extends JComponent{
    private boolean[] map; //ASCII значения клавишь от 0-255;
    
    
    
    public Input(){
        map = new boolean[256]; //Создаём новый массив для отслеживания нажатия кнопок;
        for(int i = 0; i < map.length; i++){
            final int KEY_CODE = i; //Чтобы использовать вне этого класса;
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, false), i * 2); //Передаём нажатую клавишу и присваиваем ей имя i*2;
            getActionMap().put(i * 2, new AbstractAction(){ // Здесь делаем передачу кнопки и привязывания на нёё экшена;
                @Override
                public void actionPerformed(ActionEvent arg0){ //Говорим, что клавиша с нашим именем нажата, то есть true;
                    map[KEY_CODE] = true;
                }
        });
            
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, true), i * 2 + 1); //Всё тоже самое, только говорим, что кнопка отпущена;
            getActionMap().put(i * 2 + 1, new AbstractAction(){
                @Override
                public void actionPerformed(ActionEvent arg0){ //говорим, что клавиша с нашим именем больше не нажата, то есть: false;
                    map[KEY_CODE] = false;
                }
        });
        }
    }
    public boolean[] getMap(){ //Возвращаем не сам массив, а его копию, чтобы избежать опсного изменения массива;
        return Arrays.copyOf(map, map.length);
    }
    
    public boolean getKey(int keyCode){ //Возвращаем true или false нажатия клавиши по её ASCII коду;
        return map[keyCode];
    }
}
    

