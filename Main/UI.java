package Main;
import Entity.Entity;
import Object.OBJ_Heart;
import Object.OBJ_ManaCrystal;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font CKFraternity, PurisaBold;
    Font arial_40 , arial_80B;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;          
    //BufferedImage keyImage ;
    public boolean messageOn = false ;
    //public String message = "";
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    //int messageCounter =0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;//0: the 1st screen, 1: the 2nd screen
    
    // public  int frameX;
    // public  int frameY;
    public int slotCol = 0;
    public int slotRow = 0;
    int subState = 0;

    public UI(GamePanel gp){
        this.gp = gp ;
        arial_40 = new Font("Arial",Font.PLAIN, 40);
        arial_80B = new Font("Arial",Font.BOLD, 80);
        
        try {
            InputStream is = getClass().getResourceAsStream("/font/Creepster.ttf");
            CKFraternity = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            PurisaBold = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            
            e.printStackTrace();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        //CREATE HUDOBJECT
        Entity heart = new OBJ_Heart(gp);       //633
        heart_full = heart.image;
        heart_half= heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;

        //OBJ_Key key = new OBJ_Key(gp);
        //keyImage = key.image;
    }
    public void addMessage(String text ){
        //message = text ;
        //messageOn= true;

        message.add(text);
        messageCounter.add(0);
    }
    public void draw( Graphics2D g2)
    {

        this.g2 = g2;

        g2.setFont(CKFraternity);
        g2.setColor(Color.RED);

        //TILTE STATE
        if(gp.gameState == gp.titleState)
        {
            drawTitleScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        
        /*//CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
          //  drawInventory();
        }*/

        //OPTIONS STATE
        if(gp.gameState == gp.optionsState )
        {
            drawOptionsScreen();
        }

         //GAME OVER STATE
        if(gp.gameState == gp.gameOverState )
        {
            drawGameOverScreen();
        }
        //GAME FINISH
       
        if(gameFinished == true ){
                g2.setFont(arial_40);
                g2.setColor(new Color(255, 204, 0));
        
                String text;
                int textLenght;
                int x ;
                int y ;
            
                text = " YOU FOUND THE TREASURE ";
                textLenght = (int)g2.getFontMetrics().getStringBounds(text,g2 ).getWidth();
                x=gp.screenWidth/2 - textLenght/2;
                y=gp.screenHeight/2 - (gp.tileSize*2);
                g2.drawString(text, x, y);

                g2.setFont(arial_80B);
                g2.setColor(new Color(255, 204, 0));
                text = " CONGRATULATION! ";
                textLenght = (int)g2.getFontMetrics().getStringBounds(text,g2 ).getWidth();
                x=gp.screenWidth/2 - textLenght/2;
                y=gp.screenHeight/2 + (gp.tileSize*2);
                g2.drawString(text, x, y);

                gp.gameThread = null;

        }
    
            
        
    }
    

    public void drawMessage()
    {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for(int i = 0; i < message.size(); i++)
        {
            if(message.get(i) != null)
            {
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;//messageCounter++
                messageCounter.set(i, counter);//setbthe counter to the array
                messageY += 50;

                if(messageCounter.get(i) > 180)
                {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawPlayerLife() {
//        gp.player.life = 3;
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        // DRAW MAX LIFE
        while(i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y , null);
            i++;
            x += gp.tileSize;
        }
                //RESET
                x = gp.tileSize/2;
                y = gp.tileSize/2;
                i = 0;
                //ĐRAW CURRENT LIFE
                while(i < gp.player.life) {
                    g2.drawImage(heart_half, x, y, null);
                    i++;
                    if(i < gp.player.life) {
                        g2.drawImage(heart_full, x, y,null);
                    }
                    i++; 
                    x += gp.tileSize;
            }
            // DRAW MANA ICON (chỉ vẽ 1 cục mana)
                    x = (gp.tileSize / 2) - 5;
                    y = (int)(gp.tileSize * 1.5);
                    g2.drawImage(crystal_full, x, y, null);

                    // DRAW TEXT SỐ LƯỢNG MANA
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
                    g2.setColor(Color.white);
                    g2.drawString("x" + gp.player.mana, x + 40, y + 30); // hiển thị số mana bên phải icon

            
        }
    public void drawTitleScreen()
    {
        if(titleScreenState == 0)
        {
            g2.setColor(new Color(128, 128,128));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            //TILTE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 81F));
            String text = "PacMan Plus";
            int x = getXforCenterText(text);
            int y = gp.tileSize*3;
    
            //SHADOW OF TEXT
            g2.setColor(new Color(173, 216, 230)); 

            g2.drawString(text, x + 5, y + 5);
    
            //MAIN COLOR
            g2.setColor(new Color(65, 105, 225));

            g2.drawString(text, x, y);
    
            //CHARACTER IMAGE
            g2.setColor(new Color(220, 20, 60)); // Crimson


            x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
    
            //MENU
            g2.setColor(new Color(0, 0, 139));

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
    
            text = "NEW GAME";
            x = getXforCenterText(text);
            y += gp.tileSize*3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0)
            {
                g2.drawString(">", x - gp.tileSize, y);
            }
            
            text = "LOAD GAME";
            x = getXforCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1)
            {
                g2.drawString(">", x - gp.tileSize, y);
            }
            g2.setColor(new Color(65, 105, 225)); // Light Blue (xanh nhạt như tuyết)

            text = "QUIT";
            x = getXforCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2)
            {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }

        

        
    }

    public void drawPauseScreen()
    {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSE";
        int x = getXforCenterText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);

    }
    

    public void drawDialogueScreen()
    {
        //WINDOW
        int x = gp.tileSize*2; 
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));

        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n"))
        {
            g2.drawString(line, x, y);
            y += 40; 
        }
        
    }
    /*public void drawCharacterScreen(){

        //  create a frame
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);


    

     //TEXT
     g2.setColor(Color.white);
     g2.setFont(g2.getFont().deriveFont(32F));
     int textX = frameX +20;
     int textY = frameY + gp.tileSize;
     final int lineHeight = 32;             


    
      //VALUE
      int tailX = (frameX +frameWidth) - 30;
      //reset textY
    textY = frameY + gp.tileSize;
    String value;*/
