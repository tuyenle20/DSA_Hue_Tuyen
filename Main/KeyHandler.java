package Main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    //Debug
    boolean showDebugText = false;

    GamePanel gp;
    public KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        //TiTLE STATE
        if(gp.gameState == gp.titleState)
        {
            titleState(code);
        }

        //PLAY STATE
        else if(gp.gameState == gp.playState){
            playState(code);
        }

        //Pause state
        else if(gp.gameState == gp.pauseState)
        {
        pauseState(code);
        }

        //Dialogue state
        else if(gp.gameState == gp.dialogueState)
        {
            dialogueState(code);
        }

        //CHARACTER STATE
        /*else if (gp.gameState == gp.characterState)
        {
            characterState(code);
        }
*/
        //OPTIONS STATE
        else if (gp.gameState == gp.optionsState)
        {
            optionsState(code);
        }

         //GAME OVER STATE
        else if (gp.gameState == gp.gameOverState)
        {
            gameOverState(code);
        }

}

        public void titleState(int code) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0)
                {
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2)
                {
                    gp.ui.commandNum = 0;
                }
            }     
            if(code == KeyEvent.VK_ENTER)    
            {
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                else if(gp.ui.commandNum == 1){
                }
                else if(gp.ui.commandNum == 2){
                    System.exit(0);
                }
            }
}

        public void playState(int code){
            if(code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if(code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if(code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D) {
                rightPressed = true;
            }

            //P key for pause game
            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            //if (code == KeyEvent.VK_C){
              //  gp.gameState = gp.characterState;
            //}
            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if(code == KeyEvent.VK_SPACE) {
                shotKeyPressed = true;
            }
            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.optionsState;
            }
    
            //debug
            if(code == KeyEvent.VK_T)
            {
                if(showDebugText == false)
                {
                    showDebugText = true;
                }
                else if(showDebugText == true)
                {
                    showDebugText = false;
                }
            }
            if(code == KeyEvent.VK_R)
            {
                gp.tileM.loadMap("/maps/world01.txt");
            }

        }

        public void pauseState(int code){
            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }

        public void dialogueState(int code){
            if(code == KeyEvent.VK_ENTER)
            {
                gp.gameState = gp.playState;
            }
        }
    public void optionsState(int code)
    {
        if(code == KeyEvent.VK_ESCAPE)
        {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
        
        int maxCommandNum = 0;
        switch (gp.ui.subState) 
        {
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }
        if(code == KeyEvent.VK_W)
        {
            gp.ui.commandNum--;
            gp.playSE(8);
            if(gp.ui.commandNum < 0)
            {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S)
        {
            gp.ui.commandNum++;
            gp.playSE(8);
            if(gp.ui.commandNum > maxCommandNum)
            {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_A)
        {
            if(gp.ui.subState == 0)
            {
                if(gp.ui.commandNum == 0 && gp.music.volumeScale > 0)
                {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(8);
                }
                if(gp.ui.commandNum == 1 && gp.se.volumeScale > 0)
                {
                    gp.se.volumeScale--;
                    gp.playSE(8);
                }
            }
        }
        if(code == KeyEvent.VK_D)
        {
            if(gp.ui.subState == 0)
            {
                if(gp.ui.commandNum == 0 && gp.music.volumeScale < 5)
                {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(8);
                }
                if(gp.ui.commandNum == 1 && gp.se.volumeScale < 5)
                {
                    gp.se.volumeScale++;
                    gp.playSE(8);
                }
            }
        }
    }

    public void gameOverState(int code){
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum <0){
                gp.ui.commandNum =1;
            }
            gp.playSE(8);
        }

        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum >1){
                gp.ui.commandNum =0;
            }
            gp.playSE(8);
        }
        if (code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if (gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }
    

    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        //if( code == KeyEvent.VK_ENTER){
            //gp.player.selectItem();
        //}
        if(code == KeyEvent.VK_SPACE) {
            shotKeyPressed = false;
        }
    }
}
