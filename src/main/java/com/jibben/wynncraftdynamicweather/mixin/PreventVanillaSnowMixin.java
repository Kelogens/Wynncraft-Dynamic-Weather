package com.jibben.wynncraftdynamicweather.mixin;

import com.jibben.wynncraftdynamicweather.WynncraftDynamicWeather;
import com.jibben.wynncraftdynamicweather.region.MapRegion;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WeatherEffectRenderer.class)
public class PreventVanillaSnowMixin {
    @Unique
    MapRegion nesaak = new MapRegion(-475, -575, 310, -940, 0.95);
    @Unique
    MapRegion lusuco = new MapRegion(-475, -575, -85, -295, 0.95);

    @ModifyReturnValue(
            method = "getPrecipitationAt",
            at = @At("RETURN")
    )
    private Biome.Precipitation modifyPrecipitation(Biome.Precipitation original, Level level, BlockPos pos) {
        if (Minecraft.getInstance().player != null) {
            if (!WynncraftDynamicWeather.config.renderVanillaSnow) {
                if (nesaak.isWithin(pos) || lusuco.isWithin(pos)) {
                    return Biome.Precipitation.NONE;
                }
            }
        }
        return original;
    }
}