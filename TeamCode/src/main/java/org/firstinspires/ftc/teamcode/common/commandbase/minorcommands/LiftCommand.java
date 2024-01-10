package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem.LiftHeight;

public class LiftCommand extends CommandBase {
    LiftSubsystem lift;
    LiftHeight height;
//    public static int i = 0;

    public LiftCommand(LiftSubsystem liftSubsystem, LiftHeight height) {
        this.lift = liftSubsystem;
        this.height = height;
//        telemetry.addData("Iteration", i);
//        i ++;
    }

    @Override
    public void initialize() {
        lift.updateState(height);
    }

    @Override
    public boolean isFinished() {
        return lift.checkDone(height);
    }
}
