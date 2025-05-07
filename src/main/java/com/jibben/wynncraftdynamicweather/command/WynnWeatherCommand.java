package com.jibben.wynncraftdynamicweather.command;

import com.jibben.wynncraftdynamicweather.WynncraftDynamicWeather;
import com.jibben.wynncraftdynamicweather.config.WeatherType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.chat.Component;

public class WynnWeatherCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("wynnweather")
                .then(ClientCommandManager.literal("clear")
                    .executes(context -> {
                        handleWeatherChange(context, WeatherType.CLEAR, "command.wynnweather.clear");
                        return 0;
                    }))
                .then(ClientCommandManager.literal("rain")
                    .executes(context -> {
                        handleWeatherChange(context, WeatherType.RAIN, "command.wynnweather.rain");
                        return 0;
                    }))
                .then(ClientCommandManager.literal("thunder")
                    .executes(context -> {
                        handleWeatherChange(context, WeatherType.THUNDER, "command.wynnweather.thunder");
                        return 0;
                    })));

            if (WynncraftDynamicWeather.config.advanced.enableDebugCommand) {
                dispatcher.register(ClientCommandManager.literal("wynnweather")
                    .then(ClientCommandManager.literal("status")
                        .executes(context -> {
                            context.getSource().sendFeedback(Component.literal(
                                "Daily Probability: " + (WynncraftDynamicWeather.getDailyProbability() * 100) + "%" + 
                                " Current Mode: " + WynncraftDynamicWeather.getWeatherType()));
                            return 0;
                        })));
            }
        });
    }

    private static void handleWeatherChange(
        com.mojang.brigadier.context.CommandContext<net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource> context, 
        WeatherType type, String feedbackKey) {
            
        if (!WynncraftDynamicWeather.config.enableMod) {
            context.getSource().sendFeedback(Component.translatable("command.wynnweather.modDisabled"));
        } else {
            WynncraftDynamicWeather.setWeatherType(type);
            context.getSource().sendFeedback(Component.translatable(feedbackKey));
        }
    }
}
