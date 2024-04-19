package vinnsla;

import entities.Enemy;
import entities.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import objects.Block;
import objects.Climbable;
import org.example.lodedigger.Area;

import java.util.LinkedList;

public class Pathfinding {
    private Enemy enemy;
    private Player player;

    private Node[][] map = new Node[12][12];
    private Digraph G;

    private LinkedList<Node> closestPath;

    private Timeline moveTimeline;
    private Timeline updatePathTimeline;

    public Pathfinding(Enemy enemy, GridPane map, Player player) throws NullPointerException {
        this.enemy = enemy;
        makeMap(map);
        makeGraph();

        updatePathTimeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            int[] playerPos;
            playerPos = player.findCurrentCoords();
            try {
                closestPath = G.search(this.map[enemy.currentPosTight.get(0)][enemy.currentPosTight.get(1)], this.map[playerPos[0]][playerPos[1]]);
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
            enemy.shiftToNearestTile();
        }));
        updatePathTimeline.setCycleCount(Animation.INDEFINITE);
        updatePathTimeline.play();

        enemy.currentPosTight.addListener((ListChangeListener<? super Integer>) e -> {
            if (updatePathTimeline.getStatus() == Animation.Status.RUNNING) {
                int[] playerPos;
                playerPos = player.findCurrentCoords();
                try {
                    closestPath = G.search(this.map[enemy.currentPosTight.get(0)][enemy.currentPosTight.get(1)], this.map[playerPos[0]][playerPos[1]]);
                    System.out.println("changed block");
                    updatePathTimeline.playFromStart();
                } catch (ArrayIndexOutOfBoundsException ex) {}
                catch (NullPointerException ex) {
                    System.out.println("trying to kill WNMAWAHAHA");
                    enemy.kill();
                }
            }
        });


        moveTimeline = new Timeline(new KeyFrame(Duration.millis(7), e -> {
            Area.Direction direction = whatDirection();
            enemy.move(direction);
        }));
        moveTimeline.setCycleCount(Animation.INDEFINITE);
        moveTimeline.play();
    }

    private Area.Direction whatDirection() {
        try {
            Node v = closestPath.getFirst();
            int currentTileX = (v.index - 1) % 12;
            int currentTileY = (v.index / 12);

            Node w = closestPath.get(1);
            int nextTileX = (w.index - 1) % 12;
            int nextTileY = (w.index / 12);
            if (nextTileX > currentTileX) {
                return Area.Direction.RIGHT;
            }
            if (nextTileX < currentTileX) {
                return Area.Direction.LEFT;
            }
            if (nextTileY > currentTileY) {
                return Area.Direction.DOWN;
            }
            if (nextTileY < currentTileY) {
                return Area.Direction.UP;
            }
            return Area.Direction.STOP;
        } catch (NullPointerException ex) {
            return Area.Direction.STOP;
        }
    }

    private void makeMap(GridPane map) {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                this.map[i][j] = new Node(i*12+j+1, null);
            }
        }
        for (javafx.scene.Node n : map.getChildren()) {
            int x = GridPane.getColumnIndex(n);
            int y = GridPane.getRowIndex(n);
            this.map[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)] = new Node(y*12+x+1, n);
        }
        {
            Node[][] transposedMap = new Node[12][12];
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 12; j++) {
                    transposedMap[i][j] = this.map[j][i];
                }
            }
            this.map = transposedMap;
        }
    }

    private void makeGraph() {
        G = new Digraph(12*12);
        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 12; y++) {
                Node currentNode = map[x][y];
                if (x > 0 && y < 11) {
                    if (!(map[x-1][y].object instanceof Block) && (map[x][y+1].object instanceof Block || map[x][y+1].object instanceof Climbable)) G.addEdge(currentNode, map[x-1][y]);
                }
                if (x < 11 && y < 11) {
                    if (!(map[x+1][y].object instanceof Block) && (map[x][y+1].object instanceof Block || map[x][y+1].object instanceof Climbable)) G.addEdge(currentNode, map[x+1][y]);
                }
                if (y > 0) {
                    if (currentNode.object instanceof Climbable && !(map[x][y-1].object instanceof Block)) G.addEdge(currentNode, map[x][y-1]);
                }
                if (y < 11) {
                    if (!(map[x][y+1].object instanceof Block)) G.addEdge(currentNode, map[x][y+1]);
                }
            }
        }
    }

    public void stop() {
        moveTimeline.stop();
        updatePathTimeline.stop();
    }
    public void pause() {
        moveTimeline.pause();
        updatePathTimeline.pause();
    }
    public void play() {
        moveTimeline.play();
        updatePathTimeline.play();
    }
}
