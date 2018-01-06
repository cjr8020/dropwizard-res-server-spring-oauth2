package com.dw.demo.dwhelloworld.exception;

/**
 * TODO: class comment
 */
public class OperationError {
  private String entityName;
  private String field;
  private Object value;
  private String error;

  public OperationError(
      final String entityName,
      final String field,
      final Object value,
      final String error) {
    this.entityName = entityName;
    this.field = field;
    this.value = value;
    this.error = error;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}