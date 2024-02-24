package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanIds;

public class ShooterSubsystem extends SubsystemBase {
    //Remember to update ID  
    private final CANSparkMax leftMotor = new CANSparkMax(CanIds.kLeftShooter, MotorType.kBrushless);
    private final CANSparkMax rightMotor = new CANSparkMax(CanIds.kRightShooter, MotorType.kBrushless);


    public void startMotor(){
        leftMotor.set(1);
        rightMotor.set(-1);
    }

    public void stopMotor(){
        leftMotor.set(0);
        rightMotor.set(0);
    }

    public void startMotorHalfSpeed(){
        leftMotor.set(.5);
        rightMotor.set(-.5);
    }
}
