package net.satisfy.bakery.core.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.bakery.core.registry.ObjectRegistry;
import net.satisfy.farm_and_charm.core.block.EatableBoxBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BreadBasketBlock extends EatableBoxBlock {
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 4);
    private static final VoxelShape SHAPE = Shapes.box(0.125, 0.0, 0.125, 0.875, 0.625, 0.875);

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public BreadBasketBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (world.isClientSide) {
            if (tryEat(world, pos, state, player).consumesAction()) {
                return ItemInteractionResult.SUCCESS;
            }
            if (itemStack.isEmpty()) {
                return ItemInteractionResult.CONSUME;
            }
        }
        return tryEat(world, pos, state, player);
    }

    private ItemInteractionResult tryEat(Level world, BlockPos pos, BlockState state, Player player) {
        world.playSound(null, pos, SoundEvents.FOX_EAT, SoundSource.PLAYERS, 0.5f, world.getRandom().nextFloat() * 0.1f + 0.9f);
        player.getFoodData().eat(6, 0.8f);
        int bites = state.getValue(BITES);
        world.gameEvent(player, GameEvent.EAT, pos);
        if (bites < 4) {
            world.setBlock(pos, state.setValue(BITES, bites + 1), UPDATE_ALL);
        } else {
            world.destroyBlock(pos, false);
            ItemStack bowlStack = new ItemStack(ObjectRegistry.TRAY.get());
            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, bowlStack);
            world.addFreshEntity(itemEntity);
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BITES);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("tooltip.farm_and_charm.canbeplaced").withStyle(ChatFormatting.GRAY));
    }
}


