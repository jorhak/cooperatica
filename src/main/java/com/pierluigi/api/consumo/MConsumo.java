package com.pierluigi.api.consumo;

import com.pierluigi.utils.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MConsumo {

    private int id;
    private String Finicio;
    private String Ffin;
    private String Minicio;
    private String Mfin;
    private String estado;
    private int cod_socio;

    public MConsumo() {
        this(0, "", "", "", "", "", 0);
    }

    public MConsumo(int id, String Finicio, String Ffin, String Minicio,
            String Mfin, String estado, int cod_socio) {
        this.id = id;
        this.Finicio = Finicio;
        this.Ffin = Ffin;
        this.Minicio = Minicio;
        this.Mfin = Mfin;
        this.estado = estado;
        this.cod_socio = cod_socio;
    }

    public void setData(Map<String, String> request) {
        if (request.getOrDefault("id", "0").equals("0")) {
            Map<String, String> anterior = (Map<String, String>) finAnterior(
                    "cod_socio", request.getOrDefault("socio", "0"));

            id = 0;
            Finicio = anterior.getOrDefault("Ffin", "");
            Ffin = LocalDate.now().toString();
            Minicio = anterior.getOrDefault("Mfin", "");
            Mfin = request.getOrDefault("lectura", "0");
            estado = request.getOrDefault("estado", "Impaga");
            cod_socio = Integer.parseInt(request.getOrDefault("socio", ""));
        } else {
            id = Integer.parseInt(request.getOrDefault("id", "0"));
            Mfin = request.getOrDefault("lectura", "");
            cod_socio = Integer.parseInt(request.getOrDefault("socio", ""));
        }

    }

    public Map<String, String> save() {
        boolean proccessed = false;
        String sqlInsert = "insert into consumo (Finicio, Ffin, Minicio, Mfin, estado, cod_socio) " +
                "values (?,?,?,?,?,?);";

        String sqlUpdate = "update consumo " +
                "set Mfin=? " +
                "where id=?;";

        String sql = id != 0 ? sqlUpdate : sqlInsert;

        try {
            PreparedStatement statement = Database.getInstance().getConnection().
                    prepareStatement(sql);
            if (id != 0) {
                statement.setString(1, Mfin);
                statement.setInt(2, id);
            } else {
                statement.setString(1, Finicio);
                statement.setString(2, Ffin);
                statement.setString(3, Minicio);
                statement.setString(4, Mfin);
                statement.setString(5, estado);
                statement.setInt(6, cod_socio);
            }

            proccessed = Database.getInstance().executeSQL(statement);
        } catch (SQLException e) {
        }
        Map<String, String> end = EndInsert();
        if (id == 0) {
            id = Integer.parseInt(end.get("id"));
        }
        return proccessed ? find("id", id) : null;
    }

    public boolean delete(String id) {
        String sql = "delete from consumo where id=?;";
        return Database.getInstance().delete(sql, id);
    }

    public Map<String, String> find(String columnName, Object columnValue) {
        String sql = "select * from consumo where %s='%s' limit 1;";
        sql = String.format(sql, columnName, columnValue);

        List<Map<String, String>> resultado = Database.getInstance().
                executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }

    public List<Map<String, String>> findAll() {
        String sql = "select * from consumo order by 1;";
        return Database.getInstance().executeSQLResultList(sql);
    }

    public List<Map<String, String>> findCodSocio(String columnName,
            Object columnValue) {

        String sql = "select * from consumo where %s='%s' and estado='Impaga';";
        sql = String.format(sql, columnName, columnValue);

        List<Map<String, String>> data = new LinkedList<>();
        try (PreparedStatement consulta = Database.getInstance().getConnection().prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery();) {
            while (resultado.next()) {
                Map<String, String> map = new HashMap<>();
                for (int index = 0; index < 1; index++) {
                    
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("MMMM yyyy");
                    String periodo = resultado.getString(index + 3);
                    String fecha = sdf2.format(sdf1.parse(periodo));
                    int cantidad = resultado.getInt(index+5) - resultado.getInt(index+4);
                    
                    map.put("id", resultado.getString(index + 1));
                    //map.put("periodo", resultado.getString(index + 3));
                    map.put("periodo", fecha);
                    map.put("cantidad", String.valueOf(cantidad));
                    map.put("estado", resultado.getString(index + 6));
                    map.put("cod_socio", resultado.getString(index + 7));
                }
                data.add(map);
            }
        } catch (Exception e) {
            Database.getInstance().closeConnection();
            Database.getInstance().connect();
        }
        return data;
    }

    public Map<String, String> comboBox() {
        Map<String, String> socio = new LinkedHashMap<>();

        String sql = "select * from consumo order by 1;";
        List<Map<String, String>> rows = Database.getInstance().
                executeSQLResultList(sql);

        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);

            String fullname = row.get("estado") + "---" + row.get("cod_socio");
            socio.put(row.get("id"), fullname);
        }
        return socio;
    }

    public Map<String, String> finAnterior(String columnName, Object columnValue) {

        String sql = "select max(id) as codigo, Ffin, Mfin from consumo where %s='%s'";
        sql = String.format(sql, columnName, columnValue);
        List<Map<String, String>> resultado = Database.getInstance().
                executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }

    public Map<String, String> find1(String id) {
        String sql = "select s.codigo, c.id, concat_ws(' ', s.nombre, s.apellido) as nombre, c.Mfin from socio s, consumo c where s.codigo=c.cod_socio and c.id=%s";
        sql = String.format(sql, id);
        List<Map<String, String>> resultado = Database.getInstance().
                executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }

    public Map<String, String> EndInsert() {
        String sql = "select max(id)as id from consumo;";

        List<Map<String, String>> resultado = Database.getInstance().
                executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }
}
