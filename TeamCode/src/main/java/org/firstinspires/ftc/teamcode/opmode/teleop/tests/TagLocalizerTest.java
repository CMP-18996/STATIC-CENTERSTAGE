package org.firstinspires.ftc.teamcode.opmode.teleop.tests;

import android.provider.Settings;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.AutoDriveToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.BlueApproachCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

@TeleOp(name = "tag localizer")
public class TagLocalizerTest extends CommandOpMode {
    public Robot robot;
    public MecanumDrive drive;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();
        GlobalVariables.color = GlobalVariables.Color.RED;
        GlobalVariables.distance = GlobalVariables.Distance.CLOSE;
        GlobalVariables.opMode = GlobalVariables.OpMode.AUTO;

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new MecanumDrive(hardwareMap, GlobalVariables.Distance.CLOSE.getP());

        schedule(new BlueApproachCommand(drive));
        schedule(new WaitCommand(2));
        schedule(new AutoDriveToTagCommand(robot.camera, drive));

        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        sleep(20);
    }
}
