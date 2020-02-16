package classes;

import java.util.ArrayList;
import java.util.Random;

public class CubeRubik<T> {
    private int[][][] sidesMain;
    private int[][][] sidesForXRotation, sidesForYRotation, sidesForZRotation;
    private ArrayList<T> sixValuesOnSides;
    private enum Direction { UP, RIGHT, DOWN, LEFT }

    public final int n;
    public enum Axis { X, Y, Z }
    public enum Side {  LEFT, NEAR, RIGHT, FAR, TOP, BOTTOM }

    CubeRubik(int n, boolean isRandom, T first, T second, T third, T fourth, T fifth, T sixth) {
        sixValuesOnSides = new ArrayList();
        sixValuesOnSides.add(first);
        sixValuesOnSides.add(second);
        sixValuesOnSides.add(third);
        sixValuesOnSides.add(fourth);
        sixValuesOnSides.add(fifth);
        sixValuesOnSides.add(sixth);
        for (int i = 0; i < 5; i++)
            for (int j = i + 1; j < 6; j++)
                if(sixValuesOnSides.get(i).getClass() != sixValuesOnSides.get(j).getClass())
                    throw new IllegalArgumentException("Все значения должны быть одного типа");
        this.n = n;
        sidesMain = new int[6][n][n];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    sidesMain[i][j][k] = i;
        setSidesForAxisRotation();
        if (isRandom) {
            Random rnd = new Random();
            for (int i = 0; i < 10; i++) {
                int layer = rnd.nextInt(n) + 1;
                int indexOfAxis = rnd.nextInt(3) + 1;
                switch (indexOfAxis) {
                    case 1: rotateClockwise(Axis.X, layer, layer); break;
                    case 2: rotateClockwise(Axis.Y, layer, layer); break;
                    default: rotateClockwise(Axis.Z, layer, layer);
                }
            }
        }
    }

    private void setSidesForAxisRotation () {
        sidesForXRotation = new int[4][n][n];
        sidesForYRotation = new int[4][n][n];
        sidesForZRotation = new int[4][n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                sidesForXRotation[0][i][j] = sidesMain[0][i][j];
                sidesForXRotation[1][i][j] = sidesMain[4][i][j];
                sidesForXRotation[2][i][j] = sidesMain[2][i][j];
                sidesForXRotation[3][i][j] = sidesMain[5][i][j];
                sidesForYRotation[0][i][j] = sidesMain[1][i][j];
                sidesForYRotation[1][i][j] = sidesMain[4][i][j];
                sidesForYRotation[2][i][j] = sidesMain[3][j][i];
                sidesForYRotation[3][i][j] = sidesMain[5][i][j];
                sidesForZRotation[0][i][j] = sidesMain[0][i][j];
                sidesForZRotation[1][i][j] = sidesMain[3][j][i];
                sidesForZRotation[2][i][j] = sidesMain[2][i][j];
                sidesForZRotation[3][i][j] = sidesMain[1][i][j];
            }
        rotateOnlySurface(sidesForXRotation, 2, 1);
        rotateOnlySurface(sidesForZRotation, 1, 1);
        rotateOnlySurface(sidesForXRotation, 1, 2);
        rotateOnlySurface(sidesForXRotation, 0, 3);
        rotateOnlySurface(sidesForYRotation, 2, 3);
        rotateOnlySurface(sidesForYRotation, 0, 3);
        rotateOnlySurface(sidesForYRotation, 1, 3);
        rotateOnlySurface(sidesForYRotation, 2, 3);
        rotateOnlySurface(sidesForYRotation, 3, 3);
    }

    private void rotateOnlySurface(int[][][] sides, int numberOfSide, int amountOfRotations) {
        int xMin = 0, yMin = 0, xMax = n - 1, yMax = n - 1;
        int x = 0, y = 0;
        int laps = 0;
        Direction direction = Direction.RIGHT;
        int[][] temp = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                temp[i][j] = sides[numberOfSide][i][j];
        int count = 0;
        while (count < n * n) {
            count++;
            moveCell(sides, temp, numberOfSide, x, y, laps, amountOfRotations);
            if (direction == Direction.RIGHT) {
                if (y < yMax) y++;
                else {
                    yMax--;
                    x++;
                    direction = Direction.DOWN;
                }
            }
            else if (direction == Direction.DOWN) {
                if (x < xMax) x++;
                else {
                    xMax--;
                    y--;
                    direction = Direction.LEFT;
                }
            }
            else if (direction == Direction.LEFT) {
                if (y > yMin) y--;
                else {
                    xMin++;
                    x--;
                    direction = Direction.UP;
                }
            }
            else {
                if (x > xMin) x--;
                else {
                    yMin++;
                    y++;
                    direction = Direction.RIGHT;
                    laps++;
                }
            }
        }
    }

