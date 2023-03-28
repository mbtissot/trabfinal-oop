import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.ButtonGroup;
import java.util.Hashtable;

public class Application extends JFrame {
    private Map<Comodo, DefaultListModel<Device>> comodosMap = new HashMap<>();
    private JComboBox<Comodo> comodosDropdown = new JComboBox<>();
    private JList<Device> devicesList = new JList<>();
    private JScrollPane devicesScrollPane = new JScrollPane(devicesList);
    private JButton addComodoButton = new JButton("+");
    private JButton addDeviceButton = new JButton("Add Device");
    private JButton editDeviceButton = new JButton("Edit");
    private JButton removeDeviceButton = new JButton("X");

    public Application() {
        super("Gerenciador de Casa Inteligente");
        DefaultListModel<Object> devicesListModel = new DefaultListModel<>();
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the UI components
        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel body = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add the header components
        header.add(new JLabel("Selecione um Comodo:"));
        header.add(comodosDropdown);
        header.add(addComodoButton);

        // Add the body components
        body.add(new JLabel("Things:"));
        body.add(devicesScrollPane);

        // Add the buttons
        buttons.add(addDeviceButton);
        buttons.add(editDeviceButton);
        buttons.add(removeDeviceButton);

        // Add action listeners to the buttons
        addComodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Adicione um comodo:");
                if (name != null && !name.isEmpty()) {
                    Comodo comodo = new Comodo(name);
                    DefaultListModel<Device> devicesModel = new DefaultListModel<>();
                    comodosMap.put(comodo, devicesModel);
                    comodosDropdown.addItem(comodo);
                }
            }
        });
        
        
        editDeviceButton.addActionListener(new ActionListener() {
            @Override
            // TODO: Colocar valor variavel. P geladeira vs ar condicionado
            public void actionPerformed(ActionEvent e) {
                Comodo comodo = (Comodo) comodosDropdown.getSelectedItem();
                Device selDevice = (Device) devicesList.getSelectedValue();
                
                JCheckBox onOff = new JCheckBox("Ligado: ");
                JTextField value = new JTextField();
                value.setPreferredSize(new Dimension(10,10));
                
                if(selDevice instanceof Thermo){
                    Thermo selThermo = (Thermo) selDevice;
                    JSlider temp = new JSlider(JSlider.HORIZONTAL, selThermo.getTMin(), selThermo.getTMax(), selThermo.getTemp());
                    onOff.setSelected(selThermo.getState());
                    value.setText(Integer.toString(selThermo.getTemp()));
                    temp.setMinorTickSpacing(1);
                    temp.setPaintTicks(true);
                    temp.setPaintLabels(true);
                    temp.setSnapToTicks(true);
                    
                    Hashtable labelTable = new Hashtable();
                    labelTable.put(selThermo.getTMin(), new JLabel(Integer.toString(selThermo.getTMin())) );
                    labelTable.put(selThermo.getTMax(), new JLabel(Integer.toString(selThermo.getTMax())) );
                    temp.setLabelTable( labelTable );
                    
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(3, 1));
                    panel.add(onOff);
                    panel.add(value);
                    panel.add(temp);
                    panel.setPreferredSize(new Dimension(35, 120));
                    
                    temp.addChangeListener(new ChangeListener() {
                        public void stateChanged(ChangeEvent e) {
                            value.setText(Integer.toString(temp.getValue()));
                        }
                    });
                    
                    int abc = JOptionPane.showConfirmDialog(null, panel, "Insira o nome do device, e as propriedades dele", JOptionPane.OK_CANCEL_OPTION);
                    if(abc==JOptionPane.OK_OPTION){
                        //System.out.println("Funfou");
                        //System.out.println(temp.getValue());
                        //System.out.println(onOff.isSelected());
                        selThermo.setTemp(temp.getValue());
                        selThermo.setState(onOff.isSelected());
                        updateDevicesList(comodo);
                    }
                }
                /*if(selDevice instanceof Intense){
                    //chama funcao que retorna state, insensity
                    
                }*/
            }
            });

        removeDeviceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Comodo comodo = (Comodo) comodosDropdown.getSelectedItem();
                Device selDevice = (Device) devicesList.getSelectedValue();
                int index = devicesList.getSelectedIndex();
                DefaultListModel<Device> devicesModel = comodosMap.get(comodo);
                if (index != -1) {
                    devicesModel.remove(index);
                }
                
                comodo.rmDevice(selDevice);
                updateDevicesList(comodo);
                }
            });
        
        addDeviceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Comodo comodo = (Comodo) comodosDropdown.getSelectedItem();
                JTextField nome = new JTextField();
                JCheckBox temp = new JCheckBox("Thermo: ");
                JCheckBox intens = new JCheckBox("Intensity: ");

            temp.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (temp.isSelected()) {
                        intens.setSelected(false);
                    }
                }
            });
            intens.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    if (intens.isSelected()) {
                        temp.setSelected(false);
                    }
                }
            });
                
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(3, 1));
                panel.add(nome);
                panel.add(temp);
                panel.add(intens);
                
                int confirm = JOptionPane.showConfirmDialog(null, panel, "Insira o nome do device, e as propriedades dele", JOptionPane.OK_CANCEL_OPTION);
                if (comodo != null && confirm == JOptionPane.OK_OPTION) {
                    DefaultListModel<Device> devicesModel = comodosMap.get(comodo);
                    String nomeDev = nome.getText();
                    boolean tempState = temp.isSelected();
                    boolean intensState = intens.isSelected();
                    if (nomeDev != null && !nomeDev.isEmpty()) {
                        if(tempState){
                            // Pop up pedindo a temperatura minima e maxima do dispositivo
                            JTextField min = new JTextField();
                            JTextField max = new JTextField();
                            JPanel popup = new JPanel();
                            popup.setLayout(new GridLayout(2,2));
                            popup.add(new JLabel("Temperatura minima:"));
                            popup.add(new JLabel("Temperatura maxima:"));
                            popup.add(min);
                            popup.add(max);
                            
                            int con = JOptionPane.showConfirmDialog(null, popup, "Insira as temperaturas minima e maxima do dispositivo", JOptionPane.OK_CANCEL_OPTION);
                            
                            // Le as temperaturas
                            int tmin = Integer.parseInt(min.getText());
                            int tmax = Integer.parseInt(max.getText());
                            
                            // Cria o dispositivo
                            Thermo device = new Thermo(nomeDev, tmin, tmax);
                            devicesModel.addElement(device);
                            comodo.addDevice(device);
                            updateDevicesList(comodo);
                        } else if(intensState){
                            Intense device = new Intense(nomeDev);
                            devicesModel.addElement(device);
                            comodo.addDevice(device);
                            updateDevicesList(comodo);
                        } else{
                            Device device = new Device(nomeDev);
                            devicesModel.addElement(device);
                            comodo.addDevice(device);
                            updateDevicesList(comodo);
                        }
                        }
                    }
                }
        });

        comodosDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Comodo comodo = (Comodo) comodosDropdown.getSelectedItem();
                if (comodo != null) {
                    DefaultListModel<Device> comodosModel = comodosMap.get(comodo);
                    devicesList.setModel(comodosModel);
                }
            }
        });

        // Add the components to the content pane
        contentPane.add(header, BorderLayout.NORTH);
        contentPane.add(body, BorderLayout.CENTER);
        contentPane.add(buttons, BorderLayout.SOUTH);
        setContentPane(contentPane);
    }

    private void updateDevicesList(Comodo comodo) {
        DefaultListModel<Device> devicesModel = comodosMap.get(comodo);
        devicesList.setModel(devicesModel);
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.setVisible(true);
    }
}
