package org.example;

import java.util.*;

public class Puzzle8 {
    private static int[][] GOAL_STATE;

    private static List<int[][]> solve(int[][] initialState) {
        PriorityQueue<int[][]> openSet = new PriorityQueue<>(Comparator.comparingInt(Puzzle8::heuristic));
        Map<String, int[][]> cameFrom = new HashMap<>();
        Map<String, Integer> gScore = new HashMap<>();

        String initialKey = Arrays.deepToString(initialState);
        openSet.add(initialState);
        gScore.put(initialKey, 0);

        while (!openSet.isEmpty()) {
            int[][] current = openSet.poll();
            String currentKey = Arrays.deepToString(current);

            if (Arrays.deepEquals(current, GOAL_STATE)) {
                return reconstructPath(cameFrom, current);
            }

            for (int[][] neighbor : getNeighbors(current)) {
                String neighborKey = Arrays.deepToString(neighbor);
                int tentativeG = gScore.get(currentKey) + 1;

                if (!gScore.containsKey(neighborKey) || tentativeG < gScore.get(neighborKey)) {
                    cameFrom.put(neighborKey, current);
                    gScore.put(neighborKey, tentativeG);
                    openSet.add(neighbor);
                }
            }
        }
        return null;
    }

    private static int heuristic(int[][] state) {
        int misplaced = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] != 0 && state[i][j] != GOAL_STATE[i][j]) {
                    misplaced++;
                }
            }
        }
        return misplaced;
    }

    private static List<int[][]> getNeighbors(int[][] state) {
        List<int[][]> neighbors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int x = 0, y = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
                int[][] newState = deepCopy(state);
                newState[x][y] = newState[newX][newY];
                newState[newX][newY] = 0;
                neighbors.add(newState);
            }
        }
        return neighbors;
    }

    private static List<int[][]> reconstructPath(Map<String, int[][]> cameFrom, int[][] current) {
        List<int[][]> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = cameFrom.get(Arrays.deepToString(current));
        }
        Collections.reverse(path);
        return path;
    }

    private static int[][] deepCopy(int[][] array) {
        int[][] copy = new int[3][3];
        for (int i = 0; i < 3; i++) {
            copy[i] = array[i].clone();
        }
        return copy;
    }

    private static void printSolution(List<int[][]> solution) {
        if (solution == null) {
            System.out.println("No solution found.");
            return;
        }
        for (int[][] state : solution) {
            for (int[] row : state) {
                System.out.println(Arrays.toString(row));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] initialState = new int[3][3];
        GOAL_STATE = new int[3][3];

        System.out.println("Ingrese el estado inicial (3x3, use 0 para el espacio vacío):");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                initialState[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Ingrese el estado objetivo (3x3, use 0 para el espacio vacío):");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                GOAL_STATE[i][j] = scanner.nextInt();
            }
        }

        scanner.close();

        List<int[][]> solution = solve(initialState);
        printSolution(solution);
    }
}
