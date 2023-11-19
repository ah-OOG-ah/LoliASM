package zone.rong.normalasm.core;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
import zone.rong.normalasm.config.NormalConfig;
import zone.rong.normalasm.NormalReflector;
import zone.rong.normalasm.api.datastructures.DummyMap;
import zone.rong.normalasm.api.datastructures.canonical.AutoCanonizingSet;
import zone.rong.normalasm.api.datastructures.deobf.DeobfuscatedMappingsMap;
import zone.rong.normalasm.api.datastructures.deobf.FieldDescriptionsMap;
import zone.rong.normalasm.api.NormalStringPool;

import java.util.Map;
import java.util.Set;

public class NormalFMLCallHook implements IFMLCallHook {

    @Override
    @SuppressWarnings("unchecked")
    public Void call() {
        try {
            DummyMap.of();
            if (NormalConfig.instance.disablePackageManifestMap) {
                NormalReflector.resolveFieldSetter(LaunchClassLoader.class, "packageManifests").invokeExact(Launch.classLoader, DummyMap.of());
                NormalReflector.resolveFieldSetter(LaunchClassLoader.class, "EMPTY").invoke(null);
            }
            if (NormalConfig.instance.optimizeFMLRemapper) {
                NormalReflector.resolveFieldSetter(FMLDeobfuscatingRemapper.class, "classNameBiMap").invokeExact(FMLDeobfuscatingRemapper.INSTANCE, canonicalizeClassNames((BiMap<String, String>) NormalReflector.resolveFieldGetter(FMLDeobfuscatingRemapper.class, "classNameBiMap").invokeExact(FMLDeobfuscatingRemapper.INSTANCE)));
                if (!NormalLoadingPlugin.isDeobf) {
                    NormalReflector.resolveFieldSetter(FMLDeobfuscatingRemapper.class, "rawFieldMaps").invokeExact(FMLDeobfuscatingRemapper.INSTANCE, DeobfuscatedMappingsMap.of((Map<String, Map<String, String>>) NormalReflector.resolveFieldGetter(FMLDeobfuscatingRemapper.class, "rawFieldMaps").invokeExact(FMLDeobfuscatingRemapper.INSTANCE), true));
                    NormalReflector.resolveFieldSetter(FMLDeobfuscatingRemapper.class, "rawMethodMaps").invokeExact(FMLDeobfuscatingRemapper.INSTANCE, DeobfuscatedMappingsMap.of((Map<String, Map<String, String>>) NormalReflector.resolveFieldGetter(FMLDeobfuscatingRemapper.class, "rawMethodMaps").invokeExact(FMLDeobfuscatingRemapper.INSTANCE), false));
                    NormalReflector.resolveFieldSetter(FMLDeobfuscatingRemapper.class, "fieldNameMaps").invokeExact(FMLDeobfuscatingRemapper.INSTANCE, DeobfuscatedMappingsMap.of((Map<String, Map<String, String>>) NormalReflector.resolveFieldGetter(FMLDeobfuscatingRemapper.class, "fieldNameMaps").invokeExact(FMLDeobfuscatingRemapper.INSTANCE), true));
                    NormalReflector.resolveFieldSetter(FMLDeobfuscatingRemapper.class, "methodNameMaps").invokeExact(FMLDeobfuscatingRemapper.INSTANCE, DeobfuscatedMappingsMap.of((Map<String, Map<String, String>>) NormalReflector.resolveFieldGetter(FMLDeobfuscatingRemapper.class, "methodNameMaps").invokeExact(FMLDeobfuscatingRemapper.INSTANCE), false));
                    NormalReflector.resolveFieldSetter(FMLDeobfuscatingRemapper.class, "fieldDescriptions").invoke(FMLDeobfuscatingRemapper.INSTANCE, new FieldDescriptionsMap((Map<String, Map<String, String>>) NormalReflector.resolveFieldGetter(FMLDeobfuscatingRemapper.class, "fieldDescriptions").invokeExact(FMLDeobfuscatingRemapper.INSTANCE)));
                    NormalReflector.resolveFieldSetter(FMLDeobfuscatingRemapper.class, "negativeCacheMethods").invoke(FMLDeobfuscatingRemapper.INSTANCE, new AutoCanonizingSet<>((Set<String>) NormalReflector.resolveFieldGetter(FMLDeobfuscatingRemapper.class, "negativeCacheMethods").invokeExact(FMLDeobfuscatingRemapper.INSTANCE)));
                    NormalReflector.resolveFieldSetter(FMLDeobfuscatingRemapper.class, "negativeCacheFields").invoke(FMLDeobfuscatingRemapper.INSTANCE, new AutoCanonizingSet<>((Set<String>) NormalReflector.resolveFieldGetter(FMLDeobfuscatingRemapper.class, "negativeCacheFields").invokeExact(FMLDeobfuscatingRemapper.INSTANCE)));
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) { }

    private BiMap<String, String> canonicalizeClassNames(BiMap<String, String> map) {
        ImmutableBiMap.Builder<String, String> builder = ImmutableBiMap.builder();
        map.forEach((s1, s2) -> builder.put(NormalStringPool.canonicalize(s1), NormalStringPool.canonicalize(s2)));
        return builder.build();
    }
}
