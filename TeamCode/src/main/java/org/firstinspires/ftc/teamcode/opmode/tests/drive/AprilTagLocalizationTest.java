package org.firstinspires.ftc.teamcode.opmode.tests.drive;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

@Disabled
@TeleOp(name="April Tag Localization Test")
public class AprilTagLocalizationTest extends CommandOpMode {
    public Robot robot;

    public MecanumDrive drive;
    ToTagCommand t;
    @Override
    public void initialize() {
        GlobalVariables.color = GlobalVariables.Color.RED;
        GlobalVariables.opMode = GlobalVariables.OpMode.AUTO;

        telemetry.addData("Status","Initalizing...");
        telemetry.update();
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0, Math.toRadians(0)));
        t = new ToTagCommand(robot.camera, drive);

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        t,
                        new InstantCommand(() -> {
                            telemetry.addLine("Finished");
                            telemetry.update();
                        })
                )
        );
        robot.camera.startPropProcessing();
        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }

    @Override
    public void run() {
        robot.camera.startPropProcessing();
        CommandScheduler.getInstance().run();
    }
}
