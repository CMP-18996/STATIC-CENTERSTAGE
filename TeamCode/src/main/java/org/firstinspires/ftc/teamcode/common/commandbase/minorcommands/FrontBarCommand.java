package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

public class FrontBarCommand extends CommandBase {
    IntakeSubsystem intake;

    public FrontBarCommand(IntakeSubsystem intakeSubsystem, IntakeSubsystem.FrontBarState frontBarState) {
        this.intake = intakeSubsystem;
        intake.updateFrontBarState(frontBarState);
    }

    @Override
    public void initialize() {

    }

    @Override
    public boolean isFinished() {return intake.frontBarFinished();}
}
