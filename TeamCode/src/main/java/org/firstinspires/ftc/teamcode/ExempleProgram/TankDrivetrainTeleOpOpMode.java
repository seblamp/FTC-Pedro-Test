package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@TeleOp
public class TankDrivetrainTeleOpOpMode extends OpMode {

    TankDrivetrainExemple drivetrain = new TankDrivetrainExemple();  //on appelle notre classe TankDrivetrain
    double forward,turn;   //variables pour le drivetrain

    @Override
    public void init() { // Init sur le Driver Hub
        drivetrain.init(hardwareMap);    //On initialise le matériel
        drivetrain.resetDriveEncoders(); //On reset les encodeurs de tous les moteurs
    }

    @Override
    public void loop() {

        /* -----------------------------Contrôles------------------------------------------
        Avance/recule : joystick de gauche ou gachette droite et gauche
        Tourne : joystick de droite
        Ralentir robot : bumper gauche ou droite
         */

            //Contrôles de la base
            forward = -gamepad1.left_stick_y;  // enlever le commentaire pour activer
            //forward = (gamepad1.right_trigger - gamepad1.left_trigger);  // enlever le commentaire pour activer
            turn = gamepad1.right_stick_x;

            if (gamepad1.left_bumper || gamepad1.right_bumper) {
                drivetrain.setSpeedMultiplier(0.4);
            }
            else {
                drivetrain.setSpeedMultiplier(1.0);
            }
            drivetrain.drive(forward,turn);

        //Télémétrie des moteurs et de la limit switch
        telemetry.addData("Left Motor", drivetrain.leftMotorPosition());
        telemetry.addData("Right Motor", drivetrain.rightMotorPosition());
        telemetry.addData("Heading", drivetrain.driveTrainHeading(AngleUnit.DEGREES));
    }

}

