package com.jibben.wynncraftdynamicweather.mixin;

import com.jibben.wynncraftdynamicweather.WynncraftDynamicWeather;
import com.jibben.wynncraftdynamicweather.region.MapRegion;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {
    @Unique
    private final MapRegion nesaak = new MapRegion(-475, -575, 310, -940, 0.95);
    @Unique
    private final MapRegion lusuco = new MapRegion(-475, -575, -85, -295, 0.95);
    @Unique
    private final MapRegion almuj = new MapRegion(850, -2330, 1500, -1180, 0.50);

    @Inject(method = "getPrecipitationAt", at = @At("RETURN"), cancellable = true)
    private void modifyPrecipitation(BlockPos pos, int climate, CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (!WynncraftDynamicWeather.config.enableMod) return;
        
        if (nesaak.isWithin(pos) || lusuco.isWithin(pos)) {
            cir.setReturnValue(Biome.Precipitation.SNOW);
        } else if (almuj.isWithin(pos)) {
            cir.setReturnValue(Biome.Precipitation.NONE);
        }
    }

    @Inject(method = "getBaseTemperature", at = @At("RETURN"), cancellable = true)
    private void modifyTemperature(CallbackInfoReturnable<Float> cir) {
        if (Minecraft.getInstance().player != null) {
            BlockPos pos = Minecraft.getInstance().player.blockPosition();
            if (almuj.isWithin(pos) && WynncraftDynamicWeather.config.enableMod) {
                cir.setReturnValue(1.0F);
            }
        }
    }
}
