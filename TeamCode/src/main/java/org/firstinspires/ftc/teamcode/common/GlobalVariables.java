package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.roadrunner.Pose2d;

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
    public enum Distance {
        CLOSE(new Pose2d(12.18, 59.18, Math.toRadians(-90))),
        FAR(new Pose2d(-35.08, 59.77, Math.toRadians(-90)));
        private final Pose2d p;
        Distance(Pose2d p) {
            this.p = p;
        }
        public Pose2d getP() {
            return p;
        }
    }

    public static Color color;
    public static Position position = Position.UNDETECTED;

    public GlobalVariables(Color color) {
        this.color = color;
    }
}
