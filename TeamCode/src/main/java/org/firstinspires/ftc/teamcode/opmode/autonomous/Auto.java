package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.AutoDriveToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.AutoStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.BlueApproachCommand;
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

        GlobalVariables.color = GlobalVariables.Color.BLUE;
        GlobalVariables.distance = GlobalVariables.Distance.CLOSE;
        GlobalVariables.opMode = GlobalVariables.OpMode.AUTO;

        reset();
        robot = new Robot(hardwareMap);
        drive = new MecanumDrive(hardwareMap, GlobalVariables.distance.getP());
        register(robot.camera);

        schedule(
                new BlueApproachCommand(drive),
                new AutoDriveToTagCommand(robot.camera, drive),
                new PropPixelCommand(telemetry),
                new AutoStackCommand(drive),
                new AutoDriveToTagCommand(robot.camera, drive),
                new AutoStackCommand(drive),
                new AutoDriveToTagCommand(robot.camera, drive));

        robot.camera.startPropProcessing();
        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        run();
        telemetry.addData("Status", "Running...");
        telemetry.update();
    }
}
