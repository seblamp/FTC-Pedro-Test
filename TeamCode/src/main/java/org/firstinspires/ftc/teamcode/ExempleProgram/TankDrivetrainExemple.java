package org.firstinspires.ftc.teamcode.ExempleProgram;

import static java.lang.Math.PI;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class TankDrivetrainExemple {

    private DcMotor leftMotor, rightMotor;              //Variables pour moteurs
    private IMU imu;                                    //variable pour IMU
    private static final double ticksPerRevolution = 636.0 ;        //à vérifier
    private static final double wheelDiameter = 9.0;                //à vérifier
    private double speedMultiplier = 1.0;            //variable pour ralentir le robot

    // Variables pour les virages avec le IMU
    private double turnTargetHeading;                    // Cible pour le virage
    private double turnPower;                            // Vitesse pendant le virage
    private static final double TURN_TOLERANCE = 2.0;    // Tolérance en degrés pour terminer le virage
    private boolean turning = false;                     // Variable pour vérifier si le virage est terminé

    public enum TurnDirection {                         // Enum utilisé pour choisir la direction d'un virage
        LEFT, RIGHT}

    //private static final double TURN_KP = 0.02;  //Constante qui permet d'ajuster le power selon l'erreur


    public void init(HardwareMap hwMap) {
        leftMotor = hwMap.get(DcMotor.class, "leftmotor");  //Les noms doivent correspondre à la configuration du DriverHub
        rightMotor = hwMap.get(DcMotor.class, "rightmotor");

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);       //On utilise les encodeurs
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);    //Un moteur inverse de l'autre pcq dans le robot, ils tournent inversement de l'autre
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);   //Mode brake pour des mouvements plus précis
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hwMap.get(IMU.class,"imu");                       //Par défaut le IMU se nomme "imu"
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,      //à vérifier
                RevHubOrientationOnRobot.UsbFacingDirection.UP);            //à vérifier

        imu.initialize (new IMU.Parameters(RevOrientation));
        imu.resetYaw();                                                     //Orientation à 0 degré lorsque initialisé
    }

    public void resetDriveEncoders() { // Méthode pour remettre les encodeurs à zéro

        turning = false;        //On cesse toutes manœuvres

        leftMotor.setPower(0);
        rightMotor.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  //encodeurs à 0
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);       //On remet les moteurs dans le bon mode
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drive(double forward, double turn) {   //Méthode qui permet de contrôler une base tank en téléop
        double leftPower = forward + turn;  // formules pour avancer et tourner
        double rightPower = forward - turn;
        double largest = Math.max(Math.abs(leftPower), Math.abs(rightPower));  // ajustement pour ne pas dépasser 1 d'input
        if (largest > 1.0) {
            leftPower /= largest;
            rightPower /= largest;
        }
        leftMotor.setPower(leftPower * speedMultiplier);  //la multiplication permet de ralentir le robot quand un appui sur un bouton
        rightMotor.setPower(rightPower * speedMultiplier);  //la multiplication permet de ralentir le robot quand un appui sur un bouton
    }

    //méthode pour avoir un bouton pour ralentir le robot pour des contrôles plus précis
    public void setSpeedMultiplier(double multiplier) {
        speedMultiplier = multiplier;
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
    public double driveTrainHeading(AngleUnit angleUnit) {   //méthode pour avoir le heading du robot de -180 à 180 ou -pie à pie
        return imu.getRobotYawPitchRollAngles().getYaw(angleUnit);
    }

    private double normalizeAngle(double angle) { // Méthode pour éviter le problème du -180 qui se téléporte à 180 degrés
        while (angle > 180.0) {
            angle -= 360.0;
        }
        while (angle < -180.0) {
            angle += 360.0;
        }
        return angle;
    }

    //Méthodes pour le mode autonome
    public void driveAuto(int leftTarget, int rightTarget, double speedAuto) {  // Méthode pour bouger en Autonome selon une valeur d'encodeur

        int targetLeft = leftMotor.getCurrentPosition() + leftTarget;  //La cible est la position actuelle + le mouvement voulu
        int targetRight = rightMotor.getCurrentPosition() + rightTarget;

        leftMotor.setTargetPosition(targetLeft);   //on met les moteurs à la cible
        rightMotor.setTargetPosition(targetRight);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Moteur en run to position
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(Math.abs(speedAuto));
        rightMotor.setPower(Math.abs(speedAuto)); // le power des moteurs à speed
    }

    public void turnDegrees(TurnDirection direction, double degrees, double power){ // Méthode pour tourner avec le IMU en autonome
        leftMotor.setPower(0);                              //On s'assure que les moteurs sont à 0 et non à un power d'une commande précédente
        rightMotor.setPower(0);
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // On remet les moteurs dans le bon mode
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double currentHeading = driveTrainHeading(AngleUnit.DEGREES); // On va lire l'angle actuel

        turnPower = Math.abs(power);

        if (direction == TurnDirection.RIGHT) { // Si on veut virer à droite,
            turnTargetHeading = currentHeading - degrees;  // on fait le heading moins l'angle désiré
        }
        else {
            turnTargetHeading = currentHeading + degrees; // Si on veut virer à gauche, on fait le heading plus l'angle désiré
        }
        turnTargetHeading = normalizeAngle(turnTargetHeading); // Correction pour gérer le passage de -180 à 180 degrés
        turning = true;
    }

    public void updateTurn() {                  // Méthode qui fait tourner le robot jusqu'à atteindre l'angle voulu

        if (!turning) {                     //Si on ne tourne pas, quitter cette méthode
            return;
        }
        double currentHeading = driveTrainHeading(AngleUnit.DEGREES);       //On vérifie la direction actuelle du robot

        double error = normalizeAngle(turnTargetHeading - currentHeading);  //On calcule l'écart avec la direction voulue

        if (Math.abs(error) <= TURN_TOLERANCE) {
            drive(0, 0);                //Si on est en dedans de la tolérance, les moteurs arrêtent
            turning = false;
        }
        else {
            double direction = Math.signum(error);          //Sinon, on prend le signe de l'erreur (+ ou -) et on continue de tourner
            drive(0, direction * turnPower);
        }

        /*else {
            double turnCorrection = error * TURN_KP;
            if (turnCorrection > turnPower) {
                turnCorrection = turnPower;
            }
            if (turnCorrection < -turnPower) {
            turnCorrection = -turnPower;
            }
            drive(0, turnCorrection);
        }*/


    }
    public boolean isTurning() {                //Méthode qui retourne si le robot est en train de tourner
        return turning;
    }


    public boolean isDriveBusy() {       // Vérifier si les moteurs sont occupés, que la manœuvre n'est pas terminées
        return leftMotor.isBusy() || rightMotor.isBusy();
    }

    public void driveDistance(double distanceCm, double speed) {
        int ticks = cmToTicks(distanceCm);
        driveAuto(ticks, ticks, speed);
    }
}
