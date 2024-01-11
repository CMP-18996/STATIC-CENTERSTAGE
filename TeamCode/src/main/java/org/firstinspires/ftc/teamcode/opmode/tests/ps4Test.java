/*
columns: 1 2 3 4 5
rows: 1
      2
 */
package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.common.drivers.AdaDisplay;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadAndDisplaySubsystem;

@TeleOp(name = "ps4Test",group="test")
public class ps4Test extends CommandOpMode {
    public TouchpadAndDisplaySubsystem touchpad;
    private AdaDisplay display1, display2;
    boolean f, g;
    @Override
    public void initialize() {
        telemetry.addData("Status","Initalizing...");
        telemetry.update();

        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;
        CommandScheduler.getInstance().reset();

        display1 = hardwareMap.get(AdaDisplay.class, "display1");
        display2 = hardwareMap.get(AdaDisplay.class, "display2");
        touchpad = new TouchpadAndDisplaySubsystem(gamepad1, display1, display2);
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
        telemetry.addData("Status", "Running...");
        telemetry.addData("History", touchpad.getHistory());
        telemetry.addData("LeftRight State:", touchpad.getLeftRightState());
        telemetry.addData("Option State:", touchpad.getOptionState());
        telemetry.addData("Left Row:", touchpad.getLeftRow());
        telemetry.addData("Left Column:", touchpad.getLeftColumn());
        telemetry.addData("Right Row:", touchpad.getRightRow());
        telemetry.addData("Right Column:", touchpad.getRightColumn());
        telemetry.addData("Most Recently Pressed:", touchpad.getMostRecentlyPressed());

        telemetry.update();
    }
}
