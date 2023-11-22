package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.DriveToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.StackCommand;
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
        GlobalVariables.distance = GlobalVariables.Distance.REDFAR;
        GlobalVariables.opMode = GlobalVariables.OpMode.AUTO;

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new MecanumDrive(hardwareMap, GlobalVariables.distance.getP());
        register(robot.camera);

        CommandScheduler.getInstance().schedule(
                new ApproachCommand(drive),
                new DriveToTagCommand(robot.camera, drive),
                new PropPixelCommand(telemetry),
                new StackCommand(drive),
                new DriveToTagCommand(robot.camera, drive),
                new StackCommand(drive),
                new DriveToTagCommand(robot.camera, drive));

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
