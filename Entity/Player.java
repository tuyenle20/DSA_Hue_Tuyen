    package Entity ;

import Main.GamePanel; 
import Main.KeyHandler;
import Object.OBJ_Fireball;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public final class Player extends Entity {
    KeyHandler keyH; 
    
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    private BufferedImage left2;


    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();    
        solidArea.x = 6;
        solidArea.y = 12;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 24;
        solidArea.height = 24;
        //ATTACK AREA

        setDefaultValues();
        getPlayerImage();
        
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 18;
        worldY = gp.tileSize * 18;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 30;
        life = 6;
        maxMana = 9999;
        mana = maxMana;
        ammo =10;        
        projectile = new OBJ_Fireball(gp);
        
    }
    public void setDefaultPositions(){
        worldX = gp.tileSize * 18;
        worldY = gp.tileSize * 18;
        direction = "down";
    }
    public void restoreLifeAndMan(){
        life = 6;
        mana = maxMana;
        invincible = false;


    }
    public void getPlayerImage()
    {
        up1 = setup("/player/up1",gp.tileSize,gp.tileSize);   
        down1 = setup("/player/down1",gp.tileSize,gp.tileSize);
        left1 = setup("/player/left1",gp.tileSize,gp.tileSize);
        right1 = setup("/player/right1",gp.tileSize,gp.tileSize);
    }
    
    public void update() {
        if(attacking == true)
        {
                attacking();
        }
        else   if(keyH.upPressed==true|| keyH.downPressed ==true || keyH.leftPressed == true|| keyH.rightPressed == true || keyH.enterPressed == true){
                if (keyH.upPressed == true) {
                    direction = "up";
                }
                else if (keyH.downPressed == true) {
                    direction = "down";
                }
                else if (keyH.leftPressed == true) {
                    direction = "left";
                }
                else if (keyH.rightPressed == true) {
                    direction = "right";
                 }

                // CHECK TILE COLLISION
                 collisionOn = false;
                 gp.cChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                //CHECK NPC COLLISION
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

                //CHECK MONSTER COLLISION
                int monsterIndex = gp.cChecker.checkEntity(this, gp.ghost);
                contactMonster(monsterIndex);

                // CHECK EVENT 
                gp.eHandler.checkEvent();
                
                 // IF COLLISION IS FALSE, PLAYER CAN MOVE
                 if(collisionOn == false && keyH.enterPressed == false){
                    
                    switch(direction){
                        case"up":      worldY -= speed; break;
                            
                        case "down":   worldY += speed;  break;
                    
                        case "left":   worldX -= speed;  break;
                        
                        case "right":  worldX += speed;  break;
                    }
                 }

                 if(keyH.enterPressed == true  && attackCanceled == false){
                    gp.playSE(6);
                    attacking = true;
                    spriteCounter = 0;
                }
                
                attackCanceled = false;
                

                gp.keyH.enterPressed = false;
                spriteCounter++;
                if(spriteCounter >12){
                    if(spriteNum==1){
                        spriteNum=2;
                }
                    else if (spriteNum==2){
                        spriteNum =1;
                }
                    spriteCounter = 0;
                }
            }
            if(gp.keyH.shotKeyPressed == true && projectile.alive == false
            && shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
                // SET DEFAULT COORDINATES DIRECTION AND USER
                projectile.set(worldX, worldY, direction,true, this);
                //SUBTRACT THE COST (MANA, AMMO ETC.)
                projectile.subtractResource(this);
                // ADD IT TO THE LEFT
                gp.projectileList.add(projectile);
                shotAvailableCounter =0;
                gp.playSE(10);
            }
        
           //This needs to be outside if key if statement !
           if ( invincible == true ){
                invincibleCounter++;
                if(invincibleCounter >60){
                    invincible = false;
                    invincibleCounter = 0;

                }
           }
           if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
            }
            if(life > maxLife) {
                life = maxLife;
            }
            if(mana > maxMana) {
                mana = maxMana;
            }
            if(life <= 0){
                gp.gameState = gp.gameOverState;
                gp.playSE(11);
            }
                
    }
    public void attacking(){
        spriteCounter++;
        if ( spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter >5 && spriteCounter <= 25){
            spriteNum=2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeith = solidArea.height;
            //Adjust player's world x/y for the attackarea 
            switch ( direction){
                case "up" : worldY -= attackArea.height ;break;
                case "down" : worldY += attackArea.height ;break;
                case "left" : worldX -= attackArea.width ;break;
                case "right" : worldX += attackArea.width ;break;
            }
            // attack area becomes solid area
            solidArea.width = attackArea.width;
            solidArea.height= attackArea.height;
            // check monster collision with the updated world
            int monsterIndex = gp.cChecker.checkEntity(this, gp.ghost);
            damageMonster (monsterIndex, attack);

            // after checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.height = solidAreaHeith;
            solidArea.width = solidAreaWidth;
        }
        if(spriteCounter >25){
            spriteNum =1;
            spriteCounter = 0;
            attacking = false;
        }

    }
    public void pickUpObject (int i) {
        if(i != 999) {
        String objectName = gp.obj[i].name;
        
        switch (objectName) {
            case "Chest":
                    gp.ui.gameFinished = true ;
                    gp.stopMusic();
                    
                    break;
            }
        //PICKUP ONLY ITEMS
        if(gp.obj[i].type == type_pickupOnly) {
            gp.obj[i].use(this);
            gp.obj[i] = null;
        }
        // INVENTORY ITEMS
        else {
            String text;
        if( inventory.size() != maxInventorySize){
            inventory.add(gp.obj[i]);
            gp.playSE(i);
            text = "Got a "+ gp.obj[i].name +"!";
            
    
            switch (objectName) {
            case "Chest":
                text = null; 
            }
        }
        else {
            text = "You cannot carry any more";
            
        }
        gp.ui.addMessage(text);
        gp.obj[i] = null;
        }
        }
    }

    public void interactNPC(int i)
    {
        if(gp.keyH.enterPressed == true){
            if(i != 999){    
            attackCanceled = true;
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
                         
            }
        }
    }
    public void contactMonster(int i){
        if( i != 999){
            if ( invincible == false && gp.ghost[i].dying == false){

                gp.playSE(5);

                int damage = gp.ghost[i].attack - defense;
                if(damage < 0)
                {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
            }
            
        }
    }
    public  void damageMonster(int i, int attack){
        if( i!= 999){
            if(gp.ghost[i].invincible == false){

                gp.playSE(4);

                int damage = attack - gp.ghost[i].defense;
                if(damage < 0)
                {
                    damage = 0;
                }

                gp.ghost[i].life -= damage;
                gp.ui.addMessage(damage + " damage!");

                gp.ghost[i].invincible = true;
                gp.ghost[i].damageReaction();

                if(gp.ghost[i].life <=0 ){
                    gp.ghost[i].dying = true;
                    gp.ui.addMessage("killed the " + gp.ghost[i].name + "!");
            
                }
            }
        }
    }
    
    public void draw( Graphics2D g2) {
          BufferedImage image = null;
          int tempScreenX = screenX ;
          int tempScreenY = screenY;
          switch ( direction){
          case "up":
                image = up1;
                break;
          case "down":
            image = down1;
                break;
          case "left":
            image = left1;
                break;
          case "right":
            image = right1;
                break;
          }
          if ( invincible == true ){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));

          }
          g2.drawImage(image,tempScreenX,tempScreenY,null);
          // Reset alpha
          g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
    }
}






