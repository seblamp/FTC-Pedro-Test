package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MoteurIncrementOpMode extends OpMode {

    MoteurIncrement increment = new MoteurIncrement();    //On appelle notre classe dans l'OpMode

    @Override
    public void init() { increment.init(hardwareMap);   //On initialise le matériel
    }

    @Override
    public void loop() {
        if (gamepad1.aWasPressed()) {   //démarrer moteur
            increment.setMotorPower(0.5);
        }

        if (gamepad1.yWasPressed()) {  //arrêter moteur
            increment.setMotorPower(0.0);
        }

        if (gamepad1.dpadUpWasPressed()) {  //augmenter power de 0.05
            increment.increaseMotorPower();
        }

        if (gamepad1.dpadDownWasPressed()) {  //diminuer power de 0.05
            increment.decreaseMotorPower();
        }
        telemetry.addData("Motor Power", increment.getMotorPower()); //télémétrie du power
        telemetry.update();
    }
}
