package me.sabitheotome.timedilation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.sabitheotome.timedilation.Main;
import net.minecraft.client.render.RenderTickCounter;

@Mixin(RenderTickCounter.class)
public abstract class RenderTickCounterMixin {
    @Shadow
    @Mutable
    private float tickTime;

    @Inject(method = "beginRenderTick(J)I", at = @At("HEAD"))
    public void beginRenderTick(long timeMillis, CallbackInfoReturnable<Integer> cir) {
        tickTime = (float) (50 / Main.TICKRATE_MULTIPLIER.get());
    }
}