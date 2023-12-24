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

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new WaitCommand(6000),
                        new ToSpikeMarkCommand(drive),
                        //new AutoDropCommand(),
                        new ToBoardCommand(drive),
                        new ToTagCommand(robot.camera, drive),
                        new InstantCommand(() -> telemetry.addLine("Complete!"))
                )
        );
        /*drive.leftFront.setTargetPosition(442);
        drive.rightFront.setTargetPosition(442);
        drive.leftBack.setTargetPosition(442);
        drive.rightBack.setTargetPosition(442);
        drive.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drive.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drive.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drive.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/


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
