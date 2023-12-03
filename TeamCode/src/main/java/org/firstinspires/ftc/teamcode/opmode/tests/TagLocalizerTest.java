package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToBoardCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

@Disabled
@TeleOp(name = "tag localizer", group="test")
public class TagLocalizerTest extends CommandOpMode {
    public Robot robot;
    public MecanumDrive drive;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        GlobalVariables.color = GlobalVariables.Color.RED;
        GlobalVariables.distance = GlobalVariables.Distance.REDCLOSE;
        GlobalVariables.opMode = GlobalVariables.OpMode.AUTO;

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new MecanumDrive(hardwareMap, GlobalVariables.distance.getP());

        schedule(new SequentialCommandGroup(
                new ToBoardCommand(drive),
                new ToTagCommand(robot.camera, drive)
        ));

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
