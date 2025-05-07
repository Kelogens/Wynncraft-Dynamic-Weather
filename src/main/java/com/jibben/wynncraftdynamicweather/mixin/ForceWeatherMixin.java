package com.jibben.wynncraftdynamicweather.mixin;

import com.jibben.wynncraftdynamicweather.WynncraftDynamicWeather;
import com.jibben.wynncraftdynamicweather.config.WeatherType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class ForceWeatherMixin {
    @Inject(method = "getRainLevel", at = @At("RETURN"), cancellable = true)
    private void modifyRainLevel(CallbackInfoReturnable<Float> cir) {
        if (!WynncraftDynamicWeather.config.enableMod) return;
        
        cir.setReturnValue(switch (WynncraftDynamicWeather.getWeatherType()) {
            case CLEAR -> 0F;
            case RAIN, THUNDER -> 1.0F;
            default -> cir.getReturnValue();
        });
    }

    @Inject(method = "getThunderLevel", at = @At("RETURN"), cancellable = true)
    private void modifyThunderLevel(CallbackInfoReturnable<Float> cir) {
        if (!WynncraftDynamicWeather.config.enableMod) return;
        
        cir.setReturnValue(WynncraftDynamicWeather.getWeatherType() == WeatherType.THUNDER ? 1.0F : 0F);
    }
}