    private void moveCell(int[][][] sides, int[][] temp, int numberOfSide, int x, int y, int laps, int amountOfRotations) {
        int numberColor = temp[x][y];
        int xCur = x;
        int yCur = y;
        int max = n - laps - 1;
        int min = laps;
        int steps = n - laps * 2 - 1;
        for (int i = 0; i < steps * amountOfRotations; i++) {
            if (xCur == min && yCur < max) yCur++;
            else if (yCur == max && xCur < max) xCur++;
            else if (xCur == max && yCur > min) yCur--;
            else if (yCur == min && xCur > min) xCur--;
        }
        sides[numberOfSide][xCur][yCur] = numberColor;
    }

    private void rotatePerpendicularCells(Axis axis, int start, int end) {
        int[][][] sidesForAxisOfRotation;
        if (axis == Axis.X) sidesForAxisOfRotation = sidesForXRotation;
        else if (axis == Axis.Y) sidesForAxisOfRotation = sidesForYRotation;
        else sidesForAxisOfRotation = sidesForZRotation;
        int[][][] temp = new int[4][n][n];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    temp[i][j][k] = sidesForAxisOfRotation[i][j][k];
        for (int i = 0; i < 3; i++)
            for (int j = start - 1; j < end; j++)
                for (int k = 0; k < n; k++)
                    sidesForAxisOfRotation[i + 1][j][k] = temp[i][j][k];
        for (int j = start - 1; j < end; j++)
            for (int k = 0; k < n; k++) {
                sidesForAxisOfRotation[0][j][k] = temp[3][j][k];
                sidesForAxisOfRotation[3][j][k] = temp[2][j][k];
            }
        if (axis == Axis.X) {
            sidesMain[0] = sidesForXRotation[0];
            sidesMain[4] = sidesForXRotation[1];
            sidesMain[2] = sidesForXRotation[2];
            sidesMain[5] = sidesForXRotation[3];
            rotateOnlySurface(sidesMain, 0, 1);
            rotateOnlySurface(sidesMain, 4, 2);
            rotateOnlySurface(sidesMain, 2, 3);
        }
        else if (axis == Axis.Y) {
            sidesMain[1] = sidesForYRotation[0];
            sidesMain[4] = sidesForYRotation[1];
            sidesMain[5] = sidesForYRotation[3];
            rotateOnlySurface(sidesMain, 1, 1);
            rotateOnlySurface(sidesMain, 4, 1);
            rotateOnlySurface(sidesMain, 5, 1);
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    sidesMain[3][i][j] = sidesForYRotation[2][j][i];
            rotateOnlySurface(sidesMain, 3, 2);
        }
        else {
            sidesMain[0] = sidesForZRotation[0];
            sidesMain[2] = sidesForZRotation[2];
            sidesMain[1] = sidesForZRotation[3];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    sidesMain[3][i][j] = sidesForZRotation[1][j][i];
            rotateOnlySurface(sidesMain, 3, 1);
        }
    }

    public void rotateClockwise(Axis axisOfRotation, int start, int end) {
        if (start < 1 || start > n || end < 1 || end > n || start > end)
            throw new IllegalArgumentException("Неверно введён интервал для поворота");
        boolean rotateSurfaceStart = start == 1;
        boolean rotateSurfaceEnd = end == n;
        if (axisOfRotation == Axis.X) {
            if (rotateSurfaceStart) rotateOnlySurface(sidesMain, 1, 1);
            if (rotateSurfaceEnd) rotateOnlySurface(sidesMain, 3, 1);
            rotatePerpendicularCells(Axis.X, start, end);
        } else if (axisOfRotation == Axis.Y) {
            if (rotateSurfaceStart) rotateOnlySurface(sidesMain, 2, 1);
            if (rotateSurfaceEnd) rotateOnlySurface(sidesMain, 0,  3);
            rotatePerpendicularCells(Axis.Y, start, end);
        } else {
            if (rotateSurfaceStart) rotateOnlySurface(sidesMain, 4, 1);
            if (rotateSurfaceEnd) rotateOnlySurface(sidesMain, 5, 3);
            rotatePerpendicularCells(Axis.Z, start, end);
        }
        setSidesForAxisRotation();
    }

    private int getIndexOfSide(Side side) {
        int index;
        switch (side) {
            case LEFT: index = 0; break;
            case NEAR: index = 1; break;
            case RIGHT: index = 2; break;
            case FAR: index = 3; break;
            case TOP: index = 4; break;
            default: index = 5;
        }
        return index;
    }

    public ArrayList<ArrayList<T>> getStateOfSide(Side side) {
        ArrayList<ArrayList<T>> cellsOfSide = new ArrayList<ArrayList<T>>();
        for (int i = 0; i < n; i++) {
            cellsOfSide.add(new ArrayList<T>());
            for (int j = 0; j < n; j++)
                cellsOfSide.get(i).add(sixValuesOnSides.get(sidesMain[getIndexOfSide(side)][i][j]));
        }
        return cellsOfSide;
    }

    public T getColor(Side side, int x, int y) {
        return sixValuesOnSides.get(sidesMain[getIndexOfSide(side)][x][y]);
    }

    public int[][][] getSidesForTesting() {
        return sidesMain;
    }
}
