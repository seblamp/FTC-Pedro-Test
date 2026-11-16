package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DcMotorAndServoClass {

    private DcMotor motor;
    private Servo servoPos;
    private CRServo servoRot;

    public void init(HardwareMap hwMap){
        motor = hwMap.get(DcMotor.class,"motor");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        motor.setDirection(DcMotorSimple.Direction.FORWARD);

        servoPos = hwMap.get(Servo.class, "servo_pos");
        servoRot = hwMap.get(CRServo.class,"servo_cont");
        servoPos.scaleRange(0.0,1.0);
        servoPos.setDirection(Servo.Direction.FORWARD);
        servoRot.setDirection(CRServo.Direction.REVERSE);

    }
    public void setMotorSpeed (double speed) {
        motor.setPower(speed);
    }
    public void setServoPos(double position){
        servoPos.setPosition(position);
    }
    public void setServoRot(double power){
        servoRot.setPower(power);
    }

    public int motorPosition() {
        return  motor.getCurrentPosition();
    }

    public double servoPosition() {
        return servoPos.getPosition();
    }

    public double rotationServoRotPower() {
        return servoRot.getPower();
    }


}
