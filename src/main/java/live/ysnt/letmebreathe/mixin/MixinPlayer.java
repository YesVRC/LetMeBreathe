package live.ysnt.letmebreathe.mixin;

import live.ysnt.letmebreathe.util.BreathUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import net.minecraftforge.common.extensions.IForgePlayer;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayer.class)
public abstract class MixinPlayer implements IForgeLivingEntity {

    @Override
    public boolean canDrownInFluidType(FluidType fluid) {
        if(fluid != ForgeMod.EMPTY_TYPE.get()) return IForgeLivingEntity.super.canDrownInFluidType(fluid);
        return BreathUtils.outOfRange((LivingEntity)(Object)this);
    }
}
