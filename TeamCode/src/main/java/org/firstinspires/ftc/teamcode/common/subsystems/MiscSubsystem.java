package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.Robot;

public class MiscSubsystem extends SubsystemBase {
    public Robot robot;

    public MiscSubsystem(Robot robot) {
        this.robot = robot;
    }

    public void releaseDrone() {
        robot.droneServo.setPosition(1);
    }

    public void hang() {
        robot.hangServo1.setPosition(1);
        robot.hangServo2.setPosition(1);
    }
}
