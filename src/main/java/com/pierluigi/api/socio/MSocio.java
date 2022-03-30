package com.pierluigi.api.socio;

import com.pierluigi.utils.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MSocio {

    private int codigo;
    private String nombre;
    private String apellido;
    private String direccion;

    public MSocio() {
        this(0, "", "", "");
    }

    public MSocio(int codigo, String nombre, String apellido, String direccion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
    }

    public void setData(Map<String, String> request) {
        codigo = Integer.parseInt(request.getOrDefault("codigo", "0"));
        nombre = request.getOrDefault("nombre", "");
        apellido = request.getOrDefault("apellido", "");
        direccion = request.getOrDefault("direccion", "");
    }

    public Map<String, String> save() {
        boolean proccessed = false;
        String sqlInsert = "insert into socio (nombre, apellido, direccion) " +
                 "values (?,?,?);";

        String sqlUpdate = "update socio " +
                 "set nombre=?, apellido=? " +
                 "where codigo=?;";

        String sql = codigo != 0 ? sqlUpdate : sqlInsert;

        try {
            PreparedStatement statement = Database.getInstance().getConnection().
                    prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, direccion);

            if (codigo != 0) {
                statement.setInt(3, codigo);
            }

            proccessed = Database.getInstance().executeSQL(statement);
        } catch (SQLException e) {
        }
        Map<String, String> end = EndInsert();
        if (codigo == 0) {
            codigo = Integer.parseInt(end.get("codigo"));
        }
        return proccessed ? find("codigo", codigo) : null;
    }

    public boolean delete(String codigo) {
        String sql = "delete from socio where codigo=?;";
        return Database.getInstance().delete(sql, codigo);
    }

    public Map<String, String> find(String columnName, Object columnValue) {
        String sql = "select * from socio where %s='%s' limit 1;";
        sql = String.format(sql, columnName, columnValue);

        List<Map<String, String>> resultado = Database.getInstance().
                executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }

    public List<Map<String, String>> findAll() {
        String sql = "select * from socio order by 1;";
        return Database.getInstance().executeSQLResultList(sql);
    }

    public Map<String, String> comboBox() {
        Map<String, String> socio = new LinkedHashMap<>();

        String sql = "select * from socio order by 1;";
        List<Map<String, String>> rows = Database.getInstance().
                executeSQLResultList(sql);

        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);

            String fullname = row.get("nombre") + " " + row.get("apellido");
            socio.put(row.get("codigo"), fullname);
        }
        return !socio.isEmpty() ? socio : null;
    }

    public Map<String, String> EndInsert() {
        String sql = "select max(codigo)as codigo from socio;";

        List<Map<String, String>> resultado = Database.getInstance().
                executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }
    
    public List<Map<String, String>> apiCombo() {
        String sql = "select * from socio order by 1;";
        return Database.getInstance().executeSQLResultListCombo(sql);
    }
}
