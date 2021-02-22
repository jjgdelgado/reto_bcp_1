package com.bcp.project.reto.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class Error {
  private String code;
  private String message;
}
