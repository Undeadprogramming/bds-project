package bds.services;

import bds.api.*;
import bds.data.WorkerRepository;

import java.util.List;

public class WorkerService {

    private WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public List<WorkerBasicView> getWorkerBasicView() {
        return workerRepository.getWorkersBasicView();
    }

    public void createWorker(WorkerCreateView workerCreateView) {

        workerRepository.createWorker(workerCreateView);
        System.out.println("Worker was created successfully: " + workerCreateView.getFirstName() + " " + workerCreateView.getLastName());
    }

    public void editWorker(WorkerEditView workerEditView) {
        workerRepository.editWorker(workerEditView);
        System.out.println("Worker was edited successfully: " + workerEditView.getFirstName() + " " + workerEditView.getLastName());
    }

    public void deleteWorker(WorkerBasicView selectedWorker) {
        workerRepository.deleteWorker(selectedWorker.getIdWorker());
        System.out.println("Worker with ID " + selectedWorker.getIdWorker() + " was deleted successfully.");
    }



}
