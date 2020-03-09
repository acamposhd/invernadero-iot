/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author fields
 */
public class Queries {

    Connection con = null;
    PreparedStatement ps = null;
    Date objDate = new Date();
    String strDateFormat1 = "dd-MMM-yyyy";
    String strDateFormat2 = "hh: mm: ss a";
    SimpleDateFormat objSDF1 = new SimpleDateFormat(strDateFormat1); // La cadena de formato de fecha se pasa como un argumento al objeto 
    SimpleDateFormat objSDF2 = new SimpleDateFormat(strDateFormat2);
    String fecha = objSDF1.format(objDate);
    String hora = objSDF2.format(objDate);
    public ArrayList tempSensor = new ArrayList<Data>();
    public ArrayList humiSensor = new ArrayList<Data>();
    public ArrayList soilSensor = new ArrayList<Data>();
    public ArrayList lightSensor = new ArrayList<Data>();
    public ArrayList touchSensor = new ArrayList<Data>();

    public boolean insertTemp(int read) {
        try {
            String sql = "INSERT INTO temp_sensor "
                    + "VALUES (0,'DHT11 TEMP',?,?,?)";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setInt(1, read);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            ps.execute();
            return true;
        } catch (Exception ex) {
            //System.out.println("Temp Ex " + ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                //System.out.println(ex);
            }
        }
    }

    public boolean insertHumi(int read) {
        try {
            String sql = "INSERT INTO humi_sensor "
                    + "VALUES (0,'DHT11 HUM',?,?,?)";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setInt(1, read);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            ps.execute();
            return true;
        } catch (Exception ex) {
            //System.out.println("Humi Ex" + ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                // System.out.println(ex);
            }
        }
    }

    public boolean insertFoto(int read) {
        try {
            String sql = "INSERT INTO light_sensor "
                    + "VALUES (0,'LDR LUZ',?,?,?)";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setInt(1, read);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            ps.execute();
            return true;
        } catch (Exception ex) {
            //System.out.println("FotoEx " +ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                //System.out.println(ex);
            }
        }
    }

    public boolean insertSoil(int read) {
        try {
            String sql = "INSERT INTO soil_sensor "
                    + "VALUES (0,'YL69 HUM TIERRA',?,?,?)";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setInt(1, read);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            ps.execute();
            return true;
        } catch (Exception ex) {
            //System.out.println("Soil Ex " + ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                //System.out.println(ex);
            }
        }
    }

    public boolean insertTouch(int read) {
        try {
            String sql = "INSERT INTO touch_sensor "
                    + "VALUES (0,'TTP223B TOUCH',?,?,?)";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setInt(1, read);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            ps.execute();
            return true;
        } catch (Exception ex) {
            //System.out.println("Touch Ex " + ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                //System.out.println(ex);
            }
        }
    }

    public boolean selectTemp() {
        try {
            String sql = "SELECT * FROM temp_sensor ";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            tempSensor = new ArrayList<Data>();
            while (resultSet.next()) {
                //long id = resultSet.getLong("tempID");
                String name = resultSet.getString("sensor_name");
                BigDecimal read = resultSet.getBigDecimal("read");
                String date = resultSet.getString("date");
                String hour = resultSet.getString("hour");
                Data sensor = new Data(name, read, date, hour);
                tempSensor.add(sensor);

            }
            return true;
        } catch (Exception ex) {
            //System.out.println("Temp Ex " + ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                //System.out.println(ex);
            }
        }
    }

    public boolean selectHumi() {
        try {
            String sql = "SELECT * FROM humi_sensor ";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            humiSensor = new ArrayList<Data>();
            while (resultSet.next()) {
                //long id = resultSet.getLong("tempID");
                String name = resultSet.getString("sensor_name");
                BigDecimal read = resultSet.getBigDecimal("read");
                String date = resultSet.getString("date");
                String hour = resultSet.getString("hour");
                Data sensor = new Data(name, read, date, hour);
                humiSensor.add(sensor);

            }
            return true;
        } catch (Exception ex) {
            //System.out.println("Temp Ex " + ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                //System.out.println(ex);
            }
        }
    }

    public boolean selectSoil() {
        try {
            String sql = "SELECT * FROM soil_sensor ";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            soilSensor = new ArrayList<Data>();
            while (resultSet.next()) {
                //long id = resultSet.getLong("tempID");
                String name = resultSet.getString("sensor_name");
                BigDecimal read = resultSet.getBigDecimal("read");
                String date = resultSet.getString("date");
                String hour = resultSet.getString("hour");
                Data sensor = new Data(name, read, date, hour);
                soilSensor.add(sensor);

            }
            return true;
        } catch (Exception ex) {
            //System.out.println("Temp Ex " + ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                //System.out.println(ex);
            }
        }
    }

    public boolean selectLight() {
        try {
            String sql = "SELECT * FROM light_sensor ";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            lightSensor = new ArrayList<Data>();
            while (resultSet.next()) {
                //long id = resultSet.getLong("tempID");
                String name = resultSet.getString("sensor_name");
                BigDecimal read = resultSet.getBigDecimal("read");
                String date = resultSet.getString("date");
                String hour = resultSet.getString("hour");
                Data sensor = new Data(name, read, date, hour);
                lightSensor.add(sensor);

            }
            return true;
        } catch (Exception ex) {
            //System.out.println("Temp Ex " + ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                //System.out.println(ex);
            }
        }
    }

    public boolean selectTouch() {
        try {
            String sql = "SELECT * FROM touch_sensor ";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            touchSensor = new ArrayList<Data>();
            while (resultSet.next()) {
                //long id = resultSet.getLong("tempID");
                String name = resultSet.getString("sensor_name");
                BigDecimal read = resultSet.getBigDecimal("read");
                String date = resultSet.getString("date");
                String hour = resultSet.getString("hour");
                Data sensor = new Data(name, read, date, hour);
                touchSensor.add(sensor);

            }
            return true;
        } catch (Exception ex) {
            //System.out.println("Temp Ex " + ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                //System.out.println(ex);
            }
        }
    }

    public int login(Data d) {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM users WHERE userName = ? AND pwd = md5(?)";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setString(1, d.getUsername());
            ps.setString(2, d.getPw());
            rs = ps.executeQuery();
            if (rs.next()) {

                return 1;
            }
            return 0;
        } catch (Exception ex) {
            System.err.println(ex);
            return 0;
        } finally {
            try {
                con.close();
                ps.close();
                con = null;
                ps = null;
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }
      public int checkUsr(String usr) {
        ResultSet rs = null;
        try {
            String sql = "SELECT userName FROM users WHERE userName = ?";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setString(1, usr);
            rs = ps.executeQuery();
            if (rs.next()) {

                return 1;
            }
            return 0;
        } catch (Exception ex) {
            System.err.println(ex);
            return 0;
        } finally {
            try {
                con.close();
                ps.close();
                con = null;
                ps = null;
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }

    public boolean insertUser(Data d) {
        try {
            String sql = "INSERT INTO users "
                    + "VALUES (?, md5(?),?,?)";
            con = DbConnection.connect();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setString(1, d.getUsername());
            ps.setString(2, d.getPw());
            ps.setString(3, d.getName());
            ps.setString(4, d.getLastname());
            ps.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Touch Ex " + ex);
            return false;
        } finally {
            try {
                con.close();
                con = null;
                ps.close();
                ps = null;
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
