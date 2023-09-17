package org.firstinspires.ftc.teamcode.common.drive;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;

import org.firstinspires.ftc.teamcode.common.Robot;


public class Drive extends SubsystemBase {
    private final MecanumDrive drive;
    private double strafe = 0;
    private double forward = 0;
    private double turn = 0;
    public Drive(Robot robot) {
        drive = new MecanumDrive(robot.leftFront, robot.rightFront, robot.leftRear, robot.rightRear);
    }

    public MecanumDrive getDrive() { return drive; }
    public void calculatePower(double lx, double ly, double rx) {
    }
    public void manualPower(double strafe, double forward, double turn) {
        this.strafe = strafe;
        this.forward = forward;
        this.turn = turn;
    }
    @Override
    public void periodic() {
        drive.driveRobotCentric(strafe, forward, turn);
    }
}
