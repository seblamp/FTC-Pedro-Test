package org.firstinspires.ftc.teamcode.TankDriveSL;

import static java.lang.Math.PI;

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
    private static final double ticksPerRevolution = 636.0;
    private static final double wheelDiameter = 9.0;

    private double turnTargetHeading;
    private  double turnPower;

    private static final double TURN_TOLERANCE = 1.0;
    private  static final double TURN_KP = 0.01;

    private boolean turning = false;

    public enum TurnDirection{
        LEFT,RIGHT
    }


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
        turning = false;
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveAuto (int leftTarget, int rightTarget, double speedAuto){
        int targetLeft = leftMotor.getCurrentPosition() + leftTarget;
        int targetRight = rightMotor.getCurrentPosition() + rightTarget;

        leftMotor.setTargetPosition(targetLeft);
        rightMotor.setTargetPosition(targetRight);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(Math.abs(speedAuto));
        rightMotor.setPower(Math.abs(speedAuto));
    }

    public boolean isDriveBusy(){
        return leftMotor.isBusy() || rightMotor.isBusy();
    }

    public int cmToTicks(double distanceCm){
        double wheelCircumference = PI * wheelDiameter;
        double rotations = distanceCm / wheelCircumference;
        return (int) Math.round(rotations*ticksPerRevolution);
    }

    public void driveDistance(double distanceCm, double speed){
        int ticks = cmToTicks((distanceCm));
        driveAuto(ticks,ticks,speed);
    }

    public double distanceLeftMotor(){
        return (leftMotor.getCurrentPosition()/ticksPerRevolution)*PI*wheelDiameter;
    }
    public double distanceRightMotor(){
        return (rightMotor.getCurrentPosition()/ticksPerRevolution)*PI*wheelDiameter;
    }

    private double notmalizeAngle(double angle){
        while (angle > 180){
            angle -= 360;
        }
        while (angle < -180){
            angle += 360;
        }
        return angle;
    }

    public void turnDegrees (TurnDirection direction, double degrees, double power) {
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double currentHeading = driveTrainHeading(AngleUnit.DEGREES);
        turnPower = Math.abs(power);

        if (direction == TurnDirection.RIGHT){
            turnTargetHeading = currentHeading - degrees;
        } else {
            turnTargetHeading = currentHeading + degrees;
        }

        turnTargetHeading = notmalizeAngle((turnTargetHeading));
        turning = true;
    }

    public void updateTurn() {
        if (!turning) {
            return;
        }

        double currentHeading = driveTrainHeading(AngleUnit.DEGREES);
        double error = notmalizeAngle(turnTargetHeading - currentHeading);

        if (Math.abs(error) <= TURN_TOLERANCE) {
            drive(0,0);
            turning = false;
        }else {
            double turnCorrection = error * TURN_KP;
            if (turnCorrection > turnPower) {
                turnCorrection = turnPower;
            }
            if (turnCorrection < -turnPower){
                turnCorrection = -turnPower;
            }

            drive(0, -turnCorrection);
        }
    }

    public boolean isTurning(){
        return turning;
    }
}
