package zone.rong.normalasm.client.models.datastructures.modelmanager.mixins;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.client.model.ModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import zone.rong.normalasm.NormalLogger;
import zone.rong.normalasm.NormalReflector;

import java.lang.reflect.Field;
import java.util.Map;

@Mixin(ModelManager.class)
public class ModelManagerMixin {

    @SuppressWarnings("rawtypes")
    @Inject(method = "onResourceManagerReload", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void cleanupModelLoader(IResourceManager resourceManager, CallbackInfo ci, ModelLoader modelLoader) throws IllegalAccessException {
        Field multipartModelsField = NormalReflector.getField(ModelLoader.class, "multipartModels");
        Map multipartModels = (Map) multipartModelsField.get(modelLoader);
        int size = multipartModels.size();
        multipartModels.clear();
        if (multipartModels instanceof Object2ObjectOpenHashMap) {
            ((Object2ObjectOpenHashMap) multipartModels).trim();
        }

        NormalLogger.instance.info("Clearing and Trimming {}, original size: {} entries", "multipartModels", size);

        Field multipartDefinitionsField = NormalReflector.getField(ModelLoader.class, "multipartDefinitions");
        Map multipartDefinitions = (Map) multipartDefinitionsField.get(modelLoader);
        size = multipartDefinitions.size();
        multipartDefinitions.clear();
        if (multipartDefinitions instanceof Object2ObjectOpenHashMap) {
            ((Object2ObjectOpenHashMap) multipartDefinitions).trim();
        }

        NormalLogger.instance.info("Clearing and Trimming {}, original size: {} entries", "multipartDefinitions", size);

        Field multipartVariantMapField = NormalReflector.getField(ModelBakery.class, "multipartVariantMap", "field_188642_k");
        Map multipartVariantMap = (Map) multipartVariantMapField.get(modelLoader);
        size = multipartVariantMap.size();
        multipartVariantMap.clear();
        if (multipartVariantMap instanceof Object2ObjectOpenHashMap) {
            ((Object2ObjectOpenHashMap) multipartVariantMap).trim();
        }

        NormalLogger.instance.info("Clearing and Trimming {}, original size: {} entries", "multipartVariantMap", size);

        Field blockDefinitionsField = NormalReflector.getField(ModelBakery.class, "blockDefinitions", "field_177614_t");
        Map blockDefinitions = (Map) blockDefinitionsField.get(modelLoader);
        size = blockDefinitions.size();
        blockDefinitions.clear();
        if (blockDefinitions instanceof Object2ObjectOpenHashMap) {
            ((Object2ObjectOpenHashMap) blockDefinitions).trim();
        }

        NormalLogger.instance.info("Clearing and Trimming {}, original size: {} entries", "blockDefinitions", size);
    }

}
