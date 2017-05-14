package de.zargornet.qbw.utils.files;

import de.zargornet.qbw.Qbw;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Locale.setDefault;

/**
 * Messages file
 */
public class MessagesFile {
    private Locale locale;
    private ResourceBundle resourceBundle;

    public MessagesFile() {
        copyFileToDir("messages");
        copyFileToDir("messages_de");

        String localeCFG = Qbw.getInstance().getCfg().getConfiguration().getString("locale");

        setDefault(Locale.ROOT);
        locale = new Locale(localeCFG);

        try {
            File file = new File(Qbw.getInstance().getDataFolder() + "/locales/");
            URL[] urls = {file.toURI().toURL()};
            ClassLoader loader = new URLClassLoader(urls);
            resourceBundle = ResourceBundle.getBundle("messages", locale, loader);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void copyFileToDir(String name) {
        File FILE = new File(Qbw.getInstance().getDataFolder() + "/locales/" + name + ".properties");
        try {
            if (!FILE.exists()) {
                Files.createDirectories(Paths.get(Qbw.getInstance().getDataFolder().toString() + "/locales"));
                FILE.createNewFile();

                InputStream in = getClass().getResourceAsStream("/" + name + ".properties");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String l = "";

                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    l += line + "\n";
                }

                OutputStream outputStream = new FileOutputStream(FILE.getAbsolutePath());
                outputStream.write(l.getBytes());

                outputStream.close();
                in.close();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
