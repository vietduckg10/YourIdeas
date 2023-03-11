package com.ducvn.yourideas;

import com.ducvn.yourideas.block.YourIdeasBlocksRegister;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.effect.YourIdeasEffectsRegister;
import com.ducvn.yourideas.entity.YourIdeasEntitiesRegister;
import com.ducvn.yourideas.entity.YourIdeasEntitiesRenderer;
import com.ducvn.yourideas.item.YourIdeasItemsRegister;
import com.ducvn.yourideas.potion.YourIdeasPotionsRegister;
import com.ducvn.yourideas.setup.YourIdeasBrewingSetUp;
import com.ducvn.yourideas.setup.YourIdeasDispenserSetUp;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(YourIdeasMod.MODID)
public class YourIdeasMod
{
    public static final String MODID = "yourideas";

    public YourIdeasMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register mod stuff :3
        YourIdeasItemsRegister.init(eventBus);
        YourIdeasBlocksRegister.init(eventBus);
        YourIdeasPotionsRegister.init(eventBus);
        YourIdeasEntitiesRegister.init(eventBus);
        YourIdeasEffectsRegister.init(eventBus);

        // Register ourselves for server and other game events we are interested in
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, YourIdeasConfig.SPEC, "YourIdeas-common.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            YourIdeasConfig.load(FMLPaths.CONFIGDIR.get().resolve("YourIdeas-common.toml"));
            YourIdeasDispenserSetUp.registerBehavior();
            YourIdeasBrewingSetUp.registerBrewingRecipes();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> YourIdeasEntitiesRenderer::registerEntityRenders);
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(YourIdeasBlocksRegister.SLIME_SPLASH_BLOCK.get(), RenderType.cutout());
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        }
    }
}
