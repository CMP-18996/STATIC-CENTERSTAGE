package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.GlobalVariables.Distance;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.AutoDriveToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.AutoStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.BlueApproachCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.PropPixelCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.other.InstructionInterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

@Autonomous(name = "Auto")
public class Auto extends CommandOpMode {
    public Robot robot;
    public MecanumDrive drive;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        GlobalVariables.color = GlobalVariables.Color.BLUE;

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, Robot.OpModes.AUTO);
        drive = new MecanumDrive(hardwareMap, Distance.CLOSE.getP());

        CommandScheduler.getInstance().schedule(
                new BlueApproachCommand(drive, GlobalVariables.Distance.CLOSE),
                new AutoDriveToTagCommand(robot.camera, drive),
                new AutoStackCommand(drive, GlobalVariables.Color.BLUE),
                new AutoDriveToTagCommand(robot.camera, drive),
                new AutoStackCommand(drive, GlobalVariables.Color.BLUE),
                new AutoDriveToTagCommand(robot.camera, drive));

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        robot.camera.startPropProcessing();
        CommandScheduler.getInstance().run();
        telemetry.addData("Status", "Running...");
        telemetry.update();
    }
}
