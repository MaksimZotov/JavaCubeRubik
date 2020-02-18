package classes;

import java.util.ArrayList;
import java.util.Random;

public class CubeRubik<T> {
    private int[][][] sidesMain;
    private int[][][] sidesForXRotation, sidesForYRotation, sidesForZRotation;
    private ArrayList<T> sixValuesOnSides;

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
        int[][] temp = new int[n][n];
        for (int amount = 0; amount < amountOfRotations; amount++) {
            for (int i = 0; i < n; i++)
                System.arraycopy(sides[numberOfSide][i], 0, temp[i], 0, n);
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    sides[numberOfSide][i][j] = temp[n - j - 1][i];
        }
    }

    private void rotatePerpendicularCells(Axis axis, int start, int end) {
        int[][][] sidesForAxisOfRotation;
        if (axis == Axis.X) sidesForAxisOfRotation = sidesForXRotation;
        else if (axis == Axis.Y) sidesForAxisOfRotation = sidesForYRotation;
        else sidesForAxisOfRotation = sidesForZRotation;
        int[][][] temp = new int[4][n][n];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < n; j++)
                System.arraycopy(sidesForAxisOfRotation[i][j], 0, temp[i][j], 0, n);
        for (int i = 0; i < 3; i++)
            for (int j = start - 1; j < end; j++)
                System.arraycopy(temp[i][j], 0, sidesForAxisOfRotation[i + 1][j], 0, n);
        for (int j = start - 1; j < end; j++)
            System.arraycopy(temp[3][j], 0, sidesForAxisOfRotation[0][j], 0, n);
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

    private void rotate(Axis axis, int start, int end, boolean rotateSurfaceStart, boolean rotateSurfaceEnd,
                        int numberSurfaceStart, int amountOfRotationsStart, int numberSurfaceEnd, int amountOfRotationsEnd) {
        if (rotateSurfaceStart) rotateOnlySurface(sidesMain, numberSurfaceStart, amountOfRotationsStart);
        if (rotateSurfaceEnd) rotateOnlySurface(sidesMain, numberSurfaceEnd, amountOfRotationsEnd);
        rotatePerpendicularCells(axis, start, end);
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

    public void rotateClockwise(Axis axisOfRotation, int start, int end) {
        if (start < 1 || start > n || end < 1 || end > n || start > end)
            throw new IllegalArgumentException("Неверно введён интервал для поворота");
        if (axisOfRotation == Axis.X)
            rotate(Axis.X, start, end, start == 1,  end == n, 1, 1, 3, 1);
        else if (axisOfRotation == Axis.Y)
            rotate(Axis.Y, start, end, start == 1,  end == n, 2, 1, 0, 3);
        else
            rotate(Axis.Z, start, end, start == 1,  end == n, 4, 1, 5, 3);
        setSidesForAxisRotation();
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
