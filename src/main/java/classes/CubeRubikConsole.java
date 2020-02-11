package classes;

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
}
