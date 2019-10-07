package se.mickelus.tetra.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import se.mickelus.tetra.blocks.ITetraBlock;
import se.mickelus.tetra.items.ITetraItem;

public interface IProxy {

    public void preInit(ITetraItem[] items, ITetraBlock[] blocks);
    public void init(FMLCommonSetupEvent event);
    public void postInit();
}
