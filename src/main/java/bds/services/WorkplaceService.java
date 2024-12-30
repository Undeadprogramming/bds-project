package bds.services;

import bds.api.*;
import bds.data.WorkplaceRepository;

import java.util.List;

/**
 * Class representing business logic on top of the Workplace data.
 */
public class WorkplaceService {

    private WorkplaceRepository workplaceRepository;

    public WorkplaceService(WorkplaceRepository workplaceRepository) {
        this.workplaceRepository = workplaceRepository;
    }

    /**
     * Retrieves the basic view of workplaces from the repository.
     *
     * @return List of WorkplaceBasicView objects
     */
    public List<WorkplaceBasicView> getWorkplaceBasicView() {
        return workplaceRepository.getWorkplaceBasicView();
    }

    /**
     * Creates a new workplace record using the provided WorkplaceCreateView object.
     *
     * @param workplaceCreateView the data for the new workplace
     */
    public void createWorkplace(WorkplaceCreateView workplaceCreateView) {
        // Business logic can be added here if needed
        workplaceRepository.createWorkplace(workplaceCreateView);
        System.out.println("Workplace item was created successfully at: " + workplaceCreateView.getBuildingAddress());
    }

    /**
     * Edits an existing workplace using the provided WorkplaceEditView object.
     *
     * @param workplaceEditView the updated data for the workplace
     */
    public void editWorkplace(WorkplaceEditView workplaceEditView) {
        workplaceRepository.editWorkplace(workplaceEditView);
    }

    /**
     * Deletes a workplace based on the provided selected workplace.
     *
     * @param selectedWorkplace the workplace to be deleted
     */
    public void deleteWorkplace(WorkplaceBasicView selectedWorkplace) {
        workplaceRepository.deleteWorkplace(selectedWorkplace.getIdWorkplace());
    }
}
