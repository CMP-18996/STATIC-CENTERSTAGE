package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

public class TwoSlotDetectedCommand extends CommandBase {
    IntakeSubsystem intakeSubsystem;
    public TwoSlotDetectedCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    @Override
    public boolean isFinished() {
        return intakeSubsystem.checkFilled();
    }
}
