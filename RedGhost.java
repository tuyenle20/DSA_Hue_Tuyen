package monster;

import Entity.Entity;
import Main.GamePanel;
import Object.OBJ_Heart;
import Object.OBJ_Rock;
import java.util.Random;

public class RedGhost extends Entity {
    GamePanel gp;
    public RedGhost ( GamePanel gp){
        super(gp);
        this.gp=gp;
        type =type_monster;
        name = "Red Ghost";
        speed = 2;
        maxLife = 20;
        life = maxLife;
        attack = 1;
        defense = 0;
        // exp = 2;
        projectile = new OBJ_Rock(gp);

        solidArea.x =3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height= 30;
        solidAreaDefaultX= solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){
        up1 = setup("/monster/redghost",gp.tileSize,gp.tileSize);
        down1 = setup("/monster/redghost",gp.tileSize,gp.tileSize);
        left1 = setup("/monster/redghost",gp.tileSize,gp.tileSize);
        right1 = setup("/monster/redghost",gp.tileSize,gp.tileSize);
    }
    
    public void setAction()
    {

        actionLockCounter++;
        if(actionLockCounter == 120)
        {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
    
            if(i <= 25)
            {
                direction = "up";
            }
            if(i > 25 && i <= 50)
            {
                direction = "down";
            }
            if(i > 50 && i <= 75)
            {
                direction = "left";
            }
            if(i > 75 && i <= 100)
            {
                direction = "right";
            }

            actionLockCounter = 0;
        }
        int i = new Random().nextInt(100)+1;
        if(i > 99 && projectile.alive == false && shotAvailableCounter ==30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }

    public void damageReaction()
    {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
    public void checkDrop() {
        dropItem(new OBJ_Heart(gp));
    }
}





