package zone.rong.normalasm.common.crashes;

import net.minecraft.crash.CrashReport;

public interface IMinecraftExtender {

    boolean shouldCrashIntegratedServerNextTick();

    void showWarningScreen(CrashReport report);

    void makeErrorNotification(CrashReport report);

}
