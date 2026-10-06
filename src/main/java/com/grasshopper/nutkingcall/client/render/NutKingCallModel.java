package com.grasshopper.nutkingcall.client.render;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.bb.EntityModelUnbaked;
import com.github.standobyte.jojo.client.render.entity.bb.ParseGeckoModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.bb.BlockbenchStandModelHelper;
import com.github.standobyte.jojo.client.render.entity.pose.IModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.util.general.MathUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.grasshopper.nutkingcall.NutKingCallAddon;
import com.grasshopper.nutkingcall.entity.NutKingCallEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class NutKingCallModel extends HumanoidStandModel<NutKingCallEntity> {

    private static final ResourceLocation MODEL = new ResourceLocation(
            NutKingCallAddon.MOD_ID, "geo/nut_king_call.geo.json");

    public NutKingCallModel() {
        super(64, 64);
        loadBlockbenchModel();
    }

    private void loadBlockbenchModel() {
        try (IResource resource = Minecraft.getInstance().getResourceManager().getResource(MODEL);
             Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            JsonElement json = new JsonParser().parse(reader);
            EntityModelUnbaked unbaked = ParseGeckoModel.parseGeckoModel(json, MODEL);
            BlockbenchStandModelHelper.fillFromUnbaked(unbaked, this);
        } catch (Exception e) {
            NutKingCallAddon.LOGGER.error("Failed to load Nut King Call Blockbench model", e);
        }
    }

    @Override
    protected RotationAngle[][] initSummonPoseRotations() {
        return new RotationAngle[][] {

                new RotationAngle[] {
                        RotationAngle.fromDegrees(head, 45F, 0F, 0F),
                        RotationAngle.fromDegrees(body, -12.5F, 0F, 0F),
                        RotationAngle.fromDegrees(torso, 0F, 0F, 0F),
                        RotationAngle.fromDegrees(leftArm, -77.5F, 0F, -42.5F),
                        RotationAngle.fromDegrees(leftForeArm, -87.5F, 0F, 0F),
                        RotationAngle.fromDegrees(rightArm, -55F, 0F, 40F),
                        RotationAngle.fromDegrees(rightArmJoint, -107.5F, 0F, 0F),
                        RotationAngle.fromDegrees(rightForeArm, -102.5F, 0F, 0F),
                        RotationAngle.fromDegrees(leftLeg, -9.851F, -1.728F, -9.8511F),
                        RotationAngle.fromDegrees(leftLowerLeg, 47.5F, 0F, 0F),
                        RotationAngle.fromDegrees(rightLeg, 0F, 0F, 7.5F),
                        RotationAngle.fromDegrees(rightLowerLeg, 42.5F, 0F, 0F)
                },

                new RotationAngle[] {
                        RotationAngle.fromDegrees(head, -25F, 0F, 0F),
                        RotationAngle.fromDegrees(body, 7.5F, 0F, 0F),
                        RotationAngle.fromDegrees(leftArm, -137.5F, 0F, 0F),
                        RotationAngle.fromDegrees(leftArmJoint, -182.5F, 0F, 0F),
                        RotationAngle.fromDegrees(leftForeArm, -90F, 0F, 0F),
                        RotationAngle.fromDegrees(rightArm, -117.5F, 0F, 0F),
                        RotationAngle.fromDegrees(rightArmJoint, -180F, 0F, 0F),
                        RotationAngle.fromDegrees(rightForeArm, -87.5F, 0F, 0F),
                        RotationAngle.fromDegrees(leftLeg, -37.5F, 0F, 0F),
                        RotationAngle.fromDegrees(leftLowerLeg, 82.5F, 0F, 0F),
                        RotationAngle.fromDegrees(rightLeg, 2.5F, 0F, 0F),
                        RotationAngle.fromDegrees(rightLowerLeg, 62.5F, 0F, 0F)
                },

                new RotationAngle[] {
                        RotationAngle.fromDegrees(head, -27.5F, 0F, 0F),
                        RotationAngle.fromDegrees(body, 7.5F, 0F, 0F),
                        RotationAngle.fromDegrees(leftArm, -12.7341F, -7.9937F, -31.6066F),
                        RotationAngle.fromDegrees(leftArmJoint, -182.5F, 0F, 0F),
                        RotationAngle.fromDegrees(leftForeArm, -90F, 0F, 0F),
                        RotationAngle.fromDegrees(rightArm, 16.1066F, -12.0171F, 35.7935F),
                        RotationAngle.fromDegrees(rightArmJoint, -180F, 0F, 0F),
                        RotationAngle.fromDegrees(rightForeArm, -87.5F, 0F, 0F),
                        RotationAngle.fromDegrees(leftLeg, -72.5F, 0F, 0F),
                        RotationAngle.fromDegrees(leftLowerLeg, 105F, 0F, 0F),
                        RotationAngle.fromDegrees(rightLeg, 42.5F, 0F, 0F),
                        RotationAngle.fromDegrees(rightLowerLeg, 110F, 0F, 0F)
                },

                new RotationAngle[] {
                        RotationAngle.fromDegrees(head, 45F, 0F, 0F),
                        RotationAngle.fromDegrees(body, -12.5F, 0F, 0F),
                        RotationAngle.fromDegrees(torso, 0F, 0F, 0F),
                        RotationAngle.fromDegrees(leftArm, -61.0619F, -29.1408F, -12.5689F),
                        RotationAngle.fromDegrees(leftForeArm, -15F, 0F, 0F),
                        RotationAngle.fromDegrees(rightArm, -54.7853F, 29.5032F, 21.565F),
                        RotationAngle.fromDegrees(rightArmJoint, -5F, 0F, 0F),
                        RotationAngle.fromDegrees(rightForeArm, -10F, 0F, 0F),
                        RotationAngle.fromDegrees(leftLeg, -73.4644F, -23.3955F, -16.1068F),
                        RotationAngle.fromDegrees(leftLowerLeg, 97.5F, 0F, 0F),
                        RotationAngle.fromDegrees(rightLeg, -110F, 0F, 7.5F),
                        RotationAngle.fromDegrees(rightLowerLeg, 70F, 0F, 0F)
                },

                new RotationAngle[] {
                        RotationAngle.fromDegrees(head, 27.5F, 0F, 0F),
                        RotationAngle.fromDegrees(body, 35F, 0F, 0F),
                        RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
                        RotationAngle.fromDegrees(torso, 0F, 0F, 0F),
                        RotationAngle.fromDegrees(leftArm, -110F, 0F, 0F),
                        RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 80F),
                        RotationAngle.fromDegrees(rightArm, -87.5F, 0F, 0F),
                        RotationAngle.fromDegrees(rightArmJoint, -25F, 0F, 0F),
                        RotationAngle.fromDegrees(rightForeArm, 0F, 0F, -72.5F),
                        RotationAngle.fromDegrees(leftLeg, -130.0547F, -4.8291F, 1.297F),
                        RotationAngle.fromDegrees(leftLowerLeg, 65F, 0F, 0F),
                        RotationAngle.fromDegrees(rightLeg, -48.3834F, 21.6317F, 21.3083F),
                        RotationAngle.fromDegrees(rightLowerLeg, 67.5F, 0F, 0F)
                }
        };
    }

    @Override
    protected ModelPose<NutKingCallEntity> initIdlePose() {
        return new ModelPose<>(new RotationAngle[] {
                RotationAngle.fromDegrees(head, 0F, 0F, 0F),
                RotationAngle.fromDegrees(body, 0F, 0F, 0F),
                RotationAngle.fromDegrees(leftArm, 0F, 0F, 0F),
                RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 0F),
                RotationAngle.fromDegrees(rightArm, 0F, 0F, 0F),
                RotationAngle.fromDegrees(rightForeArm, 0F, 0F, 0F),
                RotationAngle.fromDegrees(leftLeg, 0F, 0F, 0F),
                RotationAngle.fromDegrees(leftLowerLeg, 0F, 0F, 0F),
                RotationAngle.fromDegrees(rightLeg, 0F, 0F, 0F),
                RotationAngle.fromDegrees(rightLowerLeg, 0F, 0F, 0F)
        });
    }

    @Override
    protected IModelPose<NutKingCallEntity> initIdlePose2Loop() {
        return initIdlePose();
    }

    @Override
    protected void initActionPoses() {
        ModelPose<NutKingCallEntity> idle = initIdlePose();

        StandPose attack1Pose = new StandPose("attack1");
        ModelPose<NutKingCallEntity> attack1Perform = new ModelPose<>(new RotationAngle[] {
                RotationAngle.fromDegrees(head, 0F, 0F, 0F),
                RotationAngle.fromDegrees(body, 0F, 5F, 0F),
                RotationAngle.fromDegrees(leftArm, 37.5F, 0F, -15F),
                RotationAngle.fromDegrees(leftForeArm, -115F, 15F, 7.5F),
                RotationAngle.fromDegrees(rightArm, -89.4F, 11F, 70.3F),
                RotationAngle.fromDegrees(rightForeArm, 0F, 0F, 0F)
        });
        actionAnim.put(attack1Pose, new PosedActionAnimation.Builder<NutKingCallEntity>()
                .addPose(StandEntityAction.Phase.PERFORM, attack1Perform)
                .addPose(StandEntityAction.Phase.RECOVERY, idle)
                .build(idle));

        StandPose attack2Pose = new StandPose("attack2");
        ModelPose<NutKingCallEntity> attack2Perform = new ModelPose<>(new RotationAngle[] {
                RotationAngle.fromDegrees(head, 0F, 0F, 0F),
                RotationAngle.fromDegrees(body, 0F, -5F, 0F),
                RotationAngle.fromDegrees(rightArm, 37.5F, 0F, 15F),
                RotationAngle.fromDegrees(rightForeArm, -115F, -15F, -7.5F),
                RotationAngle.fromDegrees(leftArm, -89.4F, -11F, -70.3F),
                RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 0F)
        });
        actionAnim.put(attack2Pose, new PosedActionAnimation.Builder<NutKingCallEntity>()
                .addPose(StandEntityAction.Phase.PERFORM, attack2Perform)
                .addPose(StandEntityAction.Phase.RECOVERY, idle)
                .build(idle));

        StandPose barragePose = StandPose.BARRAGE;
        ModelPose<NutKingCallEntity> barragePerform = new ModelPose<>(new RotationAngle[] {
                RotationAngle.fromDegrees(head, 17.5F, 0F, 0F),
                RotationAngle.fromDegrees(body, 10F, 0F, 0F),
                RotationAngle.fromDegrees(upperPart, 0F, 30F, 0F),
                RotationAngle.fromDegrees(leftArm, -90F, 0F, -90F),
                RotationAngle.fromDegrees(leftForeArm, -135F, 0F, 0F),
                RotationAngle.fromDegrees(rightArm, 22.5F, 0F, 60F),
                RotationAngle.fromDegrees(rightForeArm, 0F, 0F, 0F)
        });
        actionAnim.put(barragePose, new NutKingCallBarrageAnimation(
                this,
                barragePerform,
                idle));

        StandPose blockPose = new StandPose("block");
        ModelPose<NutKingCallEntity> blockModelPose = new ModelPose<>(new RotationAngle[] {
                RotationAngle.fromDegrees(head, 0F, 0F, 0F),
                RotationAngle.fromDegrees(body, 10F, 0F, 0F),
                RotationAngle.fromDegrees(leftArm, -90F, 0F, 45F),
                RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 60F),
                RotationAngle.fromDegrees(rightArm, -90F, 0F, -45F),
                RotationAngle.fromDegrees(rightForeArm, 0F, 0F, -60F)
        });
        actionAnim.put(blockPose, new PosedActionAnimation.Builder<NutKingCallEntity>()
                .addPose(StandEntityAction.Phase.BUTTON_HOLD, blockModelPose)
                .addPose(StandEntityAction.Phase.PERFORM, blockModelPose)
                .addPose(StandEntityAction.Phase.RECOVERY, idle)
                .build(idle));

        StandPose heavyPunchPose = new StandPose("heavyPunch");
        ModelPose<NutKingCallEntity> heavyPunchPerform = new ModelPose<>(new RotationAngle[] {
                RotationAngle.fromDegrees(head, 0F, 0F, 0F),
                RotationAngle.fromDegrees(body, 12.5F, -30F, 0F),
                RotationAngle.fromDegrees(upperPart, 0F, -30F, 0F),
                RotationAngle.fromDegrees(leftArm, 0F, 25F, -90F),
                RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 67.5F),
                RotationAngle.fromDegrees(rightArm, -80.2F, 68F, -5.7F),
                RotationAngle.fromDegrees(rightForeArm, -90F, 50F, -90F)
        });
        actionAnim.put(heavyPunchPose, new PosedActionAnimation.Builder<NutKingCallEntity>()
                .addPose(StandEntityAction.Phase.PERFORM, heavyPunchPerform)
                .addPose(StandEntityAction.Phase.RECOVERY, idle)
                .build(idle));

        StandPose finisherPunchPose = new StandPose("finisherPunch");
        ModelPose<NutKingCallEntity> finisherPunchPerform = new ModelPose<>(new RotationAngle[] {
                RotationAngle.fromDegrees(head, 0F, 0F, 0F),
                RotationAngle.fromDegrees(body, 12.5F, -30F, 0F),
                RotationAngle.fromDegrees(upperPart, 0F, -30F, 0F),
                RotationAngle.fromDegrees(leftArm, 0F, 25F, -90F),
                RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 67.5F),
                RotationAngle.fromDegrees(rightArm, -80.2F, 68F, -5.7F),
                RotationAngle.fromDegrees(rightForeArm, -90F, 50F, -90F)
        });
        actionAnim.put(finisherPunchPose, new PosedActionAnimation.Builder<NutKingCallEntity>()
                .addPose(StandEntityAction.Phase.PERFORM, finisherPunchPerform)
                .addPose(StandEntityAction.Phase.RECOVERY, idle)
                .build(idle));

        StandPose flyingKickPose = new StandPose("new");
        ModelPose<NutKingCallEntity> flyingKickPerform = new ModelPose<>(new RotationAngle[] {
                RotationAngle.fromDegrees(head, 27.5F, 0F, 0F),
                RotationAngle.fromDegrees(body, 35F, 0F, 0F),
                RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
                RotationAngle.fromDegrees(torso, 0F, 0F, 0F),
                RotationAngle.fromDegrees(leftArm, -110F, 0F, 0F),
                RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 80F),
                RotationAngle.fromDegrees(rightArm, -87.5F, 0F, 0F),
                RotationAngle.fromDegrees(rightArmJoint, -25F, 0F, 0F),
                RotationAngle.fromDegrees(rightForeArm, 0F, 0F, -72.5F),
                RotationAngle.fromDegrees(leftLeg, -130.0547F, -4.8291F, 1.297F),
                RotationAngle.fromDegrees(leftLowerLeg, 65F, 0F, 0F),
                RotationAngle.fromDegrees(rightLeg, -48.3834F, 21.6317F, 21.3083F),
                RotationAngle.fromDegrees(rightLowerLeg, 67.5F, 0F, 0F)
        });
        actionAnim.put(flyingKickPose, new PosedActionAnimation.Builder<NutKingCallEntity>()
                .addPose(StandEntityAction.Phase.PERFORM, flyingKickPerform)
                .addPose(StandEntityAction.Phase.RECOVERY, idle)
                .build(idle));
    }

    @Override
    public void setupAnim(NutKingCallEntity entity, float walkAnimPos, float walkAnimSpeed,
                          float ticks, float yRotationOffset, float xRotation) {
        super.setupAnim(entity, walkAnimPos, walkAnimSpeed, ticks, yRotationOffset, xRotation);

        if (this.head != null) {
            this.head.xRot = xRotation * MathUtil.DEG_TO_RAD;
            this.head.yRot = yRotationOffset * MathUtil.DEG_TO_RAD;
            this.head.zRot = 0;
        }
    }
}