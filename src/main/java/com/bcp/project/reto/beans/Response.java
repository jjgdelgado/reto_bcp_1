package com.bcp.project.reto.beans;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class Response implements Serializable {
  private double quota;
  private String currency;
  private String quotaDate;
  private String status;
}
