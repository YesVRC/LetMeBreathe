package live.ysnt.letmebreathe.mixin;

import com.mojang.authlib.GameProfile;
import live.ysnt.letmebreathe.util.BreathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayer.class)
public abstract class MixinPlayer extends Player {

    public MixinPlayer(Level p_250508_, BlockPos p_250289_, float p_251702_, GameProfile p_252153_) {
        super(p_250508_, p_250289_, p_251702_, p_252153_);
    }

    @Override
    public boolean canDrownInFluidType(FluidType fluid) {
        if(fluid != ForgeMod.EMPTY_TYPE.get()) return super.canDrownInFluidType(fluid);
        return !BreathUtils.inBreathableAir((LivingEntity)(Object)this);
    }

}
