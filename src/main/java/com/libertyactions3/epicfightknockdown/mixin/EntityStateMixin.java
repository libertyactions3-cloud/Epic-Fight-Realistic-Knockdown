package com.libertyactions3.epicfightknockdown.mixin;

import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.AttackResult;

@Mixin(value = EntityState.class, remap = false)
public abstract class EntityStateMixin {
    @Shadow
    public abstract boolean hurt();

    @Shadow
    public abstract boolean knockDown();

    @Inject(method = "attackResult", at = @At("HEAD"), cancellable = true, remap = false)
    private void epicfightrealisticknockdown$allowHitWhileStunned(
            DamageSource damageSource,
            CallbackInfoReturnable<AttackResult.ResultType> cir
    ) {
        if (this.hurt() || this.knockDown()) {
            cir.setReturnValue(AttackResult.ResultType.SUCCESS);
        }
    }
}
