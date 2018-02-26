package com.yurikilian.securitydashboard.core.database;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import liquibase.change.custom.CustomSqlChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;

public class BCryptPasswordChange implements CustomSqlChange {

  private String table;
  private String column;
  private String value;
  private String whereColumn;
  private String whereValue;

  @SuppressWarnings("unused")
  private ResourceAccessor resourceAccessor;


  @Override
  public String getConfirmationMessage() {
    return "BCrypt hash generated for " + table + "." + column;
  }

  @Override
  public void setFileOpener(ResourceAccessor resourceAccessor) {
    this.resourceAccessor = resourceAccessor;
  }

  @Override
  public void setUp() throws SetupException {}

  @Override
  public ValidationErrors validate(Database database) {
    return new ValidationErrors();
  }

  @Override
  public SqlStatement[] generateStatements(Database database) throws CustomChangeException {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return new SqlStatement[] {
        new RawSqlStatement(String.format("UPDATE %s SET %s='%s' WHERE %s='%s'",
            database.escapeObjectName(null, null, table, Table.class),
            database.escapeObjectName(column, Column.class), encoder.encode(value),
            database.escapeObjectName(this.whereColumn, Column.class), this.whereValue))};
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getWhereColumn() {
    return whereColumn;
  }

  public void setWhereColumn(String whereColumn) {
    this.whereColumn = whereColumn;
  }

  public String getWhereValue() {
    return whereValue;
  }

  public void setWhereValue(String whereValue) {
    this.whereValue = whereValue;
  }

}
