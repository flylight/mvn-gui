package org.ar.mvn.gui.utils;

import java.util.Locale;
import java.util.ResourceBundle;
import org.ar.mvn.gui.state.ApplicationStateManager;

public final class LocaleUtil {

  private LocaleUtil() {
    throw new UnsupportedOperationException("It is util class");
  }

  public static String getWord(String key) {
    Locale locale =
        Locale.forLanguageTag(ApplicationStateManager.INSTANCE().getSettings().getLocale());
    ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);
    String value = bundle.getString(key);
    return value;
  }
}
