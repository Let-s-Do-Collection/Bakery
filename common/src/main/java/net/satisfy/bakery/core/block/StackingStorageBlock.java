package net.satisfy.bakery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.foundation.storage.StorageBlock;
import net.satisfy.foundation.storage.StorageBlockEntity;
import net.satisfy.foundation.util.ShapeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class StackingStorageBlock extends StorageBlock {
    public StackingStorageBlock(Properties settings) {
        super(settings);
    }

    public static final int MAX_STACK = 64;

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof StorageBlockEntity display)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        Optional<Tuple<Float, Float>> hitCoordinates = ShapeUtil.getRelativeHitCoordinatesForBlockFace(hit, state.getValue(FACING), this.unAllowedDirections());
        if (hitCoordinates.isEmpty()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        int section = this.getSection(hitCoordinates.get().getA(), hitCoordinates.get().getB());
        ItemStack current = display.getInventory().get(section);

        if (!stack.isEmpty() && this.canInsertStack(stack) && (current.isEmpty() || (ItemStack.isSameItemSameComponents(current, stack) && current.getCount() < MAX_STACK))) {
            if (!level.isClientSide) {
                int moved = Math.min(stack.getCount(), MAX_STACK - current.getCount());
                ItemStack placed = current.isEmpty() ? stack.copyWithCount(moved) : current.copyWithCount(current.getCount() + moved);
                display.setStack(section, placed);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(moved);
                }
                level.playSound(null, pos, this.getAddSound(level, pos, player, section), SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!current.isEmpty()) {
            if (!level.isClientSide) {
                ItemStack taken = current.copyWithCount(player.isShiftKeyDown() ? current.getCount() : 1);
                int left = current.getCount() - taken.getCount();
                display.setStack(section, left > 0 ? current.copyWithCount(left) : ItemStack.EMPTY);
                if (!player.getInventory().add(taken)) {
                    player.drop(taken, false);
                }
                level.playSound(null, pos, this.getRemoveSound(level, pos, player, section), SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return ItemInteractionResult.CONSUME;
    }
}
