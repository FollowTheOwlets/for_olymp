package matrix_tests;

import logger.Logger;

import java.util.*;

// Главный класс для решения задачи
public class Matrix {
    protected final char[][] map; // Первоначальная схема
    protected final char[][] bufferMap; // Схема (копия) с котрой будем работать
    protected final int ySize; // Размер по вертикали
    protected final int xSize; // Размер по горизонтали

    protected int size; // Итоговое количество S
    protected List<Integer> sources; // Все источники
    protected List<Integer> consumers; // Все потребители
    protected int[][] wayMatrix; // Матрица путей для построения графа
    protected Graph graph; // Сам граф

    protected Logger logger;

    //Класс для хранения пар значений
    public class Pair<K, T> {
        public K first;
        public T second;

        public Pair(K first, T second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "first=" + first +
                    ", second=" + second + " ";
        }
    }

    public Matrix(char[][] map, int xSize, int ySize) {
        this.map = map;
        this.xSize = xSize;
        this.ySize = ySize;
        this.bufferMap = new char[map.length][map[0].length];
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                this.bufferMap[i][j] = map[i][j];
            }
        }
        this.sources = new ArrayList<>();
        this.consumers = new ArrayList<>();
        logger = Logger.getInstance().log("Создали объект Matrix для дальнейшей работы");
    }

    public int[][] getWayMatrix() {
        return wayMatrix;
    }

    // Создание матрицы путей
    public Matrix buildWayMatrix() {
        logger.log("Начинаем строить матрицу путей");
        this.wayMatrix = new int[xSize * ySize][xSize * ySize];
        for (int i = 0; i < xSize * ySize; i++) {
            for (int j = 0; j < xSize * ySize; j++) {
                this.wayMatrix[i][j] = 0;
            }
        }
        for (int i = 1; i < ySize - 1; i++) {
            for (int j = 1; j < xSize - 1; j++) {
                if (map[i][j] == 'E') sources.add(xSize * i + j);
                if (map[i][j] == 'C') consumers.add(xSize * i + j);
                if (map[i][j] != 'X' && map[i][j] != 'T') {
                    boolean check = true;
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            if (map[i + k][j + l] == 'T') check = false;
                        }
                    }
                    if (!check) continue;
                    if (map[i][j - 1] == 'X' || map[i][j + 1] == 'X') {
                        changePoint(i, -1, j, 0);
                        changePoint(i, +1, j, 0);
                    }
                    if (map[i - 1][j] == 'X' || map[i + 1][j] == 'X') {
                        changePoint(i, 0, j, -1);
                        changePoint(i, 0, j, +1);
                    }
                    if (map[i - 1][j - 1] == 'X') {
                        changePoint(i, -1, j, 0);
                        changePoint(i, 0, j, -1);
                    }
                    if (map[i + 1][j + 1] == 'X') {
                        changePoint(i, +1, j, 0);
                        changePoint(i, 0, j, +1);
                    }
                    if (map[i - 1][j + 1] == 'X') {
                        changePoint(i, -1, j, 0);
                        changePoint(i, 0, j, +1);
                    }
                    if (map[i + 1][j - 1] == 'X') {
                        changePoint(i, +1, j, 0);
                        changePoint(i, 0, j, -1);
                    }
                    if (bufferMap[i][j] == '+') {
                        if (bufferMap[i][j - 1] == '+' || map[i][j - 1] == 'C' || map[i][j - 1] == 'E') {
                            wayMatrix[xSize * i + j][xSize * (i) + j - 1] = 1;
                            wayMatrix[xSize * (i) + j - 1][xSize * i + j] = 1;
                        }
                        if (bufferMap[i][j + 1] == '+' || map[i][j + 1] == 'C' || map[i][j + 1] == 'E') {
                            wayMatrix[xSize * i + j][xSize * (i) + j + 1] = 1;
                            wayMatrix[xSize * (i) + j + 1][xSize * i + j] = 1;
                        }
                        if (bufferMap[i - 1][j] == '+' || map[i - 1][j] == 'C' || map[i - 1][j] == 'E') {
                            wayMatrix[xSize * i + j][xSize * (i - 1) + j] = 1;
                            wayMatrix[xSize * (i - 1) + j][xSize * i + j] = 1;
                        }
                        if (bufferMap[i + 1][j] == '+' || map[i + 1][j] == 'C' || map[i + 1][j] == 'E') {
                            wayMatrix[xSize * i + j][xSize * (i + 1) + j] = 1;
                            wayMatrix[xSize * (i + 1) + j][xSize * i + j] = 1;
                        }

                    }
                }
            }
        }
        logger.log("Построили матрицу путей");
        return this;
    }

    // Вспомогательный метод для предыдущего метода
    private void changePoint(int i, int di, int j, int dj) {
        if (map[i + di][j + dj] != 'X' && map[i + di][j + dj] != 'T') {
            int place = xSize * (i + di) + j + dj;
            wayMatrix[xSize * i + j][place] = 1;
            wayMatrix[place][xSize * i + j] = 1;
            bufferMap[i][j] = '+';
        }
    }

    // Создание всевозможных вариаций наборов путей для решения задачи
    public List<Integer[]> createWaysPairs(Integer[] count) {
        List<Integer[]> answer = new ArrayList<>();
        Integer[] buffer = new Integer[count.length];
        for (int i = 0; i < count.length; i++) {
            buffer[i] = 0;
        }
        int i = count.length - 1;
        while (checkArray(buffer, count)) {
            answer.add(buffer.clone());
            buffer[i]++;

            while (Objects.equals(buffer[i], count[i])) {
                buffer[i--] = 0;

                if (!Objects.equals(buffer[i], count[i])) {
                    buffer[i]++;
                }
            }
            i = count.length - 1;
        }
        answer.add(buffer.clone());
        logger.log("Было найдено " + answer.size() + " не обязательно оптимальных решений задачи");
        return answer;
    }

    // Вспомогательный метод для предыдущего метода
    private boolean checkArray(Integer[] array, Integer[] count) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != count[i] - 1) return true;
        }
        return false;
    }

    public Matrix createGraph() {
        this.graph = new Graph(this.wayMatrix);
        logger.log("Был создан объект Graph");
        return this;
    }

    // Адаптер для метода нахождения всех путей
    public List<Way> searchWays(int i, int j) {
        return graph.searchWays(i, j, null);
    }

    // Метод для нахождения минимальной конструкции и изменения схемы
    public Matrix changeMap() {
        Map<Integer, List<Pair<Integer, Way>>> allWays = new HashMap<>();
        Integer[] buffer = new Integer[this.consumers.size()];
        Arrays.fill(buffer, 0);
        int i = 0;

        for (int consumer : this.consumers) {
            for (int source : this.sources) {
                List<Way> waysForSource = searchWays(source, consumer);
                List<Pair<Integer, Way>> ways = new ArrayList<>();

                buffer[i] += waysForSource.size();
                for (Way way : waysForSource) {
                    ways.add(new Pair<>(source, way));
                }
                if (allWays.containsKey(consumer)) {
                    allWays.get(consumer).addAll(ways);
                } else {
                    allWays.put(consumer, ways);
                }
            }
            i++;
        }
        List<List<Pair<Integer, Way>>> variants = new ArrayList<>();
        List<Integer[]> ways = createWaysPairs(buffer);

        for (Integer[] points : ways) {
            List<Pair<Integer, Way>> variant = new ArrayList<>();

            for (int k = 0; k < points.length; k++) {
                variant.add(allWays.get(this.consumers.get(k)).get(points[k]));
            }
            variants.add(variant);
        }

        int[] sources = new int[this.sources.size()];
        int minSumWays = Integer.MAX_VALUE;
        Set<Integer> minVariant = new HashSet<>();
        for (List<Pair<Integer, Way>> pairList : variants) {
            Arrays.fill(sources, 0);
            Set<Integer> schema = new HashSet<>();
            for (Pair<Integer, Way> integerWayPair : pairList) {
                for (int l = 0; l < this.sources.size(); l++) {
                    if (Objects.equals(this.sources.get(l), integerWayPair.first)) {
                        sources[l] += 1;
                        schema.addAll(integerWayPair.second.visited);
                        break;
                    }
                }
            }
            if (schema.size() < minSumWays && Arrays.stream(sources).allMatch(e -> e <= 3)) {
                minSumWays = schema.size();
                minVariant = schema;
            }
        }
        logger.log("Из выриантов был найден оптимальный по размеру");
        printWay(minVariant);
        logger.log("Изменили считаную схему согласно оптимальному решению");
        return this;
    }

    // Вспомогательный метод для предыдущего метода
    protected void printWay(Set<Integer> way) {
        this.size = 0;
        for (int i : way) {
            int x = i % 30;
            int y = (i - i % 30) / 30;
            this.size += setS(y, x);
        }
    }

    // Вспомогательный метод для предыдущего метода
    protected int setS(int i, int j) {
        if (this.map[i][j] != 'E' && this.map[i][j] != 'C') {
            this.map[i][j] = 'S';
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder().append(this.size).append("\n");

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                str.append(map[i][j]).append(" ");
            }
            str.append("\n");
        }

        return str.toString();
    }
}
