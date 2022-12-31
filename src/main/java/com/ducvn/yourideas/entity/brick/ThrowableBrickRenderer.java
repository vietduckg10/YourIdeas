package com.ducvn.yourideas.entity.brick;

import com.ducvn.yourideas.entity.EntitiesRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ThrowableBrickRenderer{
    public static void registerEntityRenders(){
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(EntitiesRegister.THROWABLE_BRICK.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
    }

}
