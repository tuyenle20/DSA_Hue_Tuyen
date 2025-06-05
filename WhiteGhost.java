package monster;

import Entity.Entity;
import Main.GamePanel;
import Object.OBJ_Chest;
import Object.OBJ_Rock;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class WhiteGhost extends Entity {
    GamePanel gp;
    public WhiteGhost ( GamePanel gp){
        super(gp);
        this.gp=gp;
        type =type_monster;
        name = "White Ghost";
        speed = 2;
        maxLife = 100;
        life = maxLife;
        attack = 1;
        defense = 0;
        projectile = new OBJ_Rock(gp);
        int ghostScale = 3; 
        int ghostSize = gp.tileSize * ghostScale;

        solidArea.x = gp.tileSize;
        solidArea.y = gp.tileSize;
        solidArea.width = ghostSize - gp.tileSize;
        solidArea.height = ghostSize - gp.tileSize;
        solidAreaDefaultX= solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){
        up1 = setup("/monster/whiteghost1",gp.tileSize*4,gp.tileSize*4);
        down1 = setup("/monster/whiteghost2",gp.tileSize*4,gp.tileSize*4);
        left1 = setup("/monster/whiteghost3",gp.tileSize*4,gp.tileSize*4);
        right1 = setup("/monster/whiteghost3",gp.tileSize*4,gp.tileSize*4);
    }
    
    private class Node {
        int x, y;
        Node parent;
        Node(int x, int y, Node parent) {
            this.x = x; this.y = y; this.parent = parent;
        }
    }
    public String pathfindingDirection() {
        int startX = worldX / gp.tileSize;
        int startY = worldY / gp.tileSize;
        int targetX = gp.player.worldX / gp.tileSize;
        int targetY = gp.player.worldY / gp.tileSize;

        boolean[][] visited = new boolean[gp.maxWorldCol][gp.maxWorldRow];
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(startX, startY, null));
        visited[startX][startY] = true;

        Node endNode = null;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.x == targetX && current.y == targetY) {
                endNode = current;
                break;
            }

            int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
            for (int[] d : directions) {
                int nx = current.x + d[0];
                int ny = current.y + d[1];

                if (nx >= 0 && ny >= 0 && nx < gp.maxWorldCol && ny < gp.maxWorldRow) {
                    if (!visited[nx][ny] && gp.tileM.isWalkable(nx, ny)) {
                        visited[nx][ny] = true;
                        queue.add(new Node(nx, ny, current));
                    }
                }
            }
        }

        if (endNode == null) return direction;

        Node current = endNode;
        Node prev = null;
        while (current.parent != null) {
            prev = current;
            current = current.parent;
        }

        if (prev == null) return direction;

        if (prev.x > startX) return "right";
        if (prev.x < startX) return "left";
        if (prev.y > startY) return "down";
        if (prev.y < startY) return "up";

        return direction;
    }
    public int distanceToPlayerTile() {
    int ghostCol = worldX / gp.tileSize;
    int ghostRow = worldY / gp.tileSize;
    int playerCol = gp.player.worldX / gp.tileSize;
    int playerRow = gp.player.worldY / gp.tileSize;

    int dx = Math.abs(ghostCol - playerCol);
    int dy = Math.abs(ghostRow - playerRow);
    return Math.max(dx, dy); // Chebyshev distance
}


    public void setAction() {
    actionLockCounter++;

    if (actionLockCounter >= 15) {
        int distance = distanceToPlayerTile();
        
        if (distance <= 5) {
            direction = pathfindingDirection(); 
        } else {
            setRandomDirection(); 
        }

        actionLockCounter = 0;
    }
    gp.cChecker.checkTile(this);
    if (!collisionOn) {
        moveToDirection();
    } else {
        int distance = distanceToPlayerTile();
        if (distance <= 0) {
            direction = pathfindingDirection(); 
        } else {
            setRandomDirection(); 
        }
    }


    int i = new Random().nextInt(100) + 1;
    if (i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
        projectile.set(worldX, worldY, direction, true, this);
        gp.projectileList.add(projectile);
        shotAvailableCounter = 0;
    }
}

    public void moveToDirection() {
    switch (direction) {
        case "up": worldY -= speed; break;
        case "down": worldY += speed; break;
        case "left": worldX -= speed; break;
        case "right": worldX += speed; break;
    }
}
public void setRandomDirection() {
    int i = new Random().nextInt(100); 

    if (i < 25) {
        direction = "up";
    } else if (i < 50) {
        direction = "down";
    } else if (i < 75) {
        direction = "left";
    } else {
        direction = "right";
    }
}

    public void damageReaction()
    {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
    public void checkDrop() {
        dropItem(new OBJ_Chest(gp));
    }
}
