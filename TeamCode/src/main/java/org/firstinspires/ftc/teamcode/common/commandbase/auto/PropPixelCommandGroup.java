package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.ToTagCommand;
import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.vision.Camera;

public class PropPixelCommandGroup extends SequentialCommandGroup {
    public PropPixelCommandGroup(MecanumDrive drive, Camera camera, Telemetry telemetry) {
        super(
                new ToSpikeMarkCommand(drive),
                new ToBoardCommand(drive),
                new ToTagCommand(camera, drive)
        );
    }
}
