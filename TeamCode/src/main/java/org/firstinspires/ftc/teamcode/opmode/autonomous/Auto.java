package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.GlobalVariables.Distance;
import org.firstinspires.ftc.teamcode.common.commandbase.AutoDriveToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.AutoStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.BlueApproachCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

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
        drive = new MecanumDrive(hardwareMap, Distance.FAR.getP());
        schedule(new BlueApproachCommand(drive, Distance.FAR));
        schedule(new WaitCommand(2));
        schedule(new AutoDriveToTagCommand(robot, drive, 2));
        schedule(new AutoStackCommand(drive, GlobalVariables.Color.BLUE));
        schedule(new AutoDriveToTagCommand(robot, drive, 2));
        schedule(new AutoStackCommand(drive, GlobalVariables.Color.BLUE));
        schedule(new AutoDriveToTagCommand(robot, drive, 2));

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
