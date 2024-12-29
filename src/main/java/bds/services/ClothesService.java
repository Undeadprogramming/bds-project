package bds.services;

import bds.api.*;
import bds.data.ClothesRepository;

import java.util.List;

/**
 * Class representing business logic on top of the Clothes data.
 */
public class ClothesService {

    private ClothesRepository clothesRepository;

    public ClothesService(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }


    public List<ClothesBasicView> getClothesBasicView() {
        return clothesRepository.getClothesBasicView();
    }

    public void createClothes(ClothesCreateView clothesCreateView) {
        // The business logic for creating a new clothes item can go here
        clothesRepository.createClothes(clothesCreateView);
        System.out.println("Clothes item was created successfully: " + clothesCreateView.getClothesName());
    }

    public void editClothes(ClothesEditView clothesEditView) {
        clothesRepository.editClothes(clothesEditView);
    }
    public void deleteClothes(ClothesBasicView selectedClothes) {
        clothesRepository.deleteClothes(selectedClothes.getIdClothes());
    }
}
