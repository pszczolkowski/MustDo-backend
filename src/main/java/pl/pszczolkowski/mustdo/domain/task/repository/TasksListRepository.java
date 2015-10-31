package pl.pszczolkowski.mustdo.domain.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pszczolkowski.mustdo.domain.task.entity.TasksList;

public interface TasksListRepository
   extends JpaRepository<TasksList, Long> {

   TasksList findOneByName(String name);
   
   TasksList findOneByNameAndBoardId(String name, Long boardId);

   List<TasksList> findByBoardId(Long boardId);
   
   int removeByBoardId(Long boardId);
}
