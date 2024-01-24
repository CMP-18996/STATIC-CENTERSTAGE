package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

public class DroneMotorTestCommand extends CommandBase {
    MiscSubsystem miscSubsystem;
    public DroneMotorTestCommand(MiscSubsystem miscSubsystem) {
        this.miscSubsystem = miscSubsystem;
        miscSubsystem.droneMotorTest();
    }

}
