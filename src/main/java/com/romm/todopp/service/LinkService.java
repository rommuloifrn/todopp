package com.romm.todopp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Link read(Long id) {
        return findOr404(id);
    }

    public Link delete(Link link) {
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

    public List<Link> getFromTaskList(TaskList taskList) {
        return linkRepository.findAllByTaskListOrderByTaskListPosition(taskList);
    }

    public void positionUp(Link link) {
        int linkPosition = link.getTaskListPosition();
        if (linkPosition>0){
            Link linkAbove = getFromTaskList(link.getTaskList()).get(linkPosition-1);
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
            Link linkBelow = getFromTaskList(link.getTaskList()).get(linkPosition+1);
            linkBelow.setTaskListPosition(linkPosition);
            link.setTaskListPosition(linkPosition+1);

            linkRepository.save(link);
            linkRepository.save(linkBelow);
        }
    }

    public Link findOr404(Long id) {
        Optional <Link> opt = linkRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return opt.get();
    }
}
