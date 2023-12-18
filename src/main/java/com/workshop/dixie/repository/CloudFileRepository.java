package com.workshop.dixie.repository;

import com.workshop.dixie.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CloudFileRepository extends JpaRepository<File, Long> {

    @Query(value = "insert into cloud_schema.files (file_name, user_id) values (:file_name, :user_id) returning 'File uploaded!'", nativeQuery = true)
    Optional<String> uploadFile(@Param("file_name") String fileName, @Param("user_id") long userId);

    @Query(value = "delete from cloud_schema.files where file_name = :file_name returning 'File deleted!'", nativeQuery = true)
    Optional<String> deleteFile(@Param("file_name") String fileName);

    @Query(value = "select * from cloud_schema.files where file_name = :file_name", nativeQuery = true)
    Optional<File> downloadFile(@Param("file_name") String filename);

    @Query(value = "update cloud_schema.files set file_name = :new_file_name where file_name = :old_file_name returning 'Filename edited!'", nativeQuery = true)
    Optional<String> editFileName(@Param("old_file_name") String oldFileName, @Param("new_file_name") String newFileName);

    @Query(value = "select * from cloud_schema.files order by user_id limit :limit", nativeQuery = true)
    List<File> getAllFiles(@Param("limit") int limit);
}
