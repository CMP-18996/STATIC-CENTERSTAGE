package org.firstinspires.ftc.teamcode.tests.arm;


import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.drivers.HT16K33;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadAndDisplaySubsystem;

import java.util.HashMap;

@TeleOp(name="Lift Test")
public class LiftTest extends CommandOpMode {
    private Robot robot;
    private int val = 1;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private LiftSubsystem liftSubsystem;
    private TouchpadAndDisplaySubsystem touchpadAndDisplaySubsystem;
    private GamepadEx gamepadEx;
    private HashMap<Integer, LiftSubsystem.LiftHeight> liftHeights = new HashMap<>();
    private HT16K33 display1, display2;
    private int rowNumber;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);
        gamepadEx = new GamepadEx(gamepad1);
        //display1 = hardwareMap.get(AdaDisplay.class, "display1");
        //display2 = hardwareMap.get(AdaDisplay.class, "display2");
        touchpadAndDisplaySubsystem = new TouchpadAndDisplaySubsystem(gamepad1, display1, display2);

        this.fillMaps();

        gamepadEx.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(new LiftCommand(liftSubsystem, liftHeights.get(val)))
        );
    }

    @Override
    public void run() {
        super.run();
        if (gamepad1.dpad_up && !upPressed) {
            val = Math.min(val + 1, 10);
            upPressed = true;
        } else if (!gamepad1.dpad_up) {
            upPressed = false;
        }

        if (gamepad1.dpad_down && !downPressed) {
            val = Math.max(val - 1, 1);
            downPressed = true;
        } else if (!gamepad1.dpad_down) {
            downPressed = false;
        }

        telemetry.addData("Height:", val);
        telemetry.addData("Associated Height:", liftHeights.get(val).target);
        telemetry.update();
    }

    public void fillMaps() {
        liftHeights.put(1, LiftSubsystem.LiftHeight.HEIGHTONE);
        liftHeights.put(2, LiftSubsystem.LiftHeight.HEIGHTTWO);
        liftHeights.put(3, LiftSubsystem.LiftHeight.HEIGHTTHREE);
        liftHeights.put(4, LiftSubsystem.LiftHeight.HEIGHTFOUR);
        liftHeights.put(5, LiftSubsystem.LiftHeight.HEIGHTFIVE);
        liftHeights.put(6, LiftSubsystem.LiftHeight.HEIGHTSIX);
        liftHeights.put(7, LiftSubsystem.LiftHeight.HEIGHTSEVEN);
        liftHeights.put(8, LiftSubsystem.LiftHeight.HEIGHTEIGHT);
        liftHeights.put(9, LiftSubsystem.LiftHeight.HEIGHTNINE);
        liftHeights.put(10, LiftSubsystem.LiftHeight.HEIGHTTEN);
    }
}
