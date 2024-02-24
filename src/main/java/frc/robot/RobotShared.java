//RobotShared is the class that holds all the instances of subsystems and other useful systems.
package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.SensorSubsystem;

public class RobotShared {

  private Optional<Alliance> m_alliance = DriverStation.getAlliance();

  protected DriveSubsystem m_robotDrive = null;
  protected ShooterSubsystem m_shooterSubsystem = null;
  protected ConveyorSubsystem m_conveyorSubsystem = null;
  protected final XboxController m_XboxController = new XboxController(OIConstants.kDriverControllerPort);

  protected LimelightSubsystem m_limelight = null;
  protected PhotonVision m_photonVision = null;
  protected SensorSubsystem m_sensorSubsystem = null;
  protected IntakeSubsystem m_intakeSubsystem = null;

  private static RobotShared instance;

  public static RobotShared getInstance() {
    if(instance == null) {
      instance = new RobotShared();
    }
    return instance;
  }

  public DriveSubsystem getDriveSubsystem() {
    if(m_robotDrive == null) {
      m_robotDrive = new DriveSubsystem();
    }
    return m_robotDrive;
  }
  public ShooterSubsystem getShooterSubsystem() {
    if(m_shooterSubsystem == null) {
      m_shooterSubsystem = new ShooterSubsystem();
    }
    return m_shooterSubsystem;
  }
  public IntakeSubsystem getIntakeSubsystem() {
    if(m_intakeSubsystem == null) {
      m_intakeSubsystem = new IntakeSubsystem();
    }
    return m_intakeSubsystem;
  }
  public SensorSubsystem getSensorSubsystem() {
    if(m_sensorSubsystem == null) {
      m_sensorSubsystem = new SensorSubsystem();
    }
    return m_sensorSubsystem;
  }
  public ConveyorSubsystem getConveyorSubsystem() {
    if(m_conveyorSubsystem == null){
      m_conveyorSubsystem = new ConveyorSubsystem();
    }
    return m_conveyorSubsystem;
  }
  public XboxController getDriverController() {
    return m_XboxController;
  }
  public LimelightSubsystem getLimelight() {
    if(m_limelight == null) {
      m_limelight = new LimelightSubsystem();
    }
    return m_limelight;
  }
  public PhotonVision getPhotonVision() {
    if(m_photonVision == null) {
      m_photonVision = new PhotonVision();
    }
    return m_photonVision;
  }
  public Alliance getAlliance() { // blue is default for the path planner (paths are made on the blue side)
    m_alliance = DriverStation.getAlliance();
    if(m_alliance.isPresent()){
      if(m_alliance.get() == Alliance.Blue){
        return Alliance.Blue;
      }else{
        return Alliance.Red;
      }
    }else{
      return Alliance.Blue;
    }
  }
}
