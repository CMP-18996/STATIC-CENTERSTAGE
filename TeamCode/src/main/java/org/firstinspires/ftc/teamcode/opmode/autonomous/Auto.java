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
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

@Autonomous(name = "Auto")
public class Auto extends CommandOpMode {
    public Robot robot;
    public MecanumDrive drive;
    /*public IntakeSubsystem intakeSubsystem;
    public DepositSubsystem depositSubsystem;
    public LiftSubsystem liftSubsystem;*/
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
        /*intakeSubsystem = new IntakeSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        liftSubsystem = new LiftSubsystem(robot);
        super.register(robot.camera, intakeSubsystem, depositSubsystem, liftSubsystem);*/

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new ApproachCommand(drive),
                        new DriveToTagCommand(robot.camera, drive),
                        new PropPixelCommand(telemetry),
                        new ParallelCommandGroup(
                                new DriveToStackCommand(drive)
                                /*new SequentialCommandGroup(
                                        new WaitCommand(2000), // subject to change
                                        new TakeFromDepositCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
                                )*/
                        ),
                        new DriveToTagCommand(robot.camera, drive),
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
