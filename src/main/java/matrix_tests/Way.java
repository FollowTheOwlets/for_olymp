package matrix_tests;

import java.util.ArrayList;
import java.util.List;

// Открытая структура данных с парой вспомогательных методов
// Сущность пути
public class Way {
    public List<Integer> visited;

    public int distance;

    public Way(int distance) {
        visited = new ArrayList<>();
        this.distance = distance;
    }

    public Way(Way old, int distance) {
        visited = new ArrayList<>(old.visited);
        this.distance = old.distance + distance;
    }

    public void visit(int vertex) {
        this.visited.add(vertex);
    }

    public boolean contains(int vertex) {
        return this.visited.contains(vertex);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Integer i : visited) {
            str.append(i).append("->");
        }
        return str.append("\n").toString();
    }
}