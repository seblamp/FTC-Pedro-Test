package org.firstinspires.ftc.teamcode.mechanismsSL;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DcMotorClassSL {
    private DcMotor motor;
    private double ticksPerRev;

    public void init(HardwareMap hwMap) {
        motor = hwMap.get(DcMotor.class,"motor");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        ticksPerRev = 288.0;
    }

    public void setMotorSpeed (double speed) {
        motor.setPower(speed);
    }

    public double getMotorRevs() {
        return  motor.getCurrentPosition()/ticksPerRev;
    }

    public void setMotorZeroPowerBehaviour (DcMotor.ZeroPowerBehavior zeroPowerBehaviour){
        motor.setZeroPowerBehavior(zeroPowerBehaviour);
    }

    public DcMotor.ZeroPowerBehavior getMotorZeroPowerBehaviour(){
        return motor.getZeroPowerBehavior();
    }

}
