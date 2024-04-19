package vinnsla;

public class Node {
    public int index;
    public javafx.scene.Node object;

    public Node(int index, javafx.scene.Node object) {
        this.index = index;
        this.object = object;
    }

    public String toString() {
        if (object != null) {
            return Integer.toString(index) + object;
        } return Integer.toString(index);
    }
}
