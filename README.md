Sup bozo, regular players shouldn't be here.

Now that I've filtered all the normies out...

# HELLO MOD DEVELOPERS!

I use vanilla breathing mechanics. Please avoid using 
### LivingEntity#canDrownInFluidType
or whatever it's called

If you must, add your own FluidType and have it do stuff
<br/>
Instead use:
### LivingBreatheEvent

It's an easily subscribed to method, and gives you get/set access to canBreathe and canRefillAir
To refill air (normally) both must be true.

[Create's Diving Helmet does this](https://github.com/Creators-of-Create/Create/blob/mc1.20.1/dev/src/main/java/com/simibubi/create/content/equipment/armor/DivingHelmetItem.java#L123)
```java
//This is Forge's default check
public static void onLivingBreathe(LivingEntity entity, int consumeAirAmount, int refillAirAmount) {
    boolean isAir = entity.getEyeInFluidType().isAir() || entity.level().getBlockState(BlockPos.containing(entity.getX(), entity.getEyeY(), entity.getZ())).is(net.minecraft.world.level.block.Blocks.BUBBLE_COLUMN);
    boolean canBreathe = !entity.canDrownInFluidType(entity.getEyeInFluidType()) || MobEffectUtil.hasWaterBreathing(entity) || entity instanceof Player && ((Player) entity).getAbilities().invulnerable;
    LivingBreatheEvent breatheEvent = new LivingBreatheEvent(entity, isAir || canBreathe, consumeAirAmount, refillAirAmount, isAir);
    MinecraftForge.EVENT_BUS.post(breatheEvent);
    if (breatheEvent.canBreathe()) {
        if (breatheEvent.canRefillAir()) {
            entity.setAirSupply(Math.min(entity.getAirSupply() + breatheEvent.getRefillAirAmount(), entity.getMaxAirSupply()));
        }
    } else {
        entity.setAirSupply(entity.getAirSupply() - breatheEvent.getConsumeAirAmount());
    }
    //blah blah blah
}
```

The reason I don't want you using `LivingEntity#canDrownInFluidType(FluidType)` without adding your own fluid type is because you shouldn't be doing it like that. Getting around this requires Mixins which I don't want to be liable for.
You can change the FluidType of vanilla blocks I'm pretty sure, or better yet just replace the block. 
You can have air-like blocks with different properties. As long as canDrown is true it should be fine, whatever you do.

I have my event set to the highest priority. As a gentleman's code, I propose this:
I take highest, Block Entities (or other non-items) can take highest, and items take last.

This mod affects the world, so it should apply first. I don't have to worry about resource consumption.

Blocks then take HIGH priority because blocks should use their resources before items.

Finally, Items can take anything else because having to refill your item when a totally capable block is right there would be really annoying, right? 