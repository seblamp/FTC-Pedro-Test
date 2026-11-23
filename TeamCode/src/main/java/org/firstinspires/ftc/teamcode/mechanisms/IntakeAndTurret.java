package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeAndTurret {

    private DcMotor intake;
    private double intakePower = 0.0;


    private CRServo servoRot;


    private DigitalChannel magneticSwitch;
    private boolean wasMagneticSwitchClosed = false;
    private boolean isTurretHome = false;

    private static final double ticks_per_rev = 8192.0;
    private int encoderZero = 0;


    public void init(HardwareMap hwMap){
        intake = hwMap.get(DcMotor.class, "motor"); //en vert "motor" doit être le nom exact de la configuration sur le Driver Hub
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); // Brake ou float
        intake.setDirection(DcMotorSimple.Direction.FORWARD); //si on a besoin d'inverser le moteur
        intake.setPower(0.0);

        servoRot = hwMap.get(CRServo.class,"servo_cont");
        servoRot.setDirection(CRServo.Direction.REVERSE);
        servoRot.setPower(0.0);

        magneticSwitch = hwMap.get(DigitalChannel.class,"magnetic_switch");
        magneticSwitch.setMode(DigitalChannel.Mode.INPUT);
        wasMagneticSwitchClosed = isMagneticSwitchClosed();
    }

    public void setIntakePower(double power) {
        intakePower = power;
        intake.setPower(intakePower);
    }

    public double getIntakePower() {
        return intakePower;
    }

    public void setTurretPower(double power) {
        servoRot.setPower(power);
    }

    public boolean isMagneticSwitchClosed() {
        return !magneticSwitch.getState();
    }

    public void updateTurretEncoder() {

        boolean magnetDetected = isMagneticSwitchClosed();


        if (magnetDetected && !wasMagneticSwitchClosed) {

            encoderZero = intake.getCurrentPosition();
            isTurretHome = true;
        }

        wasMagneticSwitchClosed = magnetDetected;
    }

    public int getTurretTicks() {
        return intake.getCurrentPosition() - encoderZero;
    }

    public double getTurretAngle() {
        return getTurretTicks() * 360.0 / ticks_per_rev;
    }


    public boolean isTurretHomed() {
        return isTurretHome;
    }
}






