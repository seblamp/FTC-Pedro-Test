package org.firstinspires.ftc.teamcode.tutorielsSL;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DcMotorAndServoSL {

    private DcMotor motor;
    private Servo servoPosition;
    private CRServo servoRotation;

    public void init(HardwareMap hwMap){
        motor = hwMap.get(DcMotor.class,"motor");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);

        servoPosition = hwMap.get(Servo.class,"servo_pos");
        servoRotation = hwMap.get(CRServo.class,"servo_cont");
        servoPosition.scaleRange(0.0,1.0);
        servoPosition.setDirection(Servo.Direction.FORWARD);
        servoRotation.setDirection(CRServo.Direction.FORWARD);

    }
    public void setMotorSpeed (double speed){
        motor.setPower(speed);
    }
    public void setServoPosition (double position){
        servoPosition.setPosition(position);
    }
    public  void setServoRotation (double power){
        servoRotation.setPower(power);
    }

    public int motorPosition(){
        return motor.getCurrentPosition();
    }

    public double servoPosition() {
        return servoPosition.getPosition();
    }

    public double rotationServoPower(){
        return servoRotation.getPower();
    }

}
