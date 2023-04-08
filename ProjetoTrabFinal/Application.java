import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.Timer;
import java.io.*;
import javax.imageio.*;

/**
 * Classe Application faz o programa inteiro rodar. Aqui eh criada a janela e os botoes que o usuario vai interagir
 *
 * @author Matheus B Tissot - 00305657
 * @version APRIL - 2023
 */

public class Application extends JFrame{
    // Elementos mais escondidos da janela
    private Map<Comodo, DefaultListModel<Device>> comodosMap = new HashMap<>();
    private ArrayList<Comodo> comodosList = new ArrayList<>();
    private JComboBox<Comodo> comodosDropdown = new JComboBox<>();
    private JList<Device> devicesList = new JList<>();
    private JScrollPane devicesScrollPane = new JScrollPane(devicesList);
    // Botoes
    private JButton addComodoButton = new JButton("+");
    private JButton addDeviceButton = new JButton("Add Device");
    private JButton editDeviceButton = new JButton("Edit");
    private JButton removeDeviceButton = new JButton("X");
    private JButton listarButton = new JButton("Listar");
    private JButton salvarButton = new JButton("Salvar");
    
    private JMenuBar menuBar = new JMenuBar();
    

    public Application() throws ValorInvalido {
        super("Gerenciador de Casa Inteligente"); // Nome da Janela
        DefaultListModel<Object> devicesListModel = new DefaultListModel<>();
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenu menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);
        
        JMenuItem testarMenuItem = new JMenuItem("Testar");
        testarMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        menu.add(testarMenuItem);
        
        
        // Componentes da Janela
        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel body = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Comoponentes do header
        header.add(new JLabel("Selecione um Comodo:"));
        header.add(comodosDropdown);
        header.add(addComodoButton);

        // Componente q vai conter os Devices do Comodo
        body.add(devicesScrollPane);

