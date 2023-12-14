package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem.LiftHeight;

public class LiftCommand extends CommandBase {
    LiftSubsystem lift;
    LiftHeight height;

    public LiftCommand(LiftSubsystem liftSubsystem, LiftHeight height) {
        this.lift = liftSubsystem;
        this.height = height;
        lift.updateState(height);
    }

    @Override
    public void initialize() {
         // change to updateStatePID later
    }

    @Override
    public boolean isFinished() {
        return lift.motorsFinished();
    }
}
