package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

@TeleOp
public class TakeFromIntakeTest extends CommandOpMode {
    Robot robot;
    LiftSubsystem liftSubsystem;
    DepositSubsystem depositSubsystem;
    IntakeSubsystem intakeSubsystem;
    public static int initialToFourBarTime = 4000;
    public static int fourBarToGrabberTime = 4000;
    public static int grabberToFourBarTime = 4000;
    public static int fourBarToCoverTime = 4000;
    public static int coverToStasisTime = 4000;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        intakeSubsystem = new IntakeSubsystem(robot);
        CommandScheduler.getInstance().schedule(
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT),
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.PICKUPHEIGHT),
                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.OPEN),
                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.STOPPED),
                new WaitCommand(initialToFourBarTime),

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.DOWN),
                new WaitCommand(fourBarToGrabberTime),

                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT),
                new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT),
                new WaitCommand(grabberToFourBarTime), // might need to be tuned

                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.STASIS),
                new WaitCommand(fourBarToCoverTime),

                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                new WaitCommand(coverToStasisTime),

                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
        );
    }

    @Override
    public void run() {

    }
}
