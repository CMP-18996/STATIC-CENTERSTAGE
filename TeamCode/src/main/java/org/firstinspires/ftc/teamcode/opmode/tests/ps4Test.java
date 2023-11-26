/*
columns: 1 2 3 4 5
rows: 1
      2
 */
package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadSubsystem;

@Disabled
@TeleOp(name = "ps4Test",group="test")
public class ps4Test extends CommandOpMode {
    public TouchpadSubsystem touchpad;
    boolean f, g;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;
        CommandScheduler.getInstance().reset();

        touchpad = new TouchpadSubsystem(gamepad1);
        super.register(touchpad);


        telemetry.addData("Status", "Initialized!");
        telemetry.update();
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        if (touchpad.getGamepad().right_bumper) {
            if (f) {
                touchpad.deleteLastInput();
            }
        } else {
            f = true;
        }
        if (touchpad.getGamepad().left_bumper && touchpad.getHistory().size() == 2) {
            if (g) {
                touchpad.rumble(500);
            }
        } else {
            g = true;
        }
        telemetry.addData("Status", "Rumning...");
        telemetry.addData("History", touchpad.getHistory());
        telemetry.update();
    }
}
