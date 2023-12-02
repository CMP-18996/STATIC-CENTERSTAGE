package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

import java.util.concurrent.ConcurrentMap;

@TeleOp(name="April Tag Localization Test")
public class AprilTagLocalizationTest extends CommandOpMode {
    public Robot robot;

    public MecanumDrive drive;
    @Override
    public void initialize() {
        GlobalVariables.color = GlobalVariables.Color.RED;
        GlobalVariables.distance = GlobalVariables.Distance.REDFAR;
        GlobalVariables.opMode = GlobalVariables.OpMode.AUTO;

        telemetry.addData("Status","Initalizing...");
        telemetry.update();
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new MecanumDrive(hardwareMap, GlobalVariables.distance.getP());
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new ToTagCommand(robot.camera, drive),
                        new InstantCommand(() -> {
                            telemetry.addLine("Finished");
                            telemetry.update();
                        })
                )
        );

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }

    @Override
    public void run() {
        robot.camera.startPropProcessing();
        CommandScheduler.getInstance().run();
    }
}