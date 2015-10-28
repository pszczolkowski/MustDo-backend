package pl.pszczolkowski.mustdo.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import pl.pszczolkowski.mustdo.domain.board.event.BoardRemovedEvent;
import pl.pszczolkowski.mustdo.domain.task.bo.TasksListBO;

@Component
public class BoardRemovedListener
   implements ApplicationListener<BoardRemovedEvent> {

   private final TasksListBO tasksListBO;

   @Autowired
   public BoardRemovedListener(TasksListBO tasksListBO) {
      this.tasksListBO = tasksListBO;
   }

   @Override
   public void onApplicationEvent(BoardRemovedEvent boardRemovedEvent) {
      tasksListBO.removeTasksListsWithBoardId(boardRemovedEvent.getBoardId());
   }

}
