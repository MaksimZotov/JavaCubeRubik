package mainClasses;

import cubeRubikClasses.CubeRubik;
import cubeRubikClasses.CubeRubikConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainProgram {
    public static void main(String[] args) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_GREEN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[39m";
        CubeRubikConsole cube = new CubeRubikConsole(3, false,
                ANSI_RED + "R", ANSI_YELLOW + "Y", ANSI_BLUE + "B", ANSI_PURPLE + "P", ANSI_GREEN + "G", ANSI_WHITE + "W");
        cube.showSidesInConsoleOnBaseOfStateOfSide();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print(ANSI_RESET + "Введите через пробел ось (x,y,z), нач. и кон. слой: ");
            String[] data = reader.readLine().split(" ");
            if ("x".equals(data[0])) cube.rotateClockwise(CubeRubik.Axis.X, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            if ("y".equals(data[0])) cube.rotateClockwise(CubeRubik.Axis.Y, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            if ("z".equals(data[0])) cube.rotateClockwise(CubeRubik.Axis.Z, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            cube.showSidesInConsoleOnBaseOfStateOfSide();
        }
    }
}