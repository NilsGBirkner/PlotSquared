package com.github.intellectualsites.plotsquared.plot.flags.types;

import com.github.intellectualsites.plotsquared.plot.config.Captions;
import com.github.intellectualsites.plotsquared.plot.flags.FlagParseException;
import com.github.intellectualsites.plotsquared.plot.util.world.BlockUtil;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BlockTypeListFlag extends ListFlag<BlockType, BlockTypeListFlag> {

    public BlockTypeListFlag(List<BlockType> blockTypeList, Captions description) {
        super(blockTypeList, Captions.FLAG_CATEGORY_BLOCK_LIST, description);
    }

    @Override public BlockTypeListFlag parse(@NotNull String input) throws FlagParseException {
        final List<BlockType> parsedBlocks = new ArrayList<>();
        final String[] split = input.split(",(?![^\\(\\[]*[\\]\\)])");
        if (split.length == 0) {
            return this.flagOf(parsedBlocks);
        }
        for (final String blockString : split) {
            final BlockState blockState = BlockUtil.get(blockString);
            if (blockState == null) {
                throw new FlagParseException(this, blockString, Captions.FLAG_ERROR_INVALID_BLOCK);
            } else {
                parsedBlocks.add(blockState.getBlockType());
            }
        }
        return this.flagOf(parsedBlocks);
    }

    @Override public String getExample() {
        return "air,grass_block";
    }

    @Override protected BlockTypeListFlag flagOf(@NotNull List<BlockType> value) {
        return new BlockTypeListFlag(value, getFlagDescription()); // copy the description
    }

}