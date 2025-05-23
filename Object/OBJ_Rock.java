package Object;
import Entity.Entity;
import Entity.Projectile;
import Main.GamePanel;
import java.awt.Color;

public class OBJ_Rock extends Projectile {
    GamePanel gp;
    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp= gp;

        name = "Rock";
        speed = 7;
        maxLife = 80;
        life = maxLife;
        attack = 1;
        useCost = 1;
        alive = false;
        getImage();     
    }
    public void getImage() {
        up1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        //up2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        //down2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        //left2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        //right2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);

    }
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if(user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    public void subtractResource(Entity user) {
        user.ammo -= useCost;
    }

    public Color getParticleColor(){
        Color color = new Color(255, 250, 240);   // chỉnh màu snowball khi ném trúngtrúng nvat   
        return color;
   }
   
    public int getParticleSize(){
           int size =6;   
           return size;
       }
   
       public int getParticleSpeed(){
           int speed =1;
           return speed;
       }
   
       public int getParticleMaxLife(){
           int maxLife = 20;
           return maxLife;
       }
    
}

