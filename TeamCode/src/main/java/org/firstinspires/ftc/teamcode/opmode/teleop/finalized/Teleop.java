package org.firstinspires.ftc.teamcode.opmode.teleop.finalized;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;

@TeleOp(name="Final TeleOp", group="Official")
public class Teleop extends CommandOpMode {
    private Robot robot;
    @Override
    public void initialize() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();
        CommandScheduler.getInstance().reset();
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;

        robot = new Robot(hardwareMap);
        schedule();

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();

        telemetry.addData("Status", "Running...");
        telemetry.update();
    }
}
