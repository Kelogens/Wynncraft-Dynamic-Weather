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
public class PreventVanillaSnowMixin {
    @Unique
    private final MapRegion nesaak = new MapRegion(-475, -575, 310, -940, 0.95);
    @Unique
    private final MapRegion lusuco = new MapRegion(-475, -575, -85, -295, 0.95);

    @Inject(method = "getPrecipitationAt", at = @At("RETURN"), cancellable = true)
    private void modifyPrecipitation(BlockPos pos, int climate, CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (Minecraft.getInstance().player != null 
            && !WynncraftDynamicWeather.config.renderVanillaSnow
            && (nesaak.isWithin(pos) || lusuco.isWithin(pos))) {
            cir.setReturnValue(Biome.Precipitation.NONE);
        }
    }
}
