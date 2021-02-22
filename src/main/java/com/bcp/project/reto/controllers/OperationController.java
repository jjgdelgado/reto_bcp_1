package com.bcp.project.reto.controllers;

import com.bcp.project.reto.beans.Simulator;
import com.bcp.project.reto.model.service.IOperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OperationController {
  private final IOperationService operationService;

  public OperationController(IOperationService operationService) {
    this.operationService = operationService;
  }

  @GetMapping
  public String index() {
    return "API Rest BCP";
  }

  @GetMapping("/operation")
  public Object exchange(@RequestBody Simulator simulator) {
    return operationService.getResponse(simulator);
  }
}
