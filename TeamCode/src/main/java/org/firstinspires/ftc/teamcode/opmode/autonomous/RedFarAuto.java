package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.StackCycleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToBoardCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToSpikeMarkCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

@Autonomous(name = "Red Far")
public class RedFarAuto extends CommandOpMode {
    public Robot robot;
    public MecanumDrive drive;
    /*public IntakeSubsystem intakeSubsystem;
    public DepositSubsystem depositSubsystem;
    public LiftSubsystem liftSubsystem;*/
    @Override
    public void initialize() {
        telemetry.addData("Status","Initializing...");
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
                        new WaitCommand(2000),
                        new ToSpikeMarkCommand(drive),
                        new ToBoardCommand(drive),
                        //new ToTagCommand(robot.camera, drive),
                                new StackCycleCommand(drive),
                                //new SequentialCommandGroup(
                                    //    new WaitCommand(2000), // subject to change
                                  //      new TakeFromDepositCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
                                //)
                        //new ToTagCommand(robot.camera, drive),
                        new StackCycleCommand(drive)
                        //new ToTagCommand(robot.camera, drive)
                )
        );

        robot.camera.startPropProcessing();
        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        robot.camera.startPropProcessing();
        telemetry.addData("Status", "Running...");
        telemetry.update();
    }
}
