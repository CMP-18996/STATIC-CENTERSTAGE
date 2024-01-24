package org.firstinspires.ftc.teamcode.tests.arm;

import com.arcrobotics.ftclib.command.CommandOpMode;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LowerHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.UpperHorizontalMoveCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
@TeleOp(name="X Axis Test")
public class XAxisTest extends CommandOpMode {
     DepositSubsystem deposit;
     DepositSubsystem.LowerHorizontalState lowerDesiredState;
     DepositSubsystem.UpperHorizontalState upperDesiredState;
     DcMotorEx xAdj;
     Robot robot;


    @Override
    public void initialize(){
        lowerDesiredState = DepositSubsystem.LowerHorizontalState.C;
        upperDesiredState = DepositSubsystem.UpperHorizontalState.C;
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        deposit = new DepositSubsystem(robot);
        // xAdj = hardwareMap.get(DcMotorEx.class, "xAdj");
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;

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
