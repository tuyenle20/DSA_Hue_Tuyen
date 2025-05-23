package Main;

import Entity.NPC_OldMan;
import Object.OBJ_Chest;
import Object.OBJ_Heart;
import monster.BlueGhost;
import monster.OrangeGhost;
import monster.PinkGhost;
import monster.RedGhost;
import monster.WhiteGhost;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    } 
    public void setObject() {
        int i = 0;
        
        gp.obj[4] = new OBJ_Heart(gp);
        gp.obj[4].worldX = 29 * gp.tileSize;
        gp.obj[4].worldY = 40 * gp.tileSize;
    }

    public void setNPC()       
    {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
    public void setGhost(){

        int i = 0;

        gp.ghost[i] = new PinkGhost(gp);
        gp.ghost[i].worldX =gp.tileSize*23;
        gp.ghost[i].worldY =gp.tileSize*36;
        i++;

        gp.ghost[i] = new PinkGhost(gp);
        gp.ghost[i].worldX =gp.tileSize*42;
        gp.ghost[i].worldY =gp.tileSize*5;
        i++;

        gp.ghost[i] = new PinkGhost(gp);
        gp.ghost[i].worldX =gp.tileSize*30;
        gp.ghost[i].worldY =gp.tileSize*42;
        i++;

        gp.ghost[i] = new RedGhost(gp);
        gp.ghost[i].worldX =gp.tileSize*16;
        gp.ghost[i].worldY =gp.tileSize*40;
        i++;

        gp.ghost[i] = new RedGhost(gp);
        gp.ghost[i].worldX =gp.tileSize*47;
        gp.ghost[i].worldY =gp.tileSize*11;
        i++;

        gp.ghost[i] = new RedGhost(gp);        
        gp.ghost[i].worldX =gp.tileSize*4;
        gp.ghost[i].worldY =gp.tileSize*2;
        i++;

        gp.ghost[i] = new BlueGhost(gp);        
        gp.ghost[i].worldX =gp.tileSize*21;
        gp.ghost[i].worldY =gp.tileSize*33;
        i++;

        gp.ghost[i] = new BlueGhost(gp);       
        gp.ghost[i].worldX =gp.tileSize*31;
        gp.ghost[i].worldY =gp.tileSize*10;
        i++;

        gp.ghost[i] = new BlueGhost(gp);        
        gp.ghost[i].worldX =gp.tileSize*48;
        gp.ghost[i].worldY =gp.tileSize*15;
        i++;

        gp.ghost[i] = new OrangeGhost(gp);
        gp.ghost[i].worldX =gp.tileSize*24;
        gp.ghost[i].worldY =gp.tileSize*47;
        i++;

        gp.ghost[i] = new OrangeGhost(gp);
        gp.ghost[i].worldX =gp.tileSize*11;
        gp.ghost[i].worldY =gp.tileSize*22;
        i++;

        gp.ghost[i] = new OrangeGhost(gp);
        gp.ghost[i].worldX =gp.tileSize*37;
        gp.ghost[i].worldY =gp.tileSize*35;
        i++;

        gp.ghost[i] = new WhiteGhost(gp);
        gp.ghost[i].worldX =gp.tileSize*36;
        gp.ghost[i].worldY =gp.tileSize*35;
        i++;
    }
}
