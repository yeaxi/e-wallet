package ua.dudka.hrm.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.hrm.application.config.CompanyConfig;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.repository.CompanyRepository;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class CurrentCompanyReaderImpl implements CurrentCompanyReader {
    private final CompanyRepository companyRepository;

    @Override
    public Company read() {
        return companyRepository.findOne(CompanyConfig.getAdminId());
    }
}