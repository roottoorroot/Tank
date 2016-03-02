
package graphics;

import java.awt.image.BufferedImage;


public class SpriteSheet {
    private BufferedImage sheet;
    private int spriteCount; //Количество индивидуальных изображений;
    private int scale; //Размер одного спрайта в пикселях;
    private int spritesInWidth; //Ширина спрайта;
    
    public SpriteSheet(BufferedImage sheet, int spriteCount, int scale){
        this.sheet = sheet;
        this.spriteCount = spriteCount;
        this.scale = scale;
        
        this.spritesInWidth = sheet.getWidth() / scale; //Находим ширину спрайта;
    }
    
    public BufferedImage getSprite(int index){
        index = index % spriteCount; //Чтобы мы могли запускать луп, так как у нас на один спрайт 8 изображений, то 9 = 1;
        int x = index % spritesInWidth * scale; //Координата для начала вырезания с учётом сдвига на размер спрайта;
        int y = index / spritesInWidth * scale; //Координата для начала вырезания с учётом сдвига на размер спрайта;
        
        return sheet.getSubimage(x, y, scale, scale); //Вырезание спрайта с учётом его размеров и позиции;
    }
}
