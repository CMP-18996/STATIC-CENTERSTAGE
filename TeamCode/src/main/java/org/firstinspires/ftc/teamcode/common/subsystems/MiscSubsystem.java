package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Robot;

public class MiscSubsystem extends SubsystemBase {
    public Robot robot;

    // private static double dronePower = 1.0;

    public MiscSubsystem(Robot robot) {
        this.robot = robot;
    }

    public void releaseDrone(double dronePower) {
        robot.droneMotor.setPower(dronePower);
    }

    public void hang(double hangPower) {
        robot.hangServo1.setPower(hangPower);
        robot.hangServo2.setPower(hangPower);
    }


    public void droneMotorTest() {
        robot.droneMotor.setPower(1);
    }
}
