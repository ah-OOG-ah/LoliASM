package zone.rong.normalasm.common.crashes;

import net.minecraftforge.fml.common.ModContainer;

import java.util.Set;

public interface ICrashReportSuspectGetter {

    Set<ModContainer> getSuspectedMods();

}
