package shared;

import javax.swing.BorderFactory;
import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AthleteFrame extends JFrame {
    private final AthleteRegistry registry;
    private final AthleteStore store;
    private final StoragePipeline pipeline;
    private final DefaultListModel<Athlete> listModel = new DefaultListModel<>();
    private final JList<Athlete> list = new JList<>(listModel);
    private final JComboBox<TypeDef> typeBox = new JComboBox<>();
    private final JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 6, 6));
    private final Map<String, JTextField> fieldInputs = new LinkedHashMap<>();
    private final JTextField fileField;
    private final List<AbstractButton> pluginChecks = new ArrayList<>();

    public AthleteFrame(String title, AthleteRegistry registry, List<StoragePlugin> storagePlugins, String defaultFile, String note) {
        super(title);
        this.registry = registry;
        this.store = new AthleteStore(registry);
        this.pipeline = new StoragePipeline(store, storagePlugins);
        this.fileField = new JTextField(defaultFile);

        for (TypeDef type : registry.all()) {
            typeBox.addItem(type);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(leftPanel(), BorderLayout.WEST);
        add(centerPanel(note), BorderLayout.CENTER);
        add(bottomPanel(storagePlugins), BorderLayout.SOUTH);
        addPluginMenu(storagePlugins);

        typeBox.addActionListener(e -> rebuildFields());
        list.addListSelectionListener(e -> fillSelectedAthlete());
        rebuildFields();
    }

    private JPanel leftPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Objects"));
        list.setCellRenderer((jList, value, index, selected, focus) -> new JLabel(value.id.substring(0, 8) + " [" + value.type() + "] " + value.summary()));
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton remove = new JButton("Remove");
        remove.addActionListener(e -> {
            Athlete athlete = list.getSelectedValue();
            if (athlete != null) {
                listModel.removeElement(athlete);
            }
        });
        panel.add(remove, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel centerPanel(String note) {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Editor"));

        JPanel top = new JPanel(new BorderLayout(6, 6));
        top.add(new JLabel("Type:"), BorderLayout.WEST);
        top.add(typeBox, BorderLayout.CENTER);
        panel.add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new BorderLayout(6, 6));
        form.add(fieldsPanel, BorderLayout.NORTH);
        if (!note.isBlank()) {
            form.add(new JLabel(note), BorderLayout.SOUTH);
        }
        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton add = new JButton("Add");
        JButton update = new JButton("Update");
        add.addActionListener(e -> addAthlete());
        update.addActionListener(e -> updateAthlete());
        buttons.add(add);
        buttons.add(update);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel bottomPanel(List<StoragePlugin> storagePlugins) {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Text file storage"));

        JPanel filePanel = new JPanel(new BorderLayout(6, 6));
        filePanel.add(new JLabel("File:"), BorderLayout.WEST);
        filePanel.add(fileField, BorderLayout.CENTER);

        JButton save = new JButton("Save");
        JButton load = new JButton("Load");
        save.addActionListener(e -> save());
        load.addActionListener(e -> load());
        JPanel fileButtons = new JPanel();
        fileButtons.add(save);
        fileButtons.add(load);
        filePanel.add(fileButtons, BorderLayout.EAST);
        panel.add(filePanel, BorderLayout.NORTH);
        return panel;
    }

    private void addPluginMenu(List<StoragePlugin> storagePlugins) {
        if (storagePlugins.isEmpty()) {
            return;
        }
        JMenuBar bar = new JMenuBar();
        JMenu settings = new JMenu("Settings");
        JMenu plugins = new JMenu("Storage plugins");
        for (StoragePlugin plugin : storagePlugins) {
            JCheckBoxMenuItem check = new JCheckBoxMenuItem(plugin.name(), true);
            pluginChecks.add(check);
            plugins.add(check);
        }
        settings.add(plugins);
        bar.add(settings);
        setJMenuBar(bar);
    }

    private void rebuildFields() {
        fieldsPanel.removeAll();
        fieldInputs.clear();
        TypeDef type = (TypeDef) typeBox.getSelectedItem();
        if (type != null) {
            for (Field field : type.fields) {
                JTextField input = new JTextField();
                fieldInputs.put(field.name, input);
                fieldsPanel.add(new JLabel(field.name));
                fieldsPanel.add(input);
            }
        }
        fieldsPanel.revalidate();
        fieldsPanel.repaint();
    }

    private void addAthlete() {
        try {
            TypeDef type = (TypeDef) typeBox.getSelectedItem();
            Athlete athlete = type.create(values());
            listModel.addElement(athlete);
        } catch (Exception error) {
            showError(error);
        }
    }

    private void updateAthlete() {
        try {
            Athlete athlete = list.getSelectedValue();
            if (athlete == null) {
                return;
            }
            Map<String, String> values = values();
            for (Field field : registry.get(athlete.type()).fields) {
                field.setter.set(athlete, values.get(field.name));
            }
            list.repaint();
        } catch (Exception error) {
            showError(error);
        }
    }

    private void fillSelectedAthlete() {
        Athlete athlete = list.getSelectedValue();
        if (athlete == null) {
            return;
        }
        TypeDef type = registry.get(athlete.type());
        typeBox.setSelectedItem(type);
        for (Field field : type.fields) {
            JTextField input = fieldInputs.get(field.name);
            if (input != null) {
                input.setText(field.getter.get(athlete));
            }
        }
    }

    private Map<String, String> values() {
        Map<String, String> values = new LinkedHashMap<>();
        for (Map.Entry<String, JTextField> entry : fieldInputs.entrySet()) {
            values.put(entry.getKey(), entry.getValue().getText());
        }
        return values;
    }

    private void save() {
        try {
            List<Athlete> athletes = allAthletes();
            String[] plugins = selectedPlugins();
            if (plugins.length == 0) {
                store.save(Path.of(fileField.getText()), athletes);
            } else {
                pipeline.save(Path.of(fileField.getText()), athletes, plugins);
            }
            JOptionPane.showMessageDialog(this, "Saved");
        } catch (Exception error) {
            showError(error);
        }
    }

    private void load() {
        try {
            String[] plugins = selectedPlugins();
            List<Athlete> loaded = plugins.length == 0
                ? store.load(Path.of(fileField.getText()))
                : pipeline.load(Path.of(fileField.getText()), plugins);
            listModel.clear();
            for (Athlete athlete : loaded) {
                listModel.addElement(athlete);
            }
        } catch (Exception error) {
            showError(error);
        }
    }

    private List<Athlete> allAthletes() {
        List<Athlete> athletes = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i++) {
            athletes.add(listModel.get(i));
        }
        return athletes;
    }

    private String[] selectedPlugins() {
        List<String> selected = new ArrayList<>();
        for (AbstractButton check : pluginChecks) {
            if (check.isSelected()) {
                selected.add(check.getText());
            }
        }
        return selected.toArray(new String[0]);
    }

    private void showError(Exception error) {
        JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
