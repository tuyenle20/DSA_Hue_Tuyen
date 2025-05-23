package Entity;

import Main.GamePanel;
import java.util.Random;

public class NPC_OldMan extends Entity
{
    public NPC_OldMan(GamePanel gp)
    {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();

    }
    public void getImage()
    {
        up1 = setup("/npc/oldman_up_1",gp.tileSize,gp.tileSize);
        down1 = setup("/npc/oldman_down_1",gp.tileSize,gp.tileSize);
        left1 = setup("/npc/oldman_left_1",gp.tileSize,gp.tileSize);
        right1 = setup("/npc/oldman_right_1",gp.tileSize,gp.tileSize);
    }
    public void setDialogue()
    {
        dialogues[0] = "Hello!";
        dialogues[1] = "Welcome you come to the new world pacman ?";
        dialogues[2] = "Your work is defeat all monster in the world \n and you would achieve a chest  ";
        dialogues[3] = "Well, good luck!";
    }

    public void setAction()
    {

        actionLockCounter++;
        if(actionLockCounter == 120)
        {
            Random random = new Random();
            int i = random.nextInt(100) + 1;//pick a random no from 1 -> 100
    
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
    }

    public void speak()
    {
        //Do this character specific stuff
        super.speak();
    }

}
