package com.romm.todopp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romm.todopp.entity.Link;
import com.romm.todopp.entity.Task;
import com.romm.todopp.entity.TaskList;
import com.romm.todopp.repository.LinkRepository;

@Service
public class LinkService {
    
    @Autowired LinkRepository linkRepository;
    @Autowired TaskListService taskListService;
    @Autowired TaskService taskService;

    public Link create(Task task, Long taskListId) {
        TaskList taskList = taskListService.read(taskListId);
        Link link = new Link();
        link.setTask(task);
        link.setTaskList(taskList);
        link.setTaskListPosition(taskList.getLinks().size());

        return linkRepository.save(link);
    }

    public Link delete(Link link) {
        Task task = link.getTask();
        linkRepository.delete(link);
        return link;
    }

    public void deleteAndCheckOrphan(Link link) {
        Task task = link.getTask();
        if (task.getLinks().size()==1) {
            taskService.delete(task.getId());
            
        }
        taskService.delete(task.getId());
    }

    public void positionUp(Link link) {
        int linkPosition = link.getTaskListPosition();
        if (linkPosition>0){
            Link linkAbove = link.getTaskList().getLinks().get(linkPosition-1);
            linkAbove.setTaskListPosition(linkPosition);
            link.setTaskListPosition(linkPosition-1);

            linkRepository.save(link);
            linkRepository.save(linkAbove);
        }
    }

    public void positionDown(Link link) {
        int linkPosition = link.getTaskListPosition();
        int taskListSize = link.getTaskList().getLinks().size();
        if (linkPosition<taskListSize-1) {
            Link linkBelow = link.getTaskList().getLinks().get(linkPosition+1);
            linkBelow.setTaskListPosition(linkPosition);
            link.setTaskListPosition(linkPosition+1);

            linkRepository.save(link);
            linkRepository.save(linkBelow);
        }
    }
}
