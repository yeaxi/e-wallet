package ua.dudka.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.company.domain.Admin;
import ua.dudka.company.repository.AdminRepository;
import ua.dudka.company.service.AdminReader;
import ua.dudka.config.AdminConfig;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class AdminReaderImpl implements AdminReader {
    private final AdminRepository adminRepository;

    @Override
    public Admin read() {
        return adminRepository.findOne(AdminConfig.getAdminId());
    }
}