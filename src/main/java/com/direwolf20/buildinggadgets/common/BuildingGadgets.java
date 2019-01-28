package com.direwolf20.buildinggadgets.common;

import com.direwolf20.buildinggadgets.common.config.Config;
import com.direwolf20.buildinggadgets.common.entities.BlockBuildEntity;
import com.direwolf20.buildinggadgets.common.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.jvm.hotspot.asm.Register;

import javax.annotation.Nonnull;
import java.util.function.Supplier;


@Mod(value = BuildingGadgets.MODID)
public class BuildingGadgets {
    public static final String MODID = "buildinggadgets";
    public static final String MODNAME = "Building Gadgets";
    public static final String VERSION = "@VERSION@";
    public static final String UPDATE_JSON = "@UPDATE@";
    public static final String DEPENDENCIES = "required-after:forge@[14.23.3.2694,)";
    public static Logger logger = LogManager.getLogger();
    private static BuildingGadgets theMod = null;

    public static BuildingGadgets getInstance() {
        assert theMod != null;
        return theMod;
    }

    private Supplier<Minecraft> minecraftSupplier;
    public BuildingGadgets() {
        theMod = (BuildingGadgets) FMLModLoadingContext.get().getActiveContainer().getMod();
        FMLModLoadingContext.get().getModEventBus().addListener(this::preInit);
        FMLModLoadingContext.get().getModEventBus().addListener(this::clientInit);
        MinecraftForge.EVENT_BUS.register(this);
        minecraftSupplier = null;
    }

    @Nonnull
    public Minecraft getMinecraft() {
        if (minecraftSupplier == null) throw new RuntimeException("Attempted to access Minecraft instance server Side");
        Minecraft mc = minecraftSupplier.get();
        assert mc != null;
        return mc;
    }

    private void preInit(final FMLCommonSetupEvent event) {
        Config.load();

        DeferredWorkQueue.runLater(PacketHandler::register);
    }

    private void clientInit(final FMLClientSetupEvent event) {
        minecraftSupplier = event.getMinecraftSupplier();
    }

    /*
    public static final CreativeTabs BUILDING_CREATIVE_TAB = new CreativeTabs(new TextComponentTranslation("buildingGadgets").getUnformattedComponentText()) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.gadgetBuilding);
        }
    };

    @SidedProxy(clientSide = "com.direwolf20.buildinggadgets.client.proxy.ClientProxy", serverSide = "com.direwolf20.buildinggadgets.common.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static BuildingGadgets instance;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        if (!SyncedConfig.poweredByFE) {
            MinecraftForge.EVENT_BUS.register(new AnvilRepairHandler());
        }
    }

    @Mod.EventHandler
    public void init(@SuppressWarnings("unused") FMLInitializationEvent e) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(@SuppressWarnings("unused") FMLPostInitializationEvent e) {
        proxy.postInit();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new FindBlockMapsCommand());
        event.registerServerCommand(new DeleteBlockMapsCommand());
    }
    */
}
