package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Chest extends Entity {

    public OBJ_Chest (GamePanel gp) {
        super(gp);
        name = "Chest";
        down1 = setup("/objects/chest",gp.tileSize,gp.tileSize);
       
        // try {
        //     image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")); 
        //     uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
}