/* 
        // add so lieu vao bang 
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY -24, null);
        textY += gp.tileSize  ;
      
    }*/

    public void drawInventory()
    {
        //FRAME
        int frameX = gp.tileSize*12;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        //DRAW PLAYER'S ITEMS
        //for(int i = 0; i < gp.player.inventory.size(); i++)
        //{
          /*  //EQUIP CURSOR
            if(gp.player.inventory.get(i) == gp.player.currentWeapon ){
                g2.setColor(new Color( 240,190,90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
                    }
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

            slotX += slotSize;

            if(i == 4 || i == 9 || i == 14)
            {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
*/
        //CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

       /*  //DESCIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFamreHeight = gp.tileSize * 3;
        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFamreHeight);*/

        //DRAW DESCRIPTION TEXT
       /*  int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));
        
        int itemIndex = getItemIndexOnSlot();

        if(itemIndex < gp.player.inventory.size())
        {
            for(String line : gp.player.inventory.get(itemIndex).description.split("\n"))
            {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        } */
        
    }



    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));

        text = "Game Over";
        g2.setColor(Color.black);
        x = getXforCenterText(text);
        y = gp.tileSize *4;
        g2.drawString(text, x,y);
        //Main
        g2.setColor(Color.red);
        g2.drawString(text, x -4, y-4);

        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        g2.setColor(new Color(30, 144, 255));
        text = "Retry";
        x =  getXforCenterText(text);
        y += gp.tileSize *4;
        g2.drawString(text, x, y);
        if(commandNum ==0){
            g2.drawString(">", x-40,y);     //Keyhandle

        }

        //Back to the title screen
        g2.setColor(new Color(30, 144, 255));
        text = "Quit";
        x =  getXforCenterText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum ==1){
            g2.drawString(">", x-40,y);     //Keyhandle

        }
    }

    public void drawOptionsScreen()
    {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        //SUB WINDOW
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        switch (subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
        }
        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX, int frameY)
    {
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXforCenterText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

    

        //MUSIC
        textX = frameX + gp.tileSize;
       
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 0)
        {
            g2.drawString(">", textX - 25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if(commandNum == 1)
        {
            g2.drawString(">", textX - 25, textY);
        }

        //CONTROL
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 2)
        {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true)
            {
                subState = 2;
                commandNum = 0;
            }
        }

        //END GAME
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 3)
        {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true)
            {
                subState = 3;
                commandNum = 0;
            }
        }

        //BACK
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 4)
        {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true)
            {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }



        //MUSIC VOLUME
        textX = frameX + (int)(gp.tileSize*4.5);
        textY = frameY + gp.tileSize*1 + 24;
        g2.drawRect(textX, textY, 120, 24);//120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

       
        

    }

    public void options_fullScreenNotification(int frameX, int frameY)
    {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "The change will take an \neffect after restarting \nthe game.";

        for(String line : currentDialogue.split("\n"))
        {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //BACK
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0)
        {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true)
                {
                    subState = 0;
                }
        }

    }

    public void options_control(int frameX, int frameY)
    {
        int textX;
        int textY;

        //TITLE
        String text = "Control";
        textX = getXforCenterText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize - 30; 
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize*6 - 30;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("SPACE", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;

        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0)
        {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true)
            {
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY)
    {
        int textX = frameX + gp.tileSize - 20;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";

        for(String line : currentDialogue.split("\n"))
        {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //YES
        String text = "Yes";
        textX = getXforCenterText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0)
        {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true)
            {
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }

        //NO
        text = "No";
        textX = getXforCenterText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1)
        {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true)
            {
                subState = 0;
                commandNum = 4;
            }
        }

    }
    public int getItemIndexOnSlot()
    {
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height)
    {
        Color c = new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    
    public int getXforAlignToRightText(String text, int tailX)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
        
    public int getXforCenterText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }   
}


