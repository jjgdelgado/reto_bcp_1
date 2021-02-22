package com.bcp.project.reto.model.service.impl;

import com.bcp.project.reto.beans.Response;
import com.bcp.project.reto.beans.Simulator;
import com.bcp.project.reto.model.entity.User;
import com.bcp.project.reto.model.repository.UserRepository;
import com.bcp.project.reto.model.service.IOperationService;
import com.bcp.project.reto.util.Constants;
import com.bcp.project.reto.util.Error;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements IOperationService {

  private final UserRepository userRepository;

  public OperationServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Object getResponse(Simulator simulator) {

    Optional<User> optUser = userRepository.findById(simulator.getDni());

    if (!optUser.isPresent()) {
      return Error.builder().code("ERROR-01").message("No es DNI válido").build();
    }

    if (!(simulator.getCard().equals(Constants.BLACK_CARD) ||
        simulator.getCard().equals(Constants.GOLD_CARD) ||
        simulator.getCard().equals(Constants.CLASSIC_CARD))) {
      return Error.builder().code("ERROR-02").message("No es una tarjeta válida").build();
    }

    if (!(simulator.getQuota() >= Constants.MIN_QUOTA && simulator.getQuota() <= Constants.MAX_QUOTA)) {
      return Error.builder().code("ERROR-03").message("No es una cuota válida").build();
    }

    if (!(simulator.getPayDay() == Constants.MIN_PAY_DAYS ||
        simulator.getPayDay() == Constants.MAX_PAY_DAYS)) {
      return Error.builder().code("ERROR-04").message("No es un día de pago válido").build();
    }

    if (!(simulator.getTea().equals(Constants.TEA_90) || simulator.getTea().equals(Constants.TEA_95) ||
        simulator.getTea().equals(Constants.TEA_99))) {
      return Error.builder().code("ERROR-05").message("No es una TEA válida").build();
    }

    // Fórmula de tasa efectiva mensual
    /*
     * TEM = ((1+TEA)^n/360 – 1) x 100
     * */
    double TEA = Double.parseDouble(simulator.getTea().substring(0, 4)) / 100;
    double TEM = (Math.pow((1 + TEA), (simulator.getPayDay() * 30.0) / 360) - 1.0);

    // Fórmula de cálculo de cuota fija mensual
    /*
    * R = P i (1 + i)^n
          (1 + i)^n - 1
    * */
    double quotaMount = (simulator.getMount() / simulator.getPayDay()) * (1 + TEM);

    Response response =
        Response.builder().quota(quotaMount).currency(Constants.CURRENCY).status(Constants.SUCCESSFUL)
            .build();

    if (LocalDate.now().getDayOfMonth() <= 5 || LocalDate.now().getDayOfMonth() > 20) {
      response.setQuotaDate(String
          .format("%s/%s/%s", Constants.MIN_PAY_DAYS, LocalDate.now().getMonthValue() + 1,
              LocalDate.now().getYear()));
    } else if (LocalDate.now().getDayOfMonth() <= 20) {
      response.setQuotaDate(String
          .format("%s/%s/%s", Constants.MAX_PAY_DAYS, LocalDate.now().getMonthValue() + 1,
              LocalDate.now().getYear()));
    }

    return response;
  }
}
