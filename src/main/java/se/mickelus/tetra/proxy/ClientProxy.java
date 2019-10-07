package se.mickelus.tetra.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.animation.TileEntityRendererAnimation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import se.mickelus.tetra.ConfigHandler;
import se.mickelus.tetra.blocks.ITetraBlock;
import se.mickelus.tetra.blocks.forged.container.TESRForgedContainer;
import se.mickelus.tetra.blocks.forged.container.TileEntityForgedContainer;
import se.mickelus.tetra.blocks.forged.extractor.TileEntityCoreExtractorPiston;
import se.mickelus.tetra.blocks.hammer.TileEntityHammerHead;
import se.mickelus.tetra.blocks.salvage.CapabililtyInteractiveOverlay;
import se.mickelus.tetra.blocks.workbench.TESRWorkbench;
import se.mickelus.tetra.blocks.workbench.TileEntityWorkbench;
import se.mickelus.tetra.client.model.ModularModelLoader;
import se.mickelus.tetra.generation.ExtendedStructureTESR;
import se.mickelus.tetra.items.ITetraItem;
import se.mickelus.tetra.items.toolbelt.OverlayToolbelt;
import se.mickelus.tetra.items.toolbelt.booster.OverlayBooster;

import java.util.Arrays;

public class ClientProxy implements IProxy {

    static {
        ModelLoaderRegistry.registerLoader(ModularModelLoader.instance);
    }

    @Override
    public void preInit(ITetraItem[] items, ITetraBlock[] blocks) {
        Arrays.stream(items).forEach(ITetraItem::clientPreInit);
        Arrays.stream(blocks).forEach(ITetraBlock::clientPreInit);
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWorkbench.class, new TESRWorkbench());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHammerHead.class, new TileEntityRendererAnimation<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoreExtractorPiston.class, new TileEntityRendererAnimation<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForgedContainer.class, new TESRForgedContainer());

        if (ConfigHandler.development) {
            ClientRegistry.bindTileEntitySpecialRenderer(StructureBlockTileEntity.class, new ExtendedStructureTESR());
        }
    }

    @Override
    public void postInit() {
        MinecraftForge.EVENT_BUS.register(new OverlayToolbelt(Minecraft.getInstance()));
        MinecraftForge.EVENT_BUS.register(new OverlayBooster(Minecraft.getInstance()));
        MinecraftForge.EVENT_BUS.register(new CapabililtyInteractiveOverlay());
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        if (ConfigHandler.generateFeatures) {
            // provides a decent item model for the container (which uses a TESR) without messing around with millions of blockstate variants

            // todo 1.14: statemappers should no longer be required as props can be ignored in the blockstate .json instead
//            ModelLoader.setCustomStateMapper(BlockForgedContainer.instance, new StateMapperBase() {
//                @Override
//                protected ModelResourceLocation getModelResourceLocation(BlockState state) {
//                    return new ModelResourceLocation(TetraMod.MOD_ID + ":forged_container");
//                }
//            });
//
//            ModelLoader.setCustomStateMapper(BlockForgedCrate.instance, new StateMap.Builder().ignore(BlockForgedCrate.propIntegrity).build());
//
//            ModelLoader.setCustomStateMapper(BlockSeepingBedrock.instance, new StateMapperBase() {
//                @Override
//                protected ModelResourceLocation getModelResourceLocation(BlockState state) {
//                    return new ModelResourceLocation(TetraMod.MOD_ID + ":seeping_bedrock",
//                            "active=" + (state.getValue(BlockSeepingBedrock.propActive) > 0));
//                }
//            });
        }
    }
}
