package org.firstinspires.ftc.teamcode.opmode.lame;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.other.DumbCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

@Disabled
@Autonomous(name = "OFFICIAL PUSHBOT AUTO")
public class LameAuto extends CommandOpMode {
    public Robot robot;
    public MecanumDrive drive;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        //TODO: at competition change distance, color to actual location, color
        GlobalVariables.color = GlobalVariables.Color.RED;
        GlobalVariables.distance = GlobalVariables.Distance.REDCLOSE;
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new MecanumDrive(hardwareMap, GlobalVariables.distance.getP());

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new WaitCommand(1), //wait at least 5 for box to be detected well
                        new DumbCommand(drive), //drives to correct spike mark
                        new InstantCommand(() -> telemetry.addLine("Complete!"))
                )
        );
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
