package live.ysnt.letmebreathe.util;

import live.ysnt.letmebreathe.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Unique;

public class BreathUtils {

    @Unique
    public static boolean outOfRange(LivingEntity entity){
        for (Config.DimensionBounds bounds : Config.dimensions){
            ResourceLocation location = entity.level().dimension().location();
            if(location.toString().equals(bounds.dim())){
                double yLevel = entity.getEyeY();
                return yLevel <= bounds.lower() || yLevel >= bounds.upper();
            }
        }
        return true;
    }

    @Unique
    public static boolean inBubbleColumn(LivingEntity entity){
        return entity.level().getBlockState(BlockPos.containing(entity.getX(), entity.getEyeY(), entity.getZ())).is(net.minecraft.world.level.block.Blocks.BUBBLE_COLUMN);
    }


}
