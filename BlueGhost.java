package monster;

import Entity.Entity;
import Main.GamePanel;
import Object.OBJ_Heart;
import Object.OBJ_Rock;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BlueGhost extends Entity {
    GamePanel gp;
    public BlueGhost ( GamePanel gp){
        super(gp);
        this.gp=gp;
        type =type_monster;
        name = "Blue Ghost";
        speed = 2;
        maxLife = 20;
        life = maxLife;
        attack = 1;
        defense = 0;
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
        up1 = setup("/monster/blueGhost",gp.tileSize,gp.tileSize);
        down1 = setup("/monster/blueGhost",gp.tileSize,gp.tileSize);
        left1 = setup("/monster/blueGhost",gp.tileSize,gp.tileSize);
        right1 = setup("/monster/blueGhost",gp.tileSize,gp.tileSize);
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
    /*public int distanceToPlayerTile() {
    int ghostCol = worldX / gp.tileSize;
    int ghostRow = worldY / gp.tileSize;
    int playerCol = gp.player.worldX / gp.tileSize;
    int playerRow = gp.player.worldY / gp.tileSize;

    int dx = Math.abs(ghostCol - playerCol);
    int dy = Math.abs(ghostRow - playerRow);
    return Math.max(dx, dy); // Chebyshev distance
}*/


    /*public void setAction() {
    actionLockCounter++;

    if (actionLockCounter >= 15) {
        int distance = distanceToPlayerTile();

        if (distance <= 5) {
            direction = pathfindingDirection(); // Đuổi theo Pacman
        } else {
            setRandomDirection(); // Lang thang ngẫu nhiên
        }

        actionLockCounter = 0;
    }
    gp.cChecker.checkTile(this);
    if (!collisionOn) {
        moveToDirection(); // OK -> di chuyển
    } else {
        // Nếu có tường -> chọn hướng khác
        int distance = distanceToPlayerTile();
        if (distance <= 0) {
            direction = pathfindingDirection(); // Tìm đường khác đến Pacman
        } else {
            setRandomDirection(); // Đi hướng khác ngẫu nhiên
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
    int i = new Random().nextInt(100); // 0 - 99

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
        dropItem(new OBJ_Heart(gp));
    }*/
    public void setAction() {
    actionLockCounter++;

    if (actionLockCounter >= 15) { // cập nhật hướng thường xuyên hơn
        direction = pathfindingDirection(); // lấy hướng gần nhất về phía Pacman
        actionLockCounter = 0;
    }
    
    collisionOn = true;
    gp.cChecker.checkTile(this);

    

    int i = new Random().nextInt(100)+1;
    if(i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
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
    public void damageReaction()
    {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
    public void checkDrop() {
        dropItem(new OBJ_Heart(gp));
    }
}





