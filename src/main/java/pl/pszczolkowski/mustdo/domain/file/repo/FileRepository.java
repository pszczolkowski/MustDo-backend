package pl.pszczolkowski.mustdo.domain.file.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pszczolkowski.mustdo.domain.file.entity.File;

public interface FileRepository extends JpaRepository<File, Long>{
	
	List<File> findAllByTaskId(Long taskId);
}
