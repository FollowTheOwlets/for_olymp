package matrix_tests;

import java.util.*;

// Класс для поиска путей согласно теории графов
public class Graph {
    protected Map<String, Integer> edges = new HashMap<>();
    protected Map<Integer, List<Integer>> vertex = new HashMap<>();
    protected int size;

    public Graph(int[][] matrix) {
        size = matrix.length;

        for (int i = 0; i < size; i++) {
            vertex.put(i, new ArrayList<>());
        }

        for (int i = 0; i < size; i++) {
            edges.put(key(i, i), 0);
            for (int j = i + 1; j < size; j++) {
                if (matrix[i][j] != 0) {
                    vertex.get(i).add(j);
                    vertex.get(j).add(i);
                    edges.put(key(i, j), matrix[i][j]);
                    edges.put(key(j, i), matrix[i][j]);
                }
            }
        }
    }

    // Поиск минимального пути согласно алгоритму Дейкстера
    // Нужен для проверки логичности направления
    public Way searchMinDistance(int i, int j, Way way) {
        way.visit(i);
        if (i == j) return way;

        Way minWay = new Way(Integer.MAX_VALUE);
        Way newWay;

        for (Integer item : vertex.get(i)) {
            if (way.contains(item)) {
                continue;
            } else {
                newWay = new Way(way, edges.get(key(i, item)));
                newWay = searchMinDistance(item, j, newWay);
                if (newWay.distance < 0) continue;
                if (minWay.distance >= newWay.distance) {
                    minWay = newWay;
                }
            }
        }

        return minWay;
    }
    // Поиск всевозможных путей
    public List<Way> searchWays(int i, int j, List<Way> ways) {
        if (ways == null) {
            ways = new ArrayList<>();
            Way newWay = new Way(0);
            newWay.visit(i);
            ways.add(newWay);
        }
        List<Way> newWays = new ArrayList<>();

        // Проходимся по всем путям
        boolean flag = true;
        for (Way way : ways) {
            List<Integer> set = way.visited;
            int last = way.visited.get(way.visited.size() - 1);
            // Для каждого пути вызываем поиск путей через доступную вершину
            for (Integer item : vertex.get(last)) {
                // Если вершины не была пройдена и такой путь существует
                if (this.edges.containsKey(key(last, item)) && !set.contains(item) && last != j &&
                        searchMinDistance(item,j,new Way(0)).distance < searchMinDistance(last,j,new Way(0)).distance) {
                    Way newWay = new Way(way, this.edges.get(key(last, item)));
                    newWay.visit(item);
                    newWays.add(newWay);
                    flag = false;
                }
                if (last == j) {
                    newWays.add(way);
                }
            }
        }
        if (flag) {
            return ways;
        } else {
            return searchWays(i, j, newWays);
        }

    }

    public String key(int i, int j) {
        return i + "-" + j;
    }
}
