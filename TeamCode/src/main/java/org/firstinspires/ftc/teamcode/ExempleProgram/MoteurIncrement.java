package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MoteurIncrement {

    private DcMotor testMotor; //le moteur du mécanisme qu'on teste. Intake/Shooter/Convoyeur/Élavateur
    private  double testMotorPower = 0.0;
    private final  double power_increment = 0.05;

    public void init(HardwareMap hwMap) {   //configuration du moteur

        testMotor = hwMap.get(DcMotor.class, "motor"); //en vert "motor" doit être le nom exact de la configuration sur le Driver Hub
        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // ex : va aider si 2 moteurs sur un gearbox doivent tourner ensemble à la même vitesse
        testMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); // Brake ou float
        testMotor.setDirection(DcMotorSimple.Direction.FORWARD); //si on a besoin d'inverser le moteur
    }
    public void setMotorPower (double power) {   //Méthode qui met la puissance du moteur à la variable power
        testMotorPower = power;
        testMotor.setPower(testMotorPower);
    }
    public void increaseMotorPower() {  // Méthode pour augmenter graduellement le power du moteur

        testMotorPower += power_increment;

        if (testMotorPower > 1.0) {   //empêche de dépasser 1
            testMotorPower = 1.0;
        }

        testMotor.setPower(testMotorPower);
    }

    public void decreaseMotorPower() {  // Méthode pour diminuer graduellement le power du moteur

        testMotorPower -= power_increment;

        if (testMotorPower < -1.0) {   // empêche d'aller en bas de -1
            testMotorPower = -1.0;
        }

            testMotor.setPower(testMotorPower);
    }

    public double getMotorPower() {   // pour avoir la télémétrie du power
        return  testMotor.getPower();
    }


}


