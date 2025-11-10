 import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class ThemeManager {
    private static final String THEME_FILE = "theme.properties";
    private static boolean isDark = false;

    public static void loadTheme() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(THEME_FILE)) {
            props.load(fis);
            isDark = "true".equals(props.getProperty("darkMode", "false"));
        } catch (IOException e) {
            // default to light
        }
    }

    public static void saveTheme(boolean dark) {
        isDark = dark;
        Properties props = new Properties();
        props.setProperty("darkMode", String.valueOf(dark));
        try (FileOutputStream fos = new FileOutputStream(THEME_FILE)) {
            props.store(fos, "Theme settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDarkMode() {
        return isDark;
    }

    public static void applyTheme(JFrame frame) {
        if (isDark) {
            UIManager.put("Panel.background", java.awt.Color.DARK_GRAY);
            UIManager.put("Label.foreground", java.awt.Color.WHITE);
            UIManager.put("Button.background", java.awt.Color.GRAY);
            UIManager.put("Button.foreground", java.awt.Color.WHITE);
            // Apply to frame
            frame.getContentPane().setBackground(java.awt.Color.DARK_GRAY);
        } else {
            // Reset to defaults
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SwingUtilities.updateComponentTreeUI(frame);
    }
}
