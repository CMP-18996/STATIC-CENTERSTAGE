package org.firstinspires.ftc.teamcode.common;

public class GlobalVariables {
    public enum Color {
        BLUE,
        RED
    }

    public enum Position {
        LEFT,
        MIDDLE,
        RIGHT
    }

    public static Color color;
    public static Position position;

    public GlobalVariables(Color color) {
        this.color = color;
    }
}
