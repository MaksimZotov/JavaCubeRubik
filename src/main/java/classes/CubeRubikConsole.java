package classes;

import java.util.ArrayList;

public class CubeRubikConsole<T> extends CubeRubik<T> {
    public CubeRubikConsole(int n, boolean isRandom, T first, T second, T third, T fourth, T fifth, T sixth) {
        super(n, isRandom, first, second, third, fourth, fifth, sixth);
    }

    public void showSidesInConsole() {
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= n + 2; j++)
                System.out.print(" ");
            for (int j = 0; j < n; j++)
                System.out.print(getColor(Side.TOP, i, j));
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(getColor(Side.LEFT, i, j));
            System.out.print("  ");
            for (int j = 0; j < n; j++)
                System.out.print(getColor(Side.NEAR, i, j));
            System.out.print("  ");
            for (int j = 0; j < n; j++)
                System.out.print(getColor(Side.RIGHT, i, j));
            System.out.print("               ");
            for (int j = 0; j < n; j++)
                System.out.print(getColor(Side.FAR, i, j));
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= n + 2; j++)
                System.out.print(" ");
            for (int j = 0; j < n; j++)
                System.out.print(getColor(Side.BOTTOM, i, j));
            System.out.println();
        }
    }

    public void showSidesInConsoleOnBaseOfStateOfSide() {
        ArrayList<ArrayList<T>> top = getStateOfSide(Side.TOP);
        ArrayList<ArrayList<T>> left = getStateOfSide(Side.LEFT);
        ArrayList<ArrayList<T>> near = getStateOfSide(Side.NEAR);
        ArrayList<ArrayList<T>> right = getStateOfSide(Side.RIGHT);
        ArrayList<ArrayList<T>> far = getStateOfSide(Side.FAR);
        ArrayList<ArrayList<T>> bottom = getStateOfSide(Side.BOTTOM);
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= n + 2; j++)
                System.out.print(" ");
            for (int j = 0; j < n; j++)
                System.out.print(top.get(i).get(j));
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(left.get(i).get(j));
            System.out.print("  ");
            for (int j = 0; j < n; j++)
                System.out.print(near.get(i).get(j));
            System.out.print("  ");
            for (int j = 0; j < n; j++)
                System.out.print(right.get(i).get(j));
            System.out.print("               ");
            for (int j = 0; j < n; j++)
                System.out.print(far.get(i).get(j));
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= n + 2; j++)
                System.out.print(" ");
            for (int j = 0; j < n; j++)
                System.out.print(bottom.get(i).get(j));
            System.out.println();
        }
    }
}
