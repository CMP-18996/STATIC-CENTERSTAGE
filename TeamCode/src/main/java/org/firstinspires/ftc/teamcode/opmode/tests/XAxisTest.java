package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.AutoDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.IntakeWait;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.SetReadyToDeposit;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.HangCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.UpperHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.Camera;
import java.util.HashMap;
import java.lang.Integer;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="X Axis Test")
public class XAxisTest extends CommandOpMode {
     DepositSubsystem deposit;
     DepositSubsystem.LowerHorizontalState lowerDesiredState;
     DepositSubsystem.UpperHorizontalState upperDesiredState;
     Robot robot;
    DcMotorEx xAdj;
    @Override
    public void initialize(){
        lowerDesiredState = DepositSubsystem.LowerHorizontalState.C;
        upperDesiredState = DepositSubsystem.UpperHorizontalState.C;
        CommandScheduler.getInstance().reset();
        deposit = new DepositSubsystem(robot);
        xAdj = hardwareMap.get(DcMotorEx.class, "xAdj");
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                    new LowerHorizontalMoveCommand(deposit, lowerDesiredState),
                    new WaitCommand(500),
                    new UpperHorizontalMoveCommand(deposit, upperDesiredState),
                    new WaitCommand(500)
            )
        );

    }
    @Override
    public void run(){
        CommandScheduler.getInstance().run();
    }

}
