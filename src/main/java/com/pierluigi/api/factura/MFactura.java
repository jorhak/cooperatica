package com.pierluigi.factura;

import com.pierluigi.utils.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MFactura{
    
  private int id;
  private String fecha;
  private Double importe;
  private List<MDetalle_Factura> detalle_facturas;

  public MFactura() {
    this(0, "", 0.0);
  }

  public MFactura(int id, String fecha, Double importe) {
    this.id = id;
    this.fecha = fecha;
    this.importe = importe;
    this.detalle_facturas = new LinkedList<>();
  }

  public void setData(Map<String, String> request) {
    id = Integer.parseInt(request.getOrDefault("id", "0"));
    fecha = request.getOrDefault("fecha", "");
    importe = Double.parseDouble(request.getOrDefault("importe", ""));
  }

  public void setDataItem(Map<String, String> request) {
    detalle_facturas.clear();
    MDetalle_Factura detalle_factura = new MDetalle_Factura();
    detalle_factura.setData(request);
    detalle_facturas.add(detalle_factura);
  }

  public void setDataItems(List<Map<String, String>> request) {
    detalle_facturas.clear();
    for (int i = 0; i < request.size(); i++) {
      MDetalle_Factura detalle_factura = new MDetalle_Factura();
      detalle_factura.setData(request.get(i));
      detalle_facturas.add(detalle_factura);
    }
  }

  public Map<String, String> save() {
    boolean proccessed = false;
    String sqlInsert = "insert into factura (fecha, importe) "
            + "values (?,?);";

    String sqlUpdate = "update factura "
            + "set fecha=?, importe=? "
            + "where id=?;";

    String sql = id != 0 ? sqlUpdate : sqlInsert;

    try {
      PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
      statement.setString(1, fecha);
      statement.setDouble(2, importe);

      if (id != 0) {
        statement.setInt(3, id);
      }

      proccessed = Database.getInstance().executeSQL(statement);
    } catch (SQLException e) {
    }

    return proccessed ? find("id", id) : null;
  }

  public Map<String, String> saveItem() {
    if (detalle_facturas.size() == 1) {
      MDetalle_Factura detalle_factura = detalle_facturas.get(0);
      return detalle_factura.save();
    }

    return null;
  }

  public List<Map<String, String>> saveItems(String idFactura) {
    List<Map<String, String>> items = new LinkedList<>();

    for (int i = 0; i < detalle_facturas.size(); i++) {
      MDetalle_Factura detalle_factura = detalle_facturas.get(i);
      detalle_factura.setIdFactura(idFactura);
      items.add(detalle_factura.save());
    }

    return items;
  }

  public boolean delete(String idFactura) {
    String sql = "delete from factura where id=?;";
    return Database.getInstance().delete(sql, idFactura);
  }

  public boolean deleteItem(String idFactura, String idDetalleFactura) {
    return MDetalle_Factura.delete(idFactura, idDetalleFactura);
  }

  public boolean deleteItems(String idFactura) {
    List<Map<String, String>> detalle_facturas = MDetalle_Factura.findAll(idFactura);

    for (int i = 0; i < detalle_facturas.size(); i++) {
      String idDetalleFactura = detalle_facturas.get(i).get("id");
      if (!MDetalle_Factura.delete(idFactura, idDetalleFactura)) {
        return false;
      }
    }
    return !detalle_facturas.isEmpty() ? true : false;
  }

  public Map<String, String> find(String columnName, Object columnValue) {
    String sql = "select * from factura where %s='%s' limit 1;";
    sql = String.format(sql, columnName, columnValue);

    List<Map<String, String>> resultado = Database.getInstance().executeSQLResultList(sql);
    return !resultado.isEmpty() ? resultado.get(0) : null;
  }

  public Map<String, String> findItem(String idFactura, String idDetalleFactura) {
    return MDetalle_Factura.find("id", idDetalleFactura, "id_factura", idFactura);
  }

  public List<Map<String, String>> findItems(String idFactura) {
    return MDetalle_Factura.findAll(idFactura);
  }

  public List<Map<String, String>> findAll() {
    String sql = "select * from factura order by 1;";
    return Database.getInstance().executeSQLResultList(sql);
  }

  public Map<String, String> comboBox() {
    Map<String, String> materia = new LinkedHashMap<>();

    String sql = "select * from factura order by 1;";
    List<Map<String, String>> rows = Database.getInstance().executeSQLResultList(sql);

    for (int i = 0; i < rows.size(); i++) {
      Map<String, String> row = rows.get(i);

      String fullname = row.get("fecha") + " " + row.get("importe");
      materia.put(row.get("id"), fullname);
    }
    return materia;
  }

}
