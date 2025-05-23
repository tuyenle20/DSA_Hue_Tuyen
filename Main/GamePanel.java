package Main;

import Entity.Entity;
import Entity.Player;
import Environment.EnvironmentManage;
import Tile.TileManager;
import java.awt.Color; // Corrected the spelling from "Dimention" to "Dimension"
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

public class GamePanel extends JPanel  implements Runnable{
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 20;//20
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol =50;
    public final int maxWorldRow =50;
    
    //FPS
    int FPS = 60;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this); 
    EnvironmentManage eManage = new EnvironmentManage(this);
    //Sound sound = new Sound();
    Sound music = new Sound();
    Sound se = new Sound();
    public AssetSetter aSetter = new AssetSetter(this);
    public CollisionChecker cChecker = new CollisionChecker(this);   
    
    
    Thread gameThread; 
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);

    //ENTITY AND OBJECTf
    public Player player= new Player(this, keyH);
    public Entity obj[] = new Entity[20];
    public Entity npc[] = new Entity[10];
    public Entity ghost[] = new Entity[20];
   
    //Put all the entities (player,npc, object) into this list
    //sort -> the entity lowest worldY will indext 0;
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    

    //Game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6; 


   
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Fixed "Dimention" typo
        this.setBackground(Color.BLACK); // Best practice to use uppercase for color constants
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setGhost();
        eManage.setup();
        

        gameState = titleState;

    }

    public void retry(){

        player.setDefaultPositions();
        player.restoreLifeAndMan();
        aSetter.setNPC();
        aSetter.setGhost();
        //aSetter.setBigSnowMan();
    
    }

    public void restart(){

        player.setDefaultValues();
        player.setDefaultPositions();
        player.restoreLifeAndMan();
        //player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setGhost();
        //aSetter.setBigSnowMan();
        // aSetter.setInteractiveTile();

    }
    public void setFullScreen()
    {
        //GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
   
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta>=1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
        if (timer >= 1000000000) {
            System.out.println("FPS:" + drawCount);
            drawCount = 0;
            timer = 0;
        }

        }
    }
    public void update() {
        if(gameState == playState)
        {
            //PLAYER
            player.update();
            //NPC
            for(int i = 0; i < npc.length; i++)
            {
                if(npc[i] != null)
                {
                    npc[i].update();
                }
            }
            for(int i = 0; i < ghost.length; i++)
            {
                if(ghost[i] != null)
                {
                    if(ghost[i].alive == true && ghost[i].dying == false){
                    ghost[i].update();
                    }
                    if(ghost[i].alive == false){
                        ghost[i].checkDrop();
                        ghost[i] = null;
                        }
                }
            }
             /*for(int i = 0; i < ghost.length; i++)
            {
                if(ghost[i] != null)
                {
                    if(ghost[i].alive == true && ghost[i].dying == false){
                    bigsnowman[i].update();
                    }
                    if(bigsnowman[i].alive == false){
                        // bigsnowman[i].checkDrop();
                        bigsnowman[i] = null;
                        }
                }
            }*/

            for(int i = 0; i < projectileList.size(); i++)
            {
                if(projectileList.get(i) != null)
                {
                    if(projectileList.get(i).alive == true){
                        projectileList.get(i).update();
                    }
                    if(projectileList.get(i).alive == false){
                        projectileList.remove(i);
                        }
                }
            
            }

            for(int i = 0; i < particleList.size(); i++)
            {
                if(particleList.get(i) != null)
                {
                    if(particleList.get(i).alive == true){
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false){
                        particleList.remove(i);
                        }
                }
            
            }

            /*for (int i = 0; i < iTile.length; i++){     
                if(iTile[i] != null){
                    iTile[i].update();
                }
            }*/

        }

        if(gameState == pauseState)
        {
            //Nothing
        }
      
    }

   
     @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        // Debug
        long drawStart = 0;
        if(keyH.showDebugText == true)
        {
            drawStart = System.nanoTime();
        }
        
        //TILTE SCREEN
        if(gameState == titleState)
        {
            ui.draw(g2);
        }

        //OTHERS
        else
        {

            // TILE
            tileM.draw(g2);

            /*for(int i = 0; i < iTile.length; i++ ) {
                if(iTile[i] != null){
                    iTile[i].draw(g2);
                }
            }*/


            //ADD ENTITY TO THE LIST
            entityList.add(player);

            //ADD ENTITIES TO THE LIST
            for(int i = 0; i< npc.length;i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i< obj.length;i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            for(int i = 0; i< ghost.length;i++){
                if(ghost[i] != null){
                    entityList.add(ghost[i]);
                }
            }
            for(int i = 0; i< projectileList.size();i++){
                if(projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
            }

            for(int i = 0; i< particleList.size();i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>(){

                    @Override
                    public int compare(Entity e1,Entity e2){
                        int result = Integer.compare(e1.worldY,e2.worldX);
                        return result;
                    }
                });

            // DRAW ENTITIES
            for(int i=0; i< entityList.size();i++){
                entityList.get(i).draw(g2);
            }

            //EMPTY ENTITY LIST
            entityList.clear();
            // ENVIRONMENT
            eManage.draw(g2);
            
            //UI
            ui.draw(g2);
        }
        
        //debug
        if(keyH.showDebugText == true)
        {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX" + player.worldX, x, y);
            y += lineHeight;
            g2.drawString("WorldY" + player.worldY, x, y);
            y += lineHeight;
            g2.drawString("Col" + (player.worldX + player.solidArea.x)/tileSize, x, y);
            y += lineHeight;
            g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize, x, y);
            y += lineHeight;
            g2.drawString("Draw Time: " + passed, x, y);
            //System.out.println("Draw time: " + passed);
        }  
        g2.dispose();
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop(); 
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

}

