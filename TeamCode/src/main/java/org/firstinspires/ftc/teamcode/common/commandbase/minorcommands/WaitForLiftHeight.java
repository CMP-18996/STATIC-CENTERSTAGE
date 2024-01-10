package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;

public class WaitForLiftHeight extends CommandBase {
    LiftSubsystem liftSubsystem;
    int height;
    public WaitForLiftHeight(LiftSubsystem liftSubsystem, int height) {
        this.liftSubsystem = liftSubsystem;
        this.height = height;
    }

    @Override
    public boolean isFinished() {
        return liftSubsystem.abovePosition(height);
    }
}
