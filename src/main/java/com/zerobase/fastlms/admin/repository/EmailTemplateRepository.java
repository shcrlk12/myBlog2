package com.zerobase.fastlms.admin.repository;

import com.zerobase.fastlms.admin.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, String> {
}