        // Adicionando botoes
        buttons.add(listarButton);
        buttons.add(addDeviceButton);
        buttons.add(editDeviceButton);
        buttons.add(removeDeviceButton);
        buttons.add(salvarButton);

        
        addComodoButton.addActionListener(new ActionListener() {
            @Override
            /** ActionListener do botao addComodo.
            * Abre um Dialog pedindo o nome do Comodo para o Usuario
            */
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Adicione um comodo:");
                if (name != null && !name.isEmpty()) {
                    Comodo comodo = new Comodo(name);
                    DefaultListModel<Device> devicesModel = new DefaultListModel<>();
                    comodosMap.put(comodo, devicesModel);
                    comodosDropdown.addItem(comodo);
                    comodosList.add(comodo);
                }
            }
        });
        
        listarButton.addActionListener(new ActionListener() {
            @Override
            /** ActionListener do botao Listar.
            * Abre um dialog com uma lista dos Comodos, e os dispositivos dentro do comodo, em formato de Arvore
            */
            public void actionPerformed(ActionEvent e) {
                String lista = "";
                JPanel panel = new JPanel(new BorderLayout());
                for(Comodo comodo: comodosList){
                    lista = lista + "\n* " + comodo.getName();
                    for(Device device: comodo.getDevices()){
                        lista = lista + "\n  - " + device.toString();
                    }
                }
                JOptionPane.showMessageDialog(null, lista);
            }
        });
        
        salvarButton.addActionListener(new ActionListener() {
            @Override
            /** ActionListener do botao Salvar.
            * Abre um dialog pedindo o nome do arquivo para Salvar o estado atual da Casa.
            * Se o arquivo existir, sobreescreve.
            * Se nao existir, cria o arquivo.
            * Se o usuario nao informar nome, salva como "casa.txt"
            */
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout());
                String nomeFile = JOptionPane.showInputDialog("Deseja salvar a casa em qual arquivo? (Padrao: casa.txt)");
                if(nomeFile == null || nomeFile.isEmpty()){
                    nomeFile = "casa.txt";
                }
                String path = "./"+nomeFile;
                File salvar = new File(path);
                try{
                    if(!salvar.exists()){
                        salvar.createNewFile();
                    }
                    FileWriter fw = new FileWriter(salvar, false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("Ultimo estado da casa: ");
                    bw.newLine();
                    for(Comodo comodo: comodosList){
                        bw.write("* " + comodo.getName()+"\n");
                        for(Device device: comodo.getDevices()){
                            bw.write("  - " + device.toString()+"\n");
                        }
                    }
                    bw.close();
                    fw.close();
                }
                catch(IOException ioe){
                    System.err.println("Erro ao escrever no arquivo " + nomeFile);
                }
                JOptionPane.showMessageDialog(null, "Casa salva no arquivo " + nomeFile);
            }
        });
        
        
        editDeviceButton.addActionListener(new ActionListener() {
            @Override
            /** ActionListener do botao EditDevice.
            * Abre um dialog com elementos relevantes para cada tipo de Device.
            * Para todos os Devices: Checkbox para ligar/desligar o Device.
            * Se o Device eh Thermo: Slider para temperatura.
            * Se o Device eh Intense: RadioButton para a intensidade.
            * Se o Device tem Timer, permite o usuario editar o tempo para o Timer.
            */
            public void actionPerformed(ActionEvent e) {
                Comodo comodo = (Comodo) comodosDropdown.getSelectedItem();
                Device selDevice = (Device) devicesList.getSelectedValue();
                
                JCheckBox onOff = new JCheckBox(": Ligado");
                JTextField tempo = new JTextField("0");
                JLabel tempoLabel = new JLabel("Tempo");
                tempo.setEnabled(false);
                
                /** Caso em que o Device eh do tipo Thermo */
                if(selDevice instanceof Thermo){
                    Thermo selThermo = (Thermo) selDevice;
                    ImageIcon icon;
                    try{
                    icon = new ImageIcon("thermometer2.png");}
                    catch(Exception excep){
                        icon = null;
                    }
                    
                    
                    JLabel valueLabel = new JLabel("Temperatura");
                    JTextField value = new JTextField();
                    value.setPreferredSize(new Dimension(10,10));
                    
                    if(selThermo.getTimer()){tempo.setEnabled(true);}
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
                    
                    panel.setLayout(new GridLayout(4, 2));
                    
                    panel.add(onOff);
                    panel.add(valueLabel);
                    
                    panel.add(temp);
                    panel.add(value);
                    
                    panel.add(new JLabel(""));
                    panel.add(tempoLabel);
                    panel.add(new JLabel(""));
                    panel.add(tempo);
                    
                    panel.setSize(new Dimension(150, 150));
                    
                    tempo.setText(""+selThermo.getTimeLeft());
                    
                    temp.addChangeListener(new ChangeListener() {
                        public void stateChanged(ChangeEvent e) {
                            value.setText(Integer.toString(temp.getValue()));
                        }
                    });
                    
                    int confirm = JOptionPane.showConfirmDialog(null, panel, "Insira o nome do device, e as propriedades dele", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
                    if(confirm==JOptionPane.OK_OPTION){
                        selThermo.setTemp(temp.getValue());
                        selThermo.setState(onOff.isSelected());
                        
                        if(selThermo.getTimer()){ // Se o Device tem um Timer, entao pede o tempo do Timer
                            int valorTempo=0;
                            try{
                                valorTempo = Integer.parseInt(tempo.getText());
                                if(valorTempo<0){
                                    throw new ValorInvalido("Valor negativo no Timer. Foi resetado para 0");
                                }
                            }
                            catch(NumberFormatException excep){
                                valorTempo = 0;
                            }
                            catch(ValorInvalido excep){
                                valorTempo=0;
                            }
                            finally{selThermo.setTimeLeft(valorTempo);}
                        }
                        updateDevicesList(comodo);
                    }
                }
                
                /** Caso em que o device eh do tipo Intense*/
                if(selDevice instanceof Intense){
                    Intense selIntense = (Intense) selDevice;
                    ImageIcon icon;
                    try{
                    icon = new ImageIcon("intensity2.png");}
                    catch(Exception excep){
                        icon = null;
                    }
                    
                    if(selIntense.getTimer()){tempo.setEnabled(true);}
                    ButtonGroup intensity = new ButtonGroup();
                    JRadioButton zero = new JRadioButton("0");
                    zero.setActionCommand("0");
                    JRadioButton um = new JRadioButton("1");
                    um.setActionCommand("1");
                    JRadioButton dois = new JRadioButton("2");
                    dois.setActionCommand("2");
                    JRadioButton tres = new JRadioButton("3");
                    tres.setActionCommand("3");
                    
                    intensity.add(zero); intensity.add(um); intensity.add(dois); intensity.add(tres); 
                    
                    JPanel panel = new JPanel();
                    
                    panel.setLayout(new GridLayout(3, 4));
                    
                    panel.add(onOff);
                    panel.add(new JLabel(""));
                    panel.add(new JLabel(""));
                    panel.add(new JLabel(""));
                    
                    panel.add(zero);
                    panel.add(um);
                    panel.add(dois);
                    panel.add(tres);
                    
                    panel.add(tempo);
                    
                    tempo.setText(""+selIntense.getTimeLeft());
                    
                    panel.add(new JLabel(""));
                    panel.add(new JLabel(""));
                    panel.add(new JLabel(""));
                    
                    panel.setSize(new Dimension(150, 150));
                    
                    onOff.setSelected(selIntense.getState());
                    
                    if(!selIntense.getState()){
                        zero.setSelected(true);
                    } else {
                        switch(selIntense.getIntensity()){
                            case 1:
                                onOff.setSelected(true);
                                um.setSelected(true);
                                break;
                            case 2:
                                onOff.setSelected(true);
                                dois.setSelected(true);
                                break;
                            case 3:
                                onOff.setSelected(true);
                                tres.setSelected(true);
                                break;
                        }
                    }
                    
                    int confirm = JOptionPane.showConfirmDialog(null, panel, "Edita as propriedades do dispositivo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
                    if(confirm==JOptionPane.OK_OPTION){
                        selIntense.setIntensity(Integer.parseInt(intensity.getSelection().getActionCommand()));
                        selIntense.setState(onOff.isSelected());
                        if(selIntense.getIntensity()>0){selIntense.setState(true);}
                        else{selIntense.setState(false);}
                        if(selIntense.getTimer()){selIntense.setTimeLeft(Integer.parseInt(tempo.getText()));}
                        updateDevicesList(comodo);
                    }
                }
                
                /** Caso em que o dispositivo nao eh Thermo nem Intense*/
                if(selDevice instanceof onOff){
                    onOff selOnOff = (onOff) selDevice;
                    
                    ImageIcon icon;
                    try{
                    icon = new ImageIcon("switch2.png");}
                    catch(Exception excep){
                        icon = null;
                    }
                    
                    if(selOnOff.getTimer()){tempo.setEnabled(true);}
                    JPanel panel = new JPanel();
                    
                    panel.setLayout(new GridLayout(2, 2));
                    
                    panel.add(onOff);
                    panel.add(new JLabel(""));
                    panel.add(tempo);
                    panel.add(new JLabel(""));
                    
                    tempo.setText(""+selOnOff.getTimeLeft());
                    
                    panel.setSize(new Dimension(150, 150));
                    
                    onOff.setSelected(selOnOff.getState());

                    int confirm = JOptionPane.showConfirmDialog(null, panel, "Edita as propriedades do dispositivo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
                    if(confirm==JOptionPane.OK_OPTION){
                        selOnOff.setState(onOff.isSelected());
                        if(selOnOff.getTimer()){selOnOff.setTimeLeft(Integer.parseInt(tempo.getText()));}
                        updateDevicesList(comodo);
                    }
                }
            }
            });

            /** Action Listener para o Timer contar o tempo dos dispositivos que tem Timer ativo e estao ligados */
        ActionListener al=new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for(Comodo comodo: comodosList){
                        if(comodo!=null){
                            for(Device device: comodo.getDevices()){
                                devicesList.repaint();
                                if(device.getTimer() && device.getState() && (device.getTimeLeft()!=0)){
                                    device.countdown();
                                }
                                if(device.getTimeLeft()==0 && device.getTimer()){
                                device.setState(false);}
                            }
                        }
                    }
                }
            };
            
            Timer timer = new Timer(1000, al);
            timer.start();
            
        
            /** ActionListener do botao que remove um dispositivo do Comodo*/
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
        
            /** ActionListener do botao de adicionar dispositivo ao Comodo.
               Mostra um dialog com opçoes pro usuario escolher Nome do dispositivo, se eh Thermo, Intense ou nenhum, e se tem
               ou nao tem Timer */
        addDeviceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Comodo comodo = (Comodo) comodosDropdown.getSelectedItem();
                JTextField nome = new JTextField();
                JCheckBox temp = new JCheckBox(": Thermo");
                JCheckBox intens = new JCheckBox(": Intensidade");
                JCheckBox timer = new JCheckBox(": Timer");

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
                panel.add(timer);
                panel.add(temp);
                panel.add(intens);
                
                int confirm = JOptionPane.showConfirmDialog(null, panel, "Insira nome e propriedades", JOptionPane.OK_CANCEL_OPTION);
                if (comodo != null && confirm == JOptionPane.OK_OPTION) {
                    DefaultListModel<Device> devicesModel = comodosMap.get(comodo);
                    String nomeDev = nome.getText();
                    boolean tempState = temp.isSelected();
                    boolean intensState = intens.isSelected();
                    boolean timerState = timer.isSelected();
                    if (nomeDev != null && !nomeDev.isEmpty()) {
                        /** Caso em que o Device eh do tipo Thermo desenvolvido por Alvaro Guglielmin Becker */ 
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
                            int tmin=0;
                            int tmax=0;
                            
                            try{
                                try{tmin = Integer.parseInt(min.getText());}
                                catch(NumberFormatException excep){tmin = -20;}
                                try{tmax = Integer.parseInt(max.getText());}
                                catch(NumberFormatException excep){tmax = 300;}
                            if(tmin>tmax){
                                throw new ValorInvalido("Intervalo proibido: temperatura minima eh maior que temperatura maxima");
                                }
                            }
                            catch(ValorInvalido excep){
                                /** Caso o usuario coloque temperatura minima maior que a temperatura maxima */
                                int seg = tmin;
                                tmin=tmax;
                                tmax=seg;
                            }
                            
                            // Cria o dispositivo
                            Thermo device;
                            if(timerState){
                                device = new Thermo(nomeDev, tmin, tmax, true);
                            }else{
                                device = new Thermo(nomeDev, tmin, tmax, false);
                            }
                            
                            devicesModel.addElement(device);
                            comodo.addDevice(device);
                            updateDevicesList(comodo);
                        } else if(intensState){
                            Intense device;
                            if(timerState){
                                device = new Intense(nomeDev, true);
                            }else{
                                 device = new Intense(nomeDev, false);
                            }
                            devicesModel.addElement(device);
                            comodo.addDevice(device);
                            updateDevicesList(comodo);
                        } else{
                            onOff device;
                            if(timerState){
                                device = new onOff(nomeDev, true);
                            }else{
                                device = new onOff(nomeDev, false);
                            }
                            devicesModel.addElement(device);
                            comodo.addDevice(device);
                            updateDevicesList(comodo);
                        }
                        }
                    }
                }
        });

        /** ActionListener do menu dropdown de Comodos */
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
        
        // Action Listener do item Testar do Menu
        testarMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                /** Criando a cozinha */
                Comodo cozinha = new Comodo("Cozinha");
                DefaultListModel<Device> devicesModel = new DefaultListModel<>();
                comodosMap.put(cozinha, devicesModel);
                comodosDropdown.addItem(cozinha);
                comodosList.add(cozinha);
                
                /** Devices da Cozinha */
                //Lampada - Timer 30s - Ligado
                onOff lampadaCoz = new onOff("Lampada Cozinha", true);
                lampadaCoz.setState(true);
                lampadaCoz.setTimeLeft(30);
                devicesModel = comodosMap.get(cozinha);
                devicesModel.addElement(lampadaCoz);
                cozinha.addDevice(lampadaCoz);
                updateDevicesList(cozinha);
                
                /** Criando a sala*/
                Comodo sala = new Comodo("Sala");
                 devicesModel = new DefaultListModel<>();
                comodosMap.put(sala, devicesModel);
                comodosDropdown.addItem(sala);
                comodosList.add(sala);
                
                /** Devices da sala: */
                //Ar condicionado - Timer 60s - Ligado
                Thermo arCondicionado = new Thermo("Ar Cond.", 16, 28, true);
                arCondicionado.setState(true);
                arCondicionado.setTimeLeft(60);
                devicesModel = comodosMap.get(sala);
                devicesModel.addElement(arCondicionado);
                sala.addDevice(arCondicionado);
                updateDevicesList(sala);
                
                //Lampada - Sem Timer - Ligado
                onOff lampadaSala = new onOff("Lampada", false);
                lampadaSala.setState(true);
                devicesModel = comodosMap.get(sala);
                devicesModel.addElement(lampadaSala);
                sala.addDevice(lampadaSala);
                updateDevicesList(sala);
                
                //Ventilador - Timer 40s - Ligado
                Intense ventilador = new Intense("Ventilador", true);
                ventilador.setState(true);
                ventilador.setIntensity(2);
                ventilador.setTimeLeft(40);
                devicesModel = comodosMap.get(sala);
                devicesModel.addElement(ventilador);
                sala.addDevice(ventilador);
                updateDevicesList(sala);
                
                comodosDropdown.setSelectedItem(cozinha);
            }
        });

        // Adiciona componentes p contentPane
        this.setJMenuBar(menuBar);
        contentPane.add(header, BorderLayout.NORTH);
        contentPane.add(body, BorderLayout.CENTER);
        contentPane.add(buttons, BorderLayout.SOUTH);
        setContentPane(contentPane);
    }

    /** Metodo que atualiza a lista de dispositivos do comodo.
     * @param comodo (Comodo)
       */
    private void updateDevicesList(Comodo comodo) {
        DefaultListModel<Device> devicesModel = comodosMap.get(comodo);
        devicesList.setModel(devicesModel);
    }

    public static void main(String[] args) throws ValorInvalido{
        Application app = new Application();
        app.setVisible(true);
    }
}
