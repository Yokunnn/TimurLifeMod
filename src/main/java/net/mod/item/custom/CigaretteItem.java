package net.mod.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CigaretteItem extends Item {

    private CigaretteStrength strength;
    public CigaretteItem(Properties properties, CigaretteStrength strength) {
        super(properties);
        this.strength = strength;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        int dmgValue = itemStack.getDamageValue();
        if (!level.isClientSide() && interactionHand == InteractionHand.MAIN_HAND
                && (player.getItemInHand(InteractionHand.OFF_HAND).getItem() == Items.FLINT_AND_STEEL || dmgValue != 0)
        ) {
            addEffects(player, strength);
            itemStack.hurtAndBreak(1, player, player1 -> {
                player1.broadcastBreakEvent(interactionHand);
            });
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIRE_AMBIENT, SoundSource.NEUTRAL, 1, level.getRandom().nextFloat() * 0.4F + 0.8F);
            if (dmgValue == 0) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1, level.getRandom().nextFloat() * 0.4F + 0.8F);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public void addEffects(Player player, CigaretteStrength strength){
        switch (strength){
            case LIGHT -> {
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 160, 2));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 160, 0));
            }
            case MIDDLE -> {
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 2));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2));
            }
            case STRONG -> {
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 240, 2));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 240, 1));
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 240, 0));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 240, 1));
            }
            case EXTRA -> {
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300, 2));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 360, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 2));
            }
        }
    }
}
