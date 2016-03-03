
package com.thebyteguru.game;

import com.thebyteguru.io.Input;
import java.awt.Graphics2D;


public abstract class Entity { //Класс будет абстракт, так как мы не будем из него ничего создавать, а другие классы, будут экстэндить его;

       public final EntityType type; //Наш Ентити тайп, который содержит названия объектов: player, boolet, tank...;
       
       protected float x; //Расположение объектов на нашей плоскости;
       protected float y; //Расположение объектов на нашей плосеости;
       
       public Entity(EntityType type, float x, float y){
           this.type = type;
           this.x = x;
           this.y = y;
       }
       
       public abstract void update(Input input); //Перехват нажатия клавишь ввода;
       public abstract void render(Graphics2D g); //Отрисовка изображений;
       
    
}
