import java.util.*;

public class LanguageManager {
    private static ResourceBundle bundle;
    private static Locale currentLocale = Locale.ENGLISH;

    static {
        loadBundle();
    }

    public static void loadBundle() {
        bundle = ResourceBundle.getBundle("messages", currentLocale);
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        loadBundle();
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key; // fallback
        }
    }

    public static void toggleLanguage() {
        if (currentLocale.equals(Locale.ENGLISH)) {
            setLocale(new Locale("hi", "IN")); // Hindi
        } else {
            setLocale(Locale.ENGLISH);
        }
    }
}
