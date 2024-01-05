package org.firstinspires.ftc.teamcode.opmode.tests.arm;


import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImplOnSimple;

import org.firstinspires.ftc.teamcode.common.Drivers.AdaDisplay;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadSubsystem;

import java.util.HashMap;

@TeleOp(name="Lift Test")
public class LiftTest extends CommandOpMode {
    private Robot robot;
    private LiftSubsystem liftSubsystem;
    private TouchpadSubsystem touchpadSubsystem;
    private GamepadEx gamepadEx;
    private HashMap<Integer, LiftSubsystem.LiftHeight> liftHeights = new HashMap<>();
    private LiftSubsystem.LiftHeight liftHeight;
    private AdaDisplay display1, display2;
    private int rowNumber;

    @Override
    public void initialize() {
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);
        gamepadEx = new GamepadEx(gamepad1);
        display1 = hardwareMap.get(AdaDisplay.class, "display1");
        display2 = hardwareMap.get(AdaDisplay.class, "display2");
        touchpadSubsystem = new TouchpadSubsystem(gamepad1, display1, display2);

        this.fillMaps();
        CommandScheduler.getInstance().reset();

        gamepadEx.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> {
                    /*try {
                        rowNumber = touchpadSubsystem.getHistory().get(1);
                        liftHeight = liftHeights.get(rowNumber);
                        telemetry.addData("Row", rowNumber);
                        CommandScheduler.getInstance().schedule(
                                new LiftCommand(liftSubsystem, liftHeight)
                        );
                    }
                    catch (Exception e) {telemetry.addData("Wow", " something probably happened");}
                        }*/
                    CommandScheduler.getInstance().schedule(
                            new SequentialCommandGroup(
                                    new InstantCommand(() -> {
                                        rowNumber = touchpadSubsystem.getHistory().get(1);
                                        liftHeight = liftHeights.get(rowNumber);
                                        telemetry.addData("Row", rowNumber);
                                    }
                                    ),
                                    new LiftCommand(liftSubsystem, liftHeight)
                            )
                    );
                });
    }

    @Override
    public void run() {CommandScheduler.getInstance().run();}

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
