package live.ysnt.letmebreathe.mixin;

import live.ysnt.letmebreathe.util.BreathUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ForgeHooks.class)
public class MixinForgeHooks {

    //@ModifyVariable(method = "onLivingBreathe", at = @At("STORE"), ordinal = 0, remap = false)
    private static boolean letmebreathe$isAir(boolean value, LivingEntity entity, int consumeAirAmount, int refillAirAmount){
        if((entity instanceof ServerPlayer) && !BreathUtils.inBreathableAir(entity)){
            return BreathUtils.inBubbleColumn(entity);
        }
        return value;
    }

}