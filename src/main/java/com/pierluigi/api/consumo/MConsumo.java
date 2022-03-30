package com.pierluigi.api.consumo;

import com.pierluigi.utils.Database;
import com.pierluigi.utils.IModelo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MConsumo implements IModelo{
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

    @Override
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

    @Override
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

    @Override
    public boolean delete(String id) {
        String sql = "delete from consumo where id=?;";
        return Database.getInstance().delete(sql, id);
    }

    @Override
    public Map<String, String> find(String columnName, Object columnValue) {
        String sql = "select * from consumo where %s='%s' limit 1;";
        sql = String.format(sql, columnName, columnValue);

        List<Map<String, String>> resultado = Database.getInstance().
                executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }

    @Override
    public List<Map<String, String>> findAll() {
        String sql = "select * from consumo order by 1;";
        return Database.getInstance().executeSQLResultList(sql);
    }

    @Override
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
