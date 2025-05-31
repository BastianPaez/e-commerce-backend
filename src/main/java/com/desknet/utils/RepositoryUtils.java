package com.desknet.utils;

import com.desknet.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class RepositoryUtils {
    public static <T, ID> T findOrThrow(JpaRepository<T, ID> repository, ID id, String name){

        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(name + " id not found: " + id));

    }
}
