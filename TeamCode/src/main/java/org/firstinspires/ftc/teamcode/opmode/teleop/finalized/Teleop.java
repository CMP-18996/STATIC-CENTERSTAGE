package org.firstinspires.ftc.teamcode.opmode.teleop.finalized;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;

@TeleOp(name="Final TeleOp", group="LinearOpMode")
public class Teleop extends CommandOpMode {
    private Robot robot;
    @Override
    public void initialize() {
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;
        robot = new Robot(hardwareMap);
    }

    @Override
    public void run() {

    }
}
