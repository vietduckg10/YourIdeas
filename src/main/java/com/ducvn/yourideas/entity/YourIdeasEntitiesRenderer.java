package com.ducvn.yourideas.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class YourIdeasEntitiesRenderer {
    public static void registerEntityRenders(){
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(YourIdeasEntitiesRegister.THROWABLE_BRICK.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
    }

}
