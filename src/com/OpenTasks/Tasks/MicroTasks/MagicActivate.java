package com.OpenTasks.Tasks.MicroTasks;

import com.OpenTasks.Logger;
import com.OpenTasks.Task;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.osrs.local.hud.interfaces.Magic;

/**
 * Created by VTM on 9/7/2016.
 */
public class MagicActivate<T> extends Task {

  private T magic;
  private int ableToCastTextureID;

  private MagicActivate(int priority, T magic, int ableToCastTextureID) {
    super("MagicActivate", priority);
    this.magic = magic;
    this.ableToCastTextureID = ableToCastTextureID;
  }

  public static MagicActivate<Magic> fromStandard(int priority, Magic magic, int ableToCastTextureID) {
    return new MagicActivate<>(priority, magic, ableToCastTextureID);
  }

  public static MagicActivate<Magic.Ancient> fromAncient(int priority, Magic.Ancient magic, int ableToCastTextureID) {
    return new MagicActivate<>(priority, magic, ableToCastTextureID);
  }

  public static MagicActivate<Magic.Lunar> fromLunar(int priority, Magic.Lunar magic, int ableToCastTextureID) {
    return new MagicActivate<>(priority, magic, ableToCastTextureID);
  }

  @Override
  public boolean validate() {
    // 1. Magic interface window must be open
    boolean isMagicInterfaceWindowOpen = InterfaceWindows.getMagic().isOpen();

    // 2. Has the right book
    boolean isBookGood = false;

    if (magic instanceof Magic) {
      isBookGood = Magic.Book.getCurrent() == Magic.Book.STANDARD;
    } else if (magic instanceof Magic.Ancient) {
      isBookGood = Magic.Book.getCurrent() == Magic.Book.ANCIENT;
    } else if (magic instanceof Magic.Lunar) {
      isBookGood = Magic.Book.getCurrent() == Magic.Book.LUNAR;
    }

    // 3. Is not selected already
    boolean isMagicNotSelected = false;

    if (magic instanceof Magic) {
      isMagicNotSelected = ((Magic) magic).isSelected();
    } else if (magic instanceof Magic.Ancient) {
      isMagicNotSelected = ((Magic.Ancient) magic).isSelected();
    } else if (magic instanceof Magic.Lunar) {
      isMagicNotSelected = ((Magic.Lunar) magic).isSelected();
    }

    // 4. Is able to cast magic
    boolean isAbleToCastMagic = false;

    if (magic instanceof Magic) {
      isAbleToCastMagic = ((Magic) magic).getComponent().getTextureId() == ableToCastTextureID;
    } else if (magic instanceof Magic.Ancient) {
      isAbleToCastMagic = ((Magic.Ancient) magic).getComponent().getTextureId() == ableToCastTextureID;
    } else if (magic instanceof Magic.Lunar) {
      isAbleToCastMagic = ((Magic.Lunar) magic).getComponent().getTextureId() == ableToCastTextureID;
    }

    Logger.debug("isMagicInterfaceWindowOpen: %b, isBookGood: %b, isMagicNotSelected: %b, isAbleToCastMagic: %b",
        isMagicInterfaceWindowOpen, isBookGood, isMagicNotSelected, isAbleToCastMagic);

    return isMagicInterfaceWindowOpen
      && isBookGood
      && isMagicNotSelected
      && isAbleToCastMagic;
  }

  @Override
  public void execute() {
    Logger.debug("Activating magic...");

    if (magic instanceof Magic) {
      ((Magic) magic).activate();
    } else if (magic instanceof Magic.Ancient) {
      ((Magic.Ancient) magic).activate();
    } else if (magic instanceof Magic.Lunar) {
      ((Magic.Lunar) magic).activate();
    }

    super.execute();
  }
}
