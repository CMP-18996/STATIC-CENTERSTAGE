package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Robot;

public class MiscSubsystem extends SubsystemBase {
    public Robot robot;

    private static double dronePower = 0.7;

    public MiscSubsystem(Robot robot) {
        this.robot = robot;
    }

    public void releaseDrone() {
        // This code is obsolete
        // robot.droneServo.setPosition(1);
        robot.droneMotor.setTargetPosition(2000);
        robot.droneMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.droneMotor.setPower(dronePower);
        // Could set this to run to a position so that it eventually stops running?
        // Need at least 862 run position
    }

    public void hang() {
        robot.hangServo1.setPosition(1);
        robot.hangServo2.setPosition(1);
    }

    public void droneMotorTest() {
        robot.droneMotor.setPower(1);
    }
}
