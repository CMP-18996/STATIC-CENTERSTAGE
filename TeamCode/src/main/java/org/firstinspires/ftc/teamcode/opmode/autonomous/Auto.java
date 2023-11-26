package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.TakeFromDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.DriveToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.DriveToStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ApproachCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.PropPixelCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

@Autonomous(name = "Auto")
public class Auto extends CommandOpMode {
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
        super.register(robot.camera);

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new ApproachCommand(drive),
                        new WaitCommand(500),
                        new DriveToTagCommand(robot.camera, drive),
                        new PropPixelCommand(telemetry),
                        new ParallelCommandGroup(
                                new DriveToStackCommand(drive),
                                new SequentialCommandGroup(
                                        new WaitCommand(2000)
                                        //n/ew TakeFromDepositCommand(),
                                )
                        ),
                        new DriveToStackCommand(drive),
                        new DriveToTagCommand(robot.camera, drive)
                )
        );

        robot.camera.startPropProcessing();
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
