package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

public class DroneCommand extends SequentialCommandGroup {
    public DroneCommand(MiscSubsystem miscSubsystem) {
        addCommands(
           new InstantCommand(() -> miscSubsystem.releaseDrone(1)),
           new WaitCommand(2000),
           new InstantCommand(() -> miscSubsystem.releaseDrone(0.0))
        );
    }
}
