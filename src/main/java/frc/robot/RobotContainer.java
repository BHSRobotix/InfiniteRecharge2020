/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArmDown;
import frc.robot.commands.ArmStop;
import frc.robot.commands.ArmUp;
import frc.robot.commands.BallIn;
import frc.robot.commands.BallOut;
import frc.robot.commands.BallStop;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Arm;
import frc.robot.commands.IntakeDefault;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final DriveTrain drive = new DriveTrain();
  private final Intake intake = new Intake();
  private final Arm arm = new Arm();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final XboxController controller = new XboxController(0);


  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  // private final SlewRateLimiter speedLimiter = new SlewRateLimiter(3);
  // private final SlewRateLimiter rotLimiter = new SlewRateLimiter(3);

  // JoystickButton button = new JoystickButton(controller, 1);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    drive.setDefaultCommand(
        // A split-stick arcade command, with forward/backward controlled by the left
        // hand, and turning controlled by the right.
        new RunCommand(() -> drive
            .arcadeDrive(-controller.getY(GenericHID.Hand.kLeft),
                       -controller.getX(GenericHID.Hand.kRight)), drive));
    
    intake.setDefaultCommand(new BallStop(intake));
    arm.setDefaultCommand(new ArmStop(arm));
       
    // All 3 of these should do the same/similar thing
    // intake.setDefaultCommand(new IntakeDefault(intake));
    // intake.setDefaultCommand(new RunCommand(()->intake.stop(), intake));
    // intake.setDefaultCommand(new PerpetualCommand(new InstantCommand(()->intake.stop(), intake)));

  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(controller, Button.kBumperRight.value)
        .whenPressed(new BallIn(intake))
        .whenReleased(new BallStop(intake));

    new JoystickButton(controller, Button.kBumperLeft.value)
        .whenPressed(new BallOut(intake))
        .whenReleased(new BallStop(intake));

    new JoystickButton(controller, Button.kY.value)
        .whenPressed(new ArmUp(arm))
        .whenReleased(new ArmStop(arm));

    new JoystickButton(controller, Button.kA.value)
        .whenPressed(new ArmDown(arm))
        .whenReleased(new ArmStop(arm));
    // new JoystickButton(controller, kA).whenPressed(command);

    // new JoystickButton(controller, Button.kB.value)
    // .whenPressed(new InstantCommand(intake::ballIn, intake));
    // new JoystickButton(controller, Button.kA.value)
    // .whileHeld(new InstantCommand(intake::ballOut, intake));

    
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
