package haloofblocks.additionalguns;

import haloofblocks.additionalguns.client.ClientHandler;
import haloofblocks.additionalguns.config.Config;
import haloofblocks.additionalguns.datagen.ModRecipeGenerator;
import haloofblocks.additionalguns.core.registry.ItemRegistry;
import haloofblocks.additionalguns.core.registry.SoundRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author Autovw
 */
@Mod(AdditionalGuns.ID)
public class AdditionalGuns {
    public static final String ID = "additionalguns";

    public AdditionalGuns() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientConfig);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(this);
        AdditionalGunsTab.ADDITIONAL_TAB.register(bus);

        ItemRegistry.ITEMS.register(bus);
        SoundRegistry.SOUNDS.register(bus);

        bus.addListener(this::clientSetup);
        bus.addListener(this::gatherData);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(ClientHandler::registerModelOverrides);
    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        generator.addProvider(event.includeServer(), new ModRecipeGenerator(packOutput));
    }
}
