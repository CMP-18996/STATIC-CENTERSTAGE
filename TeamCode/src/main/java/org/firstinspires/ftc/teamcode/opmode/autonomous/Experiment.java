package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.TwoPlusFourAuto;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

@Config
@Autonomous(name = "pain au chocolat")
public class Experiment extends CommandOpMode {
    public Robot robot;
    public SampleMecanumDrive drive;
    public IntakeSubsystem intakeSubsystem;
    public DepositSubsystem depositSubsystem;
    public LiftSubsystem liftSubsystem;
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
        intakeSubsystem = new IntakeSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        liftSubsystem = new LiftSubsystem(robot);
        super.register(robot.camera, intakeSubsystem, depositSubsystem, liftSubsystem);
        CommandScheduler.getInstance().schedule(
                new TwoPlusFourAuto(depositSubsystem, liftSubsystem, robot.camera, drive, intakeSubsystem)
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
