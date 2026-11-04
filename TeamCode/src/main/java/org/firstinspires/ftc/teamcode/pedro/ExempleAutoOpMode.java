package org.firstinspires.ftc.teamcode.pedro;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

public class ExempleAutoOpMode extends OpMode {
    private Follower follower; // Add this
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(24, 8, 90);
    private final Pose park = p.of(71.5, 48, 0);
    // other poses...
    private final Pose controlPose = p.of(36, 60, 45);
    //poses from before
    private Path park() {
        return curve(startPose, controlPose, park).linear(startPose, park);
    }

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);

    }

    @Override
    public void start() {
        super.start();
        line(startPose, park).linear(startPose, park);
    }

    @Override
    public void loop() {

    }
}
