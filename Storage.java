import java.io.*;

public class Storage {
    public static void save(Library lib, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(lib);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Library load(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj instanceof Library) return (Library) obj;
        } catch (FileNotFoundException fnf) {
            // no saved data yet
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Library();
    }
}
