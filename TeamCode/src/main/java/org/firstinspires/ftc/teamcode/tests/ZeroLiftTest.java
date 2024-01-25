package org.firstinspires.ftc.teamcode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.ZeroLiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

@Disabled
@TeleOp
public class ZeroLiftTest extends CommandOpMode {
    Robot robot;
    LiftSubsystem liftSubsystem;
    @Override
    public void initialize() {
        super.reset();
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);
        super.schedule(
                new SequentialCommandGroup(
                        new ZeroLiftCommand(liftSubsystem),
                        new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.HEIGHTTWO)
                )
        );
    }

    @Override
    public void run() {
        super.run();
    }
}
