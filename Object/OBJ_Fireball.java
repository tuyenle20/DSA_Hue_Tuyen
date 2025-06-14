package Object;
import Entity.Entity;
import Entity.Projectile;
import Main.GamePanel;
import java.awt.Color;

public class OBJ_Fireball extends Projectile{
    GamePanel gp;
    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp= gp;
        name = "Fireball";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 6;
        useCost = 1;
        alive = false;
        getImage();     
    }
    public void getImage() {
        up1 = setup("/projectile/fireball_up_1",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/fireball_down_1",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/fireball_left_1",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/fireball_right_1",gp.tileSize,gp.tileSize);
    }
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if(user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    public Color getParticleColor(){
        Color color = new Color(70, 130, 180);  
        
        return color;
   }
   
    public int getParticleSize(){
           int size =4;   
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
