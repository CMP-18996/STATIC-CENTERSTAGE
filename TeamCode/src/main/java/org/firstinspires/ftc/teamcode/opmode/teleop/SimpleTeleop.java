package org.firstinspires.ftc.teamcode.opmode.teleop;

import static java.lang.Math.abs;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.StasisCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.majorcommands.TakeFromIntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositRotatorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.GrabberGripCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.ZeroLiftCommand;
import org.firstinspires.ftc.teamcode.common.drive.Drive;
import org.firstinspires.ftc.teamcode.common.drivers.HT16K33;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.MiscSubsystem;

import java.util.HashMap;

@Config
@TeleOp(name="Simple Final TeleOp", group="AOfficial")
public class SimpleTeleop extends CommandOpMode {
    private Robot robot;
    private Drive drive;
    private GamepadEx drivePad;
    private GamepadEx liftPad;
    private LiftSubsystem liftSubsystem;
    private DepositSubsystem depositSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private HT16K33 display;
    private MiscSubsystem miscSubsystem;
    private HashMap<Integer, LiftSubsystem.LiftHeight> liftHeights = new HashMap<>();
    private HashMap<Integer, IntakeSubsystem.FrontBarState> intakeHeights = new HashMap<>();
    private HashMap<Integer, DepositSubsystem.LowerHorizontalState> lowerHorizontalStateHashMap = new HashMap<>();
    private HashMap<Integer, DepositSubsystem.UpperHorizontalState> upperHorizontalStateHashMap = new HashMap<>();
    int inputtedLiftHeight = 1;
    double xAxisPosition = DepositSubsystem.centerVal;
    double incrementVal = 0.067 / 2;
    boolean intaking = false;
    boolean stasisAvailable = true;
    int robotFront = 1;
    double forwardPowerAdjusted = 0;
    double strafePowerAdjusted = 0;
    double turnPowerAdjusted = 0;


    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);
        intakeSubsystem = new IntakeSubsystem(robot);
        depositSubsystem = new DepositSubsystem(robot);
        miscSubsystem = new MiscSubsystem(robot);
        drivePad = new GamepadEx(gamepad1);
        drive = new Drive(robot); 
        liftPad = new GamepadEx(gamepad2);
        display = hardwareMap.get(HT16K33.class, "display");

        this.fillMaps();

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                () -> {
                    inputtedLiftHeight = Math.min(inputtedLiftHeight + 1, 10);
                    schedule(
                            new LiftCommand(liftSubsystem, liftHeights.get(inputtedLiftHeight))
                    );
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
                () -> {
                    inputtedLiftHeight = Math.max(inputtedLiftHeight - 1, 1);
                    schedule(
                            new LiftCommand(liftSubsystem, liftHeights.get(inputtedLiftHeight))
                    );
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(
                () -> {
                    xAxisPosition += incrementVal;
                    robot.xAdj.setPosition(xAxisPosition);
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(
                () -> {
                    xAxisPosition -= incrementVal;
                    robot.xAdj.setPosition(xAxisPosition);
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(
                        new SequentialCommandGroup(
                                //new ConditionalCommand(
                                  //      new SequentialCommandGroup(
                                                new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
                                                new WaitCommand(750),
                                                new DepositRotatorCommand(depositSubsystem, DepositSubsystem.DepositRotationState.PARALLEL),
                                                new WaitCommand(500),
                                                new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                                                new InstantCommand(() -> {stasisAvailable = true;})
                                    //    ),
                                     //   new InstantCommand(() -> {}),
                                     //   () -> intakeSubsystem.getCoverState() == IntakeSubsystem.CoverState.OPEN
                               // )
                        )
                )
        );

        liftPad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                () -> {
                    inputtedLiftHeight = 0;
                    schedule(
                            new SequentialCommandGroup(
                                    new ConditionalCommand(
                                            new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem),
                                            new InstantCommand(() -> {}),
                                            () -> depositSubsystem.getFourBarState() == DepositSubsystem.FourBarState.HIGH
                                    ),
                                    new TakeFromIntakeCommand(liftSubsystem, depositSubsystem, intakeSubsystem),
                                    new InstantCommand(() -> {
                                        gamepad2.rumble(200);
                                        stasisAvailable = false;
                                    })
                            )
                    );
                }
        );

        liftPad.getGamepadButton(GamepadKeys.Button.B).whenPressed(//x
                () -> {
                    if (stasisAvailable) {
                        inputtedLiftHeight = 0;
                        schedule(
                                new StasisCommand(liftSubsystem, depositSubsystem, intakeSubsystem)
                        );
                    }
                }
        );


        liftPad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(
                        () -> schedule(new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.LEFT))
                )
                .whenReleased(
                        () -> schedule(new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.LEFT))
                );

        liftPad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(
                        () -> schedule(new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.OPEN, DepositSubsystem.GrabberPos.RIGHT))
                )
                .whenReleased(
                        () -> schedule(new GrabberGripCommand(depositSubsystem, DepositSubsystem.GrabberState.CLOSED, DepositSubsystem.GrabberPos.RIGHT))
                );


        drivePad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                () -> {
                    intaking = true;
                    robot.intakeMotor.set(-.7);
                }
        );

        drivePad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                () -> schedule(
                    new SequentialCommandGroup(
                            new InstantCommand(() -> {
                                intaking = false;
                                robot.intakeMotor.set(.8);
                            }),
                            new WaitCommand(600),
                            new InstantCommand(() -> robot.intakeMotor.set(0))

                    )
                )
        );

        drivePad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                () -> schedule(new DroneCommand(miscSubsystem))
        );

        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                () -> schedule(
                        new InstantCommand(() -> {
                            if (intakeSubsystem.getFrontBarState() == IntakeSubsystem.FrontBarState.LEVEL2) {
                                schedule(new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL3));
                            }
                            else if (intakeSubsystem.getFrontBarState() == IntakeSubsystem.FrontBarState.LEVEL1) {
                                schedule(new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL2));
                            }
                            else if (intakeSubsystem.getFrontBarState() == IntakeSubsystem.FrontBarState.GROUND) {
                                schedule(new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL1));
                            }
                        })
                )
        );

        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
                new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.GROUND)
        );

        drivePad.getGamepadButton(GamepadKeys.Button.X).whenPressed(//square
                new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.HANGHEIGHT)
        );

        drivePad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new ZeroLiftCommand(liftSubsystem)
        );

        super.schedule(
                new SequentialCommandGroup(
                        new InstantCommand(), //prevents first command glitch
                        new ZeroLiftCommand(liftSubsystem),
                        // new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.GROUND),
                        new InstantCommand(() -> {
                            robot.xAdj.setPosition(xAxisPosition);
                        }),
                        new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED)
                )
        );
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        //display.writeInt(HT16K33.DeviceNumber.ONE, inputtedLiftHeight);

        robot.hangServo1.setPower(gamepad2.left_stick_y);
        robot.hangServo2.setPower(-1 * gamepad2.right_stick_y);

        if (intaking) {
            robotFront = -1;
            robot.intakeMotor.set(-drivePad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
            if (drivePad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1) {
                schedule(
                        new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.GROUND)
                );
            }
            else {
                schedule(
                        new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL4)
                );
            }
            intakeSubsystem.identifyColor();
            if (intakeSubsystem.slotOneFilled() && intakeSubsystem.slotTwoFilled()) {
                schedule(
                        new SequentialCommandGroup(
                        new InstantCommand(() -> {
                            robotFront = 1;
                            intaking = false;
                            gamepad1.rumble(300);
                            gamepad2.rumble(300);
                            inputtedLiftHeight = 0;
                        }),
                        new WaitCommand(400),
                        new InstantCommand(() -> robot.intakeMotor.set(.8)),
                        new WaitCommand(600),
                        new InstantCommand(() -> robot.intakeMotor.set(0))

                ));
            }
        }

        forwardPowerAdjusted = (87.0 / 95) * drivePad.getLeftY() + Math.signum(drivePad.getLeftY()) * 0.08;
            /*if (Math.abs(forwardPowerAdjusted)<=0.13){
                forwardPowerAdjusted = 0;
            }*/
        strafePowerAdjusted = (87.0 / 95) * drivePad.getLeftX() + Math.signum(drivePad.getLeftX()) * 0.08;
            /*if (Math.abs(strafePowerAdjusted)<=0.13){
                strafePowerAdjusted = 0;
            }*/
        turnPowerAdjusted = (87.0 / 95) * drivePad.getRightX() + Math.signum(drivePad.getRightX()) * 0.08;
            /*if (Math.abs(turnPowerAdjusted)<=0.13){
                turnPowerAdjusted = 0;
            }*/

        drive.manualPower(
                robotFront * strafePowerAdjusted,
                -robotFront * forwardPowerAdjusted,
                -turnPowerAdjusted
        );

        telemetry.addData("Color Detected in Slot 1:", intakeSubsystem.slotOne.toString());
        telemetry.addData("Color Detected in Slot 2:", intakeSubsystem.slotTwo.toString());
        telemetry.addData("Forward/Backward Power", drivePad.getLeftY());
        telemetry.addData("Strafe Power", drivePad.getLeftX());
        telemetry.addData("Turning Power", drivePad.getRightX());
        //telemetry.addData("square", drivePad.getButton(GamepadKeys.Button.X));
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

        //heighteleven throws error - clark
        //liftHeights.put(11, LiftSubsystem.LiftHeight.HEIGHTELEVEN);

        intakeHeights.put(1, IntakeSubsystem.FrontBarState.GROUND);
        intakeHeights.put(2, IntakeSubsystem.FrontBarState.LEVEL1);
        intakeHeights.put(3, IntakeSubsystem.FrontBarState.LEVEL2);
        intakeHeights.put(4, IntakeSubsystem.FrontBarState.LEVEL3);
        intakeHeights.put(5, IntakeSubsystem.FrontBarState.LEVEL4);

        lowerHorizontalStateHashMap.put(-4, DepositSubsystem.LowerHorizontalState.A);
        lowerHorizontalStateHashMap.put(-2, DepositSubsystem.LowerHorizontalState.B);
        lowerHorizontalStateHashMap.put(0, DepositSubsystem.LowerHorizontalState.C);
        lowerHorizontalStateHashMap.put(2, DepositSubsystem.LowerHorizontalState.D);
        lowerHorizontalStateHashMap.put(4, DepositSubsystem.LowerHorizontalState.E);

        upperHorizontalStateHashMap.put(-5, DepositSubsystem.UpperHorizontalState.A);
        upperHorizontalStateHashMap.put(-3, DepositSubsystem.UpperHorizontalState.B);
        upperHorizontalStateHashMap.put(-1, DepositSubsystem.UpperHorizontalState.C);
        upperHorizontalStateHashMap.put(1, DepositSubsystem.UpperHorizontalState.D);
        upperHorizontalStateHashMap.put(3, DepositSubsystem.UpperHorizontalState.E);
        upperHorizontalStateHashMap.put(5, DepositSubsystem.UpperHorizontalState.F);

    }
}
