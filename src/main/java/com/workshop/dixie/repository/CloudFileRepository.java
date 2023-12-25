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

    @Query(value = "insert into cloud_schema.files (hash, filename, username, file) " +
            "values (:hash, :filename, :username, :file) returning 'File uploaded!'", nativeQuery = true)
    Optional<String> uploadFile(@Param("hash") String hash,
                                @Param("filename") String filename,
                                @Param("username") String username,
                                @Param("file") String file);

    @Query(value = "delete from cloud_schema.files where filename = :filename " +
            "returning 'File deleted!'", nativeQuery = true)
    Optional<String> deleteFile(@Param("filename") String filename);

    @Query(value = "select * from cloud_schema.files where filename = :filename", nativeQuery = true)
    Optional<File> downloadFile(@Param("filename") String filename);

    @Query(value = "update cloud_schema.files set filename = :new_file_name " +
            "where filename = :old_file_name returning 'Filename edited!'", nativeQuery = true)
    Optional<String> editFileName(@Param("old_file_name") String oldFileName,
                                  @Param("new_file_name") String newFileName);

    @Query(value = "select * from cloud_schema.files where username = :username limit :limit", nativeQuery = true)
    List<File> getAllFiles(@Param("limit") int limit,
                           @Param("username") String username);
}
