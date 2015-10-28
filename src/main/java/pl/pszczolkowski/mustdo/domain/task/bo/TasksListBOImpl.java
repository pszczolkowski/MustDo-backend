package pl.pszczolkowski.mustdo.domain.task.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.entity.TasksList;
import pl.pszczolkowski.mustdo.domain.task.exception.TasksListAlreadyExistException;
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.BussinesObject;

@BussinesObject
public class TasksListBOImpl
   implements TasksListBO {

   private static final Logger LOGGER = LoggerFactory.getLogger(TasksListBOImpl.class);
   private final TasksListRepository tasksListRepository;

   @Autowired
   public TasksListBOImpl(TasksListRepository tasksListRepository) {
      this.tasksListRepository = tasksListRepository;
   }

   @Override
   public TasksListSnapshot add(String name, Long boardId) {
      if (tasksListRepository.findOneByNameAndBoardId(name, boardId) != null) {
         throw new TasksListAlreadyExistException();
      }

      TasksList tasksList = new TasksList(name, boardId);
      tasksList = tasksListRepository.save(tasksList);

      TasksListSnapshot tasksListSnapshot = tasksList.toSnapshot();
      LOGGER.info("Tasks List with id <{}>, name <{}> and boardID <{}> added", tasksListSnapshot.getId(),
         tasksListSnapshot.getName(), tasksListSnapshot.getBoardId());

      return tasksListSnapshot;
   }

   @Override
   public TasksListSnapshot rename(Long id, String name) {
      TasksList tasksList = tasksListRepository.findOneByName(name);
      if (tasksList != null) {
         TasksListSnapshot tasksListSnapshot = tasksList.toSnapshot();
         if (tasksListSnapshot.getId().equals(id)) {
            return tasksListSnapshot;
         } else {
            throw new TasksListAlreadyExistException();
         }
      }

      tasksList = tasksListRepository.findOne(id);

      tasksList.rename(name);
      tasksList = tasksListRepository.save(tasksList);

      TasksListSnapshot tasksListSnapshot = tasksList.toSnapshot();
      LOGGER.info("Tasks List with id <{}> renamed to <{}>", tasksListSnapshot.getId(), tasksListSnapshot.getName());

      return tasksListSnapshot;
   }

   @Override
   public void delete(Long id) {
      tasksListRepository.delete(id);
      LOGGER.info("Tasks List with id <{}> deleted", id);
   }

   @Override
   public int removeTasksListsWithBoardId(Long boardId) {
      int removedTasksListCount = tasksListRepository.removeByBoardId(boardId);
      LOGGER.info("<{}> tasks lists removed", removedTasksListCount);
      return removedTasksListCount;
   }

}
