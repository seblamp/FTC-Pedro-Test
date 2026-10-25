
package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@Autonomous(name = "Tank Drive Auto")
public class TankDriveAutoOpMode extends LinearOpMode { // LinearOpMode permet de faire des manœuvres dans l'ordre

    TankDrivetrainExemple drivetrain = new TankDrivetrainExemple(); // On crée un objet de notre classe TankDrivetrain


    @Override
    public void runOpMode() { // Méthode principale de l'autonome (remplace init et loop)

        drivetrain.init(hardwareMap);       // On initialise les moteurs et le IMU
        drivetrain.resetDriveEncoders();    // On remet les encodeurs des moteurs à zéro

        telemetry.addLine("Robot ready");
        telemetry.update();

        waitForStart();                     // Le programme attend qu'on appuie sur START sur le Driver Hub

        if (isStopRequested()) {            // Si on appui sur STOP, on sort du programme
            return;
        }


        // ------------------------- PARCOURS AUTONOME -------------------------
        // On peut changer les valeurs et faire du copier/coller pour modifier le trajet
        // Les commandes sont exécutées dans l'ordre, une manœuvre à la fois

        avancer(70, 0.6);                   // Avance 70 cm à 60 % de power
        tournerDroite(90, 0.4);             // Tourne à droite 90 degrés à 40 % de power
        avancer(40, 0.7);                   // Avance 40 cm à 70 % de power
        tournerGauche(90, 0.5);             // Tourne à gauche 90 degrés à 50 % de power
        reculer(30, 0.8);                   // Recule 30 cm à 80 % de power


        drivetrain.drive(0, 0);            // Les moteurs s'arrêtent
        afficherTelemetrie("Autonomous finished"); // Info sur le DriverHub
    }


    // ------------------------- MÉTHODES POUR LES MOUVEMENTS -------------------------

    public void avancer(double distance, double puissance) { // Méthode pour avancer en cm

        if (!opModeIsActive()) return;      // Si l'autonome n'est plus actif, on quitte la méthode

        drivetrain.driveDistance(Math.abs(distance), puissance); // Distance positive pour avancer

        while (opModeIsActive() && drivetrain.isDriveBusy()) { // Tant que les moteurs n'ont pas atteint leur cible
            afficherTelemetrie("AVANCE");   // On affiche la manœuvre et les informations des moteurs
            idle();                         // On laisse du temps au système pour ses autres tâches
        }
    }


    public void reculer(double distance, double puissance) { // Méthode pour reculer en cm

        if (!opModeIsActive()) return;

        drivetrain.driveDistance(-Math.abs(distance), puissance); // Distance négative pour reculer

        while (opModeIsActive() && drivetrain.isDriveBusy()) { // On attend que les moteurs terminent leur déplacement
            afficherTelemetrie("RECULE");
            idle();
        }
    }


    public void tournerDroite(double angle, double puissance) { // Méthode pour tourner à droite en degrés

        if (!opModeIsActive()) return;

        drivetrain.turnDegrees(
                TankDrivetrainExemple.TurnDirection.RIGHT, // On choisit la direction du virage
                angle,                             // Angle désiré en degrés
                puissance                          // Puissance utilisée pour tourner
        );

        while (opModeIsActive() && drivetrain.isTurning()) { // Tant que le virage n'est pas terminé
            drivetrain.updateTurn();             // On vérifie le heading et on ajuste la commande des moteurs
            afficherTelemetrie("TOURNE À DROITE");
            idle();
        }
    }


    public void tournerGauche(double angle, double puissance) { // Méthode pour tourner à gauche en degrés

        if (!opModeIsActive()) return;

        drivetrain.turnDegrees(
                TankDrivetrainExemple.TurnDirection.LEFT, // On choisit la direction du virage
                angle,                            // Angle désiré en degrés
                puissance                         // Puissance utilisée pour tourner
        );

        while (opModeIsActive() && drivetrain.isTurning()) { // On attend que le robot atteigne l'angle désiré
            drivetrain.updateTurn();             // On lit le IMU et on continue ou termine le virage
            afficherTelemetrie("TOURNE À GAUCHE");
            idle();
        }
    }


    // ------------------------- MÉTHODE POUR LA TÉLÉMÉTRIE -------------------------

    public void afficherTelemetrie(String manoeuvre) { // String permet de recevoir le nom de la manœuvre

        telemetry.addData("Manoeuvre", manoeuvre); // Affiche ce que le robot est en train de faire

        telemetry.addData("Left Motor",
                drivetrain.leftMotorPosition()); // Position de l'encodeur gauche en ticks

        telemetry.addData("Right Motor",
                drivetrain.rightMotorPosition()); // Position de l'encodeur droit en ticks

        telemetry.addData("Distance gauche (cm)", "%.2f",
                drivetrain.distanceLeftMotor()); // Distance calculée à partir de l'encodeur gauche

        telemetry.addData("Distance droite (cm)", "%.2f",
                drivetrain.distanceRightMotor()); // Distance calculée à partir de l'encodeur droit

        telemetry.addData("Heading (degres)", "%.2f",
                drivetrain.driveTrainHeading(AngleUnit.DEGREES)); // Orientation du robot selon le IMU

        telemetry.update();                      // Envoie les nouvelles données au Driver Hub
    }
}
