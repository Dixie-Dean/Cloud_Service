package com.workshop.dixie.repository;

import com.workshop.dixie.entity.CloudUser;
import com.workshop.dixie.entity.UserFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CloudRepository extends PagingAndSortingRepository<CloudUser, Long> {

    @Query(value = "insert into cloud_schema.files (file_name, cloud_user_id) values ('stub_file', 0)", nativeQuery = true)
    Optional<String> uploadFile(@Param("UserFile") UserFile userFile);

    @Query(value = "delete from cloud_schema.files where file_name = :file_name", nativeQuery = true)
    Optional<String> deleteFile(@Param("file_name") String fileName);

    @Query(value = "select * from cloud_schema.files where file_name = :file_name", nativeQuery = true)
    Optional<UserFile> downloadFile(@Param("file_name") String fileName);

    @Query(value = "update cloud_schema.files set file_name = :new_file_name where file_name = :old_file_name", nativeQuery = true)
    Optional<String> editFileName(@Param("old_file_name") String oldFileName, @Param("new_file_name") String newFileName);

    @Query(value = "select * from cloud_schema.files order by cloud_user_id limit :limit", nativeQuery = true)
    List<UserFile> getAllFiles(@Param("limit") int limit);
}
