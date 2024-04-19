package vinnsla;

import java.util.*;

public class Digraph {
    private int V;
    private int E;
    private Map<Node, ArrayList<Node>> adj;
    private HashSet<Node> visited;

    public Digraph(int V) {
        this.V = V;
        adj = new HashMap<>();
    }

    public void addEdge(Node from, Node to) {
        if (!adj.containsKey(from)) {
            adj.put(from, new ArrayList<>());
        }
        adj.get(from).add(to);
        E++;
    }

    public boolean hasEdge(Node from, Node to) {
        for (Node dest : adj(from)) {
            if (dest == to) return true;
        }
        return false;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public ArrayList<Node> adj(Node v) {
        return adj.get(v);
    }

    public LinkedList<Node> search(Node v, Node w) throws NullPointerException {
        LinkedList<Node> list = null;
        if (adj(v) == null) throw new NullPointerException();
        for (Node m : adj(v)) {
            visited = new HashSet<>();
            visited.add(v);
            if (list == null) {
                list = recursiveSearch(v, m, w);
            } else {
                LinkedList<Node> newList = recursiveSearch(v, m, w);
                if (newList != null && newList.size() < list.size()) {
                    list = newList;
                }
            }
        }
        if (list != null) {
            list.addFirst(v);
        }
        return list;
    }

    private LinkedList<Node> recursiveSearch(Node v, Node x, Node w) {
        visited.add(x);
        LinkedList<Node> list = null;
        if (x == w) {
            list = new LinkedList<>();
            list.addFirst(x);
            return list;
        }

        if (adj(x) != null) {
            for (Node m : adj(x)) {
                if (visited.contains(m)) continue;
                if (list == null) {
                    list = recursiveSearch(x, m, w);
                } else {
                    LinkedList<Node> newList = recursiveSearch(x, m, w);
                    if (newList != null && newList.size() < list.size()) {
                        list = (LinkedList<Node>) newList.clone();
                    }
                }
            }
        }
        if (list != null) {
            list.addFirst(x);
        }
        return list;
    }
}
