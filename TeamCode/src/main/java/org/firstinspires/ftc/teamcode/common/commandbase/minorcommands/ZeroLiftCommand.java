package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.controller.PController;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ZeroLiftCommand extends SequentialCommandGroup {
    public ZeroLiftCommand(LiftSubsystem liftSubsystem) {
        addCommands(
                /* new ParallelDeadlineGroup(
                        // new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.GROUND),
                        // new WaitCommand(1000)
                        new WaitCommand(1000),
                        new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.GROUND)
                ),
                new InstantCommand(() -> {
                    liftSubsystem.robot.liftOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    liftSubsystem.robot.liftTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    liftSubsystem.robot.liftOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    liftSubsystem.robot.liftTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }) */
                new SequentialCommandGroup(
                        new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.GROUND),
                        new WaitCommand(500)
                ),
                // new WaitCommand(500),
                new InstantCommand(() -> {
                    liftSubsystem.robot.liftOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    liftSubsystem.robot.liftTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    liftSubsystem.robot.liftOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    liftSubsystem.robot.liftTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }),
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.ZERO)
        );
    }
}
