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
import com.pedropathing.ivy.Command;
import static com.pedropathing.ivy.groups.Groups.sequential;

public class ExempleAutoOpMode extends OpMode {
    private Follower follower; // Add this

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose startPose = poseFactory.of(36, 8, 0);
    private final Pose scorePose = poseFactory.of(60, 30, 90);
    private final Pose parkPose = poseFactory.of(9, 108, 90);
    private final PoseFactory p = PoseFactory.degrees();


    private Path startToScore() {
        return line(startPose, scorePose).linear(startPose, scorePose);
    }

    private Path park(){
        return line(scorePose, parkPose).linear(scorePose, parkPose);
    }

    private Command autoRoutine() {
        return sequential(
                follow(follower, startToScore()),
                // Add mechanism commands here.
                follow(follower, park())
        );
    }
    /*private final Pose park = p.of(71.5, 48, 0);
    // other poses...
    private final Pose controlPose = p.of(36, 60, 45);

    //poses from before
    private Path park() {
        return curve(startPose, controlPose, park).linear(startPose, park);
    }*/

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();

    }

    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();

    }
}
