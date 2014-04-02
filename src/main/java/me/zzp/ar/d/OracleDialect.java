package me.zzp.ar.d;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import me.zzp.ar.ex.DBOpenException;
import me.zzp.ar.ex.UnsupportedDatabaseException;

public class OracleDialect implements Dialect {
  @Override
  public boolean accept(Connection c) {
    try {
      DatabaseMetaData d = c.getMetaData();
      String name = d.getDatabaseProductName(); // Oracle
      int version = d.getDatabaseMajorVersion();
      if (name.equalsIgnoreCase("oracle")) {
        if (version >= 12) {
          return true; // since 12.c
        } else {
          throw new UnsupportedDatabaseException(String.format("%s %d: Oracle database need 12c+", name, version));
        }
      } else {
        return false;
      }
    } catch (SQLException e) {
      throw new DBOpenException(e);
    }
  }

  @Override
  public String getIdentity() {
    return "number generated by default on null as identity";
  }
}