package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

public class GrabberGripCommand extends InstantCommand {

    public GrabberGripCommand(DepositSubsystem deposit, DepositSubsystem.GrabberState grabberState, DepositSubsystem.GrabberPos grabberPos) {
        super(
                () -> deposit.setGrabberState(grabberState, grabberPos)
        );
    }
}
