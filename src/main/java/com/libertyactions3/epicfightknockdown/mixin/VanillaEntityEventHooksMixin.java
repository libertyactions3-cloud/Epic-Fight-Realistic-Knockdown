package com.libertyactions3.epicfightknockdown.mixin;

import java.util.function.Consumer;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import yesman.epicfight.api.event.impl.VanillaEntityEventHooks;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HurtableEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;

@Mixin(value = VanillaEntityEventHooks.class, remap = false)
public abstract class VanillaEntityEventHooksMixin {
    @ModifyArgs(
            method = "onCalculateDamagePre",
            at = @At(
                    value = "INVOKE",
                    target = "Lyesman/epicfight/world/capabilities/entitypatch/HurtableEntityPatch;applyStun(Lyesman/epicfight/world/damagesource/StunType;F)Z"
            ),
            remap = false
    )
    private static void epicfightrealisticknockdown$chooseFollowupStunReaction(
            Args args,
            LivingEntity hitEntity,
            DamageSource damageSource,
            float amount,
            Consumer<Float> modifiedDamageApplier
    ) {
        if (!epicfightrealisticknockdown$wasAlreadyStunned(hitEntity)) {
            return;
        }

        if (!damageSource.is(EpicFightDamageTypeTags.IS_MELEE)) {
            args.set(0, StunType.NEUTRALIZE);
            args.set(1, 2.0F);
            return;
        }

        StunType originalStunType = args.get(0);
        float originalStunTime = args.get(1);

        if (originalStunType == StunType.NONE) {
            args.set(0, StunType.SHORT);
        }

        args.set(1, Math.max(originalStunTime, 0.25F));
    }

    @Inject(method = "onCalculateDamagePre", at = @At("HEAD"), remap = false)
    private static void epicfightrealisticknockdown$handleOtherDamageDuringStun(
            LivingEntity hitEntity,
            DamageSource damageSource,
            float amount,
            Consumer<Float> modifiedDamageApplier,
            CallbackInfo ci
    ) {
        if (amount <= 0.0F) {
            return;
        }

        if (damageSource instanceof EpicFightDamageSource epicFightDamageSource
                && !epicFightDamageSource.is(EpicFightDamageTypeTags.NO_STUN)) {
            return;
        }

        HurtableEntityPatch<?> entityPatch =
                EpicFightCapabilities.getEntityPatch(hitEntity, HurtableEntityPatch.class);

        if (entityPatch == null || !entityPatch.isStunned()) {
            return;
        }

        if (damageSource.is(EpicFightDamageTypeTags.IS_MELEE)) {
            entityPatch.applyStun(StunType.SHORT, 0.25F);
        } else {
            entityPatch.applyStun(StunType.NEUTRALIZE, 2.0F);
        }
    }

    private static boolean epicfightrealisticknockdown$wasAlreadyStunned(LivingEntity hitEntity) {
        HurtableEntityPatch<?> entityPatch =
                EpicFightCapabilities.getEntityPatch(hitEntity, HurtableEntityPatch.class);

        return entityPatch != null && entityPatch.isStunned();
    }
}
