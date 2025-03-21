package rpgclasses.expbar;

import necesse.engine.Settings;
import necesse.engine.eventStatusBars.EventStatusBarData;
import necesse.engine.eventStatusBars.EventStatusBarManager;
import necesse.engine.window.WindowManager;
import necesse.gfx.fairType.FairTypeDrawOptions;
import necesse.gfx.forms.MainGameFormManager;
import rpgclasses.data.PlayerData;

import java.awt.*;

public class ExpBarManger {
    public static BarForm barForm = null;
    public static float barPercent;
    public static MainGameFormManager mainGameFormManager = null;
    public static boolean movedByInv = false;
    public static int progressBarsSize = 0;

    public static void updateExpBar(PlayerData playerData) {
        updateExpBar(playerData.getExpActual(), playerData.getExpNext());
    }

    public static void updateExpBar(int expActual, int expNext) {
        barPercent = (float) expActual / expNext;
    }

    public static void updateExpBarPosition(MainGameFormManager mainGameFormManager) {
        int midY = WindowManager.getWindow().getHudHeight() / 2;
        int drawY = mainGameFormManager.toolbar.getY() - 10;

        if (!mainGameFormManager.inventory.isHidden()) {
            drawY = mainGameFormManager.inventory.getY() - 10;
        }

        int n = 0;
        for (EventStatusBarData statusBar : EventStatusBarManager.getStatusBars()) {
            if(n == 0) {
                drawY -= 10;
            }
            n++;
            if (Settings.showBossHealthBars || statusBar.category != EventStatusBarData.BarCategory.boss) {
                boolean draw = false;
                Color bufferColor = statusBar.getBufferColor();
                if (bufferColor != null) {
                    draw = true;
                }

                Color fillColor = statusBar.getFillColor();
                if (fillColor != null) {
                    draw = true;
                }

                if (draw) {
                    drawY -= Settings.UI.healthbar_big_background.getHeight();
                }

                FairTypeDrawOptions displayNameDrawOptions = statusBar.getDisplayNameDrawOptions();
                if (displayNameDrawOptions != null) {
                    drawY -= displayNameDrawOptions.getBoundingBox().height + 2;
                }

                if (drawY < midY + 100) {
                    break;
                }
            }
        }

        ExpBarManger.barForm.setPosition(mainGameFormManager.toolbar.getX(), drawY - 7);
    }
}
