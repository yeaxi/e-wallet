package ua.dudka.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.application.config.CompanyConfig;
import ua.dudka.domain.model.Company;
import ua.dudka.repository.CompanyRepository;

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