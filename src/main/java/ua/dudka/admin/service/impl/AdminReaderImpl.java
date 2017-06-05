package ua.dudka.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.admin.domain.Admin;
import ua.dudka.admin.repository.AdminRepository;
import ua.dudka.admin.service.AdminReader;
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