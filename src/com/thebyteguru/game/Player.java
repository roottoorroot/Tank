
package com.thebyteguru.game;

import com.thebyteguru.io.Input;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


public class Player extends Entity{ //Так как класс экстэндится от Ентити, должны быть реализованы все методы и конструктор предка;
    public static final int     SPRITE_SCALE =       16; //Размер спрайта;
    public static final int     SPRITES_PER_HEAD =   1; //Колличество спрайтов которые захватим;
    
    private enum Heading{ //Инумерация нужна для определения направления движеия танка;
        NORTH(0 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST(6 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH(4 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST(2 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE);
        
        private int x,y,h,w; //Для того чтобы определить из какой области вырезать спрайт, вводим координаты;
        Heading(int x, int y, int h, int w){
            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
        }
        
        protected BufferedImage texture(TextureAtlas atlas){ //Вырезаем нужный нам спрайт;
           return atlas.cut(x,y,h,w);
        }
        
    }
    private Heading                 heading;
    private Map<Heading , Sprite>   spriteMap; //В Джаве есть интересная фича, можно просто задать мапу и вырезать нужный объект, то есть соединить направление и картинку;
    private float                   scale; //Количество спрайтов, которые нужно выбрать; 
    private int                     speed; //Скорость;
    
    public Player(float x, float y, float scale,int speed, TextureAtlas atlas){
        super(EntityType.player,x,y);
        heading = Heading.NORTH; //Задали начальное положение;
        spriteMap = new HashMap<Heading,Sprite>();
        this.scale = scale; 
        this.speed = speed;
        //****Луп для того что бы связать картинку и направление;
        for(Heading h:Heading.values()){ //В Инумерации эта фича работает как луп foreach(Heading h in Heading);
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITES_PER_HEAD, SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, scale); //Для создания спрайта нужного размера;
            spriteMap.put(h,sprite); //Связали направление с картинкой, чтобы объект при изменении направления менял и картинку;
        }
    }
    
    
    @Override
    public void update(Input input) {
       float newX = x;
       float newY = y;
       
       if(input.getKey(KeyEvent.VK_UP)){
           newY -= speed;
           heading = Heading.NORTH;
       }else if(input.getKey(KeyEvent.VK_RIGHT)){
           newX += speed;
           heading = Heading.EAST;
       }else if(input.getKey(KeyEvent.VK_DOWN)){
           newY += speed;
           heading = Heading.SOUTH;
       }else if(input.getKey(KeyEvent.VK_LEFT)){
           newX -= speed;
           heading = Heading.WEST;
       }
       
       //****Проверка на положение объекта в нутри окна, чтобю не выходил заграницы экрана;
       //**** Game.WIDTH - SPRITE_SCALE * scale означает: не больше размеров окна минус размеры самого спрайта * колличество спрайтов;
       if(newX < 0){
           newX = 0;
       }else if(newX >= (Game.WIDTH - SPRITE_SCALE * scale)){
           newX = (Game.WIDTH - SPRITE_SCALE * scale);
       }
       
       if(newY < 0){
           newY = 0;
       }else if(newY >=(Game.HEIGHT - SPRITE_SCALE * scale)){
           newY = (Game.HEIGHT - SPRITE_SCALE * scale);
       }
       
       
       x = newX;
       y = newY;
    }

    @Override
    public void render(Graphics2D g) {
        spriteMap.get(heading).render(g, x, y); //отрисовка изображения;
    }
    
}
