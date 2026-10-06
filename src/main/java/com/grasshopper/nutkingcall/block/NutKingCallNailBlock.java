package com.grasshopper.nutkingcall.block;

import java.util.Random;

import com.grasshopper.nutkingcall.init.InitBlocks;
import com.grasshopper.nutkingcall.tileentity.NutKingCallNailTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class NutKingCallNailBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final IntegerProperty NAILS = IntegerProperty.create("nails", 0, 4);
    public static final IntegerProperty NUTS = IntegerProperty.create("nuts", 0, 4);

    public NutKingCallNailBlock() {
        super(Properties.of(Material.METAL)
                .strength(0.5F)
                .sound(SoundType.METAL)
                .noCollission()
                .noOcclusion()
                .isViewBlocking((state, world, pos) -> false));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.UP)
                .setValue(NAILS, 1)
                .setValue(NUTS, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, NAILS, NUTS);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return Block.box(5, 5, 5, 11, 11, 11);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        BlockState state = this.defaultBlockState().setValue(FACING, direction);
        return state.canSurvive(context.getLevel(), pos) ? state : null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world,
                                  BlockPos currentPos, BlockPos facingPos) {
        if (!state.canSurvive(world, currentPos)) {
            world.destroyBlock(currentPos, true);
        }
        return state;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos supportPos = pos.relative(facing.getOpposite());
        BlockState supportState = world.getBlockState(supportPos);
        return supportState.isFaceSturdy(world, supportPos, facing);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new NutKingCallNailTileEntity();
    }

    public static void placeNail(World world, BlockPos supportPos, Direction face) {
        BlockPos targetPos = supportPos.relative(face);
        BlockState existing = world.getBlockState(targetPos);

        if (existing.getBlock() instanceof NutKingCallNailBlock) {
            int nails = existing.getValue(NAILS);
            if (nails < 4) {
                world.setBlockAndUpdate(targetPos, existing.setValue(NAILS, nails + 1));
            }
            return;
        }

        BlockState supportState = world.getBlockState(supportPos);
        if (!supportState.isFaceSturdy(world, supportPos, face)) return;

        if (existing.isAir(world, targetPos)) {
            world.setBlockAndUpdate(targetPos,
                    InitBlocks.NUT_KING_CALL_NAIL_BLOCK.get()
                            .defaultBlockState()
                            .setValue(FACING, face)
                            .setValue(NAILS, 1)
                            .setValue(NUTS, 0));
        }
    }

    public static void placeNut(World world, BlockPos supportPos, Direction face) {
        BlockPos targetPos = supportPos.relative(face);
        BlockState existing = world.getBlockState(targetPos);

        if (existing.getBlock() instanceof NutKingCallNailBlock) {
            int nuts = existing.getValue(NUTS);
            if (nuts < 4) {
                world.setBlockAndUpdate(targetPos, existing.setValue(NUTS, nuts + 1));
            }
            return;
        }

        if (existing.isAir(world, targetPos)) {
            world.setBlockAndUpdate(targetPos,
                    InitBlocks.NUT_KING_CALL_NAIL_BLOCK.get()
                            .defaultBlockState()
                            .setValue(FACING, face)
                            .setValue(NAILS, 0)
                            .setValue(NUTS, 1));
        }
    }

    public static boolean isClimbable(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof NutKingCallNailBlock) {
            return state.getValue(NAILS) >= 1 && state.getValue(NUTS) >= 1;
        }
        return false;
    }

    public static void dismantleNail(World world, BlockPos nailPos) {
        BlockState nailState = world.getBlockState(nailPos);
        if (!(nailState.getBlock() instanceof NutKingCallNailBlock)) return;

        Direction facing = nailState.getValue(FACING);
        BlockPos supportPos = nailPos.relative(facing.getOpposite());
        BlockState supportState = world.getBlockState(supportPos);

        if (supportState.getDestroySpeed(world, supportPos) < 0) {
            world.removeBlock(nailPos, false);
            return;
        }

        dropMaterials(world, supportState, supportPos);

        world.playSound(null,
                supportPos.getX() + 0.5,
                supportPos.getY() + 0.5,
                supportPos.getZ() + 0.5,
                SoundEvents.GENERIC_EXPLODE,
                SoundCategory.BLOCKS,
                1.0F, 1.0F);

        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(
                    ParticleTypes.EXPLOSION,
                    supportPos.getX() + 0.5,
                    supportPos.getY() + 0.5,
                    supportPos.getZ() + 0.5,
                    1, 0, 0, 0, 0);
        }

        world.removeBlock(supportPos, false);
        world.removeBlock(nailPos, false);
    }

    private static void dropMaterials(World world, BlockState supportState, BlockPos supportPos) {
        RecipeManager recipeManager = world.getRecipeManager();
        ItemStack blockItem = new ItemStack(supportState.getBlock().asItem());

        if (blockItem.isEmpty()) return;

        Random random = world.random;

        for (IRecipe<?> recipe : recipeManager.getRecipes()) {
            if (!(recipe instanceof ICraftingRecipe) || recipe.isSpecial()) continue;
            ICraftingRecipe crafting = (ICraftingRecipe) recipe;
            ItemStack result = crafting.getResultItem();
            if (result.isEmpty()) continue;
            if (!ItemStack.isSame(result, blockItem) || !ItemStack.tagMatches(result, blockItem)) continue;

            NonNullList<Ingredient> ingredients = crafting.getIngredients();
            for (Ingredient ingredient : ingredients) {
                if (ingredient.isEmpty()) continue;
                ItemStack[] matching = ingredient.getItems();
                if (matching.length == 0) continue;
                if (random.nextFloat() < 0.5F) continue;
                ItemStack give = matching[random.nextInt(matching.length)].copy();
                give.setCount(1);
                ItemEntity itemEntity = new ItemEntity(world,
                        supportPos.getX() + 0.5, supportPos.getY() + 0.5, supportPos.getZ() + 0.5, give);
                world.addFreshEntity(itemEntity);
            }
            return;
        }

        ItemEntity itemEntity = new ItemEntity(world,
                supportPos.getX() + 0.5, supportPos.getY() + 0.5, supportPos.getZ() + 0.5, blockItem);
        world.addFreshEntity(itemEntity);
    }
}