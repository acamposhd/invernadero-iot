/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import model.Data;
import model.Queries;
import model.ThreadFoto;
import model.ThreadHumi;
import model.ThreadSoil;
import model.ThreadTemp;
import model.ThreadTouch;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author fields
 */
public class MainScreen extends javax.swing.JFrame {

    int i = 0;
    int tempOldValue = 0;
    int humiOldValue = 0;
    int soilOldValue = 0;
    int fotoOldValue = 0;
    int touchOldValue = 0;
    public static String auxLed = "1";
    public static String auxTable = "NO";
    public static String rfidVal="";
    /**
     * Creates new form MainScreen
     */
    /*final XYSeries Serie  = new XYSeries("Temperatura");
    final XYSeriesCollection Coleccion = new XYSeriesCollection();
    JFreeChart Grafica;
    PanamaHitek_Arduino Arduino = new PanamaHitek_Arduino();
    int i = 0;
    SerialPortEventListener evento = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if(Arduino.isMessageAvailable() == true){
                    i++;
                    Serie.add(i, Integer.parseInt(Arduino.printMessage()));
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArduinoException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };*/
    final XYSeries SerieTemperatura = new XYSeries("Temperatura");
    final XYSeriesCollection ColeccionT = new XYSeriesCollection();
    JFreeChart GraficaT;
    final XYSeries SerieHumedad = new XYSeries("Humedad");
    final XYSeriesCollection ColeccionH = new XYSeriesCollection();
    JFreeChart GraficaH;
    final XYSeries SerieSoil = new XYSeries("Tierra");
    final XYSeriesCollection ColeccionS = new XYSeriesCollection();
    JFreeChart GraficaS;
    final XYSeries SerieLuz = new XYSeries("Luz");
    final XYSeriesCollection ColeccionL = new XYSeriesCollection();
    JFreeChart GraficaL;
    final XYSeries SeriePotenciometro = new XYSeries("Potenciometro");
    final XYSeriesCollection ColeccionP = new XYSeriesCollection();
    JFreeChart GraficaP;
    final XYSeries SerieTouch = new XYSeries("Touch");
    final XYSeriesCollection ColeccionC = new XYSeriesCollection();
    JFreeChart GraficaC;
    final XYSeries SerieRFID = new XYSeries("RFID");
    final XYSeriesCollection ColeccionR = new XYSeriesCollection();
    JFreeChart GraficaR;
    PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    PanamaHitek_MultiMessage multi = new PanamaHitek_MultiMessage(7, ino);
    SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {

            try {
                if (multi.dataReceptionCompleted()) {
                    /* System.out.println(multi.getMessage(0));//TEMPERATURA
                System.out.println(multi.getMessage(1));//HUMEDAD
                System.out.println(multi.getMessage(2));//SOIL
                System.out.println(multi.getMessage(3));//FOTO
                System.out.println(multi.getMessage(4));//POTEN
                System.out.println(multi.getMessage(5));//TOUCH
                System.out.println(multi.getMessage(6));//RFID
                multi.flushBuffer();*/
                    i++;
                    int tempValue = Integer.parseInt(multi.getMessage(0));
                    int humiValue = Integer.parseInt(multi.getMessage(1));
                    int soilValue = Integer.parseInt(multi.getMessage(2));
                    int fotoValue = Integer.parseInt(multi.getMessage(3));
                    int potenValue = Integer.parseInt(multi.getMessage(4));
                    int touchValue = Integer.parseInt(multi.getMessage(5));
                    String rfidValue = (multi.getMessage(6));
                    rfidVal = rfidValue;
                    
                    //System.out.println("1 " + rfidVal + "2 " + rfidValue);

                    SerieTemperatura.add(i, tempValue);
                    SerieHumedad.add(i, humiValue);
                    SerieSoil.add(i, soilValue);
                    SerieLuz.add(i, fotoValue);
                    SeriePotenciometro.add(i, potenValue);
                    SerieTouch.add(i, touchValue);
                    //SerieRFID.add(i,(multi.getMessage(6));

                    Queries q = new Queries();

                    if (tempValue != tempOldValue) {
                        ThreadTemp tt = new ThreadTemp(tempValue, q);
                        Thread processTemp = new Thread(tt);
                        processTemp.start();
                    }
                    if (humiValue != humiOldValue) {
                        ThreadHumi th = new ThreadHumi(humiValue, q);
                        Thread processHumi = new Thread(th);
                        processHumi.start();
                    }
                    if (soilValue != soilOldValue) {
                        ThreadSoil ts = new ThreadSoil(soilValue, q);
                        Thread processSoil = new Thread(ts);
                        processSoil.start();
                    }
                    if (fotoValue != fotoOldValue) {
                        ThreadFoto tf = new ThreadFoto(fotoValue, q);
                        Thread processFoto = new Thread(tf);
                        processFoto.start();
                    }
                    if (touchValue != touchOldValue) {
                        ThreadTouch tc = new ThreadTouch(touchValue, q);
                        Thread processTouch = new Thread(tc);
                        processTouch.start();
                    }

                    tempOldValue = tempValue;
                    humiOldValue = humiValue;
                    soilOldValue = soilValue;
                    fotoOldValue = fotoValue;
                    touchOldValue = touchValue;

                    if (touchValue == 1 || fotoValue < 6 || auxLed == "0") {
                        lightOnBtn.setVisible(true);
                        lightOffBtn.setVisible(false);

                    } else {
                        lightOnBtn.setVisible(false);
                        lightOffBtn.setVisible(true);
                    }

                    multi.flushBuffer();
                    Thread.sleep(1000);

                }
            } catch (ArduinoException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };

    public MainScreen() {
        initComponents();
        HistoricoGeneralBtn.setVisible(false);
        try {
            ino.arduinoRXTX("COM4", 9600, listener);
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        SerieTemperatura.add(0, 0);
        ColeccionT.addSeries(SerieTemperatura);
        GraficaT = ChartFactory.createXYLineChart("Temperatura Vs Tiempo", "Tiempo", "Temperatura", ColeccionT, PlotOrientation.VERTICAL, true, false, true);

        SerieHumedad.add(0, 0);
        ColeccionH.addSeries(SerieHumedad);
        GraficaH = ChartFactory.createXYLineChart("Humedad Vs Tiempo", "Tiempo", "Humedad", ColeccionH, PlotOrientation.VERTICAL, true, false, true);

        SerieSoil.add(0, 0);
        ColeccionS.addSeries(SerieSoil);
        GraficaS = ChartFactory.createXYLineChart("Humedad en Tierra Vs tiempo", "Tiempo", "Humedad en tierra", ColeccionS, PlotOrientation.VERTICAL, true, false, true);

        SerieLuz.add(0, 0);
        ColeccionL.addSeries(SerieLuz);
        GraficaL = ChartFactory.createXYLineChart("Iluminación Vs Tiempo", "Tiempo", "Iluminación", ColeccionL, PlotOrientation.VERTICAL, true, false, true);

        SerieTouch.add(0, 0);
        ColeccionC.addSeries(SerieTouch);
        GraficaC = ChartFactory.createXYLineChart("Lámpara manual Vs Tiempo", "Tiempo", "Lámpara manual", ColeccionC, PlotOrientation.VERTICAL, true, false, true);
        /*
        try {
            Arduino.arduinoRX("COM4", 9600, evento);
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        Serie.add(0,0);
        Coleccion.addSeries(Serie);
        Grafica = ChartFactory.createXYLineChart("Temperatura Vs tiempo", "Tiempo", "Temperatura", Coleccion, PlotOrientation.VERTICAL, true, false, true);
         */

        lightOffBtn.setVisible(true);
        lightOnBtn.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lightOffBtn = new javax.swing.JButton();
        lightOnBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        realTimeLampBtn = new javax.swing.JButton();
        HistoricoGeneralBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        realTimeFotoBtn = new javax.swing.JButton();
        HistoricoFotoBtn = new javax.swing.JButton();
        userLbl = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        realTimeTempBtn = new javax.swing.JButton();
        historicoTempBtn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        realTimeHumiBtn = new javax.swing.JButton();
        HistoricoHumiJbtn = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        realTimeSoilBtn = new javax.swing.JButton();
        HistoricoSoilJbtn = new javax.swing.JButton();
        HistoricoTouchJbtn = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        logOutBtn = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Invernadero UTA Panel de Control");
        setMinimumSize(new java.awt.Dimension(980, 640));
        setResizable(false);
        getContentPane().setLayout(null);

        lightOffBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/lightOff.png"))); // NOI18N
        lightOffBtn.setBorder(null);
        lightOffBtn.setBorderPainted(false);
        lightOffBtn.setContentAreaFilled(false);
        lightOffBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lightOffBtn.setFocusPainted(false);
        lightOffBtn.setMaximumSize(new java.awt.Dimension(160, 136));
        lightOffBtn.setMinimumSize(new java.awt.Dimension(160, 136));
        lightOffBtn.setPreferredSize(new java.awt.Dimension(160, 136));
        lightOffBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lightOffBtnActionPerformed(evt);
            }
        });
        getContentPane().add(lightOffBtn);
        lightOffBtn.setBounds(80, 140, 90, 160);

        lightOnBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/lightOn.png"))); // NOI18N
        lightOnBtn.setBorderPainted(false);
        lightOnBtn.setContentAreaFilled(false);
        lightOnBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lightOnBtn.setFocusPainted(false);
        lightOnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lightOnBtnActionPerformed(evt);
            }
        });
        getContentPane().add(lightOnBtn);
        lightOnBtn.setBounds(80, 140, 90, 160);

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel1.setText("Estatus");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(130, 300, 70, 19);

        realTimeLampBtn.setBackground(new java.awt.Color(255, 255, 255));
        realTimeLampBtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        realTimeLampBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/realTimeGraph.png"))); // NOI18N
        realTimeLampBtn.setText("Tiempo Real");
        realTimeLampBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        realTimeLampBtn.setBorderPainted(false);
        realTimeLampBtn.setContentAreaFilled(false);
        realTimeLampBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        realTimeLampBtn.setFocusPainted(false);
        realTimeLampBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realTimeLampBtnActionPerformed(evt);
            }
        });
        getContentPane().add(realTimeLampBtn);
        realTimeLampBtn.setBounds(40, 340, 160, 60);

        HistoricoGeneralBtn.setBackground(new java.awt.Color(255, 255, 255));
        HistoricoGeneralBtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        HistoricoGeneralBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/historic.png"))); // NOI18N
        HistoricoGeneralBtn.setText(" Histórico General   ");
        HistoricoGeneralBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        HistoricoGeneralBtn.setBorderPainted(false);
        HistoricoGeneralBtn.setContentAreaFilled(false);
        HistoricoGeneralBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HistoricoGeneralBtn.setFocusPainted(false);
        getContentPane().add(HistoricoGeneralBtn);
        HistoricoGeneralBtn.setBounds(380, 440, 280, 60);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/lightSensor.png"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(230, 160, 130, 130);

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel3.setText("Sensor de Luz");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(250, 130, 100, 19);

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel4.setText("Lámpara:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(60, 300, 70, 17);

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel5.setText("Estatus");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(290, 300, 80, 19);

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel6.setText("Luz:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(260, 300, 30, 19);

        realTimeFotoBtn.setBackground(new java.awt.Color(255, 255, 255));
        realTimeFotoBtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        realTimeFotoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/realTimeGraph.png"))); // NOI18N
        realTimeFotoBtn.setText("Tiempo Real");
        realTimeFotoBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        realTimeFotoBtn.setBorderPainted(false);
        realTimeFotoBtn.setContentAreaFilled(false);
        realTimeFotoBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        realTimeFotoBtn.setFocusPainted(false);
        realTimeFotoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realTimeFotoBtnActionPerformed(evt);
            }
        });
        getContentPane().add(realTimeFotoBtn);
        realTimeFotoBtn.setBounds(220, 340, 160, 60);

        HistoricoFotoBtn.setBackground(new java.awt.Color(255, 255, 255));
        HistoricoFotoBtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        HistoricoFotoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/historic.png"))); // NOI18N
        HistoricoFotoBtn.setText(" Histórico   ");
        HistoricoFotoBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        HistoricoFotoBtn.setBorderPainted(false);
        HistoricoFotoBtn.setContentAreaFilled(false);
        HistoricoFotoBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HistoricoFotoBtn.setFocusPainted(false);
        HistoricoFotoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistoricoFotoBtnActionPerformed(evt);
            }
        });
        getContentPane().add(HistoricoFotoBtn);
        HistoricoFotoBtn.setBounds(220, 410, 160, 60);

        userLbl.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        userLbl.setText("Usuario");
        getContentPane().add(userLbl);
        userLbl.setBounds(120, 30, 230, 17);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/tempSensor.png"))); // NOI18N
        getContentPane().add(jLabel8);
        jLabel8.setBounds(410, 160, 130, 130);

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel9.setText("Sensor de Temperatura");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(400, 130, 170, 19);

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel10.setText("Estatus");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(500, 300, 80, 19);

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel11.setText("Temperatura:");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(410, 300, 90, 19);

        realTimeTempBtn.setBackground(new java.awt.Color(255, 255, 255));
        realTimeTempBtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        realTimeTempBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/realTimeGraph.png"))); // NOI18N
        realTimeTempBtn.setText("Tiempo Real");
        realTimeTempBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        realTimeTempBtn.setBorderPainted(false);
        realTimeTempBtn.setContentAreaFilled(false);
        realTimeTempBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        realTimeTempBtn.setFocusPainted(false);
        realTimeTempBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realTimeTempBtnActionPerformed(evt);
            }
        });
        getContentPane().add(realTimeTempBtn);
        realTimeTempBtn.setBounds(400, 340, 160, 60);

        historicoTempBtn.setBackground(new java.awt.Color(255, 255, 255));
        historicoTempBtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        historicoTempBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/historic.png"))); // NOI18N
        historicoTempBtn.setText(" Histórico   ");
        historicoTempBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        historicoTempBtn.setBorderPainted(false);
        historicoTempBtn.setContentAreaFilled(false);
        historicoTempBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        historicoTempBtn.setFocusPainted(false);
        historicoTempBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historicoTempBtnActionPerformed(evt);
            }
        });
        getContentPane().add(historicoTempBtn);
        historicoTempBtn.setBounds(400, 410, 160, 60);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/humiditySensor.png"))); // NOI18N
        getContentPane().add(jLabel12);
        jLabel12.setBounds(590, 160, 130, 130);

        jLabel13.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel13.setText("Sensor de Humedad");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(590, 130, 170, 19);

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel14.setText("Estatus");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(670, 300, 80, 19);

        jLabel15.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel15.setText("Humedad:");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(600, 300, 70, 19);

        realTimeHumiBtn.setBackground(new java.awt.Color(255, 255, 255));
        realTimeHumiBtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        realTimeHumiBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/realTimeGraph.png"))); // NOI18N
        realTimeHumiBtn.setText("Tiempo Real");
        realTimeHumiBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        realTimeHumiBtn.setBorderPainted(false);
        realTimeHumiBtn.setContentAreaFilled(false);
        realTimeHumiBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        realTimeHumiBtn.setFocusPainted(false);
        realTimeHumiBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realTimeHumiBtnActionPerformed(evt);
            }
        });
        getContentPane().add(realTimeHumiBtn);
        realTimeHumiBtn.setBounds(580, 340, 160, 60);

        HistoricoHumiJbtn.setBackground(new java.awt.Color(255, 255, 255));
        HistoricoHumiJbtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        HistoricoHumiJbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/historic.png"))); // NOI18N
        HistoricoHumiJbtn.setText(" Histórico   ");
        HistoricoHumiJbtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        HistoricoHumiJbtn.setBorderPainted(false);
        HistoricoHumiJbtn.setContentAreaFilled(false);
        HistoricoHumiJbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HistoricoHumiJbtn.setFocusPainted(false);
        HistoricoHumiJbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistoricoHumiJbtnActionPerformed(evt);
            }
        });
        getContentPane().add(HistoricoHumiJbtn);
        HistoricoHumiJbtn.setBounds(580, 410, 160, 60);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/soilSensor.png"))); // NOI18N
        getContentPane().add(jLabel16);
        jLabel16.setBounds(790, 160, 110, 130);

        jLabel17.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel17.setText("Sensor de Humedad en Tierra");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(750, 130, 200, 19);

        jLabel18.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel18.setText("Estatus");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(860, 300, 80, 19);

        jLabel19.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel19.setText("Humedad:");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(790, 300, 70, 19);

        realTimeSoilBtn.setBackground(new java.awt.Color(255, 255, 255));
        realTimeSoilBtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        realTimeSoilBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/realTimeGraph.png"))); // NOI18N
        realTimeSoilBtn.setText("Tiempo Real");
        realTimeSoilBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        realTimeSoilBtn.setBorderPainted(false);
        realTimeSoilBtn.setContentAreaFilled(false);
        realTimeSoilBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        realTimeSoilBtn.setFocusPainted(false);
        realTimeSoilBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realTimeSoilBtnActionPerformed(evt);
            }
        });
        getContentPane().add(realTimeSoilBtn);
        realTimeSoilBtn.setBounds(770, 340, 160, 60);

        HistoricoSoilJbtn.setBackground(new java.awt.Color(255, 255, 255));
        HistoricoSoilJbtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        HistoricoSoilJbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/historic.png"))); // NOI18N
        HistoricoSoilJbtn.setText(" Histórico   ");
        HistoricoSoilJbtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        HistoricoSoilJbtn.setBorderPainted(false);
        HistoricoSoilJbtn.setContentAreaFilled(false);
        HistoricoSoilJbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HistoricoSoilJbtn.setFocusPainted(false);
        HistoricoSoilJbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistoricoSoilJbtnActionPerformed(evt);
            }
        });
        getContentPane().add(HistoricoSoilJbtn);
        HistoricoSoilJbtn.setBounds(770, 410, 160, 60);

        HistoricoTouchJbtn.setBackground(new java.awt.Color(255, 255, 255));
        HistoricoTouchJbtn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        HistoricoTouchJbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/historic.png"))); // NOI18N
        HistoricoTouchJbtn.setText(" Histórico   ");
        HistoricoTouchJbtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        HistoricoTouchJbtn.setBorderPainted(false);
        HistoricoTouchJbtn.setContentAreaFilled(false);
        HistoricoTouchJbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HistoricoTouchJbtn.setFocusPainted(false);
        HistoricoTouchJbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistoricoTouchJbtnActionPerformed(evt);
            }
        });
        getContentPane().add(HistoricoTouchJbtn);
        HistoricoTouchJbtn.setBounds(40, 410, 160, 60);

        jLabel20.setFont(new java.awt.Font("Bahnschrift", 0, 15)); // NOI18N
        jLabel20.setText("Lámpara del Invernadero");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(50, 130, 170, 19);

        jLabel21.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        jLabel21.setText("Panel de Control");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(410, 40, 190, 29);

        logOutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/logOut.png"))); // NOI18N
        logOutBtn.setBorderPainted(false);
        logOutBtn.setContentAreaFilled(false);
        logOutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutBtn.setFocusPainted(false);
        logOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBtnActionPerformed(evt);
            }
        });
        getContentPane().add(logOutBtn);
        logOutBtn.setBounds(870, 20, 82, 58);

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/logo_uta_mini.png"))); // NOI18N
        jButton13.setBorderPainted(false);
        jButton13.setContentAreaFilled(false);
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton13.setFocusPainted(false);
        getContentPane().add(jButton13);
        jButton13.setBounds(10, 530, 210, 60);

        jButton14.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jButton14.setText("Made By Alberto Campos & Emiliano Roque");
        jButton14.setBorderPainted(false);
        jButton14.setContentAreaFilled(false);
        jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton14.setFocusPainted(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton14);
        jButton14.setBounds(600, 550, 360, 40);

        jLabel23.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel23.setText("Bienvenido");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(20, 30, 100, 17);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/sensorsWall.jpg"))); // NOI18N
        jLabel22.setText("jLabel22");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(0, 0, 970, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lightOffBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lightOffBtnActionPerformed
        /*.setVisible(true);
        lightOffBtn.setVisible(false); 
        auxLed = "0";
        try {
            ino.sendData(auxLed);
  
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }//GEN-LAST:event_lightOffBtnActionPerformed

    private void lightOnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lightOnBtnActionPerformed
        /* lightOffBtn.setVisible(true);
        lightOnBtn.setVisible(false);  
        auxLed = "1";
        try {
        ino.sendData(auxLed);
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
         */

    }//GEN-LAST:event_lightOnBtnActionPerformed

    private void realTimeTempBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realTimeTempBtnActionPerformed
        /* final XYSeries Serie  = new XYSeries("Temperatura");
        final XYSeriesCollection Coleccion = new XYSeriesCollection();
        JFreeChart Grafica;
        PanamaHitek_Arduino Arduino = new PanamaHitek_Arduino();
        //int i = 0;
        SerialPortEventListener evento = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if(Arduino.isMessageAvailable() == true){
                    i++;
                    Serie.add(i, Integer.parseInt(Arduino.printMessage()));
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArduinoException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
        try {
            Arduino.arduinoRX("COM4", 9600, evento);
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        SerieTemperatura.add(0,0);
        ColeccionT.addSeries(SerieTemperatura);
        GraficaT = ChartFactory.createXYLineChart("Temperatura Vs tiempo", "Tiempo", "Temperatura", ColeccionT, PlotOrientation.VERTICAL, true, false, true);
         */
        ChartPanel Panel = new ChartPanel(GraficaT);
        JFrame Ventana = new JFrame("Temperatura en tiempo real");
        Ventana.getContentPane().add(Panel);
        Ventana.pack();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_realTimeTempBtnActionPerformed

    private void realTimeFotoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realTimeFotoBtnActionPerformed
        ChartPanel Panel = new ChartPanel(GraficaL);
        JFrame Ventana = new JFrame("Fotoresistencia en tiempo real");
        Ventana.getContentPane().add(Panel);
        Ventana.pack();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_realTimeFotoBtnActionPerformed

    private void realTimeHumiBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realTimeHumiBtnActionPerformed

        ChartPanel Panel = new ChartPanel(GraficaH);
        JFrame Ventana = new JFrame("Humedad en tiempo real");
        Ventana.getContentPane().add(Panel);
        Ventana.pack();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_realTimeHumiBtnActionPerformed

    private void realTimeLampBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realTimeLampBtnActionPerformed
        ChartPanel Panel = new ChartPanel(GraficaC);
        JFrame Ventana = new JFrame("Botón Touch en tiempo real");
        Ventana.getContentPane().add(Panel);
        Ventana.pack();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_realTimeLampBtnActionPerformed

    private void realTimeSoilBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realTimeSoilBtnActionPerformed
        ChartPanel Panel = new ChartPanel(GraficaS);
        JFrame Ventana = new JFrame("Humedad en tierra en tiempo real");
        Ventana.getContentPane().add(Panel);
        Ventana.pack();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_realTimeSoilBtnActionPerformed

    private void historicoTempBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historicoTempBtnActionPerformed
        /*Queries q = new Queries();
        q.selectTemp();
        Data d =  new Data();
        d = (Data) q.tempSensor.get(0);
        JOptionPane.showMessageDialog(null, d.getName(), null, 0);*/
        auxTable = "temp";
        Table Ventana = new Table();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            ino.sendData("1");
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_historicoTempBtnActionPerformed

    private void HistoricoTouchJbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistoricoTouchJbtnActionPerformed

        auxTable = "touch";

        Table Ventana = new Table();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            ino.sendData("4");
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_HistoricoTouchJbtnActionPerformed

    private void HistoricoFotoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistoricoFotoBtnActionPerformed
        auxTable = "foto";
        Table Ventana = new Table();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            ino.sendData("5");
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_HistoricoFotoBtnActionPerformed

    private void HistoricoHumiJbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistoricoHumiJbtnActionPerformed
        auxTable = "humi";
        Table Ventana = new Table();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            ino.sendData("2");
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_HistoricoHumiJbtnActionPerformed

    private void HistoricoSoilJbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistoricoSoilJbtnActionPerformed
        auxTable = "soil";
        Table Ventana = new Table();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            ino.sendData("3");
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_HistoricoSoilJbtnActionPerformed

    private void logOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutBtnActionPerformed
        try {
            ino.killArduinoConnection();
            dispose();
            Login l =  new Login();
            l.setVisible(true);
            l.setLocationRelativeTo(null);
        } catch (ArduinoException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_logOutBtnActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton HistoricoFotoBtn;
    private javax.swing.JButton HistoricoGeneralBtn;
    private javax.swing.JButton HistoricoHumiJbtn;
    private javax.swing.JButton HistoricoSoilJbtn;
    private javax.swing.JButton HistoricoTouchJbtn;
    private javax.swing.JButton historicoTempBtn;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton lightOffBtn;
    private javax.swing.JButton lightOnBtn;
    private javax.swing.JButton logOutBtn;
    private javax.swing.JButton realTimeFotoBtn;
    private javax.swing.JButton realTimeHumiBtn;
    private javax.swing.JButton realTimeLampBtn;
    private javax.swing.JButton realTimeSoilBtn;
    private javax.swing.JButton realTimeTempBtn;
    public static javax.swing.JLabel userLbl;
    // End of variables declaration//GEN-END:variables
}
