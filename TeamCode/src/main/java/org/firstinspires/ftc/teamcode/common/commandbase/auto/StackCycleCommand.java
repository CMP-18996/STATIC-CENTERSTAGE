/**
 * Purpose: Drive from the backdrop to a stack, then drives back to the backdrop.
 * Dependencies (variables): COLOR
 * Dependencies (subsystem): RR-drive
 * Most Likely Errors:
 * - Crashes into other robots
 * - Intake system fails to obtain pixel
 * - General odometry drift
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.FromStackCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.core.ToStackCommand;
import org.firstinspires.ftc.teamcode.common.drive.SampleMecanumDrive;

public class StackCycleCommand extends SequentialCommandGroup {
    public StackCycleCommand(SampleMecanumDrive drive) {
        addCommands(
                new ToStackCommand(drive),
                new FromStackCommand(drive)
        );
    }
}



