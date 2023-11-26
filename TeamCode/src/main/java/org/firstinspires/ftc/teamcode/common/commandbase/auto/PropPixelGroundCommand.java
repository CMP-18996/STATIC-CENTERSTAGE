/**
 * Purpose: Use prop location to drop the yellow pixel in the relative position.
 * Dependencies (variables): POSITION
 * Dependencies (subsystem): ...
 * Most Likely Errors:
 * - idk
 */
package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;

public class PropPixelGroundCommand extends CommandBase {
    public Telemetry telemetry;
    WaitCommand t = new WaitCommand(5);
    //TODO: add the other subsystems and their respective commands
    public PropPixelGroundCommand(Telemetry telemetry) {
        this.telemetry = telemetry;
    }
    @Override
    public void initialize() {
        switch (GlobalVariables.position) {
            case UNDETECTED:
                telemetry.addData("Detected", "nothing");
                break;
            case LEFT:
                telemetry.addData("Detected", "left");
                break;
            case MIDDLE:
                telemetry.addData("Detected", "middle");
                break;
            case RIGHT:
                telemetry.addData("Detected", "right");
                break;
        }
        telemetry.update();
        CommandScheduler.getInstance().schedule(t);
    }
    @Override
    public void execute() {}
    @Override
    public boolean isFinished() { return t.isFinished();}
}
