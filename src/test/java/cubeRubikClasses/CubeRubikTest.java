package cubeRubikClasses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CubeRubikTest {
    CubeRubik cube;
    int[][][] sidesExpected;

    @Test
    void test1() {
        cube = new CubeRubikConsole(3, false, "1", "2", "3", "4", "5", "6");
        sidesExpected = new int[][][] {
                        { { 0, 0, 0 },
                        { 0, 0, 0 },   // LEFT
                        { 0, 0, 0 } },
                        { { 1, 1, 1 },
                        { 1, 1, 1 },   // NEAR
                        { 1, 1, 1 } },
                        { { 2, 2, 2 },
                        { 2, 2, 2 },   // RIGHT
                        { 2, 2, 2 } },
                        { { 3, 3, 3 },
                        { 3, 3, 3 },   // FAR
                        { 3, 3, 3 } },
                        { { 4, 4, 4 },
                        { 4, 4, 4 },   // TOP
                        { 4, 4, 4 } },
                        { { 5, 5, 5 },
                        { 5, 5, 5 },   // BOTTOM
                        { 5, 5, 5 } },
        };
        assertArrayEquals(cube.getSidesForTesting(), sidesExpected);
    }

    @Test
    void test2() {
        cube = new CubeRubikConsole(3, false, 1, 2, 3, 4, 5, 6);
        cube.rotateClockwise(CubeRubik.Axis.X, 1, 1);
        sidesExpected = new int[][][] {
                        { { 0, 0, 5 },
                        { 0, 0, 5 },   // LEFT
                        { 0, 0, 5 } },
                        { { 1, 1, 1 },
                        { 1, 1, 1 },   // NEAR
                        { 1, 1, 1 } },
                        { { 4, 2, 2 },
                        { 4, 2, 2 },   // RIGHT
                        { 4, 2, 2 } },
                        { { 3, 3, 3 },
                        { 3, 3, 3 },   // FAR
                        { 3, 3, 3 } },
                        { { 4, 4, 4 },
                        { 4, 4, 4 },   // TOP
                        { 0, 0, 0 } },
                        { { 2, 2, 2 },
                        { 5, 5, 5 },   // BOTTOM
                        { 5, 5, 5 } },
        };
        assertArrayEquals(cube.getSidesForTesting(), sidesExpected);
    }

    @Test
    void test3() {
        cube = new CubeRubikConsole(3, false, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        cube.rotateClockwise(CubeRubik.Axis.X, 1, 1);
        cube.rotateClockwise(CubeRubik.Axis.Y, 1, 2);
        sidesExpected = new int[][][] {
                        { { 0, 0, 5 },
                        { 0, 0, 5 },   // LEFT
                        { 0, 0, 5 } },
                        { { 1, 2, 2 },
                        { 1, 5, 5 },   // NEAR
                        { 1, 5, 5 } },
                        { { 4, 4, 4 },
                        { 2, 2, 2 },   // RIGHT
                        { 2, 2, 2 } },
                        { { 3, 0, 0 },
                        { 3, 4, 4 },   // FAR
                        { 3, 4, 4 } },
                        { { 4, 1, 1 },
                        { 4, 1, 1 },   // TOP
                        { 0, 1, 1 } },
                        { { 2, 3, 3 },
                        { 5, 3, 3 },   // BOTTOM
                        { 5, 3, 3 } },
        };
        assertArrayEquals(cube.getSidesForTesting(), sidesExpected);
    }

    @Test
    void test4() {
        cube = new CubeRubikConsole(3, false, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        cube.rotateClockwise(CubeRubik.Axis.X, 1, 1);
        cube.rotateClockwise(CubeRubik.Axis.Y, 1, 2);
        cube.rotateClockwise(CubeRubik.Axis.Z, 2, 3);
        sidesExpected = new int[][][] {
                        { { 0, 0, 5 },
                        { 1, 5, 5 },   // LEFT
                        { 1, 5, 5 } },
                        { { 1, 2, 2 },
                        { 2, 2, 2 },   // NEAR
                        { 2, 2, 2 } },
                        { { 4, 4, 4 },
                        { 4, 4, 3 },   // RIGHT
                        { 4, 4, 3 } },
                        { { 3, 0, 0 },
                        { 5, 0, 0 },   // FAR
                        { 5, 0, 0 } },
                        { { 4, 1, 1 },
                        { 4, 1, 1 },   // TOP
                        { 0, 1, 1 } },
                        { { 3, 3, 3 },
                        { 3, 3, 3 },   // BOTTOM
                        { 2, 5, 5 } },
        };
        assertArrayEquals(cube.getSidesForTesting(), sidesExpected);
    }
}