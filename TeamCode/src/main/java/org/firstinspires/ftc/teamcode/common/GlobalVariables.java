package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;

@Config
public class GlobalVariables {
    //team color (must be inputted)
    public enum Color {
        BLUE,
        RED
    }
    //prop position (updated in run)
    public enum Position {
        UNDETECTED,
        LEFT,
        MIDDLE,
        RIGHT
    }
    //robot position (ruh roh)
    public enum Distance {
        BLUECLOSE(new Pose2d(12, 61, Math.toRadians(-90))),
        BLUEFAR(new Pose2d(-35, 61, Math.toRadians(-90))),
        REDCLOSE(new Pose2d(12, -61, Math.toRadians(90))),
        REDFAR(new Pose2d(-35, -61, Math.toRadians(90)));
        private final Pose2d p;
        Distance(Pose2d p) {
            this.p = p;
        }
        public Pose2d getP() {
            return p;
        }
    }
    //opmode type (set in program type)
    public enum OpMode {
        AUTO,
        TELEOP
    }
    public static Color color = Color.RED;
    public static Position position = Position.UNDETECTED;
    public static Distance distance = Distance.REDFAR;
    public static OpMode opMode = OpMode.TELEOP;
    public GlobalVariables(Color color) {
        GlobalVariables.color = color;
    }
}
