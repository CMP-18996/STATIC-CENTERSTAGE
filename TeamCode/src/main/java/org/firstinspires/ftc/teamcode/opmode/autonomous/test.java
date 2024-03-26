package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.TwoPlusFourAuto;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

@Config
@Autonomous(name = "test")
public class test extends CommandOpMode {
    public Robot robot;
    public SampleMecanumDrive drive;
    public IntakeSubsystem intake;
    public DepositSubsystem deposit;
    public LiftSubsystem lift;
    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        GlobalVariables.color = GlobalVariables.Color.BLUE;
        GlobalVariables.distance = GlobalVariables.Distance.BLUECLOSE;
        GlobalVariables.opMode = GlobalVariables.OpMode.AUTO;
        GlobalVariables.position = GlobalVariables.Position.UNDETECTED;

        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(GlobalVariables.distance.getP());
        intake = new IntakeSubsystem(robot);
        deposit = new DepositSubsystem(robot);
        lift = new LiftSubsystem(robot);
        super.register(robot.camera, intake, deposit, lift);
        CommandScheduler.getInstance().schedule(
                new InstantCommand(),
                new SequentialCommandGroup(
                        new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED),
                        new TakeFromIntakeCommand(lift, deposit, intake),
                        new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                        new WaitCommand(200),
                        new FourBarCommand(deposit, DepositSubsystem.FourBarState.HIGHDROP),
                        new WaitCommand(300),
                        new DepositRotatorCommand(deposit, DepositSubsystem.DepositRotationState.PARALLEL),
                        new WaitCommand(600),
                        new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                        new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                        new WaitCommand(200),

                        new FourBarCommand(deposit, DepositSubsystem.FourBarState.HIGH),
                        new WaitCommand(200),
                        new TakeFromIntakeCommand(lift, deposit, intake),
                        new WaitCommand(200),
                        new FourBarCommand(deposit, DepositSubsystem.FourBarState.HIGHDROP),
                        new WaitCommand(300),
                        new DepositRotatorCommand(deposit, DepositSubsystem.DepositRotationState.PARALLEL),
                        new WaitCommand(600),
                        new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                        new GrabberGripCommand(deposit, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                        new WaitCommand(200),

                        new StasisCommand(lift, deposit, intake)
                )
        );

        robot.camera.startPropProcessing();

        telemetry.addData("Status", "Initialized! Please wait for camera init before playing.");
        switch (GlobalVariables.distance) {
            case BLUECLOSE:
                telemetry.addLine("You are registered for the blue close position.");
                break;
            case BLUEFAR:
                telemetry.addLine("You are registered for the blue far position.");
                break;
            case REDCLOSE:
                telemetry.addLine("You are registered for the red close position.");
                break;
            case REDFAR:
                telemetry.addLine("You are registered for the red far position.");
                break;
        }
        if ((GlobalVariables.distance == GlobalVariables.Distance.BLUECLOSE ||
                GlobalVariables.distance == GlobalVariables.Distance.BLUEFAR) &&
                GlobalVariables.color != GlobalVariables.Color.BLUE) {
            telemetry.addLine("ERROR!!! It appears as if you are using the red setup in a blue position.");
        }
        if ((GlobalVariables.distance == GlobalVariables.Distance.REDCLOSE ||
                GlobalVariables.distance == GlobalVariables.Distance.REDFAR) &&
                GlobalVariables.color != GlobalVariables.Color.RED) {
            telemetry.addLine("ERROR!!! It appears as if you are using the blue setup in a red position.");
        }
        telemetry.update();
        while (opModeInInit()) {
            if (robot.camera.getPropProcessor().objectDetected) {
                switch (GlobalVariables.position) {
                    case LEFT: telemetry.addLine("Detected: Left Position"); break;
                    case RIGHT: telemetry.addLine("Detected: Right Position"); break;
                    case MIDDLE: telemetry.addLine("Detected: Middle Position"); break;
                    default: telemetry.addLine("Detected: Nothing"); break;
                }
                telemetry.update();
            }
        }
        super.schedule(new InstantCommand(() -> robot.camera.stopPropProcessing()));
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        telemetry.addData("Status", "Running...");
        telemetry.update();
        robot.camera.stopPropProcessing();
        drive.update();
    }
}
