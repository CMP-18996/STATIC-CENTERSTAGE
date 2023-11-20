package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;

public class PropPixelCommand extends CommandBase {
    public Telemetry telemetry;
    WaitCommand t = new WaitCommand(5);
    //TODO: add the other subsystems and their respective commands
    public PropPixelCommand(Telemetry telemetry) {
        this.telemetry = telemetry;
    }
    @Override
    public void initialize() {
        switch (GlobalVariables.position) {
            case UNDETECTED:
                telemetry.addData("Detected", "nothing");
            case LEFT:
                telemetry.addData("Detected", "left");
            case MIDDLE:
                telemetry.addData("Detected", "middle");
            case RIGHT:
                telemetry.addData("Detected", "right");
        }
        telemetry.update();
        CommandScheduler.getInstance().schedule(t);
    }
    @Override
    public void execute() {}
    @Override
    public boolean isFinished() { return t.isFinished();}

}
