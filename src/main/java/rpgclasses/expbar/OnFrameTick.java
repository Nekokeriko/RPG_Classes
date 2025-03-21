package rpgclasses.expbar;

import necesse.engine.eventStatusBars.EventStatusBarManager;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.gfx.forms.MainGameFormManager;
import net.bytebuddy.asm.Advice;

import java.util.Collection;

@ModMethodPatch(target = MainGameFormManager.class, name = "frameTick", arguments = {TickManager.class})
public class OnFrameTick {
    public static int invOpenMovement;
    @Advice.OnMethodExit
    static void onExit(@Advice.This MainGameFormManager mainGameFormManager) {
        boolean anyUpdate = false;
        if(mainGameFormManager.isInventoryHidden() == ExpBarManger.movedByInv) {
            ExpBarManger.movedByInv = !ExpBarManger.movedByInv;
            anyUpdate = true;
        }
        int newProgressBarsSize = getProgressBarsSize(EventStatusBarManager.getStatusBars());
        if(ExpBarManger.progressBarsSize != newProgressBarsSize) {
            anyUpdate = true;
            ExpBarManger.progressBarsSize = newProgressBarsSize;
        }
        if(anyUpdate) {
            ExpBarManger.updateExpBarPosition(mainGameFormManager);
        }
        ExpBarManger.barForm.setHidden(mainGameFormManager.toolbar.isHidden());
    }

    public static int getProgressBarsSize(Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection<?>) iterable).size();
        }

        int count = 0;
        for (Object ignored : iterable) {
            count++;
        }
        return count;
    }
}
