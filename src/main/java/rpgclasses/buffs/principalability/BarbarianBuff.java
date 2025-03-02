package rpgclasses.buffs.principalability;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.SimpleClassBuff;

public class BarbarianBuff extends SimpleClassBuff {
    public BarbarianBuff() {
        super(
                new ModifierValue<>(BuffModifiers.MAX_HEALTH, 1F),
                new ModifierValue<>(BuffModifiers.HEALTH_REGEN, 1F),
                new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN, 1F),
                new ModifierValue<>(BuffModifiers.ARMOR, -1000F),
                new ModifierValue<>(BuffModifiers.MAX_RESILIENCE, -1000F),
                new ModifierValue<>(BuffModifiers.MAX_MANA, -1000F)
        );
    }

    @Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        super.init(buff, eventSubscriber);
        updateBuff(buff);
        buff.setMaxModifier(BuffModifiers.ARMOR, -1000F);
        buff.setMaxModifier(BuffModifiers.MAX_RESILIENCE, -1000F);
        buff.setMaxModifier(BuffModifiers.MAX_MANA, -1000F);
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        updateBuff(buff);
    }

    @Override
    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        updateBuff(buff);
    }

    public void updateBuff(ActiveBuff buff) {
        float healthPercent = buff.owner.getHealthPercent();
        buff.setModifier(
                BuffModifiers.MELEE_DAMAGE, 1 - healthPercent
        );
        buff.setModifier(
                BuffModifiers.ATTACK_MOVEMENT_MOD, healthPercent
        );
    }
}
