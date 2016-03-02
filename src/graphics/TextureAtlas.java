
package graphics;

import com.thebyteguru.utils.ResourceLoader;
import java.awt.image.BufferedImage;


public class TextureAtlas {
    BufferedImage image;
    
    public TextureAtlas(String imageName){
        image = ResourceLoader.loadImage(imageName);
    }
    
    public BufferedImage cut(int x, int y, int h, int w){
        return image.getSubimage(x, y, h, w);
    }
}
