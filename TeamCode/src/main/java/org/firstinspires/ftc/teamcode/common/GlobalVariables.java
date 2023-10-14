package org.firstinspires.ftc.teamcode.common;

public class GlobalVariables {
    public enum Color {
        BLUE,
        RED
    }

    public enum Position {
        UNDETECTED,
        LEFT,
        MIDDLE,
        RIGHT
    }

    public static Color color;
    public static Position position = Position.UNDETECTED;

    public GlobalVariables(Color color) {
        this.color = color;
    }
}
