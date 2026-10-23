package org.firstinspires.ftc.teamcode.ExempleProgram;

import static java.lang.Math.PI;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class TankDrivetrainExemple {

    private DcMotor leftMotor, rightMotor;
    private IMU imu;
    private static final double ticksPerRevolution = 636.0 ;
    private static final double wheelDiameter = 9.0;

    public void init(HardwareMap hwMap) {
        leftMotor = hwMap.get(DcMotor.class, "leftmotor");
        rightMotor = hwMap.get(DcMotor.class, "rightmotor");

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hwMap.get(IMU.class,"imu");
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP);

        imu.initialize (new IMU.Parameters(RevOrientation));
        imu.resetYaw();
    }

    public void drive(double forward, double turn) {   //Méthode qui permet de contrôler une base tank en téléop
        double leftPower = forward + turn;  // formules pour avancer et tourner
        double rightPower = forward - turn;
        double largest = Math.max(Math.abs(leftPower), Math.abs(rightPower));  // ajustement pour ne pas dépasser 1
        if (largest > 1.0) {
            leftPower /= largest;
            rightPower /= largest;
        }
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
    }

    //méthode pour transformer les cm en ticks
    public int cmToTicks(double distanceCm) {
        double wheelCircumference = PI * wheelDiameter;
        double rotations = distanceCm / wheelCircumference;

        return (int) Math.round(rotations * ticksPerRevolution);
    }

    // Méthodes de télémétrie (encodeur, distance en cm, heading en degrés, power et modes du moteur)
    public int leftMotorPosition() {  //methode pour avoir la télémétrie du moteur gauche
        return leftMotor.getCurrentPosition();
    }
    public int rightMotorPosition() {  //methode pour avoir la télémétrie du moteur droit
        return rightMotor.getCurrentPosition();
    }
    public double distanceLeftMotor() {
        return  (leftMotor.getCurrentPosition()/ticksPerRevolution) * PI * wheelDiameter; // calcul de la distance en cm, roue gauche
    }
    public double distanceRightMotor() {
        return  (rightMotor.getCurrentPosition()/ticksPerRevolution) * PI * wheelDiameter; // calcul de la distance en cm, roue droite
    }
    public double classBotHeading(AngleUnit angleUnit) {   //méthode pour avoir le heading du robot de -180 à 180 ou -pie à pie
        return imu.getRobotYawPitchRollAngles().getYaw(angleUnit);
    }

    //Méthodes pour le mode autonome
    public void driveAuto(int leftTarget, int rightTarget, double speedAuto) {  // Méthode pour bouger en Autonome selon une valeur d'encodeur

        int targetLeft = leftMotor.getCurrentPosition() + leftTarget;  //La cible est la position actuelle + le mouvement voulu
        int targetRight = rightMotor.getCurrentPosition() + rightTarget;

        leftMotor.setTargetPosition(targetLeft);   //on met les moteurs à la cible
        rightMotor.setTargetPosition(targetRight);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Moteur en run to position
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(speedAuto); // le power des moteurs à speed
        rightMotor.setPower(speedAuto);
    }
    public boolean isDriveBusy() {       // Vérifier si les moteurs sont occupés, que la manœuvre n'est pas terminées
        return leftMotor.isBusy() || rightMotor.isBusy();
    }

    public void driveDistance(double distanceCm, double speed) {
        int ticks = cmToTicks(distanceCm);
        driveAuto(ticks, ticks, speed);
    }


}
