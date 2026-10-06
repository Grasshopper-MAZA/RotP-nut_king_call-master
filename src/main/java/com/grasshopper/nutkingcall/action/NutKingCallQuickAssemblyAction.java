package com.grasshopper.nutkingcall.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.nutkingcall.entity.NutKingCallEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class NutKingCallQuickAssemblyAction extends StandEntityAction {

    private static final Random RANDOM = new Random();
    private static final float ASSEMBLE_FAIL_CHANCE = 0.10F;
    private static final float DISASSEMBLE_LOST_CHANCE = 0.15F;

    public NutKingCallQuickAssemblyAction(Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) {
            return;
        }

        LivingEntity user = userPower.getUser();
        if (!(user instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) user;

        boolean disassemble = standEntity instanceof NutKingCallEntity
                && ((NutKingCallEntity) standEntity).isDisassembleMode();

        if (disassemble) {
            doDisassemble(player, world);
        } else {
            doAssemble(player, world);
        }
    }

    @Override
    protected ResourceLocation getIconTexturePath(IStandPower power) {
        if (power != null && power.isActive() && power.getStandManifestation() instanceof NutKingCallEntity) {
            boolean disassemble = ((NutKingCallEntity) power.getStandManifestation()).isDisassembleMode();
            return new ResourceLocation("nutkingcall", "textures/action/nut_king_call_quick_assembly_"
                    + (disassemble ? "disassemble" : "assemble") + ".png");
        }
        return super.getIconTexturePath(power);
    }

    private void doAssemble(PlayerEntity player, World world) {
        RecipeManager recipeManager = world.getRecipeManager();
        List<ICraftingRecipe> craftable = new ArrayList<>();

        for (IRecipe<?> recipe : recipeManager.getRecipes()) {
            if (recipe instanceof ICraftingRecipe && !recipe.isSpecial()) {
                ICraftingRecipe crafting = (ICraftingRecipe) recipe;
                if (canCraft(player, crafting)) {
                    craftable.add(crafting);
                }
            }
        }

        if (craftable.isEmpty()) {
            notifyPlayer(player, "action.nutkingcall.nut_king_call_quick_assembly.no_material");
            return;
        }

        ICraftingRecipe chosen = craftable.get(RANDOM.nextInt(craftable.size()));

        if (RANDOM.nextFloat() < ASSEMBLE_FAIL_CHANCE) {
            notifyPlayer(player, "action.nutkingcall.nut_king_call_quick_assembly.assemble_fail");
            return;
        }

        consumeIngredients(player, chosen);

        ItemStack result = chosen.getResultItem();
        if (!result.isEmpty()) {
            ItemStack give = result.copy();

            if (give.isDamageableItem()) {
                int maxDurability = give.getMaxDamage();
                float damageRatio = 0.1F + RANDOM.nextFloat() * 0.2F;
                int damage = (int) (maxDurability * damageRatio);
                give.setDamageValue(Math.min(damage, maxDurability - 1));
            }

            if (!player.inventory.add(give)) {
                player.drop(give, false);
            }

            notifyPlayer(player, "action.nutkingcall.nut_king_call_quick_assembly.assemble_success");
        }
    }

    private void doDisassemble(PlayerEntity player, World world) {
        RecipeManager recipeManager = world.getRecipeManager();

        List<Integer> candidateSlots = new ArrayList<>();
        for (int i = 0; i < player.inventory.getContainerSize(); i++) {
            ItemStack stack = player.inventory.getItem(i);
            if (!stack.isEmpty() && canDisassemble(stack, recipeManager)) {
                candidateSlots.add(i);
            }
        }

        if (candidateSlots.isEmpty()) {
            notifyPlayer(player, "action.nutkingcall.nut_king_call_quick_assembly.no_disassemble");
            return;
        }

        int slot = candidateSlots.get(RANDOM.nextInt(candidateSlots.size()));
        ItemStack chosenStack = player.inventory.getItem(slot);

        for (IRecipe<?> recipe : recipeManager.getRecipes()) {
            if (!(recipe instanceof ICraftingRecipe) || recipe.isSpecial()) {
                continue;
            }
            ICraftingRecipe crafting = (ICraftingRecipe) recipe;
            ItemStack result = crafting.getResultItem();
            if (result.isEmpty()) {
                continue;
            }
            if (!ItemStack.isSame(result, chosenStack) || !ItemStack.tagMatches(result, chosenStack)) {
                continue;
            }
            NonNullList<Ingredient> ingredients = crafting.getIngredients();
            if (!isReversibleRecipe(ingredients)) {
                continue;
            }

            float durabilityFactor = 1.0F;
            if (chosenStack.isDamageableItem()) {
                int max = chosenStack.getMaxDamage();
                int damage = chosenStack.getDamageValue();
                durabilityFactor = max > 0 ? (float) (max - damage) / max : 1.0F;
            }

            chosenStack.shrink(1);
            if (chosenStack.isEmpty()) {
                player.inventory.setItem(slot, ItemStack.EMPTY);
            }

            int lost = 0;
            for (Ingredient ingredient : ingredients) {
                if (ingredient.isEmpty()) {
                    continue;
                }
                ItemStack[] matching = ingredient.getItems();
                if (matching.length == 0) {
                    continue;
                }

                float lostChance = DISASSEMBLE_LOST_CHANCE + (1.0F - durabilityFactor) * 0.5F;
                if (RANDOM.nextFloat() < lostChance) {
                    lost++;
                    continue;
                }

                ItemStack give = matching[0].copy();
                give.setCount(1);
                if (!player.inventory.add(give)) {
                    player.drop(give, false);
                }
            }

            if (lost > 0) {
                notifyPlayer(player, "action.nutkingcall.nut_king_call_quick_assembly.disassemble_lost");
            } else {
                notifyPlayer(player, "action.nutkingcall.nut_king_call_quick_assembly.disassemble_success");
            }
            return;
        }
    }

    private void notifyPlayer(PlayerEntity player, String translationKey) {
        player.displayClientMessage(new TranslationTextComponent(translationKey), true);
    }

    private boolean canDisassemble(ItemStack stack, RecipeManager recipeManager) {
        if (stack.isEmpty() || stack.getCount() > 1) {
            return false;
        }
        for (IRecipe<?> recipe : recipeManager.getRecipes()) {
            if (!(recipe instanceof ICraftingRecipe) || recipe.isSpecial()) {
                continue;
            }
            ICraftingRecipe crafting = (ICraftingRecipe) recipe;
            ItemStack result = crafting.getResultItem();
            if (result.isEmpty()) {
                continue;
            }
            if (!ItemStack.isSame(result, stack) || !ItemStack.tagMatches(result, stack)) {
                continue;
            }
            if (isReversibleRecipe(crafting.getIngredients())) {
                return true;
            }
        }
        return false;
    }

    private boolean isReversibleRecipe(NonNullList<Ingredient> ingredients) {
        if (ingredients.isEmpty()) {
            return false;
        }
        for (Ingredient ing : ingredients) {
            if (!ing.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean canCraft(PlayerEntity player, ICraftingRecipe recipe) {
        ItemStack result = recipe.getResultItem();
        if (result.isEmpty()) {
            return false;
        }

        NonNullList<Ingredient> ingredients = recipe.getIngredients();

        int totalIngredients = 0;
        for (Ingredient ingredient : ingredients) {
            if (!ingredient.isEmpty()) {
                totalIngredients++;
            }
        }

        if (result.getCount() > totalIngredients) {
            return false;
        }

        Map<Integer, Integer> available = new HashMap<>();
        for (int i = 0; i < player.inventory.getContainerSize(); i++) {
            ItemStack stack = player.inventory.getItem(i);
            if (!stack.isEmpty()) {
                available.put(i, stack.getCount());
            }
        }

        for (Ingredient ingredient : ingredients) {
            if (ingredient.isEmpty()) {
                continue;
            }
            boolean found = false;
            for (Map.Entry<Integer, Integer> entry : available.entrySet()) {
                if (entry.getValue() <= 0) {
                    continue;
                }
                int slot = entry.getKey();
                ItemStack stack = player.inventory.getItem(slot);
                if (!stack.isEmpty() && ingredient.test(stack)) {
                    entry.setValue(entry.getValue() - 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private void consumeIngredients(PlayerEntity player, ICraftingRecipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient.isEmpty()) {
                continue;
            }
            for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                ItemStack stack = player.inventory.getItem(i);
                if (!stack.isEmpty() && ingredient.test(stack)) {
                    stack.shrink(1);
                    if (stack.isEmpty()) {
                        player.inventory.setItem(i, ItemStack.EMPTY);
                    }
                    break;
                }
            }
        }
    }
}