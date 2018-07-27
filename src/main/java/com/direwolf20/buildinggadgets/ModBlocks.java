package com.direwolf20.buildinggadgets;

import com.direwolf20.buildinggadgets.blocks.ConstructionBlock;
import com.direwolf20.buildinggadgets.blocks.EffectBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    @GameRegistry.ObjectHolder("buildinggadgets:effectblock")
    public static EffectBlock effectBlock;
    @GameRegistry.ObjectHolder("buildinggadgets:constructionblock")
    public static ConstructionBlock constructionBlock;
}