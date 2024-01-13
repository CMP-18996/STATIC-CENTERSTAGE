package org.firstinspires.ftc.teamcode.opmode.autonomous;

import static org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem.UpperHorizontalState.C;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToBoardCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.ToSpikeMarkCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.AutoDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.GroundDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

@Autonomous(name = "Blue Close")
public class BlueCloseAuto extends CommandOpMode {
    public Robot robot;
    public MecanumDrive drive;
    public IntakeSubsystem intakeSubsystem;
    public DepositSubsystem depositSubsystem;
    public LiftSubsystem liftSubsystem;
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
        intakeSubsystem = new IntakeSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        liftSubsystem = new LiftSubsystem(robot);
        super.register(robot.camera, intakeSubsystem, depositSubsystem, liftSubsystem);
        //parallel command group op
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new ToSpikeMarkCommand(drive),
                        new TakeFromIntakeCommand(liftSubsystem, depositSubsystem, intakeSubsystem),
                        new GroundDropCommand(depositSubsystem, liftSubsystem),
                        new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                        new ToBoardCommand(drive),
                        new WaitCommand(1000),
                        new ConditionalCommand(new AutoDropCommand(depositSubsystem, liftSubsystem, robot.camera, drive, DepositSubsystem.LowerHorizontalState.A),
                                new ConditionalCommand(new AutoDropCommand(depositSubsystem, liftSubsystem, robot.camera, drive, DepositSubsystem.LowerHorizontalState.E),
                                        new AutoDropCommand(depositSubsystem, liftSubsystem, robot.camera, drive, DepositSubsystem.LowerHorizontalState.C),
                                        () -> GlobalVariables.position == GlobalVariables.Position.RIGHT),
                                () -> GlobalVariables.position == GlobalVariables.Position.LEFT),
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
