package com.pierluigi.factura;

import com.pierluigi.utils.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MDetalle_Factura{

  private int id;
  private int cantidad;
  private double importe;
  private String periodo;
  private int id_factura;
  private int id_consumo;

  public MDetalle_Factura() {
    this(0, 0, 0, "", 0, 0);
  }

  public MDetalle_Factura(int id, int cantidad, double importe, String periodo, int id_factura, int id_consumo) {
    this.id = id;
    this.cantidad = cantidad;
    this.importe = importe;
    this.periodo = periodo;
    this.id_factura = id_factura;
    this.id_consumo = id_consumo;
  }

  public void setData(Map<String, String> request) {
    id = Integer.parseInt(request.getOrDefault("id", "0"));
    cantidad = Integer.parseInt(request.getOrDefault("cantidad", ""));
    importe = Double.parseDouble(request.getOrDefault("importe", "0.0"));
    periodo = request.getOrDefault("periodo", "");
    id_factura = Integer.parseInt(request.getOrDefault("id_factura", "0"));
    id_consumo = Integer.parseInt(request.getOrDefault("id_consumo", "0"));
  }

  public void setIdFactura(String idFactura) {
    this.id_factura = Integer.parseInt(idFactura);
  }

  public Map<String, String> save() {
    boolean proccessed = false;
    String sqlInsert = "insert into detalle_factura (cantidad, importe, periodo, periodo, id_factura, id_consumo) "
            + "values (?,?,?,?,?,?);";

    String sqlUpdate = "update detalle_factura "
            + "set cantidad=?, importe=?, periodo=?, id_factura=?, id_consumo=? "
            + "where id=?;";

    String sql = id != 0 ? sqlUpdate : sqlInsert;

    try {
      PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
      statement.setInt(1, cantidad);
      statement.setDouble(2, importe);
      statement.setString(3, periodo);
      statement.setInt(4, id_factura);
      statement.setInt(5, id_consumo);

      if (id != 0) {
        statement.setInt(6, id);
      }

      proccessed = Database.getInstance().executeSQL(statement);
    } catch (SQLException e) {
    }

    return proccessed ? find("id", id, "id_factura", id_factura) : null;
  }

  public static boolean delete(String idFactura, String id) {
    String sql = "delete from detalle_factura where id_factura=? and id=?;";
    return Database.getInstance().delete(sql, idFactura, id);
  }

  public static Map<String, String> find(String columnName0, Object value0, String columnName1, Object value1) {
    String sql = "select * from detalle_factura where %s='%s' and %s='%s' limit 1;";
    sql = String.format(sql, columnName0, value0, columnName1, value1);

    List<Map<String, String>> resultado = Database.getInstance().executeSQLResultList(sql);
    return !resultado.isEmpty() ? resultado.get(0) : null;
  }

  public static List<Map<String, String>> findAll(String carga_horaria_id) {
    String sql = "select * from detalle_factura where id_factura='%s';";
    sql = String.format(sql, carga_horaria_id);
    return Database.getInstance().executeSQLResultList(sql);
  }

}
