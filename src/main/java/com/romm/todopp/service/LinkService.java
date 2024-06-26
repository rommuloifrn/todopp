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
import com.romm.todopp.exceptions.AlreadyLinkedException;
import com.romm.todopp.repository.LinkRepository;

@Service
public class LinkService {
    
    @Autowired LinkRepository linkRepository;

    public Link create(Task task, TaskList taskList) {
        if (alreadyExists(taskList.getId(), task.getId())) throw new AlreadyLinkedException("A link between those already exists.", null);

        Link link = new Link();
        link.setTask(task);
        link.setTaskList(taskList);

        int listSize = taskList.getLinks().size();
        int position = 0;
        if (listSize > 0)
            position = getFromTaskList(taskList).get( listSize-1 ).getTaskListPosition()+1;
        link.setTaskListPosition(position);

        return linkRepository.save(link);
    }

    public Link read(Long id) {
        return findOr404(id);
    }

    public Link delete(Link link) {
        linkRepository.delete(link);
        return link;
    }

    public List<Link> getFromTaskList(TaskList taskList) {
        return linkRepository.findAllByTaskListOrderByTaskListPosition(taskList);
    }

    public void positionUp(Link link) throws ResponseStatusException {
        int linkPosition = link.getTaskListPosition();

        Link firstOfTheTaskList = getFromTaskList(link.getTaskList()).get(0);
        boolean linkPassedIsFirst = (link == firstOfTheTaskList);
        if (!linkPassedIsFirst){
            Link linkAbove = findOr404(link.getTaskList(), linkPosition-1);
            linkAbove.setTaskListPosition(linkPosition);
            link.setTaskListPosition(linkPosition-1);

            linkRepository.save(link);
            linkRepository.save(linkAbove);
        }
    }

    public void positionDown(Link link) throws ResponseStatusException {
        int linkPosition = link.getTaskListPosition();

        boolean linkPassedIsLast = linkRepository.findByTaskListAndTaskListPosition(link.getTaskList(), linkPosition+1).isEmpty();
        if (!linkPassedIsLast) {
            Link linkBelow = findOr404(link.getTaskList(), linkPosition+1);
            linkBelow.setTaskListPosition(linkPosition);
            link.setTaskListPosition(linkPosition+1);

            linkRepository.save(link);
            linkRepository.save(linkBelow);
        }
    }

    public boolean alreadyExists(Long taskListId, Long taskId) {
        return !linkRepository.findByTaskListIdAndTaskId(taskListId, taskId).isEmpty();
    }

    public Link findOr404(Long id) {
        Optional <Link> opt = linkRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return opt.get();
    }

    public Link findOr404(Long taskListId, Long taskId) {
        Optional <Link> opt = linkRepository.findByTaskListIdAndTaskId(taskListId, taskId);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return opt.get();
    }

    public Link findOr404(TaskList taskList, Task task) {
        Optional <Link> opt = linkRepository.findByTaskListAndTask(taskList, task);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return opt.get();
    }

    public Link findOr404(TaskList taskList, int taskListPosition) {
        Optional <Link> opt = linkRepository.findByTaskListAndTaskListPosition(taskList, taskListPosition);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return opt.get();
    }
}
