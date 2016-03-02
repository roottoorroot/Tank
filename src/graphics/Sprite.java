
package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite {
    private SpriteSheet sheet; 
    private float       scale; //Насколько большим мы хотим делать объект, увеличение;
    
    public Sprite(SpriteSheet sheet, float scale){
        this.sheet = sheet;
        this.scale = scale;
    }
    
    public void render(Graphics2D g, float x, float y){
        BufferedImage image = sheet.getSprite(0);
        g.drawImage(image, (int)(x), (int)(y), (int)(image.getWidth()*scale), (int)(image.getHeight() * scale), null); //Отрисовка изображения с учётом размеров и позиции;
    }
}
