package org.firstinspires.ftc.teamcode.opmode.teleop.finalized;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;

@TeleOp(name="Final TeleOp", group="LinearOpMode")
public class Teleop extends CommandOpMode {
    private Robot robot;
    @Override
    public void initialize() {
        robot = new Robot(hardwareMap, Robot.OpModes.TELEOP);

    }

    @Override
    public void run() {

    }
}
