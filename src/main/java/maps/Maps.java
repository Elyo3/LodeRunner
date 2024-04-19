package maps;

import entities.Enemy;
import javafx.scene.Node;
import objects.Gold;
import objects.Ground;
import objects.Ladder;
import objects.Scaffolding;

import java.io.IOException;

public class Maps {
    private static Node[][] map1;
    private static Node[][] map2 = new Node[][]{};
    private static Node[][] map3 = new Node[][]{};
    private static Node[][] map4;
    private static Node[][] map5;

    private static Node[][] ladder1;
    private static Node[][] ladder2;
    private static Node[][] ladder3;
    private static Node[][] ladder4;
    private static Node[][] ladder5;

    private static int[] respawn1 = new int[]{9, 0};
    private static int[] respawn2 = new int[]{0, 0};
    private static int[] respawn3 = new int[]{0, 0};
    private static int[] respawn4 = new int[]{8, 2};
    private static int[] respawn5 = new int[]{9, 0};

    static {
        try {
            map1 = new Node[][]{
                    {new Gold(), new Scaffolding(), null, null, null, null, new Scaffolding(), new Scaffolding(), new Scaffolding(), null, null, new Ground()},
                    {null, new Scaffolding(), null, null, null, null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ground()},
                    {null, new Ladder(), new Ladder(), new Scaffolding(), null, new Gold(), new Scaffolding(), new Scaffolding(), new Scaffolding(), null, null, new Ground()},
                    {null, null, null, new Scaffolding(), null, null, null, null, new Scaffolding(), null, null, new Ground()},
                    {null, null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Scaffolding(), null, null, new Ground()},
                    {null, null, new Scaffolding(), new Scaffolding(), null, null, null, null, new Scaffolding(), null, null, new Ground()},
                    {null, null, null, null, null, null, null, null, new Scaffolding(), new Gold(), null, new Ground()},
                    {null, null, null, null, null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), null, null, new Ground()},
                    {new Scaffolding(), new Scaffolding(), null, null, null, null, new Scaffolding(), null, null, null, new Scaffolding(), new Ground()},
                    {new Enemy(), new Scaffolding(), null, null, null, new Gold(), new Scaffolding(), null, null, null, new Scaffolding(), new Ground()},
                    {null, new Scaffolding(), null, null, null, null, new Scaffolding(), null, null, null, null, new Ground()},
                    {null, null, null, null, null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ground()}
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            map2 = new Node[][]{
                    {new Enemy(), new Scaffolding(), new Gold(), null, null, null, null, new Gold(), new Scaffolding(), null, null, new Ground()},
                    {null, new Scaffolding(), new Scaffolding(), new Scaffolding(), null, null, null, null, new Scaffolding(), null, null, new Ground()},
                    {null, new Ladder(), new Ladder(), new Scaffolding(), null, null, null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ground()},
                    {null, null, null, new Scaffolding(), null, null, null, null, null, null, null, new Ground()},
                    {null, null, null, new Scaffolding(), null, null, null, null, null, null, null, new Ground()},
                    {null, null, null, new Scaffolding(), null, null, null, null, null, null, null, new Ground()},
                    {null, null, null, new Scaffolding(), null, new Gold(), new Scaffolding(), null, null, null, null, new Ground()},
                    {null, null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Scaffolding(), null, new Ladder(), new Ladder(), new Ladder(), new Ground()},
                    {null, new Scaffolding(), null, null, null, null, new Scaffolding(), null, null, new Scaffolding(), null, new Ground()},
                    {null, new Scaffolding(), null, null, null, null, new Scaffolding(), null, new Gold(), new Scaffolding(), null, new Ground()},
                    {null, new Scaffolding(), null, null, null, null, new Scaffolding(), null, null, new Scaffolding(), null, new Ground()},
                    {null, new Scaffolding(), null, null, null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Scaffolding(), null, new Ground()}
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            map3 = new Node[][]{
                    {new Enemy(), new Scaffolding(), null, new Gold(), new Scaffolding(), null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ground()},
                    {null, new Scaffolding(), null, null, new Scaffolding(), null, null, new Scaffolding(), null, null, null, new Ground()},
                    {null, new Scaffolding(), null, null, new Scaffolding(), null, null, new Scaffolding(), null, null, new Gold(), new Ground()},
                    {null, new Ladder(), new Ladder(), new Ladder(), new Scaffolding(), null, null, null, new Scaffolding(), null, null, new Ground()},
                    {null, null, new Scaffolding(), null, null, new Gold(), new Scaffolding(), null, null, new Scaffolding(), new Scaffolding(), new Ground()},
                    {null, null, new Scaffolding(), null, null, null, new Scaffolding(), null, null, null, null, new Ground()},
                    {null, null, new Scaffolding(), null, null, new Scaffolding(), null, null, null, null, null, new Ground()},
                    {null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Scaffolding(), null, null, null, null, null, new Ground()},
                    {null, null, new Scaffolding(), null, null, new Scaffolding(), null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ground()},
                    {null, null, new Scaffolding(), null, null, new Scaffolding(), null, new Scaffolding(), null, new Scaffolding(), null, new Ground()},
                    {null, null, new Scaffolding(), null, new Ladder(), new Ladder(), new Ladder(), new Scaffolding(), null, new Scaffolding(), null, new Ground()},
                    {null, null, new Scaffolding(), null, null, null, null, new Scaffolding(), null, new Scaffolding(), null, new Ground()}
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            map4 = new Node[][]{
                    {new Scaffolding(), new Gold(),new Scaffolding(),new Scaffolding(),null,null,null,new Scaffolding(),null,null,null,new Ground()},
                    {new Scaffolding(), new Enemy(),new Scaffolding(),new Scaffolding(),null,null,null,new Scaffolding(),null,null,new Gold(),new Ground()},
                    {new Scaffolding(), null,new Scaffolding(),new Scaffolding(),null,new Gold(),null,new Scaffolding(),null,null,null,new Ground()},
                    {new Scaffolding(), null,new Scaffolding(),new Scaffolding(),null,null,null,new Scaffolding(),null,null,null,new Ground()},
                    {new Scaffolding(), null,new Scaffolding(),new Scaffolding(),null,null,null,null,null,new Scaffolding(),null,new Ground()},
                    {null, null,new Ladder(),new Ladder(),new Ladder(),new Ladder(),new Ladder(),new Ladder(),new Ladder(),new Scaffolding(),null,new Ground()},
                    {null, null,null,new Scaffolding(),null,null,null,null,null,new Scaffolding(),null,new Ground()},
                    {null, null,null,new Scaffolding(),null,null,null,null,null,new Ladder(),new Ladder(),new Ground()},
                    {null, new Gold(),new Enemy(),new Scaffolding(),null,new Scaffolding(),new Scaffolding(),null,null,null,null,new Ground()},
                    {null, new Scaffolding(),new Scaffolding(),new Scaffolding(),null,null,new Scaffolding(),null,null,null,null,new Ground()},
                    {null, new Ladder(),new Ladder(),new Ladder(),new Ladder(),new Ladder(),new Scaffolding(),null,new Gold(),null,null,new Ground()},
                    {null, null,null,null,null,null,new Scaffolding(),null,null,null,null,new Ground()}
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            map5 = new Node[][]{
                    {new Enemy(), new Scaffolding(), null, null, null, null, null, new Ladder(), new Ladder(), new Ladder(), new Scaffolding(), new Ground()},
                    {null, new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Scaffolding(), null, null, new Ladder(), new Ground()},
                    {null, new Scaffolding(), null, new Scaffolding(), null, null, null, new Gold(), new Scaffolding(), null, null, new Ground()},
                    {null, new Scaffolding(), null, new Gold(), new Scaffolding(), null, null, null, null, new Scaffolding(), null, new Ground()},
                    {null, new Scaffolding(), null, null, null, null, null, null, null, null, null, new Ground()},
                    {null, new Scaffolding(), new Scaffolding(), null, null, null, null, null, new Scaffolding(), null, null, new Ground()},
                    {null, new Scaffolding(), null, new Scaffolding(), null, null, null, null, new Ladder(), new Scaffolding(), null, new Ground()},
                    {null, new Scaffolding(), null, null, new Scaffolding(), null, null, null, null, new Ladder(), new Scaffolding(), new Ground()},
                    {null, new Scaffolding(), new Scaffolding(), null, new Gold(), new Scaffolding(), null, null, null, null, new Ladder(), new Ground()},
                    {new Enemy(), new Scaffolding(), new Scaffolding(), null, null, null, null, null, null, new Scaffolding(), new Scaffolding(), new Ground()},
                    {null, null, new Scaffolding(), null, null, null, new Scaffolding(), null, null, new Ladder(), new Ladder(), new Ground()},
                    {null, null, null, null, new Gold(), new Scaffolding(), null, null, null, new Ladder(), new Gold(), new Ground()}
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static {
        try {
            ladder1 = new Node[][]{
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {new Ladder(), new Ladder(), new Ladder(), new Ladder(), new Ladder(), null, null, null, null, null, null, null}
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            ladder2 = new Node[][]{
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {new Ladder(), new Ladder(), null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null}
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            ladder3 = new Node[][]{
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {new Ladder(), null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null}
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            ladder4 = new Node[][]{
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {new Ladder(), null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null}
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            ladder5 = new Node[][]{
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {new Ladder(), null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null}
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Node[][] getMap(int i) {
        switch (i) {
            case 1:
                return map1;
            case 2:
                return map2;
            case 3:
                return map3;
            case 4:
                return map4;
            case 5:
                return map5;
            default:
                return null;
        }
    }

    public static Node[][] getLadder(int i) {
        switch (i) {
            case 1:
                return ladder1;
            case 2:
                return ladder2;
            case 3:
                return ladder3;
            case 4:
                return ladder4;
            case 5:
                return ladder5;
            default:
                return null;
        }
    }

    public static int[] getRespawn(int i) {
        switch (i) {
            case 1:
                return respawn1;
            case 2:
                return respawn2;
            case 3:
                return respawn3;
            case 4:
                return respawn4;
            case 5:
                return respawn5;
            default:
                return null;
        }
    }
}
