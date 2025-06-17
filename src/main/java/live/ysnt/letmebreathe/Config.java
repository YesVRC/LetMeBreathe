package live.ysnt.letmebreathe;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = LetMeBreatheMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSION_STRINGS = BUILDER
            .comment("A list of dimensions to set as unbreathable [name;lower;upper] (name required, bounds optional)")
            .defineListAllowEmpty("dimensions", List.of("minecraft:overworld;0;128", "minecraft:the_nether", "minecraft:the_end"), Config::validateDimensionName);

    private static final ForgeConfigSpec.EnumValue<TickingType> TICKING_TYPE = BUILDER
            .comment("What type of entities to tick for breathing [" + TickingType.displayString() + "]")
            .defineEnum("ticking_type", TickingType.PLAYERS_ONLY);

    static final ForgeConfigSpec COMMON_SPEC = BUILDER.build();

    public static Set<DimensionBounds> dimensions;

    private static boolean validateDimensionName(final Object obj)
    {
        return true;
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        // convert the list of strings into a set of items
        dimensions = DIMENSION_STRINGS.get().stream()
                .map(dimName -> {
                    String[] split = dimName.split(";");
                    if (split.length > 1){
                        return new DimensionBounds(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                    } else {
                        return new DimensionBounds(dimName, -1000, -1001);
                    }
                })
                .collect(Collectors.toSet());
    }

    public record DimensionBounds(String dim, int lower, int upper){}

    public enum TickingType {
        PLAYERS_ONLY("players_only"),
        ALL_LIVING("all_living");

        private static final Map<String, TickingType> BY_LABEL = new HashMap<>();
        static {
            for (TickingType e : values()) {
                BY_LABEL.put(e.label, e);
            }
        }

        public static TickingType valueOfLabel(String label) {
            return BY_LABEL.get(label);
        }

        public final String label;
        TickingType(final String label) {
            this.label = label;
        }

        public static String displayString(){
            StringBuilder output = new StringBuilder();
            for (TickingType t : values()) {
                output.append(t.label).append(",");
            }
            output.deleteCharAt(output.length() - 1);
            return output.toString();
        }
    }
}
