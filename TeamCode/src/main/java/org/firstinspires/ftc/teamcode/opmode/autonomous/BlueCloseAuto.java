package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToBoardCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToSpikeMarkCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;

@Autonomous(name = "Blue Close")
public class BlueCloseAuto extends CommandOpMode {
    public Robot robot;
    public MecanumDrive drive;
    /*public IntakeSubsystem intakeSubsystem;
    public DepositSubsystem depositSubsystem;
    public LiftSubsystem liftSubsystem;*/
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        GlobalVariables.color = GlobalVariables.Color.BLUE;
        GlobalVariables.distance = GlobalVariables.Distance.BLUECLOSE;
        GlobalVariables.opMode = GlobalVariables.OpMode.AUTO;

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new MecanumDrive(hardwareMap, GlobalVariables.distance.getP());
       /* intakeSubsystem = new IntakeSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        liftSubsystem = new LiftSubsystem(robot);
        super.register(robot.camera, intakeSubsystem, depositSubsystem, liftSubsystem);*/
        //parallel command group op
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new ToSpikeMarkCommand(drive),
                        new WaitCommand(1000),
                        //new GroundDropCommand(),
                        new ToBoardCommand(drive),
                        new ToTagCommand(robot.camera, drive),
                        //new AutoDropCommand replaces tagcommand
                        new InstantCommand(() -> telemetry.addData("Status", "Complete!"))
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
        robot.camera.startPropProcessing();
    }
}
