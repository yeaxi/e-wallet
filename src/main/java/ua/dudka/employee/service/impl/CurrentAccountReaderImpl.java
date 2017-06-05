package ua.dudka.employee.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.dudka.config.EmployeeConfig;
import ua.dudka.employee.domain.Account;
import ua.dudka.employee.repository.EmployeeRepository;
import ua.dudka.employee.service.CurrentAccountReader;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class CurrentAccountReaderImpl implements CurrentAccountReader {

    private final EmployeeRepository employeeRepository;

    @Override
    public Account read() {
        String email;
        try {
            email = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            //possible only if no-security profile
            email = EmployeeConfig.DEV_EMPLOYEE_USERNAME;
        }
        return employeeRepository.findByEmail(email).get().getAccount();
    }
}