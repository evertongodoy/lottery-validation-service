package com.lottery.validation.infrastructure.adapters.output.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.lottery.validation.domain.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    private String id;
    
    private UUID uuid;
    
    private String name;
    
    private UserRole role;
    
    @Indexed(unique = true)
    private String subject;
    
    @Indexed
    private String cellphone;
    
    private LocalDateTime createdAt;

}
