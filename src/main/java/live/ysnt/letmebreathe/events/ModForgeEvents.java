package live.ysnt.letmebreathe.events;

import live.ysnt.letmebreathe.LetMeBreatheMod;
import live.ysnt.letmebreathe.util.BreathUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingDrownEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = LetMeBreatheMod.MODID)
public class ModForgeEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingBreathe(LivingBreatheEvent event) {
        if(!(event.getEntity() instanceof ServerPlayer player) || player.level().isClientSide()) return;
        if(!event.canBreathe() || !BreathUtils.inBreathableAir(player)){
            event.setCanRefillAir(false);
            event.setCanBreathe(false);
        }
    }

    @SubscribeEvent
    public static void onLivingDrown(LivingDrownEvent event) {

    }
}
