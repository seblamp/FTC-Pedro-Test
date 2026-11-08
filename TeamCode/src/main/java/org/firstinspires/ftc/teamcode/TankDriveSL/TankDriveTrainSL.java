package org.firstinspires.ftc.teamcode.TankDriveSL;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class TankDriveTrainSL {

    private DcMotor leftMotor, rightMotor;
    private IMU imu;

    private double speedMultiplier = 1.0;

    public void init(HardwareMap hwMap) {
        leftMotor = hwMap.get(DcMotor.class, "leftmotor");
        rightMotor = hwMap.get(DcMotor.class, "rightmotor");

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hwMap.get(IMU.class,"imu");
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,RevHubOrientationOnRobot.UsbFacingDirection.UP);

        imu.initialize(new IMU.Parameters(RevOrientation));
        imu.resetYaw();
    }

    public void setSpeedMultiplier(double multiplier) {
        speedMultiplier = multiplier;
    }

    public void drive(double forward, double turn){
        double leftPower = forward + turn;
        double rightPower = forward - turn;

        double largest = Math.max(Math.abs(leftPower),Math.abs(rightPower));
        if (largest > 1.0){
            leftPower /= largest;
            rightPower /= largest;
        }
        leftMotor.setPower(leftPower*speedMultiplier);
        rightMotor.setPower(rightPower*speedMultiplier);
    }

    public int leftMotorPosition(){
        return leftMotor.getCurrentPosition();
    }

    public int rightMotorPosition(){
        return rightMotor.getCurrentPosition();
    }

    public double driveTrainHeading(AngleUnit angleUnit){
        return imu.getRobotYawPitchRollAngles().getYaw(angleUnit);
    }

    public void resetDriveEncoders(){
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
